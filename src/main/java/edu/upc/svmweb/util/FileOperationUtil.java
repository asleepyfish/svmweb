package edu.upc.svmweb.util;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileOperationUtil {
    public static Set<String> set = new HashSet<>();
    public static StringBuilder top_word = new StringBuilder();
    public static StringBuilder feature_txt = new StringBuilder();

    public static void writeFile(String path, String data) {
        FileWriter fw;
        BufferedWriter bw = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fw = new FileWriter(path, false);
            bw = new BufferedWriter(fw);
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bw != null;
                bw.flush();
                bw.close();
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
        return sb.toString();
    }

    public static void readStopWords(String path) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            set.add(line);
        }
        br.close();
    }

    public static void readTopNumberWord(String path, int number) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        int i = 0;
        String line;
        while ((line = br.readLine()) != null && i < number) {
            i++;
            top_word.append(line).append("\n");
        }
        br.close();
    }

    public static void readFeatureWeight(String path) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int i = 1;
        while ((line = br.readLine()) != null) {
            if ((i++) % 5 != 0) {
                feature_txt.append(line).append("\t\t");
            } else {
                feature_txt.append(line).append("\n");
            }
        }
        br.close();
    }
}
