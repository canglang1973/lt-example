package lt.ratelimiter.example.guava.dynamicrate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leitao.
 * @time: 2017/12/4  17:26
 * @version: 1.0
 * @description:
 **/
@RestController
public class RefreshRateController {

    @Autowired
    RefreshLimiterRateService refreshLimiterRateService;

    /**
     * 更新单个速率
     * @param rateVo
     */
    @RequestMapping(value = "/refreshRate")
    public void refreshRate(RateVo rateVo){
        System.out.println("RefreshRateController rate="+rateVo.getRate()+";key="+rateVo.getRateKey());
        refreshLimiterRateService.refreshRate(rateVo);
    }
}
