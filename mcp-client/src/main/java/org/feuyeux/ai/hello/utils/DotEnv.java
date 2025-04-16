package org.feuyeux.ai.hello.utils;

import static java.util.Optional.ofNullable;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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
        .orElseThrow(() -> new IllegalArgumentException("no ZHIPUAI APIKEY provided!"));
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
    Path filePath = Paths.get(path.toString(), ".env");
    for (int i = 0; !filePath.toFile().exists(); ++i) {
      path = path.getParent();
      filePath = Paths.get(path.toString(), ".env");
      if (i == 3) {
        throw new RuntimeException("no .env file found!");
      }
    }
    // 加载.env内容到系统属性
    try {
      final java.util.Properties properties = new java.util.Properties();
      try (Reader r = new FileReader(filePath.toFile())) {
        properties.load(r);
      }
      System.getProperties().putAll(properties);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
