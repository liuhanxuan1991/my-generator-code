package com.lhx.study.mygeneratorcode.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * 文件工具类
 */
public class FileToolUtil {

    /**
     * 创建文件
     * @param filePath
     * @param fileName
     * @param context
     * @return
     * @throws IOException
     */
    public static Path createFile(String filePath, String fileName, String context) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createDirectories(path);//创建文件夹（父级不存在也会创建）
        }

        path = Paths.get(path + "/" + fileName);
        BufferedWriter writer = Files.newBufferedWriter(path);
        writer.write(context);
        writer.close();
        return path;
    }

    public static String zipFiles(List<Path> pathList, String zipPath, String zipName) throws IOException {
        byte[] buf = new byte[1024];
        Path path = Paths.get(zipPath);
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createDirectories(path);
        }

        String zipDownloadPath = path + "/" + zipName + ".zip";
        ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(Paths.get(zipDownloadPath)));
        for (Path x : pathList) {
            File file = x.toFile();
            ZipEntry zipEntry = new ZipEntry(file.getName());

            try {
                zipOut.putNextEntry(zipEntry);
                InputStream inputStream = Files.newInputStream(x);

                int len;
                while((len = inputStream.read(buf)) > 0) {
                    zipOut.write(buf, 0, len);
                }

                zipOut.closeEntry();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        zipOut.close();
        //已生成压缩文件，删除源文件
        for (Path x : pathList){
            Files.deleteIfExists(x);
        }
        return zipDownloadPath;
    }
}
