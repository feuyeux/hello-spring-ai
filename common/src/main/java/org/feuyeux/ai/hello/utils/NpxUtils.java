package org.feuyeux.ai.hello.utils;

public class NpxUtils {
  public static String buildNpxCmd() {
    return System.getProperty("os.name").toLowerCase().contains("win") ? "npx.cmd" : "npx";
  }
}
