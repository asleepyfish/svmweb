package edu.upc.svmweb.model;

import com.hankcs.hanlp.classification.models.AbstractModel;
import de.bwaldvogel.liblinear.Model;
import edu.upc.svmweb.util.TfIdfFeatureWeighterUtil;

/**
 * 线性SVM模型
 */
public class SVMModel extends AbstractModel {
    //特征权重计算工具
    public TfIdfFeatureWeighterUtil featureWeighter;
    //SVM分类模型
    public Model svmModel;
}