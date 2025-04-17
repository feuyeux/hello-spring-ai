package org.feuyeux.ai.hello.utils;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/** 环境变量和配置工具接口 负责加载和管理配置信息，从.env文件或系统环境变量中获取API密钥等信息 */
public interface DotEnv {

  /**
   * 获取智谱AI API密钥
   *
   * @return 智谱AI API密钥字符串
   * @throws IllegalArgumentException 如果未找到API密钥
   */
  static String getZhipuAiKey() {
    return valueOf("ZHIPUAI_API_KEY")
        .orElseThrow(() -> new IllegalArgumentException("no ZHIPUAI_API_KEY provided!"));
  }

  static String[] getQianfanModelApiKeys() {
    return new String[]{
        valueOf("QIANFAN_ACCESS_KEY")
            .orElseThrow(() -> new IllegalArgumentException("no QIANFAN_ACCESS_KEY provided!")),
        valueOf("QIANFAN_SECRET_KEY")
            .orElseThrow(() -> new IllegalArgumentException("no QIANFAN_SECRET_KEY provided!"))
    };
  }

  static String[] getQianfanTokenKeys() {
    return new String[]{
        valueOf("QIANFAN_API_KEY")
            .orElseThrow(() -> new IllegalArgumentException("no QIANFAN_API_KEY provided!")),
        valueOf("QIANFAN_API_SECRET_KEY")
            .orElseThrow(() -> new IllegalArgumentException("no QIANFAN_API_SECRET_KEY provided!"))
    };
  }

  /**
   * 获取百度地图API密钥
   *
   * @return 百度地图API密钥字符串
   * @throws IllegalArgumentException 如果未找到API密钥
   */
  static String getLbsBaidui() {
    return valueOf("BAIDU_MAP_API_KEY")
        .orElseThrow(() -> new IllegalArgumentException("no LBSYUN_BAIDU APIKEY provided!"));
  }

  /**
   * 获取高德地图API密钥
   *
   * @return 高德地图API密钥字符串
   * @throws IllegalArgumentException 如果未找到API密钥
   */
  static String getLbsGaode() {
    return valueOf("AMAP_MAPS_API_KEY")
        .orElseThrow(() -> new IllegalArgumentException("no AMAP_MAPS_API_KEY APIKEY provided!"));
  }

  /**
   * 获取地图服务提供商配置
   *
   * @return 地图服务提供商名称（BAIDU或AMAP）
   * @throws IllegalArgumentException 如果未找到配置
   */
  static String getMcpMap() {
    return valueOf("MCP_MAP")
        .orElseThrow(() -> new IllegalArgumentException("no LBS_GAODE APIKEY provided!"));
  }

  /**
   * 获取配置值 首先尝试从系统环境变量获取，然后从系统属性获取
   *
   * @param key 配置键名
   * @return 包含配置值的Optional对象
   */
  static Optional<String> valueOf(String key) {
    String value = System.getenv(key);
    if (value == null) {
      value = System.getProperty(key);
    }
    return ofNullable(value);
  }

  /**
   * 加载.env文件内容到系统属性 递归向上搜索目录树，查找.env文件
   *
   * @throws RuntimeException 如果找不到.env文件或加载过程出错
   */
  static void loadEnv() {
    // 搜索.env文件
    Path path = Paths.get(".").toAbsolutePath();
    Path filePath;
    int maxDepth = 5; // 限制递归深度，避免无限循环
    for (int i = 0; i < maxDepth; i++) {
      filePath = Paths.get(path.toString(), ".env");
      if (filePath.toFile().exists()) {
        // 加载.env内容到系统属性
        try {
          final java.util.Properties properties = new java.util.Properties();
          try (Reader r = new FileReader(filePath.toFile())) {
            properties.load(r);
          }
          System.getProperties().putAll(properties);
        } catch (Exception e) {
          throw new RuntimeException("Error loading .env file: " + e.getMessage(), e);
        }
        return;
      }
      path = path.getParent();
      if (path == null) {
        break; // 已到达文件系统根目录
      }
    }
    throw new RuntimeException("No .env file found within " + maxDepth + " levels of directory hierarchy!");
  }
}
