package org.example.config;

import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class AppConfig {
  private Map<String, Object> config;

  public AppConfig() {
    Yaml yaml = new Yaml();
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
        "application.yml")) {
      if (inputStream != null) {
        config = yaml.load(inputStream);
      } else {
        throw new RuntimeException("Could not load application.yml");
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to load configuration", e);
    }
  }

  public String getDatabaseUrl() {
    return (String) ((Map<String, Object>) config.get("database")).get("url");
  }

  public String getDatabaseUsername() {
    return (String) ((Map<String, Object>) config.get("database")).get("username");
  }

  public String getDatabasePassword() {
    return (String) ((Map<String, Object>) config.get("database")).get("password");
  }
}
