package me.hhhaiai.xtils.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileUtils {

    public static String readString(String path) {
        return readString(new File(path));

    }
    /**
     * 读取文件中的字符串
     *
     * @param file
     * @return
     */
    public static String readString(File file) {

        FileInputStream outputStream = null;
        BufferedReader reader = null;
        InputStreamReader in = null;
        StringBuilder builder = new StringBuilder();
        try {
            if (!file.exists()) {
                return "";
            }
            outputStream = new FileInputStream(file);
            in = new InputStreamReader(outputStream);
            reader = new BufferedReader(in);
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                builder.append(str).append("\n");
            }
        } catch (Throwable igone) {
            L.e(igone);
        } finally {
            StreamerUtils.safeClose(in, reader, outputStream);
        }
        return builder.toString();
    }

}
