package net.bxx2004.packets

enum class ContextType(var chinese:String) {
    FILE("文件"),
    JSON("JSON格式化类型"),
    YAML("YAML格式化类型"),
    STRING("字符串"),
    BYTEARRAY("二进制数组"),
    EMPTY("空"),
    ATTRIBUTE("对象成员属性"),
    NUMBER("数字"),
    SERIALIZABLE("序列化"),
    ENUM("枚举类型")
}