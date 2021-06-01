import com.huaban.analysis.jieba.JiebaSegmenter;
import edu.upc.svmweb.util.FileOperationUtil;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        /*SVMClassifier classifier = new SVMClassifier(SVMClassifierUtil.trainOrLoadModel());
        ChineseWordSegmentationUtil cwsu = new ChineseWordSegmentationUtil();
        cwsu.getWordFrequency(classifier);
        System.out.println(ChineseWordSegmentationUtil.predict);*/
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String s = segmenter.sentenceProcess(FileOperationUtil.readFile("data/项目文本/ClearData.txt")).toString().replaceAll("(?:\\[|null|\\]| +)", "");
        System.out.println(s);
    }
}
