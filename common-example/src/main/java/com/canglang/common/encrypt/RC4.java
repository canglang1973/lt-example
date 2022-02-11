package com.canglang.common.encrypt;

/**
 * @author leitao.
 * @category
 * @time: 2020/4/10 0010-13:32
 * @version: 1.0
 * @description:
 **/
public class RC4 {

    private final int sboxLen;
    private int[] sBox;
    private int i = 0;
    private int j = 0;

    public RC4(byte[] key)
    {
        this(key, 256);
    }

    public RC4(byte[] key, int sboxLen)
    {
        this.sboxLen = sboxLen;
        init(key);
        this.i = 0;
        this.j = 0;
    }

    private void init(byte[] key)
    {
        this.sBox = new int[this.sboxLen];
        byte[] iK = new byte[this.sboxLen];
        for (int i = 0; i < this.sboxLen; i++)
        {
            this.sBox[i] = i;
            iK[i] = key[(i % key.length)];
        }
        int j = 0;
        for (int i = 0; i < this.sboxLen; i++)
        {
            j = j + this.sBox[i] + iK[i] & 255 % this.sboxLen;
            int temp = this.sBox[i];
            this.sBox[i] = this.sBox[j];
            this.sBox[j] = temp;
        }
    }

    public byte[] encrypt(byte[] input)
    {
        return encrypt(input, 0, input.length);
    }

    public byte[] encrypt(byte[] input, int off, int len)
    {
        if (input == null) {
            return null;
        }
        byte[] ret = new byte[len];
        for (int x = 0; x < len; x++)
        {
            this.i = ((this.i + 1) % this.sboxLen);
            this.j = (this.j + this.sBox[this.i] & 255 % this.sboxLen);
            int temp = this.sBox[this.i];
            this.sBox[this.i] = this.sBox[this.j];
            this.sBox[this.j] = temp;

            int t = this.sBox[this.i] + this.sBox[this.j] & 255 % this.sboxLen;

            ret[x] = ((byte)((input[(x + off)] ^ this.sBox[t]) & 0xFF));
        }
        return ret;
    }

    public void encryptSelf(byte[] input, int off, int len)
    {
        if (input == null) {
            return;
        }
        int max = off + len;
        for (int x = off; x < max; x++)
        {
            this.i = ((this.i + 1) % this.sboxLen);
            this.j = (this.j + this.sBox[this.i] & 255 % this.sboxLen);
            int temp = this.sBox[this.i];
            this.sBox[this.i] = this.sBox[this.j];
            this.sBox[this.j] = temp;

            int t = this.sBox[this.i] + this.sBox[this.j] & 255 % this.sboxLen;
            input[x] = ((byte)((input[x] ^ this.sBox[t]) & 0xFF));
        }
    }

    public byte encrypt(byte input)
    {
        this.i = ((this.i + 1) % this.sboxLen);
        this.j = (this.j + this.sBox[this.i] & 255 % this.sboxLen);
        int temp = this.sBox[this.i];
        this.sBox[this.i] = this.sBox[this.j];
        this.sBox[this.j] = temp;

        int t = this.sBox[this.i] + this.sBox[this.j] & 255 % this.sboxLen;
        return (byte)((input ^ this.sBox[t]) & 0xFF);
    }

    public static void main(String[] args)
    {
        String inputStr = "水电费根深蒂固的第三方";
        String key = "12342354234";
        System.out.println("begin----------------" + System.currentTimeMillis());
        RC4 rc4 = new RC4(key.getBytes());
        byte[] buff = rc4.encrypt(inputStr.getBytes());

        System.out.println(new String(buff));
        System.out.println("crypted--------------" + System.currentTimeMillis());

        rc4 = new RC4(key.getBytes());
        System.out.println(new String(rc4.encrypt(buff)));
        System.out.println("udcrypted------------" + System.currentTimeMillis());
    }
}