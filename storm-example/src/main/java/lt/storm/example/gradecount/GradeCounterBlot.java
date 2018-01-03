package lt.storm.example.gradecount;

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
 * @time: 2017/12/22  11:02
 * @version: 1.0
 * @description:
 **/
public class GradeCounterBlot implements IRichBolt {
    Integer id;
    String name;
    Map<String, Double> counters;
    private OutputCollector collector;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.counters = new HashMap<String, Double>();
        this.collector = collector;
        this.name = context.getThisComponentId();
        this.id = context.getThisTaskId();
    }

    @Override
    public void execute(Tuple input) {
        String name = input.getString(0);
        double grade = input.getDouble(1);
        /**
         * 如果单词尚不存在于map，我们就创建一个，如果已在，我们就为它加1
         */
        if (!counters.containsKey(name)) {
            counters.put(name, grade);
        } else {
            double c = counters.get(name) + grade;
            counters.put(name, c);
        }
        //对元组作为应答
        collector.ack(input);
    }

    @Override
    public void cleanup() {
        for (Map.Entry<String, Double> entry : counters.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            writeToFile("D://result.txt", entry.getKey() + ": " + entry.getValue());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
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
