package me.hhhaiai.xtils.utils;


import android.os.Looper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MThredPool {

    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    //    public ThreadPoolExecutor(int corePoolSize,
    //                              int maximumPoolSize,
    //                              long keepAliveTime,
    //                              TimeUnit unit,
    //                              BlockingQueue<Runnable> workQueue) {
    private static ExecutorService fixExecutor =
            new ThreadPoolExecutor(
                    6, 800, 500L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    private static ScheduledExecutorService executorUpload =
            Executors.newSingleThreadScheduledExecutor();
    // 任务队列,为了最后的清理数据
    private static List<WeakReference<ScheduledFuture<?>>> queue =
            new ArrayList<WeakReference<ScheduledFuture<?>>>();
    //    private static long MAX_WAIT_SECONDS = 5;

    public static void executeFix(Runnable command) {
        if (fixExecutor.isShutdown()) {
            fixExecutor =
                    new ThreadPoolExecutor(
                            6,
                            800,
                            500L,
                            TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<Runnable>());
        }
        fixExecutor.execute(command);
    }

    /**
     * 琐碎文件操作的线程池
     */
    public static void execute(Runnable command) {
        if (executor.isShutdown()) {
            executor = Executors.newSingleThreadExecutor();
        }
        executor.execute(command);
    }

    /**
     * 耗时服务线程池.处理网络,缓存和文件操作
     */
    public static synchronized void post(Runnable command) {

        if (executorUpload.isShutdown()) {
            executorUpload = Executors.newSingleThreadScheduledExecutor();
        }
        //        queue.add( new WeakReference<ScheduledFuture<?>>(executorUpload.schedule(command,
        // 0, TimeUnit.MILLISECONDS)));
        executorUpload.execute(command);
    }

    public static synchronized void postDelayed(Runnable command, long delay) {
        if (executorUpload.isShutdown()) {
            executorUpload = Executors.newSingleThreadScheduledExecutor();
        }

        queue.add(
                new WeakReference<ScheduledFuture<?>>(
                        executorUpload.schedule(command, delay, TimeUnit.MILLISECONDS)));
    }

    /**
     * 非主线程调用
     */
    public static void runOnWorkThread(final Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (isMainThread()) {
            MThredPool.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                runnable.run();
                            } catch (Throwable igone) {
                                L.e(igone);
                            }
                        }
                    });
        } else {
            try {
                runnable.run();
            } catch (Throwable igone) {
                L.e(igone);
            }
        }
    }

    /**
     * 非主线程调用
     */
    public static void runOnPosthread(final Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (isMainThread()) {
            MThredPool.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                runnable.run();
                            } catch (Throwable igone) {
                                L.e(igone);
                            }
                        }
                    });
        } else {
            try {
                runnable.run();
            } catch (Throwable igone) {
                L.e(igone);
            }
        }
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
