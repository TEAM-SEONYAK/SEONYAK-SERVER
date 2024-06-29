package org.sopt.seonyakServer.global.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDto<T>(
        String code,
        T data,
        String message
) {
    public static <T> ResponseDto<T> success(final T data) {
        return new ResponseDto<>("success", data, null);
    }

    public static <T> ResponseDto<T> fail(ErrorType errorType) {
        return new ResponseDto<>(errorType.getCode(), null, errorType.getMessage());
    }
}
