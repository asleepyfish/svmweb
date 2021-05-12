package edu.upc.svmweb.controller;

import edu.upc.svmweb.util.FileOperationUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping(value = "/getFeature")
public class TextFeatureController {
    private static final String FEATURE_WEIGHT_PATH = "data/项目文本/FeatureWeight.txt";

    @ResponseBody
    @RequestMapping(value = "/getFeatureWeight", method = RequestMethod.POST)
    public String getFeatureWeight() throws IOException {
        FileOperationUtil.readFeatureWeight(FEATURE_WEIGHT_PATH);
        StringBuilder feature_txt = FileOperationUtil.feature_txt;
        FileOperationUtil.feature_txt = new StringBuilder();
        return feature_txt.toString();
    }
}
