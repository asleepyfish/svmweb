package edu.upc.svmweb.util;

import edu.upc.svmweb.classifier.SVMClassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private final Map<String, Integer> wq_map = new ConcurrentHashMap<>();
    //文章的纯中文文本路径
    private static final String CLEAR_DATAFILE_PATH = "data/项目文本/ClearData.txt";
    //停用词文本路径
    private static final String STOPWORDS_PATH = "data/项目文本/StopWords.txt";
    //词频文本路径
    private static final String WORD_FREQUENCY_PATH = "data/项目文本/WordFrequency.txt";
    //预测分类结果路径
    private static final String PREDICT_PATH = "data/项目文本/predict.txt";

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
        predict = SVMClassifierUtil.predict(classifier, article);
        Map<Integer, Integer> tfMapIt = SVMClassifier.tfMapIt;
        Map<Integer, String> wordMap = SVMClassifier.WORD_MAP;
        for (Map.Entry<Integer, String> entry : wordMap.entrySet()) {
            if (entry.getKey() != null) {
                Integer key = entry.getKey() + 1;
                String word = entry.getValue();
                Integer frequency = tfMapIt.get(key);
                if (word.length() > 1 && frequency != null) {
                    wq_map.put(word, frequency);
                }
            }
        }
        removeStopWords(wq_map, set);
        int i = 1;
        for (Map.Entry<String, Integer> entry : wq_map.entrySet()) {
            if ((i++) % 11 != 0) {
                wf.append(entry.getKey()).append(": ").append(entry.getValue()).append("\t\t");
            } else {
                wf.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }
        FileOperationUtil.writeFile(WORD_FREQUENCY_PATH, wf.toString());
    }

    /**
     * 统计词频前number
     */
    public void getTopNumberWord() {
        Map.Entry<String, Integer> entry;
        //int size = wq_map.size() >= number ? number : wq_map.size();
        int size = wq_map.size();
        for (int i = 0; i < size; i++) {
            entry = getMaxEntry(wq_map);
            list.add(entry);
        }
        for (int i = 0; i < size - 1; i++) {
            top_word.append(list.get(i)).append("\n");
        }
        top_word.append(list.get(size - 1));
        FileOperationUtil.writeFile("data/项目文本/TopWord.txt", top_word.toString());
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

        return FileOperationUtil.readFile(CLEAR_DATAFILE_PATH);
    }

}
