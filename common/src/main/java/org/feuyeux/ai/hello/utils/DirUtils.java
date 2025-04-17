package org.feuyeux.ai.hello.utils;

import java.nio.file.Paths;

public class DirUtils {

  public static String getUserDir() {
      return Paths.get(System.getProperty("user.dir")).toString();
  }
}
