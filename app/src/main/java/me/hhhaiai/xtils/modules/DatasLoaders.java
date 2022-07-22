package me.hhhaiai.xtils.modules;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import org.json.JSONArray;

import java.io.File;

import de.robv.android.xposed.callbacks.XC_LoadPackage;
import me.hhhaiai.xtils.utils.FileUtils;
import me.hhhaiai.xtils.utils.L;

public class DatasLoaders {
    public static JSONArray mHookJson = new JSONArray();

    private static XC_LoadPackage.LoadPackageParam memLpparam = null;

    // load
    public static void loadScheduleder(XC_LoadPackage.LoadPackageParam lpparam) {
        memLpparam = lpparam;
        // 1. load
        File f = new File(Contents.PATH_JSON);
        String info = FileUtils.readStringFromFile(f);
        if (!TextUtils.isEmpty(info)) {
            try {
                JSONArray arr = new JSONArray(info);
                if (arr == null || arr.length() < 1) {
                    return;
                }
                mHookJson = arr;

            } catch (Throwable e) {
                L.e(e);
            }
        }
        // 2. 设置hook工作
        XtImpl.hook(lpparam, mHookJson);
        // 3. 设置hook工作后，定时更新
        repostMsg();
    }

    /**
     * 30秒更新一次
     */
    private static void repostMsg() {
        initHandler();
        if (mHandler.hasMessages(MSG_UPDATE)) {
            mHandler.removeMessages(MSG_UPDATE);
        }
        Message msg = Message.obtain();
        msg.what = MSG_UPDATE;
        mHandler.sendMessageDelayed(msg, 30 * 1000);
    }

    public static int MSG_UPDATE = 0x999;
    static Handler mHandler = null;


    static {
        initHandler();
    }

    private static void initHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == MSG_UPDATE) {
                        loadScheduleder(memLpparam);
                    }
                }
            };
        }
    }

}
