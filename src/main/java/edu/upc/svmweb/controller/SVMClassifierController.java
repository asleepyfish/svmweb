package edu.upc.svmweb.controller;

import edu.upc.svmweb.classifier.SVMClassifier;
import edu.upc.svmweb.util.ChineseWordSegmentationUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping(value = "/getLinearSVM")
public class SVMClassifierController {
    @ResponseBody
    @RequestMapping(value = "/getLinearSVMClassifier", method = RequestMethod.POST)
    public String getLinearSVMClassifier() throws IOException {
        String predict = ChineseWordSegmentationUtil.predict;
        double[] probs = SVMClassifier.probs;
        String sports = "体育: " + probs[0];
        String healthy = "健康: " + probs[1];
        String military = "军事: " + probs[2];
        String education = "教育: " + probs[3];
        String travel = "旅游: " + probs[4];
        String car = "汽车: " + probs[5];
        String finance = "财经: " + probs[6];
        return "基于SVM预测各分类可能性为:" + "\n\n" + sports + "\n\n" + healthy + "\n\n" + military + "\n\n" + education + "\n\n" + travel + "\n\n" + car + "\n\n" + finance + "\n\n" + "该网页为" + predict + "类";
    }
}
