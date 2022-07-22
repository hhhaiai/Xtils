package me.hhhaiai.xtils.modules;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import me.hhhaiai.xtils.utils.L;

public class XtImpl {
    private static XC_LoadPackage.LoadPackageParam mLpparam = null;

    public static void hook(XC_LoadPackage.LoadPackageParam lpparam, JSONArray arr) {

        if (lpparam == null) {
            L.i("XtImpl lpparam is null!");
            return;
        }
        if (arr == null || arr.length() < 1) {
            L.i("XtImpl arr is null!");
            return;
        }
        mLpparam = lpparam;
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.optJSONObject(i);
            //单个有效性检测
            if (obj == null || obj.length() < 1) {
                continue;
            }
            // 单个HOOK对象解析
            String hClassName = obj.optString(Contents.JKEY_hClassName, "");
            String htype = obj.optString(Contents.JKEY_hType, "");
            boolean hStatic = obj.optBoolean(Contents.JKEY_hStatic, false);
            String constructorArgsType = obj.optString(Contents.JKEY_ConstructorArgsType, "");
            String nFieldName = obj.optString(Contents.JKEY_nField, "");
            String fieldType = obj.optString(Contents.JKEY_FieldType, "");
            String nMethodName = obj.optString(Contents.JKEY_nMethod, "");
            String methodArgsType = obj.optString(Contents.JKEY_MethodArgsType, "");
            String hProcessType = obj.optString(Contents.JKEY_hProcessType, Contents.ProcessType.processCat);
            String hookTime = obj.optString(Contents.JKEY_HookTime, Contents.HookTime.atAnyTime);
            boolean needCall = obj.optBoolean(Contents.JKEY_NeedCall, false);
            boolean hAll = obj.optBoolean(Contents.JKEY_hALL, false);
            // 参数有效性解析
            if (TextUtils.isEmpty(hClassName) || TextUtils.isEmpty(htype)) {
                continue;
            }
            // todo 暂不支持hAll和主动调用needCall
            if (Contents.HookType.hookConstructor.equals(htype)) {
                // hook constructor
                hookConstructor(hClassName, constructorArgsType, hProcessType, hookTime);
            } else if (Contents.HookType.hookField.equals(htype)) {
                // hook field
                if (!TextUtils.isEmpty(fieldType)) {
                    // 变量必须有类型
                    hookField(hClassName, nFieldName, fieldType, hStatic, hProcessType);
                }
            } else if (Contents.HookType.hookMethod.equals(htype)) {
                // hook method
                hookMethod(hClassName, nMethodName, methodArgsType, hookTime);
            }
        }

    }




    /**
     * hook构造
     * @param hClassName
     * @param constructorArgsTypes
     * @param hProcessType
     * @param hookTime
     */
    private static void hookConstructor(String hClassName, String constructorArgsTypes, String hProcessType, String hookTime) {

    }

    /**
     * hook 变量
     * @param hClassName
     * @param fieldName
     * @param fieldType
     * @param hStatic
     * @param hProcessType
     */
    private static void hookField(String hClassName, String fieldName, String fieldType, boolean hStatic, String hProcessType) {
    }


    /**
     * hook方法
     * @param hClassName
     * @param methodName
     * @param methodArgsType
     * @param hookTime
     */
    private static void hookMethod(String hClassName, String methodName, String methodArgsType, String hookTime) {
        if (TextUtils.isEmpty(hClassName) || TextUtils.isEmpty(methodName)) {
            return;
        }
        if (TextUtils.isEmpty(methodArgsType)) {
            //无参数
            // hook default_random_domain
            XposedHelpers.findAndHookMethod(hClassName, mLpparam.classLoader, methodName, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (Contents.HookTime.atBefore.equals(hookTime) || Contents.HookTime.atAnyTime.equals(hookTime)) {
                        //入参
//                        Object[] args = param.args;
                        L.i(hClassName + "." + methodName + "   没有入参");
                    }
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Object o = param.getResult();
                    if (o != null) {
                        L.i(hClassName + "." + methodName + "返回值：" + o);
                    }
                }
            });
        } else {
            String[] classNames = methodArgsType.split("\\|");
            // hook default_random_domain
            XposedHelpers.findAndHookMethod(hClassName, mLpparam.classLoader, methodName, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (Contents.HookTime.atBefore.equals(hookTime) || Contents.HookTime.atAnyTime.equals(hookTime)) {
                        //入参
//                        Object[] args = param.args;
                        L.i(hClassName + "." + methodName + "   没有入参");
                    }
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Object o = param.getResult();
                    if (o != null) {
                        L.i(hClassName + "." + methodName + "返回值：" + o);
                    }
                }
            });
        }
    }
}
