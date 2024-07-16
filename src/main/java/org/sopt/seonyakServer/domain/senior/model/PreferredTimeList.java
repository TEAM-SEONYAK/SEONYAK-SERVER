package org.sopt.seonyakServer.domain.senior.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferredTimeList {
    private Map<String, List<TimeRange>> preferredTimeList = new HashMap<>();

    @JsonAnySetter
    public void setPreferredTime(String day, List<TimeRange> times) {
        preferredTimeList.put(day, times);
    }

}
