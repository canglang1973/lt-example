package lt.storm.example.worldcount;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author leitao.
 * @time: 2017/12/13  14:45
 * @version: 1.0
 * @description: WordReader(Spout)，用于从外部数据源words.txt中获取数据
 *
 * Spout实例方法被调用的顺序为：getComponentConfiguration()-->declareOutputFields----->open-->active-->nextTuple(死循环)-->close。
 **/
public class WordReaderSpout implements IRichSpout {

    private SpoutOutputCollector collector;
    private BufferedReader reader;
    private boolean completed = false;

    /**
     * 当Spout被创建之后，这个方法会被调用
     * <p>
     * 当Spout被实例化之后，open方法会被调用一次。通常情况下，这个方法中，我们会做一些初始化的动作。
     * 例如建立与外部数据源的链接，在本例中，就是获取到了外部数据文件words.txt的读取器。
     * 这个方法还传递进来了3个参数。
     * 参数1：Map conf
     * conf对象中维护的是一些配置信息。例如在这个方法中，我们获取words.txt文件的位置就是通过
     * conf.get("fileName")这种方式来进行的。conf对象中还维护了一些其他的默认参数，读者自己可以将这个对象中的参数打印出来进行观察。
     * 参数2：TopologyContext context
     * 这个参数中，包含了Topology运行时的上下文信息。要知道这个对象的作用，最简单的方式就是查看这个对象中，有哪些方法和字段，此处不做过多讲解。
     * 参数3：SpoutOutputCollector collector
     * 从名字中，我们就可以看出，这个对象的作用，Spout输出收集器，我们之前说过Spout需要将数据以Tuple的形式发送给Bolt，
     * 就是通过这个对象的emit方法来实现的。
     */
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        System.out.println("WordReader.open(Map conf, TopologyContext context, SpoutOutputCollector collector)");
        String fileName = conf.get("fileName").toString();
        InputStream inputStream = WordReaderSpout.class.getClassLoader().getResourceAsStream(fileName);
        reader = new BufferedReader(new InputStreamReader(inputStream));
        this.collector = collector;
    }

    /**
     * 当Topology停止时，会调用这个方法
     */
    @Override
    public void close() {
        System.out.println("WordReader.close()");
    }

    @Override
    public void activate() {
        System.out.println("WordReader.activate()");
    }

    @Override
    public void deactivate() {
        System.out.println("WordReader.deactivate()");
    }

    /**
     * 这个方法做的惟一一件事情就是分发文件中的文本行
     * <p>
     * 这个方法会被不断的调用，因为Spout需要不断的从外部数据源中获取最新的数据，然后使用SpoutOutputCollector的emit方法来进行发射。
     */
    @Override
    public void nextTuple() {
        /**
         * 这个方法会不断的被调用，直到整个文件都读完了，我们将等待并返回。
         */
        if (completed) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                //什么也不做
            }
        }
        String str;
        try {
            int i = 0;
            // 读所有文本行
            while ((str = reader.readLine()) != null) {
                System.out.println("WordReader.nextTuple(),emits time:" + i++);
                /**
                 * 按行发布一个新值;
                 * SpoutOutputCollector的emit方法有很多种重载形式;
                 * 在本例中，我们使用的这个方法第一个参数是List集合。但是我们的代码中使用的却是Values这个对象;
                 * 这是因为Values对象是ArrayList集合的子类。读者可以自行查看这个类的源码。这里主要说明，我们构
                 * 造Values对象时，其构造方法接受一个可变参数，我们这里只传递了一个str，说明最终List集合中只有
                 * 一个元素。而且只能是一个元素。因为我们我们已经在declareOutputFields这个方法中声明过，只有一个字段line。
                 */
                this.collector.emit(new Values(str), str);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading tuple", e);
        } finally {
            completed = true;
        }
    }

    /**
     * 当一个Tuple处理成功时，会调用这个方法
     */
    @Override
    public void ack(Object msgId) {
        System.out.println("WordReader.ack(Object msgId):" + msgId);
    }

    /**
     * 当一个Tuple处理失败时，会调用这个方法
     */
    @Override
    public void fail(Object msgId) {
        System.out.println("WordReader.fail(Object msgId):" + msgId);
    }

    /**
     * 声明数据格式，即输出的一个Tuple中，包含几个字段
     * <p>
     * 这个方法的作用是，声明由这个Spout输出的流中的每一个Tuple，包含哪些字段。 之前我们提到过，在Topology中，
     * 不同的Spout和Bolt以及Bolt与Bolt之间形成数据流中，同一个Stream中Tuple的数据格式一定是相同的，不同的数据流
     * 中的Tuple的数据格式可能相同也可能不同。那么一个Tuple中的数据格式到底是怎么样的呢？就是在这个方法中定义的。
     * 例如在这里，我们声明了一个line字段。declarer .declare(new Fields( "line"));这就相当于宣告，每个Tuple中只
     * 能含有一个字段，这个字段的名字叫做line。如果一个Tuple中有多个字段，那么我们在这里就要声明多个字段。
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        System.out.println("WordReader.declareOutputFields(OutputFieldsDeclarer declarer)");
        declarer.declare(new Fields("line"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        System.out.println("WordReader.getComponentConfiguration()");
        return null;
    }
}
