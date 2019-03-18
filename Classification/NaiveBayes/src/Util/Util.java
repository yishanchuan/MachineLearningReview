package Util;

import model.Example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Util {

    //读取文件，封装数据
    public static ArrayList<Example> loadData(String data_path, String label){
        ArrayList<Example> dataList = new ArrayList<Example>();
        String message = new String();
        BufferedReader input = null;
        try{
            input = new BufferedReader(new FileReader(data_path));
            while((message = input.readLine())!= null){
                String[] info = message.split(label);
                info[1] = cleanData(info[1]);
                if(info[1]!=null){
                    Example data = new Example(info[0], info[1]);
                    dataList.add(data);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataList;
    }

    /*
    * 清洗数据，去掉非字母的字符，和字节长度小于2的单词
    * */
    private static String cleanData(String info) {
        //去标点
        Pattern p = Pattern.compile("\\w");
        String[] tempInfo = p.split(info);
        info = "";

        for(int i=0; i<tempInfo.length; i++){
            if(tempInfo[i].length()>=3){
                info+=tempInfo[i]+" ";
            }
        }

        info = info.toLowerCase();
        if(info.equals("")){
            return null;//经观察，处理后有25条数据为空
        }else {
            return info;
        }
    }

    //按比例把数据集分为训练集和测试集(书上的方法测试集和训练集重复，不应该作为参考)
    public static ArrayList<ArrayList<Example>> dataProcessing2(ArrayList<Example> dataSetList, double trainRate){
        ArrayList<ArrayList<Example>> trainTestSet = new ArrayList<ArrayList<Example>>();
        ArrayList<Example> trainSet = new ArrayList<Example>();
        ArrayList<Example> testSet = new ArrayList<Example>();
        int dataSize = dataSetList.size();
        int trainLength = (int)(dataSize * trainRate);
        int[] sum = new int[dataSize];
        for(int i=0; i< dataSize;i++){
            sum[i] = i;
        }
        int num = dataSize;
        for(int i=0; i< trainLength; i++){
            //这里用随机数划分数据集，增加鲁棒性
            int temp = (int)(Math.random() *(num--));
            trainSet.add(dataSetList.get(sum[i]));
            sum[temp] = sum[num];
        }
        trainTestSet.add(trainSet);
        int m=0;
        for(int i=0;i<dataSize-trainLength;i++){
            testSet.add(dataSetList.get(sum[i]));
            if(trainSet.contains(dataSetList.get(sum[i]))){
                m++;
                System.out.println("出现重复："+sum[i]);
            }
        }
        trainTestSet.add(testSet);
        System.out.println(m+","+dataSize+"\n"+trainLength);
        return trainTestSet;
    }

    //这样直接切分开才是更合适的
    public static ArrayList<ArrayList<Example>> dataProcessing(ArrayList<Example> dataSetList, double trainRate) {
        ArrayList<ArrayList<Example>> trainTestSet = new ArrayList<ArrayList<Example>>();
        ArrayList<Example> trainSet = new ArrayList<>();
        ArrayList<Example> testSet = new ArrayList<>();
        int dataSize = dataSetList.size();
        int trainLength = (int) (dataSize * trainRate);
        for(int i=0;i<trainLength;i++){
            trainSet.add(dataSetList.get(i));
        }
        for(int j=trainLength;j<dataSize;j++){
            testSet.add(dataSetList.get(j));
        }
        trainTestSet.add(trainSet);
        trainTestSet.add(testSet);
        return trainTestSet;
    }

    public static void main(String[] args){
        ArrayList<Example> exampleList = loadData("data/SMSSpamCollection.txt","\t");
        exampleList=(ArrayList)exampleList.subList(1,100);
        dataProcessing(exampleList,0.8);
    }
}