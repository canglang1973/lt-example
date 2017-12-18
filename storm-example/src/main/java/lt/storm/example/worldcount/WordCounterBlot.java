package lt.storm.example.worldcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leitao.
 * @time: 2017/12/13  15:13
 * @version: 1.0
 * @description: 这个Bolt将统计到的最终结果放到一个map集合中。并在Bolt销毁时，调用cleanup方法时，打印出统计结果。
 **/
public class WordCounterBlot implements IRichBolt {

    Integer id;
    String name;
    Map<String, Integer> counters;
    private OutputCollector collector;

    /**
     * 初始化
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.counters = new HashMap<String, Integer>();
        this.collector = collector;
        this.name = context.getThisComponentId();
        this.id = context.getThisTaskId();
    }

    /**
     * 为每个单词计数
     */
    @Override
    public void execute(Tuple input) {
        System.out.println("WordCounter.execute()");
        String str = input.getString(0);
        /**
         * 如果单词尚不存在于map，我们就创建一个，如果已在，我们就为它加1
         */
        if (!counters.containsKey(str)) {
            counters.put(str, 1);
        } else {
            Integer c = counters.get(str) + 1;
            counters.put(str, c);
        }
        //对元组作为应答
        collector.ack(input);
    }

    /**
     * 当Bolt销毁时，我们会显示单词数量
     */
    @Override
    public void cleanup() {
        for (Map.Entry<String, Integer> entry : counters.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            writeToFile("E:/result.txt", entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("WordCounter.cleanup()");
    }

    /**
     * 这个Bolt因为不需要继续输出，所以declareOutputFields方法中仅仅是打印了一句话。
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        System.out.println("WordCounter.declareOutputFields()");
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        System.out.println("WordCounter.getComponentConfiguration()");
        return null;
    }

    private synchronized void writeToFile(String file, String content) {
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
