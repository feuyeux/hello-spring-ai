package org.feuyeux.ai.hello.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 元素类
 *
 * <p>此类表示化学元素周期表中的一个元素实体。 包含元素的基本属性如原子序数、符号、名称等。
 */
@Data
@AllArgsConstructor
public class Element {
  /** 元素的原子序数，表示元素在周期表中的位置 */
  private int atomicNumber;

  /** 元素的符号，如"H"代表氢 */
  private String symbol;

  /** 元素的中文名称，如"氢" */
  private String name;

  /** 元素的中文读音，如"qīng" */
  private String pronunciation;

  /** 元素的英文名称，如"Hydrogen" */
  private String englishName;

  /** 元素的相对原子质量 */
  private double atomicWeight;

  /** 元素在周期表中的周期（横行） */
  private int period;

  /** 元素在周期表中的族（纵列） */
  private String group;
}
