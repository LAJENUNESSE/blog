package com.example.blog.dto.response;

import com.example.blog.entity.Setting;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingDTO {

    private Long id;
    private String key;
    private String value;
    private String description;

    public static SettingDTO fromEntity(Setting setting) {
        return SettingDTO.builder()
                .id(setting.getId())
                .key(setting.getKey())
                .value(setting.getValue())
                .description(setting.getDescription())
                .build();
    }
}
