package edu.upc.svmweb.util;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChineseWordSegmentationUtil {
    //统计word frequency的字符串
    public StringBuilder wq = new StringBuilder();
    //统计词频前number的字符串
    public StringBuilder top_word = new StringBuilder();
    //存储词频前number的entry列表
    private List<Map.Entry<String, Integer>> list = new ArrayList<>();
    //存储去停用词后的词频的map集合
    private Map<String, Integer> map = new ConcurrentHashMap<>();
    //文章的纯中文文本路径
    private static final String clearDataFilePath = "data/项目文本/ClearData.txt";

    /**
     * 统计词频
     *
     * @throws IOException
     */
    public void getWordFrequency() throws IOException {


        FileOperationUtil.readStopWords("data/项目文本/StopWords.txt");
        Set<String> set = FileOperationUtil.set;

        String article = txtToString();
        JiebaSegmenter segmenter = new JiebaSegmenter();
        //对文本进行基本分词,分词结果用逗号分隔
        String result = segmenter.sentenceProcess(article).toString().replaceAll("(?:\\[|null|\\]| +)", "");
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

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            wq.append(entry.getKey()).append(": ").append(entry.getValue()).append("\t");

        }
    }

    /**
     * 统计词频前number
     *
     * @param number
     */
    public void getTopNumberWord(Integer number) {
        Map.Entry<String, Integer> entry;
        int size = map.size() >= number ? number : map.size();
        for (int i = 0; i < size; i++) {
            entry = getMaxEntry(map);
            list.add(entry);
        }
        for (int i = 0; i < size; i++) {
            top_word.append(list.get(i)).append("\n");
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
    public Map.Entry<String, Integer> getMaxEntry(Map<String, Integer> map) {
        if (map == null) {
            return null;
        }
        Map.Entry<String, Integer> maxEntry = null;
        boolean flag = false;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (!flag) {
                maxEntry = entry;
                flag = true;
            }
            if (entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        assert maxEntry != null;
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
