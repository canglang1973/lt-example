//package lt.ratelimiter.example.redis_lua.client.jedis;
//
//import lt.ratelimiter.example.redis_lua.client.Token;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * @author leitao.
// * @time: 2017/12/19  14:51
// * @version: 1.0
// * @description: 漏桶算法限流-测试
// **/
//public class LeakyBucketRateLimiterTest {
//
//    public static void main(String[] args) throws Exception {
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        LeakyBucketRateLimiterRedisClient client = new LeakyBucketRateLimiterRedisClient();
////        limiter.init("LeakyBucketRateLimiter","lei",300);
//        for (int j =0;j<10;j++){
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    for (int i = 0; i < 1000; i++) {
//                        Token acquire = client.acquireToken("lei", "LeakyBucketRateLimiter");
//                        if (acquire.isPass()) {
//                            client.getLeakyBucketRateLimiter().access("LeakyBucketRateLimiter", 1);
//                        }
//                        System.out.println(i + acquire.name());
//                    }
//                }
//            });
//        }
//
//    }
//}
