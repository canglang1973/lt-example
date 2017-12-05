package lt.zuul.example.dynamicrate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leitao.
 * @time: 2017/12/4  17:11
 * @version: 1.0
 * @description:
 **/
@Service
public class RefreshLimiterRateService {

    @Autowired
    RateMapping rateMapping;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomRateConfig customRateConfig;

    /**
     * 刷新删除旧的放入新的
     * @param rateVo
     */
    public void refreshRate(RateVo rateVo) {
        if (StringUtils.isNotBlank(rateVo.getRateKey())){
            rateMapping.put(rateVo.getRateKey(),new Rate(rateVo.getRate(),rateVo.getDefaultRate(),rateVo.getWarmupPeriod(),true)); //相同key会覆盖
            save2DB(rateVo.getRateKey(),rateVo.getRate(),jdbcTemplate);
        }else {
            //加载数据库中的速率
            List<RateVo> rateVos = customRateConfig.locateDB(jdbcTemplate);
            for (RateVo vo : rateVos){
                rateMapping.put(vo.getRateKey(),new Rate(vo.getRate(),vo.getDefaultRate(),vo.getWarmupPeriod(),true));
            }

        }
    }

    /**
     * 刷新时将数据保存到数据库中(异步)
     * @param key
     * @param newRate
     */
    private  void save2DB(String key,double newRate,JdbcTemplate jdbcTemplate){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String sql= "update rate_limiter set rate = ? where `key` = ?";
                jdbcTemplate.update(sql,newRate,key);
            }
        });
        thread.start();
    }
}
