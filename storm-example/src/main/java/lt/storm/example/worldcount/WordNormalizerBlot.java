package lt.storm.example.worldcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @author leitao.
 * @time: 2017/12/13  15:03
 * @version: 1.0
 * @description: IRichBolt实例的生命周期方法为：
 * getComponentConfiguration-->declareOutputFields()-->execute(执行多次)-->cleanup。prepare方法并不是生命周期方法。
 * 为什么这么说？我们可以看到在打印的日志中，WordNormalizer.prepare()执行了，但是WordCounter的prepare方法并没有执行。
 * 通常情况下，如果一个IRichBolt如果没有声明任何输出，即declareOutputFields方法返回的是null，则prepare方法不会执行。
 **/
public class WordNormalizerBlot implements IRichBolt {

    private OutputCollector collector;

    /**
     * 这个方法与Spout的open方法参数很类似，唯一不同的就是最后一个参数，Spout中的open方法传递的
     * 是SpoutOutPutCollector，这里传递的是OutputCollector。不过都是数据收集器。主要的区别是，
     * emit方法可以多出一个anchor或者anchors的参数，这与消息的可靠性保证有关
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        System. out.println("WordNormalizer.prepare()" );
        this. collector=collector ;
    }

    /**
     * *bolt*从单词文件接收到文本行，并标准化它。*文本行会全部转化成小写，并切分它，从中得到所有单词。
     *
     * 每当Bolt接受到一个Tuple的时候，就会调用一次execute方法。Tuple就是Spout传递给Bolt的数据。额..等等，
     * 我们在之前刚说过，本案例中WordReader发射数据时，使用的是下面这个方法：emit(List<Object> tuple, Object messageId)
     * 明明发送的是一个List集合。但是这里接受参数却是Tuple类型。实际上是没有错的，Tuple是一个接口，我们可以看看其实现类TupleImpl中的源码。
     * -- public class TupleImpl extends IndifferentAccessMap implements Seqable, Indexed, IMeta, Tuple {
     * -- private List<Object> values;
     * -- private int taskId;
     * -- private String streamId;
     * -- private GeneralTopologyContext context;
     * -- private MessageId id;
     * -- private IPersistentMap _meta;
     * 相信看到这个读者应该明白了，Tuple实际上是将Spout端发送的数据进行了一层封装，原来的List集合还在，此外还封装了一些元数据。
     *因为我们原来的Spout每次发送的List<Object>集合中，只有一个元素，因此，在这里，就可以通过input.getString(0);来获取。
     */
    @Override
    public void execute(Tuple input) {
        System.out.println("WordNormalizer.execute()");
        String sentence = input.getString(0);
        String[] words = sentence.split(" ");
        for (String word : words) {
            word = word.trim();
            if (!word.isEmpty()) {
                word = word.toLowerCase();
                /*//发布这个单词*/
                collector.emit(input, new Values(word));
            }
        }
        //对元组做出应答
        collector.ack(input);
    }

    @Override
    public void cleanup() {
        System. out.println("WordNormalizer.cleanup()" );
    }

    /**
     * 这个*bolt*只会发布“word”域
     * 因为Bolt还可以发送数据到下一级Bolt，因此，我们同样要指定这个Tuple中的数据格式。
     * 如果这个Bolt不要输出的话，我们就可以不声明。通常最后一个Bolt，因为没有下一级Bolt，这个方法就可以直接返回null。
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        System. out.println("WordNormalizer.declareOutputFields()" );
        declarer.declare(new Fields("word"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        System. out.println("WordNormalizer.getComponentConfiguration()" );
        return null;
    }
}
