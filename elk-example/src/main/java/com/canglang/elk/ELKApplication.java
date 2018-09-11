//package com.canglang.elk;
//
//import org.apache.http.Header;
//import org.apache.http.HttpHost;
//import org.apache.http.RequestLine;
//import org.apache.http.util.EntityUtils;
//import org.elasticsearch.client.Request;
//import org.elasticsearch.client.Response;
//import org.elasticsearch.client.RestClient;
//
//import java.io.IOException;
//
///**
// * @author leitao.
// * @category
// * @time: 2018/8/27 0027-15:18
// * @version: 1.0
// * @description:
// **/
//public class ELKApplication {
//
//    public static void main(String[] args) throws IOException {
//
//        RestClient restClient = RestClient.builder(
//                new HttpHost("10.100.12.123", 9200, "http")).build();
//        //index/type/id
//        Request request = new Request("GET","/shop_item_compute-2018-08-27/SHOP_ITEM_COMPUTE/PJFtemUBnHxKlCMROalu");
//        Response response = restClient.performRequest(request);
//        RequestLine requestLine = response.getRequestLine();
//        HttpHost host = response.getHost();
//        int statusCode = response.getStatusLine().getStatusCode();
//        Header[] headers = response.getHeaders();
//        String responseBody = EntityUtils.toString(response.getEntity());
//
//        restClient.close();
//    }
//
//}
