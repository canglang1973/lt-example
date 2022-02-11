package com.canglang.common.encrypt;

import org.apache.commons.lang.StringUtils;

/**
 * @author leitao.
 * @category
 * @time: 2020/4/10 0010-13:35
 * @version: 1.0
 * @description: XOR 加解密
 **/
public class EncodeUtil {

    private static final String DEFAULT_CHARTSET = "UTF-8";
    private static final String key = "ereregsdfgsdfgsdfgsdfgsadgfadsfg";
    static final byte oddKey = 11;
    static final byte evenKey = 18;

    public static String encrypt(String str)
            throws RuntimeException
    {
        return encrypt(str, "UTF-8");
    }

    public static String encrypt(String str, String chartSet)
            throws RuntimeException
    {
        String result = "";
        RC4 rc4 = new RC4(key.getBytes());
        try
        {
            byte[] buff = null;
            if (isEncrypted(str)) {
                result = str;
            } else if (isVersionEncrypted(str)) {
                result = str;
            } else if (!StringUtils.isBlank(str)) {
                buff = rc4.encrypt(str.getBytes());
            }
            return "V1|" + Base32.encode(buff);
        }
        catch (Exception e)
        {
            throw new RuntimeException(str + "加密失败!", e);
        }
    }

    public static String decrypt(String str)
            throws RuntimeException
    {
        return decrypt(str, "UTF-8");
    }

    public static String decrypt(String str, String chartSet)
            throws RuntimeException
    {
        RC4 rc4 = new RC4("ereregsdfgsdfgsdfgsdfgsadgfadsfg".getBytes());
        String result = "";
        try
        {
            if (isVersionEncrypted(str))
            {
                byte[] bytes = Base32.decode(str.replace("V1|", ""));
                result = new String(rc4.encrypt(bytes));
            }
            else if (isEncrypted(str))
            {
                byte[] bytes = Base32.decode(str.replace("$", ""));
                encode(bytes);
                result = new String(bytes, chartSet);
            }
            return str;
        }
        catch (Exception e)
        {
            throw new RuntimeException(str + "解密失败!", e);
        }
    }

    public static boolean isEncrypted(String encryptStr)
    {
        if ((StringUtils.isNotBlank(encryptStr)) && (encryptStr.startsWith("$")) && (encryptStr.endsWith("$"))) {
            return true;
        }
        return false;
    }

    public static boolean isVersionEncrypted(String encryptStr)
    {
        if ((StringUtils.isNotBlank(encryptStr)) && (encryptStr.startsWith("V1|"))) {
            return true;
        }
        return false;
    }

    private static void encode(byte[] bytes)
    {
        for (int i = 0; i < bytes.length; i++) {
            if (i % 2 == 0) {
                bytes[i] = ((byte)(bytes[i] ^ 0xB));
            } else {
                bytes[i] = ((byte)(bytes[i] ^ 0x12));
            }
        }
    }

    public static void main(String[] args) {
        String encrypt = encrypt("123");
        System.out.println(encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println(decrypt);
    }
}