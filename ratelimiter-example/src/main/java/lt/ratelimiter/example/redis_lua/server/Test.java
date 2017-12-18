package lt.ratelimiter.example.redis_lua.server;

import lt.ratelimiter.example.redis_lua.server.dao.RateDao;

/**
 * @author leitao.
 * @time: 2017/12/11  10:56
 * @version: 1.0
 * @description:
 **/
public class Test {
    public static void main(String[] args){
        RateDao dao = new RateDao();
//        dao.selectAll();
//        dao.selectByName("APP");
//        dao.saveOrUpdate("sdgfdsg","asdfasdgffg",2345,3);
        dao.deleteByName("sdgfdsg");
    }
}
