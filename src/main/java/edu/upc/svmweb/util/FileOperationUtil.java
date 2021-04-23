package edu.upc.svmweb.util;

import java.io.*;

public class FileOperationUtil {
    public static void writeFile(String path, String data) {
        FileWriter fw = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fw = new FileWriter(path, false);
            fw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFile(String path) throws IOException {
        //读取清洗后的数据源
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        fr.close();
        return sb.toString();
    }
}
