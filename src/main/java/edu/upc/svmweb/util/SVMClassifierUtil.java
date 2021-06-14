package edu.upc.svmweb.util;

import de.bwaldvogel.liblinear.FeatureNode;
import edu.upc.svmweb.classifier.SVMClassifier;
import edu.upc.svmweb.model.SVMModel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.hankcs.hanlp.utility.Predefine.logger;

public class SVMClassifierUtil {
    //语料库路径
    public static final String CORPUS_FOLDER = "data/搜狗文本分类语料库微型版";
    //模型保存路径
    public static final String MODEL_PATH = "data/svm-classification-model.ser";
    //特征权重保存路径
    public static final String FEATURE_WEIGHT_PATH = "data/项目文本/FeatureWeight.txt";
    //保存特征权重的StringBuilder
    public StringBuilder feature_txt = new StringBuilder();

    public static String predict(SVMClassifier classifier, String text) {
        return classifier.classify(text);
    }

    /**
     * 获取特征权重的方法
     */
    public void getFeatureWeight() {
        Map<Integer, String> wordMap = ChineseWordSegmentationUtil.WORD_MAP;
        FeatureNode[] x = SVMClassifier.x;
        Map<String, Double> fw_map = new HashMap<>();
        //获取特征名和对应的权重
        for (FeatureNode featureNode : x) {
            int index = featureNode.getIndex() - 1;
            double value = featureNode.getValue();
            String word = wordMap.get(index);
            fw_map.put(word, value);
        }
        for (Map.Entry<String, Double> entry : fw_map.entrySet()) {
            String word = entry.getKey();
            double value = entry.getValue();
            if (word.length() > 1) {
                feature_txt.append(word).append(": ").append(value).append("\n");
            }
        }
        FileOperationUtil.writeFile(FEATURE_WEIGHT_PATH, feature_txt.toString());
    }

    public static SVMModel trainOrLoadModel() throws IOException {
        SVMModel model = (SVMModel) readObjectFrom(MODEL_PATH);
        if (model != null) {
            return model;
        }
        // 创建分类器
        SVMClassifier classifier = new SVMClassifier();
        // 训练后的模型支持持久化
        classifier.train(CORPUS_FOLDER, "UTF-8");
        model = classifier.getModel();
        saveObjectTo(model, MODEL_PATH);
        return model;
    }

    /**
     * 序列化对象
     *
     * @param o    存储对象
     * @param path 存储路径
     */
    public static void saveObjectTo(Object o, String path) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(o);
            oos.close();
        } catch (IOException e) {
            logger.warning("在保存对象" + o + "到" + path + "时发生异常" + e);
        }
    }

    /**
     * 反序列化对象
     *
     * @param path 读取路径
     * @return 读取模型
     */
    public static Object readObjectFrom(String path) {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream(path));
            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (Exception e) {
            logger.warning("在从" + path + "读取对象时发生异常" + e);
        }
        return null;
    }

}
