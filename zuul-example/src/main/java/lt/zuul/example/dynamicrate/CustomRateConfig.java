package lt.zuul.example.dynamicrate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author leitao.
 * @time: 2017/12/5  10:36
 * @version: 1.0
 * @description:
 **/
@Configuration
public class CustomRateConfig {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public RateMapping rateMappingLocator() {
        RateMapping rateMapping  = new RateMapping();
        rateMapping.put("APP",new Rate());
        rateMapping.put("APP1",new Rate());
        List<RateVo> rateVos = locateDB(jdbcTemplate);
        for (RateVo rateVo : rateVos){
            rateMapping.put(rateVo.getRateKey(),new Rate(rateVo.getRate(),rateVo.getDefaultRate(),rateVo.getWarmupPeriod(),true));
        }
        return rateMapping;
    }

    public   List<RateVo>  locateDB(JdbcTemplate jdbcTemplate){
        return jdbcTemplate.query("select rate rate,`key` rateKey from rate_limiter",new BeanPropertyRowMapper<>(RateVo.class));
    }
}
