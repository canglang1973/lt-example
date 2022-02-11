package com.canglang.common.encrypt;

/**
 * @author leitao.
 * @category
 * @time: 2020/4/10 0010-13:15
 * @version: 1.0
 * @description:
 **/

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESTool {

    private byte[] initVector = {50, 55, 54, 53, 52, 51, 50, 49, 56, 39, 54, 53, 51, 35, 50, 49};

    public static String findKeyById(String appid) {
        String key = "12345678901234567890123456789012";
        return key;
    }

    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(1, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());

        return new BASE64Encoder().encode(encrypted);
    }

    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());

            cipher.init(2, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original);
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args)throws Exception {
        String cKey = "1234567890abcdef";

        String cSrc = "user=15063142270,pwd=110120";
        System.out.println(cSrc);

        long lStart = System.currentTimeMillis();
        String enString = encrypt(cSrc, cKey);
        String enString1 = encrypt(cSrc, cKey);
        String enString2 = decrypt(enString1, cKey);
        System.out.println("加密后的字符串" + enString);
        System.out.println("加密后的字符串" + enString1);
        System.out.println("加密后的字符串" + enString2);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时" + lUseTime + "毫秒");

        lStart = System.currentTimeMillis();
        String DeString = decrypt("ZCS64QUTl4aPd0BTJD/46la/Ur3NO85TAFCvj9TY99c=", cKey);
        System.out.println("解密后的字符串" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时" + lUseTime + "毫秒");
    }
}
