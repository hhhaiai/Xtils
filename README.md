# Xtils

Xposed utils

## 支持类型

1. hook对象: 变量、构造、方法
2. hook方式: 查看、修改
3. hook时机: 方法调用之前、方法调用之后
4. 主动调用、方法拦截

## 暂定配置文件

``` json
[
    {
        "hclassName":"xxx.xx.xx",
        "htype":"constructor/field/method",
        "hAll":false,
        "hStatic":false,
        "constructorArgsType":"类名全称1|类名全称2",
        "fieldType":"类名全称",
        "methodArgsType":"类名全称1|类名全称2",
        "hProcessType":"modify/cat",
        "hookTime":"before/after/all",
        "needCall":false
    },
    {
        "hclassName":"xxx.xx.xx",
        "htype":"constructor/field/method",
        "hAll":false,
        "hStatic":true,
        "constructorArgsType":"类名全称1|类名全称2",
        "fieldType":"类名全称",
        "methodArgsType":"类名全称1|类名全称2",
        "hProcessType":"modify/cat",
        "hookTime":"before/after/all",
        "needCall":false
    }
]
```

* 解析

    - "hclassName":"xxx.xx.xx",
    - "htype":"constructor/field/method",
    - "hAll":true/false(是否hook所有的),
    - "hStatic":true/false(是否静态),
    - "constructorArgsType":""(无参数)/"类名全称1|类名全称2"(多参数),
    - "fieldName":"hook变量名称",
    - "fieldType":"类名全称",
    - "methodName":"hook方法名称",
    - "methodArgsType":""(无参数)/"类名全称1|类名全称2"(多参数),
    - "hProcessType":"modify/cat"(操作类型: 查看、修改),
    - "hookTime":"before/after/all"(hook时机:方法调用前、方法调用后、所有-入参、出参全看),
    - "needCall":true/false(是否需要主动调用，部分不会调用)
    - 修改内容需要重新规划

## 用法

根据配置文件，放到`/data/local/tmp/config.json`
