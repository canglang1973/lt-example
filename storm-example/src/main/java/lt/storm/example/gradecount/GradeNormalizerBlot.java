package lt.storm.example.gradecount;

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
 * @time: 2017/12/22  10:56
 * @version: 1.0
 * @description:
 **/
public class GradeNormalizerBlot implements IRichBolt {
    private OutputCollector collector;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this. collector=collector ;
    }

    @Override
    public void execute(Tuple input) {
        String sentence = input.getString(0);
        String[] strings = sentence.split(" ");
        if (strings.length==2){
            String name = strings[0].trim();
            double grade = Double.parseDouble(strings[1].trim());
            collector.emit(input, new Values(name,grade));
        }
        //对元组做出应答
        collector.ack(input);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("name","grade"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
