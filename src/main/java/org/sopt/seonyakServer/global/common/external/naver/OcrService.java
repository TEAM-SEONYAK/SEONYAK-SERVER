package org.sopt.seonyakServer.global.common.external.naver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.sopt.seonyakServer.global.common.external.naver.dto.OcrUnivResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class OcrService {
    private final OcrConfig ocrConfig;

    public OcrUnivResponse ocrText(MultipartFile file) throws IOException {
        // OCR 설정파일로부터 URL, Secret Key 가져옴
        String apiUrl = ocrConfig.getApiUrl();
        String apiKey = ocrConfig.getApiKey();

        // 네이버 OCR API 요청 헤더 설정
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setReadTimeout(30000);
        con.setRequestMethod("POST");
        String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        con.setRequestProperty("X-OCR-SECRET", apiKey);

        // 네이버 OCR API 요청 바디 설정
        JSONObject json = new JSONObject();
        json.put("version", "V2");
        json.put("requestId", UUID.randomUUID().toString());
        json.put("timestamp", System.currentTimeMillis());
        JSONObject image = new JSONObject();
        image.put("format", "jpg");
        image.put("name", "demo");
        JSONArray images = new JSONArray();
        images.put(image);
        json.put("images", images);
        String postParams = json.toString();

        // 네이버 OCR API 요청 날림
        con.connect();
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            writeMultiPart(wr, postParams, file, boundary);
            wr.flush();
        }

        int responseCode = con.getResponseCode();
        BufferedReader br;
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        return OcrUnivResponse.of(extractUnivText(response.toString()));
    }


    // 네이버 공식문서 대로 OCR 요청 보내는 함수
    private static void writeMultiPart(OutputStream out, String jsonMessage, MultipartFile file, String boundary)
            throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (!file.isEmpty()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString.append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getOriginalFilename() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (InputStream fis = file.getInputStream()) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }

    // OCR 응답 JSON에서 "대학교"가 포함된 inferText만 추출하는 함수
    private String extractUnivText(String jsonResponse) {
        JSONObject responseJson = new JSONObject(jsonResponse);
        JSONArray images = responseJson.getJSONArray("images");

        Pattern pattern = Pattern.compile(".*?대학교");

        return IntStream.range(0, images.length())
                .mapToObj(images::getJSONObject)
                .flatMap(image -> {
                    JSONArray fields = image.getJSONArray("fields");
                    return IntStream.range(0, fields.length())
                            .mapToObj(fields::getJSONObject);
                })
                .map(field -> field.getString("inferText"))
                .filter(inferText -> pattern.matcher(inferText).find())
                .map(inferText -> {
                    Matcher matcher = pattern.matcher(inferText);
                    if (matcher.find()) {
                        return matcher.group();
                    }
                    return inferText;
                })
                .collect(Collectors.joining(","));
    }
}