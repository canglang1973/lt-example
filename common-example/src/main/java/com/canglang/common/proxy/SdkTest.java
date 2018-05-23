package com.canglang.common.proxy;

import com.winxuan.open.sdk.DefaultWinxuanClient;
import com.winxuan.open.sdk.WinxuanApiException;
import com.winxuan.open.sdk.WinxuanClient;
import com.winxuan.open.sdk.request.shopitem.ShopItemsRequest;
import com.winxuan.open.sdk.response.shopitem.ShopItemsResponse;

/**
 * @author leitao.
 * @category
 * @time: 2018/5/23  14:40
 * @version: 1.0
 * @description:
 **/
public class SdkTest {
    public static void main(String[] args) throws WinxuanApiException {
        String URL = "http://gw.api.winxuan.com/router/rest";
        WinxuanClient client = new DefaultWinxuanClient(URL, "100052", "d46f00b4f757a3c045a7bb5e7e0a1208",
                "e36cf3458eb59f7820f5989ede69066c", 60000, 60000);
        ShopItemsRequest request = new ShopItemsRequest();
        request.setItemIds("1201559706");
        ShopItemsResponse response = client.execute(request);
        System.out.println();
    }
}
