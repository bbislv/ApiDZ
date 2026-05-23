package com.example.ApiDZ.controller;

import com.example.ApiDZ.service.CacheManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
public class ManagementController {

    private final CacheManagementService cacheManagementService;
    private final Optional<BuildProperties> buildProperties;

    @Value("${management.service.name:${spring.application.name}}")
    private String serviceName;

    @PostMapping("/clear-caches")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCaches() {
        cacheManagementService.clearAllCaches();
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> info() {
        String version = buildProperties.map(BuildProperties::getVersion).orElse("unknown");
        String name = buildProperties.map(BuildProperties::getName).orElse(serviceName);
        return ResponseEntity.ok(Map.of(
                "name", name,
                "version", version
        ));
    }
}
