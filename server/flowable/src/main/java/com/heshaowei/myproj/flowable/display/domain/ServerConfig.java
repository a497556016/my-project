package com.heshaowei.myproj.flowable.display.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "flowable.server-config")
public class ServerConfig{

  private String serverAddress;
  private Integer port;
  private String contextRoot;
  private String restRoot;
  private String userName;
  private String password;

  public String getServerAddress() {
    return serverAddress;
  }

  public void setServerAddress(String serverAddress) {
    this.serverAddress = serverAddress;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getContextRoot() {
    return contextRoot;
  }

  public void setContextRoot(String contextRoot) {
    this.contextRoot = contextRoot;
  }

  public String getRestRoot() {
    return restRoot;
  }

  public void setRestRoot(String restRoot) {
    this.restRoot = restRoot;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}