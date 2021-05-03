package edu.upc.svmweb.util;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import edu.upc.svmweb.classifier.LinearSVMClassifier;
import edu.upc.svmweb.model.LinearSVMModel;

import java.io.*;

import static com.hankcs.hanlp.utility.Predefine.logger;

public class LinearSVMClassifierUtil {
    //语料库路径
    public static final String CORPUS_FOLDER = "data/搜狗文本分类语料库微型版";
    //模型保存路径
    public static final String MODEL_PATH = "data/svm-classification-model.ser";

    public static String predict(IClassifier classifier, String text) {
        return classifier.classify(text);
    }

    public static LinearSVMModel trainOrLoadModel() throws IOException {
        LinearSVMModel model = (LinearSVMModel) readObjectFrom(MODEL_PATH);
        if (model != null) {
            return model;
        }

        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory()) {
            System.err.println("暂时没有文本分类语料");
            System.exit(1);
        }
        // 创建分类器
        IClassifier classifier = new LinearSVMClassifier();
        // 训练后的模型支持持久化
        classifier.train(CORPUS_FOLDER);
        model = (LinearSVMModel) classifier.getModel();
        saveObjectTo(model, MODEL_PATH);
        return model;
    }

    /**
     * 序列化对象
     *
     * @param o
     * @param path
     * @return
     */
    public static boolean saveObjectTo(Object o, String path) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(o);
            oos.close();
        } catch (IOException e) {
            logger.warning("在保存对象" + o + "到" + path + "时发生异常" + e);
            return false;
        }
        return true;
    }

    /**
     * 反序列化对象
     *
     * @param path
     * @return
     */
    public static Object readObjectFrom(String path) {
        ObjectInputStream ois = null;
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
