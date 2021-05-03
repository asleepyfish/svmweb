import edu.upc.svmweb.util.ChineseWordSegmentationUtil;

import java.io.IOException;

public class ChineseWordSegmentationTest {
    public static void main(String[] args) throws IOException {
        ChineseWordSegmentationUtil cwsu = new ChineseWordSegmentationUtil();
        cwsu.getWordFrequency();
    }
}
