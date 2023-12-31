package com.company.neutralizationtools.utils;


import java.io.*;
import java.util.UUID;

/**
 * 文件操作工具类
 */
public class FileUtils {

    /**
     * 写入文件
     *
     * @param target
     * @param src
     * @throws IOException
     */
    public static void write(String target, InputStream src) throws IOException {
        OutputStream os = new FileOutputStream(target);
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            os.write(buf, 0, len);
        }
        os.flush();
        os.close();
    }

    /**
     * 分块写入文件
     *
     * @param target
     * @param targetSize
     * @param src
     * @param srcSize
     * @param chunks
     * @param chunk
     * @throws IOException
     */
    public static void writeWithBlok(String target, Long targetSize, InputStream src, Long srcSize, Integer chunks, Integer chunk) throws IOException {
        mkdirs(target);
        RandomAccessFile randomAccessFile = new RandomAccessFile(target, "rw");
        randomAccessFile.setLength(targetSize);
        if (chunk == chunks - 1) {
            randomAccessFile.seek(targetSize - srcSize);
        } else {
            randomAccessFile.seek(chunk * srcSize);
        }
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            randomAccessFile.write(buf, 0, len);
        }
        randomAccessFile.close();
    }

    /**
     * 生成随机文件名
     *
     * @return
     */
    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    public static void mkdirs(String path) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();
    }
}
