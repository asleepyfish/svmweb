package edu.upc.svmweb.controller;

import edu.upc.svmweb.classifier.SVMClassifier;
import edu.upc.svmweb.util.ChineseWordSegmentationUtil;
import edu.upc.svmweb.util.FileOperationUtil;
import edu.upc.svmweb.util.SVMClassifierUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/getWord")
public class ChineseWordSegmentationController {
    private static final String TOP_WORD_PATH = "data/项目文本/TopWord.txt";

    @ResponseBody
    @RequestMapping(value = "/getWordFrequency", method = RequestMethod.POST)
    public String getWordFrequency() throws IOException {
        SVMClassifier classifier = new SVMClassifier(SVMClassifierUtil.trainOrLoadModel());
        ChineseWordSegmentationUtil cws = new ChineseWordSegmentationUtil();
        cws.getWordFrequency(classifier);
        cws.getTopNumberWord();
        SVMClassifierUtil svmcu = new SVMClassifierUtil();
        svmcu.getFeatureWeight();
        return cws.wf.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getTopNumberWord", method = RequestMethod.POST)
    public String getTopNumberWord(@RequestParam(value = "number") Integer number) throws IOException {
        FileOperationUtil.readTopNumberWord(TOP_WORD_PATH, number);
        StringBuilder to = FileOperationUtil.top_word;
        FileOperationUtil.top_word = new StringBuilder();
        return to.toString();
    }
}
