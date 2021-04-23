package edu.upc.svmweb.util;

import java.io.*;
import java.util.*;

import org.ansj.splitWord.analysis.ToAnalysis;

public class ChineseWordSegmentationUtil {
    //统计word frequency
    public StringBuilder wq = new StringBuilder();
    //统计词频前十
    public StringBuilder top_word = new StringBuilder();

    private static String clearDataFilePath = "data/项目文本/clearData.txt";


    public void wordFrequency() throws IOException {

        Map<String, Integer> map = new HashMap<>();


        String article = txtToString();

        //对文本进行基本分词,分词结果用逗号分隔
        String result = ToAnalysis.parse(article).toStringWithOutNature();

        //以,分割为字符串数组
        String[] words = result.split(",");

        for (String word : words) {

            String str = word.trim();

            // 过滤空白字符
            if (str.equals("")) {
                continue;
            }
            // 过滤一些高频率的符号
            else if (str.matches("[）|（|.|，|。|+|-|“|”|：|？|\\s]")) {
                continue;
            }
            // 此处过滤长度为1的str
            else if (str.length() < 2) {
                continue;
            }
            /*
             * containsKey用于检查是否包含指定的词汇，没有词频置为1，有则词频加1
             */
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                int n = map.get(word);
                map.put(word, ++n);
            }
        }


        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            wq.append(entry.getKey() + ": " + entry.getValue() + "\t");

        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>();

        Map.Entry<String, Integer> entry;

        for (int i = 0; i < 10; i++) {
            entry = getMax(map);
            list.add(entry);
        }
        for (int i = 0; i < 10; i++) {
            top_word.append(list.get(i) + "\n");
        }

    }


    /**
     * 找出map中value最大的entry, 返回此entry, 并在map删除此entry
     *
     * @param map
     * @return map中value最大的
     */
    public Map.Entry<String, Integer> getMax(Map<String, Integer> map) {
        if (map.size() == 0) {
            return null;
        }
        Map.Entry<String, Integer> maxEntry = null;
        boolean flag = false;
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (!flag) {
                maxEntry = entry;
                flag = true;
            }
            if (entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        map.remove(maxEntry.getKey());
        return maxEntry;
    }

    /**
     * 从文件中读取待分割的文章素材.
     *
     * @return 文章
     * @throws IOException
     */
    public static String txtToString() throws IOException {

        return FileOperationUtil.readFile(clearDataFilePath);
    }
}
