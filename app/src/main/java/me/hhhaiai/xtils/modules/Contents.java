package me.hhhaiai.xtils.modules;

/**
 * @Copyright © 2022 sanbo Inc. All rights reserved.
 * @Description: 内部变量
 * @Version: 1.0
 * @Create: 2022/07/203 12:02:01
 * @author: sanbo
 */
public class Contents {
    public static String PATH_JSON = "/data/local/tmp/config.json";
    // hook类名称
    public static String JKEY_hClassName = "hclassName";

    // hook类型
    public static String JKEY_hType = "htype";
    // 是否hook所有
    public static String JKEY_hALL = "hAll";
    // 被hook是否是静态的
    public static String JKEY_hStatic = "hStatic";
    // hook构造参数类型
    public static String JKEY_ConstructorArgsType = "constructorArgsType";
    public static String JKEY_nField = "fieldName";
    // hook参数类型
    public static String JKEY_FieldType = "fieldType";
    public static String JKEY_nMethod = "methodName";
    // hook方法参数类型
    public static String JKEY_MethodArgsType = "methodArgsType";
    // hook方式
    public static String JKEY_hProcessType = "hProcessType";
    // hook时机
    public static String JKEY_HookTime = "hookTime";
    // 是否需要主动hook
    public static String JKEY_NeedCall = "needCall";

    // hook对象的类型
    static class HookType {
        static String hookConstructor = "constructor";
        static String hookField = "field";
        static String hookMethod = "method";
    }

    // hook的操作
    static class ProcessType {
        static String processModify = "modify";
        static String processCat = "cat";
    }

    /**
     * hook的时机:
     * 1. 入方法前,看入参
     * 2. 出方法后,看出参
     * 3. 所有的. 看入参和出参
     */
    static class HookTime {
        static String atBefore = "before";
        static String atAfter = "after";
        static String atAnyTime = "all";
    }
}
