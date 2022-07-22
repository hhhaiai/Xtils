package me.hhhaiai.xtils.utils;

import android.os.Build;

import java.io.Closeable;
import java.net.HttpURLConnection;

public class StreamerUtils {
    public static void safeClose(Object... os) {
        if (os != null && os.length > 0) {
            for (Object o : os) {
                if (o != null) {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            if (o instanceof AutoCloseable) {
                                ((AutoCloseable) o).close();
                            }
                        } else {
                            if (o instanceof HttpURLConnection) {
                                ((HttpURLConnection) o).disconnect();
                            } else if (o instanceof Closeable) {
                                ((Closeable) o).close();
                            }
                        }


                    } catch (Throwable e) {
                        L.e(e);
                    }
                }
            }
        }
    }
}
