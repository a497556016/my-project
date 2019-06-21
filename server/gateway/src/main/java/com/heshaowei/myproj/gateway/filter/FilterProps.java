package com.heshaowei.myproj.gateway.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("filter")
@RefreshScope
public class FilterProps {
    private AuthProp auth = new AuthProp();

    public AuthProp getAuth() {
        return auth;
    }

    public void setAuth(AuthProp auth) {
        this.auth = auth;
    }

    public class AuthProp {
        private List<String> ignoreUrls;

        public List<String> getIgnoreUrls() {
            return ignoreUrls;
        }

        public void setIgnoreUrls(List<String> ignoreUrls) {
            this.ignoreUrls = ignoreUrls;
        }
    }
}
