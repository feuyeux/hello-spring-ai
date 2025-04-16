package org.feuyeux.ai.hello.service;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.ai.hello.pojo.Element;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * HelloMcpService类
 *
 * <p>提供元素周期表查询服务的工具类，通过Spring AI的@Tool注解将方法暴露为MCP工具。 包含了完整的元素周期表数据和查询接口，支持按名称和序号查询元素信息。
 */
@Slf4j
@Service
public class HelloMcpService {
  /** 存储所有元素周期表元素的列表 该列表在静态初始化块中被填充，包含了所有已知化学元素 */
  private static final List<Element> elements = new ArrayList<>();

  /** 静态初始化块，程序启动时填充元素周期表数据 */
  static {
    initializePeriodicTable();
  }

  /** 初始化元素周期表数据 按照最新的Element结构填充元素数据，包括元素的原子序数、符号、中文名称、 读音、英文名称、相对原子质量、周期和族等信息 */
  private static void initializePeriodicTable() {
    // 周期1（2种）
    elements.add(new Element(1, "H", "氢", "qīng", "Hydrogen", 1.008, 1, "IA"));
    elements.add(new Element(2, "He", "氦", "hài", "Helium", 4.0026, 1, "0族"));

    // 周期2（8种）
    elements.add(new Element(3, "Li", "锂", "lǐ", "Lithium", 6.941, 2, "IA"));
    elements.add(new Element(4, "Be", "铍", "pí", "Beryllium", 9.0122, 2, "IIA"));
    elements.add(new Element(5, "B", "硼", "péng", "Boron", 10.811, 2, "IIIA"));
    elements.add(new Element(6, "C", "碳", "tàn", "Carbon", 12.011, 2, "IVA"));
    elements.add(new Element(7, "N", "氮", "dàn", "Nitrogen", 14.007, 2, "VA"));
    elements.add(new Element(8, "O", "氧", "yǎng", "Oxygen", 15.999, 2, "VIA"));
    elements.add(new Element(9, "F", "氟", "fú", "Fluorine", 18.998, 2, "VIIA"));
    elements.add(new Element(10, "Ne", "氖", "nǎi", "Neon", 20.180, 2, "0族"));

    // 周期3（8种）
    elements.add(new Element(11, "Na", "钠", "nà", "Sodium", 22.990, 3, "IA"));
    elements.add(new Element(12, "Mg", "镁", "měi", "Magnesium", 24.305, 3, "IIA"));
    elements.add(new Element(13, "Al", "铝", "lǚ", "Aluminum", 26.982, 3, "IIIA"));
    elements.add(new Element(14, "Si", "硅", "guī", "Silicon", 28.085, 3, "IVA"));
    elements.add(new Element(15, "P", "磷", "lín", "Phosphorus", 30.974, 3, "VA"));
    elements.add(new Element(16, "S", "硫", "liú", "Sulfur", 32.06, 3, "VIA"));
    elements.add(new Element(17, "Cl", "氯", "lǜ", "Chlorine", 35.45, 3, "VIIA"));
    elements.add(new Element(18, "Ar", "氩", "yà", "Argon", 39.948, 3, "0族"));

    // 周期4（18种）
    elements.add(new Element(19, "K", "钾", "jiǎ", "Potassium", 39.098, 4, "IA"));
    elements.add(new Element(20, "Ca", "钙", "gài", "Calcium", 40.078, 4, "IIA"));
    elements.add(new Element(21, "Sc", "钪", "kàng", "Scandium", 44.956, 4, "IIIB"));
    elements.add(new Element(22, "Ti", "钛", "tài", "Titanium", 47.867, 4, "IVB"));
    elements.add(new Element(23, "V", "钒", "fán", "Vanadium", 50.942, 4, "VB"));
    elements.add(new Element(24, "Cr", "铬", "gè", "Chromium", 51.996, 4, "VIB"));
    elements.add(new Element(25, "Mn", "锰", "měng", "Manganese", 54.938, 4, "VIIB"));
    elements.add(new Element(26, "Fe", "铁", "tiě", "Iron", 55.845, 4, "VIIIB"));
    elements.add(new Element(27, "Co", "钴", "gǔ", "Cobalt", 58.933, 4, "VIIIB"));
    elements.add(new Element(28, "Ni", "镍", "niè", "Nickel", 58.693, 4, "VIIIB"));
    elements.add(new Element(29, "Cu", "铜", "tóng", "Copper", 63.546, 4, "IB"));
    elements.add(new Element(30, "Zn", "锌", "xīn", "Zinc", 65.38, 4, "IIB"));
    elements.add(new Element(31, "Ga", "镓", "jiā", "Gallium", 69.723, 4, "IIIA"));
    elements.add(new Element(32, "Ge", "锗", "zhě", "Germanium", 72.63, 4, "IVA"));
    elements.add(new Element(33, "As", "砷", "shēn", "Arsenic", 74.922, 4, "VA"));
    elements.add(new Element(34, "Se", "硒", "xī", "Selenium", 78.971, 4, "VIA"));
    elements.add(new Element(35, "Br", "溴", "xiù", "Bromine", 79.904, 4, "VIIA"));
    elements.add(new Element(36, "Kr", "氪", "kè", "Krypton", 83.798, 4, "0族"));

    // 周期5（18种）
    elements.add(new Element(37, "Rb", "铷", "rú", "Rubidium", 85.468, 5, "IA"));
    elements.add(new Element(38, "Sr", "锶", "sī", "Strontium", 87.62, 5, "IIA"));
    elements.add(new Element(39, "Y", "钇", "yǐ", "Yttrium", 88.906, 5, "IIIB"));
    elements.add(new Element(40, "Zr", "锆", "gào", "Zirconium", 91.224, 5, "IVB"));
    elements.add(new Element(41, "Nb", "铌", "ní", "Niobium", 92.906, 5, "VB"));
    elements.add(new Element(42, "Mo", "钼", "mù", "Molybdenum", 95.95, 5, "VIB"));
    elements.add(new Element(43, "Tc", "锝", "dé", "Technetium", 98.0, 5, "VIIB"));
    elements.add(new Element(44, "Ru", "钌", "liǎo", "Ruthenium", 101.07, 5, "VIIIB"));
    elements.add(new Element(45, "Rh", "铑", "láo", "Rhodium", 102.91, 5, "VIIIB"));
    elements.add(new Element(46, "Pd", "钯", "bǎ", "Palladium", 106.42, 5, "VIIIB"));
    elements.add(new Element(47, "Ag", "银", "yín", "Silver", 107.87, 5, "IB"));
    elements.add(new Element(48, "Cd", "镉", "gé", "Cadmium", 112.41, 5, "IIB"));
    elements.add(new Element(49, "In", "铟", "yīn", "Indium", 114.82, 5, "IIIA"));
    elements.add(new Element(50, "Sn", "锡", "xī", "Tin", 118.71, 5, "IVA"));
    elements.add(new Element(51, "Sb", "锑", "tī", "Antimony", 121.76, 5, "VA"));
    elements.add(new Element(52, "Te", "碲", "dì", "Tellurium", 127.6, 5, "VIA"));
    elements.add(new Element(53, "I", "碘", "diǎn", "Iodine", 126.9, 5, "VIIA"));
    elements.add(new Element(54, "Xe", "氙", "xiān", "Xenon", 131.29, 5, "0族"));

    // 周期6（32种）
    elements.add(new Element(55, "Cs", "铯", "sè", "Cesium", 132.91, 6, "IA"));
    elements.add(new Element(56, "Ba", "钡", "bèi", "Barium", 137.33, 6, "IIA"));
    elements.add(new Element(57, "La", "镧", "lán", "Lanthanum", 138.91, 6, "镧系"));
    elements.add(new Element(58, "Ce", "铈", "shì", "Cerium", 140.12, 6, "镧系"));
    elements.add(new Element(59, "Pr", "镨", "pǔ", "Praseodymium", 140.91, 6, "镧系"));
    elements.add(new Element(60, "Nd", "钕", "nǚ", "Neodymium", 144.24, 6, "镧系"));
    elements.add(new Element(61, "Pm", "钷", "pō", "Promethium", 145.0, 6, "镧系"));
    elements.add(new Element(62, "Sm", "钐", "shàn", "Samarium", 150.36, 6, "镧系"));
    elements.add(new Element(63, "Eu", "铕", "yǒu", "Europium", 151.96, 6, "镧系"));
    elements.add(new Element(64, "Gd", "钆", "gá", "Gadolinium", 157.25, 6, "镧系"));
    elements.add(new Element(65, "Tb", "铽", "tè", "Terbium", 158.93, 6, "镧系"));
    elements.add(new Element(66, "Dy", "镝", "dí", "Dysprosium", 162.5, 6, "镧系"));
    elements.add(new Element(67, "Ho", "钬", "huǒ", "Holmium", 164.93, 6, "镧系"));
    elements.add(new Element(68, "Er", "铒", "ěr", "Erbium", 167.26, 6, "镧系"));
    elements.add(new Element(69, "Tm", "铥", "diū", "Thulium", 168.93, 6, "镧系"));
    elements.add(new Element(70, "Yb", "镱", "yì", "Ytterbium", 173.05, 6, "镧系"));
    elements.add(new Element(71, "Lu", "镥", "lǔ", "Lutetium", 174.97, 6, "镧系"));
    elements.add(new Element(72, "Hf", "铪", "hā", "Hafnium", 178.49, 6, "IVB"));
    elements.add(new Element(73, "Ta", "钽", "tǎn", "Tantalum", 180.95, 6, "VB"));
    elements.add(new Element(74, "W", "钨", "wū", "Tungsten", 183.84, 6, "VIB"));
    elements.add(new Element(75, "Re", "铼", "lái", "Rhenium", 186.21, 6, "VIIB"));
    elements.add(new Element(76, "Os", "锇", "é", "Osmium", 190.23, 6, "VIIIB"));
    elements.add(new Element(77, "Ir", "铱", "yī", "Iridium", 192.22, 6, "VIIIB"));
    elements.add(new Element(78, "Pt", "铂", "bó", "Platinum", 195.08, 6, "VIIIB"));
    elements.add(new Element(79, "Au", "金", "jīn", "Gold", 196.97, 6, "IB"));
    elements.add(new Element(80, "Hg", "汞", "gǒng", "Mercury", 200.59, 6, "IIB"));
    elements.add(new Element(81, "Tl", "铊", "shā/tā", "Thallium", 204.38, 6, "IIIA"));
    elements.add(new Element(82, "Pb", "铅", "qiān", "Lead", 207.2, 6, "IVA"));
    elements.add(new Element(83, "Bi", "铋", "bì", "Bismuth", 208.98, 6, "VA"));
    elements.add(new Element(84, "Po", "钋", "pō", "Polonium", 209.0, 6, "VIA"));
    elements.add(new Element(85, "At", "砹", "ài", "Astatine", 210.0, 6, "VIIA"));
    elements.add(new Element(86, "Rn", "氡", "dōng", "Radon", 222.0, 6, "0族"));

    // 周期7（32种）
    elements.add(new Element(87, "Fr", "钫", "fāng", "Francium", 223.0, 7, "IA"));
    elements.add(new Element(88, "Ra", "镭", "léi", "Radium", 226.03, 7, "IIA"));
    elements.add(new Element(89, "Ac", "锕", "è", "Actinium", 227.0, 7, "锕系"));
    elements.add(new Element(90, "Th", "钍", "tǔ", "Thorium", 232.04, 7, "锕系"));
    elements.add(new Element(91, "Pa", "镤", "pú", "Protactinium", 231.04, 7, "锕系"));
    elements.add(new Element(92, "U", "铀", "yóu", "Uranium", 238.03, 7, "锕系"));
    elements.add(new Element(93, "Np", "镎", "ná", "Neptunium", 237.0, 7, "锕系"));
    elements.add(new Element(94, "Pu", "钚", "bù", "Plutonium", 244.0, 7, "锕系"));
    elements.add(new Element(95, "Am", "镅", "méi", "Americium", 243.0, 7, "锕系"));
    elements.add(new Element(96, "Cm", "锔", "jú", "Curium", 247.0, 7, "锕系"));
    elements.add(new Element(97, "Bk", "锫", "péi", "Berkelium", 247.0, 7, "锕系"));
    elements.add(new Element(98, "Cf", "锎", "kāi", "Californium", 251.0, 7, "锕系"));
    elements.add(new Element(99, "Es", "锿", "āi", "Einsteinium", 252.0, 7, "锕系"));
    elements.add(new Element(100, "Fm", "镄", "fèi", "Fermium", 257.0, 7, "锕系"));
    elements.add(new Element(101, "Md", "钔", "mén", "Mendelevium", 258.0, 7, "锕系"));
    elements.add(new Element(102, "No", "锘", "nuò", "Nobelium", 259.0, 7, "锕系"));
    elements.add(new Element(103, "Lr", "铹", "láo", "Lawrencium", 262.0, 7, "锕系"));
    elements.add(new Element(104, "Rf", "𬬻", "lú", "Rutherfordium", 267.0, 7, "IVB"));
    elements.add(new Element(105, "Db", "𬭊", "dù", "Dubnium", 270.0, 7, "VB"));
    elements.add(new Element(106, "Sg", "𬭳", "xī", "Seaborgium", 271.0, 7, "VIB"));
    elements.add(new Element(107, "Bh", "𬭛", "bō", "Bohrium", 270.0, 7, "VIIB"));
    elements.add(new Element(108, "Hs", "𬭶", "hēi", "Hassium", 277.0, 7, "VIIIB"));
    elements.add(new Element(109, "Mt", "鿏", "mài", "Meitnerium", 278.0, 7, "VIIIB"));
    elements.add(new Element(110, "Ds", "𫟼", "dá", "Darmstadtium", 281.0, 7, "VIIIB"));
    elements.add(new Element(111, "Rg", "𬬭", "lún", "Roentgenium", 282.0, 7, "IB"));
    elements.add(new Element(112, "Cn", "鎶", "gē", "Copernicium", 285.0, 7, "IIB"));
    elements.add(new Element(113, "Nh", "鉨", "ní", "Nihonium", 286.0, 7, "IIIA"));
    elements.add(new Element(114, "Fl", "𫓧", "fū", "Flerovium", 289.0, 7, "IVA"));
    elements.add(new Element(115, "Mc", "镆", "mò", "Moscovium", 290.0, 7, "VA"));
    elements.add(new Element(116, "Lv", "鉝", "lì", "Livermorium", 293.0, 7, "VIA"));
    elements.add(new Element(117, "Ts", "鿬", "tián", "Tennessine", 294.0, 7, "VIIA"));
    elements.add(new Element(118, "Og", "鿫", "ào", "Oganesson", 294.0, 7, "0族"));
  }

