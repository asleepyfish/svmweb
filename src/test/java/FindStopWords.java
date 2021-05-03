import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FindStopWords {
    public static Map<String, String> map = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {

        read("data/项目文本/baidu_stopwords.txt");
        read("data/项目文本/cn_stopwords.txt");
        read("data/项目文本/hit_stopwords.txt");
        read("data/项目文本/scu_stopwords.txt");
        Set<Map.Entry<String, String>> set = map.entrySet();
        Iterator<Map.Entry<String, String>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            write("data/项目文本/StopWords.txt", entry.getKey());
        }
    }

    public static void read(String path) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            map.put(line, "stopword");
        }
    }

    public static void write(String path, String data) throws IOException {
        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(data);
        bw.newLine();
        bw.flush();
        bw.close();
    }
}
