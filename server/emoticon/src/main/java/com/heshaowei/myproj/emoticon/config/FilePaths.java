package com.heshaowei.myproj.emoticon.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "file")
@Data
public class FilePaths {

    private Map<String, String> savePath;
}
