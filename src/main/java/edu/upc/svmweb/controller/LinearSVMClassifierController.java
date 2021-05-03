package edu.upc.svmweb.controller;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import edu.upc.svmweb.classifier.LinearSVMClassifier;
import edu.upc.svmweb.util.FileOperationUtil;
import edu.upc.svmweb.util.LinearSVMClassifierUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping(value = "/getLinearSVM")
public class LinearSVMClassifierController {
    private static final String CLEAR_DATA_PATH = "data/项目文本/ClearData.txt";
    private static final String WORD_FREQUENCY_PATH = "data/项目文本/WordFrequency.txt";
    private static final String TOP_WORD_PATH = "data/项目文本/TopWord.txt";

    @ResponseBody
    @RequestMapping(value = "/getLinearSVMClassifier", method = RequestMethod.POST)
    public static String getLinearSVMClassifier() throws IOException {
        String clear_data = FileOperationUtil.readFile(CLEAR_DATA_PATH);
        String word_frequency = FileOperationUtil.readFile(WORD_FREQUENCY_PATH);
        String top_word = FileOperationUtil.readFile(TOP_WORD_PATH);
        IClassifier classifier = new LinearSVMClassifier(LinearSVMClassifierUtil.trainOrLoadModel());
        String clear_data_category = LinearSVMClassifierUtil.predict(classifier, clear_data);
        String word_frequency_category = LinearSVMClassifierUtil.predict(classifier, word_frequency);
        String top_word_category = LinearSVMClassifierUtil.predict(classifier, top_word);
        String category;
        if (clear_data_category.equals(word_frequency_category)) {
            category = clear_data_category;
        } else {
            if (clear_data_category.equals(top_word_category)) {
                category = clear_data_category;
            } else if (word_frequency_category.equals(top_word_category)) {
                category = word_frequency_category;
            } else {
                category = clear_data_category;
            }
        }
        System.out.println(clear_data_category);
        System.out.println(word_frequency_category);
        System.out.println(top_word_category);
        category = "该网页为" + category + "类";
        return category;
    }
}
