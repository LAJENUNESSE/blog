package com.example.blog.controller.admin;

import com.example.blog.common.Result;
import com.example.blog.dto.request.SettingRequest;
import com.example.blog.dto.response.SettingDTO;
import com.example.blog.service.SettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/settings")
@RequiredArgsConstructor
public class AdminSettingController {

    private final SettingService settingService;

    @GetMapping
    public Result<List<SettingDTO>> getAllSettings() {
        return Result.success(settingService.getAllSettings());
    }

    @GetMapping("/{key}")
    public Result<SettingDTO> getSettingByKey(@PathVariable String key) {
        return Result.success(settingService.getSettingByKey(key));
    }

    @PostMapping
    public Result<SettingDTO> createOrUpdateSetting(@Valid @RequestBody SettingRequest request) {
        return Result.success(settingService.createOrUpdateSetting(request));
    }

    @PutMapping("/batch")
    public Result<Void> updateSettings(@RequestBody Map<String, String> settings) {
        settingService.updateSettings(settings);
        return Result.success();
    }

    @DeleteMapping("/{key}")
    public Result<Void> deleteSetting(@PathVariable String key) {
        settingService.deleteSetting(key);
        return Result.success();
    }
}
