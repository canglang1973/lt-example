package lt.storm.example.gradecount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.io.*;

/**
 * @author leitao.
 * @time: 2017/12/22  10:48
 * @version: 1.0
 * @description:
 **/
public class GradeCountTopo {

    public static void main(String[] args) throws Exception{
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("grade-reader",new GradeReaderSpout(),1);
        builder.setBolt("grade-normalizer",new GradeNormalizerBlot(),2).shuffleGrouping("grade-reader");
        builder.setBolt("grade-counter",new GradeCounterBlot(),1).fieldsGrouping("grade-normalizer",new Fields("name","grade"));
        StormTopology topology = builder.createTopology();

        Config conf = new Config();
        String fileName ="grade.txt";
        conf.put("fileName", fileName);
        conf.setDebug(false);
        if (args.length>0){
            StormSubmitter. submitTopology(args[0], conf, topology);
        }else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("grade-topo" , conf , topology);
            Thread. sleep(20000);
            cluster.shutdown();
        }
//            generatData();
    }

    /**
     * 构造测试数据
     */
    private static void generatData(){
        for (int i=0;i<100000;i++){
            String countent=null;
            if (i%3==0){
                countent = "leitao"+" "+Math.ceil(Math.random()*10);
            }
            if (i%3==1){
                countent = "mike"+" "+Math.ceil(Math.random()*10);
            }
            if (i%3==2){
                countent = "canglang"+" "+Math.ceil(Math.random()*10);
            }
            writeToFile("storm-example/grade.txt",countent);
        }
    }
    private static void writeToFile(String file, String content) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(new File(file), true));
            writer.println(content);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
