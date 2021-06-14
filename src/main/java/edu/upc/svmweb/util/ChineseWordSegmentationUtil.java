package edu.upc.svmweb.util;

import com.hankcs.hanlp.collection.trie.ITrie;
import com.huaban.analysis.jieba.JiebaSegmenter;
import edu.upc.svmweb.classifier.SVMClassifier;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChineseWordSegmentationUtil {
    //统计word frequency的字符串
    public StringBuilder wf = new StringBuilder();
    //统计词频前number的字符串
    public StringBuilder top_word = new StringBuilder();
    //分类预测
    public static String predict;
    //存储词频前number的entry列表
    private final List<Map.Entry<String, Integer>> list = new ArrayList<>();
    //存储去停用词后的词频的map集合
    public static final Map<String, Integer> wf_map = new ConcurrentHashMap<>();
    //文章的纯中文文本路径
    private static final String CLEAR_DATAFILE_PATH = "data/项目文本/ClearData.txt";
    //停用词文本路径
    private static final String STOPWORDS_PATH = "data/项目文本/StopWords.txt";
    //词频文本路径
    private static final String WORD_FREQUENCY_PATH = "data/项目文本/WordFrequency.txt";
    //经排序后词频文本路径
    private static final String TOP_WORD_PATH = "data/项目文本/TopWord.txt";
    //存储分词结果
    public static String[] words;
    //存储特征词和对应的字典化id
    public static final Map<Integer, String> WORD_MAP = new LinkedHashMap<>();

    /**
     * 统计词频
     *
     * @throws IOException Exception
     */
    public void getWordFrequency(SVMClassifier classifier) throws IOException {

        //读停用词,得到停用词set,为后面的词频统计过滤部分词
        FileOperationUtil.readStopWords(STOPWORDS_PATH);
        Set<String> set = FileOperationUtil.set;
        //得到中文文本字符串
        String article = txtToString();
        JiebaSegmenter segmenter = new JiebaSegmenter();
        words = segmenter.sentenceProcess(article).toString().replaceAll("(?:\\[|null|\\]| +)", "").split(",");
        ITrie<Integer> wordIdTrie = classifier.model.wordIdTrie;
        for (String word : words) {
            Integer id = wordIdTrie.get(word.toCharArray());
            if (id != null) {
                WORD_MAP.put(id, word);
            }
            if (id == null) {
                continue;
            }
            if (word.length() < 2) {
                continue;
            }
            if (!wf_map.containsKey(word)) {
                wf_map.put(word, 1);
            } else {
                int frequency = wf_map.get(word);
                wf_map.put(word, ++frequency);
            }
        }
        removeStopWords(wf_map, set);
        int i = 1;
        for (Map.Entry<String, Integer> entry : wf_map.entrySet()) {
            if ((i++) % 12 != 0) {
                wf.append(entry.getKey()).append(": ").append(entry.getValue()).append("\t\t");
            } else {
                wf.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }
        FileOperationUtil.writeFile(WORD_FREQUENCY_PATH, wf.toString());
        predict = SVMClassifierUtil.predict(classifier, article);
    }

    /**
     * 统计词频前number
     */
    public void getTopNumberWord() {
        Map.Entry<String, Integer> entry;
        int size = wf_map.size();
        for (int i = 0; i < size; i++) {
            entry = getMaxEntry(wf_map);
            list.add(entry);
        }
        for (int i = 0; i < size - 1; i++) {
            top_word.append(list.get(i)).append("\n");
        }
        top_word.append(list.get(size - 1));
        FileOperationUtil.writeFile(TOP_WORD_PATH, top_word.toString());
    }

    /**
     * 去除词频表中的停用词
     *
     * @param map 分词map
     * @param set 停用词set
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
     * @param map 分词map
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
     */
    public String txtToString() throws IOException {

        return FileOperationUtil.readFile(CLEAR_DATAFILE_PATH);
    }

}
