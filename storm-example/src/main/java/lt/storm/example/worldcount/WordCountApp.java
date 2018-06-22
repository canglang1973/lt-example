package lt.storm.example.worldcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * @author leitao.
 * @time: 2017/12/13  15:16
 * @version: 1.0
 * @description:
 **/
public class WordCountApp {

    public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
        //定义拓扑
        /**
         * TopologyBuilder对象用于创建Topology。在我们设置完Spout和Bolt之后，最后通过对象的createTopology()
         * 方法来创建StormTopology对象。
         * Config对象是Storm中提供的一个配置类，其实Map的子类，还记得我们之前在WordReader中的open方法中，
         * 是通过conf对象来获取words.txt的文件的路径，从而创建BufferedReader。
         */
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader" , new WordReaderSpout());
        builder.setBolt("word-normalizer" , new WordNormalizerBlot()).shuffleGrouping("word-reader" );
        builder.setBolt("word-counter" , new WordCounterBlot()).fieldsGrouping("word-normalizer" , new Fields("word"));
        StormTopology topology = builder .createTopology();
        //配置

        Config conf = new Config();
        String fileName ="words.txt" ;
        conf.put("fileName" , fileName );
        conf.setDebug(false);

        /**运行拓扑
         * Storm支持两种运行方式，本地模式和远程模式。在本例中，我们使用LocalCluster这个类，模拟一个本地集群，
         * 从而使我们创建的代码在本地就可以运行，不用提交到Storm集群中。通常情况下，在开发的时候，我们会使用本地模式，
         * 在提测和产品环境下，我们才会将代码真正的提交到Storm集群去运行。
         */
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Getting-Started-Topologie" , conf , topology );
        Thread. sleep(20000);
        cluster.shutdown();
    }
}
