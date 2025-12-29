package com.example.blog.service;

import com.example.blog.dto.request.SettingRequest;
import com.example.blog.dto.response.SettingDTO;
import com.example.blog.entity.Setting;
import com.example.blog.exception.BusinessException;
import com.example.blog.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;

    public List<SettingDTO> getAllSettings() {
        return settingRepository.findAll()
                .stream()
                .map(SettingDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Map<String, String> getAllSettingsAsMap() {
        return settingRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Setting::getKey, s -> s.getValue() != null ? s.getValue() : ""));
    }

    public SettingDTO getSettingByKey(String key) {
        Setting setting = settingRepository.findByKey(key)
                .orElseThrow(() -> BusinessException.notFound("Setting not found"));
        return SettingDTO.fromEntity(setting);
    }

    public String getSettingValue(String key) {
        return settingRepository.findByKey(key)
                .map(Setting::getValue)
                .orElse(null);
    }

    @Transactional
    public SettingDTO createOrUpdateSetting(SettingRequest request) {
        Setting setting = settingRepository.findByKey(request.getKey())
                .orElse(new Setting());

        setting.setKey(request.getKey());
        setting.setValue(request.getValue());
        setting.setDescription(request.getDescription());

        settingRepository.save(setting);
        return SettingDTO.fromEntity(setting);
    }

    @Transactional
    public void updateSettings(Map<String, String> settings) {
        settings.forEach((key, value) -> {
            Setting setting = settingRepository.findByKey(key)
                    .orElse(new Setting());
            setting.setKey(key);
            setting.setValue(value);
            settingRepository.save(setting);
        });
    }

    @Transactional
    public void deleteSetting(String key) {
        Setting setting = settingRepository.findByKey(key)
                .orElseThrow(() -> BusinessException.notFound("Setting not found"));
        settingRepository.delete(setting);
    }
}
