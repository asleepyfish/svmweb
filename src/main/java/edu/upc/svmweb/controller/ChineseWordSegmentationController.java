package edu.upc.svmweb.controller;

import edu.upc.svmweb.util.ChineseWordSegmentationUtil;
import edu.upc.svmweb.util.FileOperationUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/getWord")
public class ChineseWordSegmentationController {
    private static String wordFrequencyFilePath = "data/项目文本/WordFrequency.txt";
    private static String topWordFilePath = "data/项目文本/TopWord.txt";

    @ResponseBody
    @RequestMapping(value = "/getWordFrequency", method = RequestMethod.POST)
    public static String getWordFrequency() throws IOException {
        ChineseWordSegmentationUtil cws = new ChineseWordSegmentationUtil();
        cws.wordFrequency();
        FileOperationUtil.writeFile(wordFrequencyFilePath, cws.wq.toString());
        return cws.wq.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getTopTenWord", method = RequestMethod.POST)
    public static String getTopTenWord() throws IOException {
        ChineseWordSegmentationUtil cws = new ChineseWordSegmentationUtil();
        cws.wordFrequency();
        FileOperationUtil.writeFile(topWordFilePath, cws.top_word.toString());
        return cws.top_word.toString();
    }
}
