package me.hhhaiai.xtils.modules;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import org.json.JSONArray;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import me.hhhaiai.xtils.modules.callbacks.IProcessOver;
import me.hhhaiai.xtils.utils.FileUtils;
import me.hhhaiai.xtils.utils.L;
import me.hhhaiai.xtils.utils.MThredPool;

public class DatasLoaders {
    public static JSONArray mHookJson = new JSONArray();

    static {
        initHandler();
    }

    private static XC_LoadPackage.LoadPackageParam memLpparam = null;


    public static void initZygote(IXposedHookZygoteInit.StartupParam startupParam) {
    }

    public static void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) {
    }

    // load
    public static void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        memLpparam = lpparam;
        //设置hook工作后，定时更新
        repostMsg(memLpparam, Contents.delayMillis / 3);
    }

    /**
     * 定时更新一次
     * @param memLpparam
     * @param delayMillis
     */
    private static void repostMsg(XC_LoadPackage.LoadPackageParam memLpparam, long delayMillis) {
        initHandler();
        if (mHandler.hasMessages(MSG_UPDATE)) {
            mHandler.removeMessages(MSG_UPDATE);
        }
        Message msg = Message.obtain();
        msg.what = MSG_UPDATE;
        msg.obj = memLpparam;
        mHandler.sendMessageDelayed(msg, delayMillis);
    }

    public static int MSG_UPDATE = 0x999;
    static Handler mHandler = null;




    private static void initHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == MSG_UPDATE) {
                        final XC_LoadPackage.LoadPackageParam param = (XC_LoadPackage.LoadPackageParam) msg.obj;
                        //单线程的线程池
                        MThredPool.runOnWorkThread(new Runnable() {
                            @Override
                            public void run() {
                                //处理完成后10秒后继续循环检测
                                loadFileAndExec(param, new IProcessOver() {

                                    @Override
                                    public void onProcessed() {
                                        repostMsg(param, Contents.delayMillis);
                                    }
                                });
                            }
                        });
                    }
                }
            };
        }
    }

    /**
     * 加载文件并加载应用
     * @param lpparam
     * @param callback
     */
    private static void loadFileAndExec(XC_LoadPackage.LoadPackageParam lpparam, IProcessOver callback) {
        // 1. load
        try {
            String info = FileUtils.readString(Contents.PATH_JSON);
            if (!TextUtils.isEmpty(info)) {
                JSONArray arr = new JSONArray(info);
                if (arr != null && arr.length() > 0) {
                    L.d(Contents.PATH_JSON + " 有内容...");
                    if (mHookJson.equals(arr)) {
                        mHookJson = arr;
                        L.i("文件中内容.发生变动。。。。");

                        // update memory and hook
                        // 2. 设置hook工作
                        XtImpl.hook(lpparam, mHookJson);
                    } else {
                        L.d("文件中内容.发生变动。。。。");
                    }
                }

            } else {
                L.i("[" + Contents.PATH_JSON + "]文件为空，或者读取失败");
            }

        } catch (Throwable e) {
            L.e(e);
        } finally {
            if (callback != null) {
                callback.onProcessed();
            }
        }
    }


}
