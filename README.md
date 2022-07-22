# Xtils

Xposed utils

## 支持类型

1. hook对象: 变量、构造、方法
2. hook方式: 查看、修改
3. hook时机: 方法调用之前、方法调用之后
4. 主动调用、方法拦截

## 暂定配置文件

``` json
{
    "hclassName":"xxx.xx.xx",
    "htype":"constructor/field/method",
    "hAll":true/false,
    "constructorArgsType":""(无参数)/"类名全称1|类名全称2"(多参数),
    "fieldType":"类名全称",
    "methodArgsType":""(无参数)/"类名全称1|类名全称2"(多参数),
    "hProcessType":"modify/cat"(操作类型: 查看、修改),
    "hookTime":"before/after"(hook时机:方法调用前、方法调用后),
    "needCall":true/false(是否需要主动调用，部分不会调用)
}
```
