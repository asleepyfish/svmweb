import edu.upc.svmweb.util.FileOperationUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class RemoveStopWords {
    private static final String OLD_PATH = "data/项目文本/StopWords.txt";
    private static final String NEW_PATH = "data/项目文本/StopWords1.txt";

    public static void main(String[] args) throws IOException {
        FileOperationUtil.readStopWords(OLD_PATH);
        Set<String> set = FileOperationUtil.set;
        set.removeIf(s -> s.length() < 2);
        for (String s : set) {
            write(NEW_PATH, s);
        }

    }

    public static void write(String path, String data) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fw = new FileWriter(path, true);
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
