import Algorithm.NaiveBayes;
import Util.Util;
import model.Classification;
import model.Example;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.List;

public class MainClass {

    private static final String data_path = "data/SMSSpamCollection.txt";
    private static final double trainRate = 0.8;

    public static void main(String[] args){
        // 1、初始化分类器
        Classification classification = new Classification();

        // 2、读取数据
        ArrayList<Example> dataSetList = Util.loadData(data_path, "\t");

        // 3、按一定比例分测试集和训练集
        ArrayList<ArrayList<Example>> trainTestSet = Util.dataProcessing(dataSetList, trainRate);
        ArrayList<Example> trainingList = trainTestSet.get(0);
        ArrayList<Example> testList = trainTestSet.get(1);

        // 4、训练朴素贝叶斯分类器
        NaiveBayes naiveBayes = new NaiveBayes();
        classification = naiveBayes.training(trainingList);

        // 5、测试
        naiveBayes.test(testList, classification);
    }
}
