package com.canglang.elk;




import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author leitao.
 * @category
 * @time: 2018/8/27 0027-16:52
 * @version: 1.0
 * @description:
 **/
public class ELKHighLevelCilentApplication {

    public static void main(String[] args) throws IOException {
        search();
    }

    private static void search() throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.100.12.123"), 9200));
        GetResponse response = client.prepareGet("shop_item_compute-2018-08-27", "SHOP_ITEM_COMPUTE", "PJFtemUBnHxKlCMROalu").get();

// on shutdown

        client.close();

    }
//    private static void restHighLevelClientTest() throws IOException {
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(new HttpHost("10.100.12.123", 9200, "http")));
//
//
//        GetRequest getRequest = new GetRequest("shop_item_compute-2018-08-27",
//                "SHOP_ITEM_COMPUTE","PJFtemUBnHxKlCMROalu");
//        //禁用_source检索，默认为启用
//        getRequest.fetchSourceContext(new FetchSourceContext(false));
//        //同步执行
//        GetResponse getResponse1 = client.get(getRequest);
//        client.close();
//    }



}