  /**
   * 根据元素中文名称查询元素信息
   *
   * <p>此方法通过MCP协议暴露为工具，可供LLM调用。
   *
   * @param name 元素的中文名称，如"氢"、"氦"等
   * @return 元素的详细信息字符串，包括名称、读音、英文名、原子序数等；如果未找到返回"元素不存在"
   */
  @Tool(description = "根据元素名称获取元素周期表元素信息")
  public String getElement(String name) {
    log.info("获取元素周期表元素信息: {}", name);
    if (name == null || name.isEmpty()) {
      return "元素名称不能为空";
    }
    return elements.stream()
        .filter(element -> element.getName().equals(name))
        .map(
            element ->
                String.format(
                    "元素名称: %s (%s, %s), 原子序数: %d, 符号: %s, 相对原子质量: %.3f, 周期: %d, 族: %s",
                    element.getName(),
                    element.getPronunciation(),
                    element.getEnglishName(),
                    element.getAtomicNumber(),
                    element.getSymbol(),
                    element.getAtomicWeight(),
                    element.getPeriod(),
                    element.getGroup()))
        .findFirst()
        .orElse("元素不存在");
  }

  /**
   * 根据元素在周期表中的位置（原子序数）查询元素信息
   *
   * <p>此方法通过MCP协议暴露为工具，可供LLM调用。
   *
   * @param position 元素的原子序数，范围从1到118
   * @return 元素的详细信息字符串，包括名称、读音、英文名、原子序数等；如果未找到返回错误信息
   */
  @Tool(description = "获取元素周期表指定位置的元素信息")
  public String getElementByPosition(int position) {
    log.info("获取元素周期表第{}个元素的信息", position);
    if (position <= 0 || position > elements.size()) {
      return "元素位置无效，应在1到" + elements.size() + "之间";
    }

    // 通过原子序数（周期表中的位置）查找元素
    return elements.stream()
        .filter(element -> element.getAtomicNumber() == position)
        .map(
            element ->
                String.format(
                    "元素名称: %s (%s, %s), 原子序数: %d, 符号: %s, 相对原子质量: %.3f, 周期: %d, 族: %s",
                    element.getName(),
                    element.getPronunciation(),
                    element.getEnglishName(),
                    element.getAtomicNumber(),
                    element.getSymbol(),
                    element.getAtomicWeight(),
                    element.getPeriod(),
                    element.getGroup()))
        .findFirst()
        .orElse("元素不存在");
  }
}
