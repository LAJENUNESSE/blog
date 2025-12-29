package com.example.blog.controller;

import com.example.blog.common.Result;
import com.example.blog.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @GetMapping
    public Result<Map<String, String>> getAllSettings() {
        return Result.success(settingService.getAllSettingsAsMap());
    }

    @GetMapping("/{key}")
    public Result<String> getSettingByKey(@PathVariable String key) {
        return Result.success(settingService.getSettingValue(key));
    }
}
