package edu.upc.svmweb.controller;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import edu.upc.svmweb.classifiers.LinearSVMClassifier;
import edu.upc.svmweb.util.LinearSVMClassifierUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping(value = "/getLinearSVM")
public class LinearSVMClassifierController {

    @ResponseBody
    @RequestMapping(value = "/getLinearSVMClassifier", method = RequestMethod.POST)
    public static String getLinearSVMClassifier(@RequestParam(value = "show", required = true) String pattern) throws IOException {
        IClassifier classifier = new LinearSVMClassifier(LinearSVMClassifierUtil.trainOrLoadModel());
        return LinearSVMClassifierUtil.predict(classifier, pattern) + "类";
    }
}
