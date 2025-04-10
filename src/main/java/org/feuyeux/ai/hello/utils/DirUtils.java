package org.feuyeux.ai.hello.utils;

import java.nio.file.Paths;

public class DirUtils {

  public static String getUserDir() {
    String userDir = Paths.get(System.getProperty("user.dir")).toString();
    return userDir;
  }
}
