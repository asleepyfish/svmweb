package edu.upc.svmweb.util;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.ansj.splitWord.analysis.ToAnalysis;

public class ChineseWordSegmentationUtil {
    //统计word frequency
    public StringBuilder wq = new StringBuilder();
    //统计词频前十
    public StringBuilder top_word = new StringBuilder();

    private static String clearDataFilePath = "data/项目文本/ClearData.txt";

    /**
     * 统计词频
     *
     * @throws IOException
     */
    public void wordFrequency() throws IOException {

        Map<String, Integer> map = new ConcurrentHashMap<>();
        FileOperationUtil.readStopWords("data/项目文本/StopWords.txt");
        Set<String> set = FileOperationUtil.set;

        String article = txtToString();

        //对文本进行基本分词,分词结果用逗号分隔
        String result = ToAnalysis.parse(article).toStringWithOutNature();

        //以,分割为字符串数组
        String[] words = result.split(",");

        for (String word : words) {

            String term = word.trim();

            // 此处过滤长度为1的str，如：一、被、就
            if (term.length() < 2) {
                continue;
            }
            //containsKey用于检查是否包含指定的词汇，没有词频置为1，有则词频加1
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                int n = map.get(word);
                map.put(word, ++n);
            }
        }

        removeStopWords(map, set);

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
     * 去除词频表中的停用词
     *
     * @param map
     * @param set
     */
    public void removeStopWords(Map<String, Integer> map, Set<String> set) {
        Set<String> keySet = map.keySet();
        for (String term : keySet) {
            if (set.contains(term)) {
                map.remove(term);
            }
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
    public String txtToString() throws IOException {

        return FileOperationUtil.readFile(clearDataFilePath);
    }
}
