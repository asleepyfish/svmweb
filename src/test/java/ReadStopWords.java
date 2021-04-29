import edu.upc.svmweb.util.FileOperationUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ReadStopWords {
    public static void main(String[] args) throws IOException {
        FileOperationUtil.readStopWords("data/项目文本/StopWords.txt");
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("做运动", 1);
        map.put("百度", 2);
        map.put("好人", 3);
        Set<String> set = FileOperationUtil.set;
        removeStopWords(map, set);
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            System.out.println(entry.getKey());
        }
    }

    public static void removeStopWords(Map<String, Integer> map, Set<String> set) {
        Set<String> keySet = map.keySet();
        for (String term : keySet) {
            if (set.contains(term)) {
                map.remove(term);
            }
        }
    }
}
