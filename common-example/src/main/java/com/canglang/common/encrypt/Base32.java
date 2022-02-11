package com.canglang.common.encrypt;

/**
 * @author leitao.
 * @category
 * @time: 2020/4/10 0010-13:36
 * @version: 1.0
 * @description:
 **/
public class Base32 {
    private static final char[] ALPHABET = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7'};
    private static final byte[] DECODE_TABLE = new byte['?'];

    static {
        for (int i = 0; i < DECODE_TABLE.length; i++) {
            DECODE_TABLE[i] = -1;
        }
        for (int i = 0; i < ALPHABET.length; i++) {
            DECODE_TABLE[ALPHABET[i]] = ((byte) i);
            if (i < 24) {
                DECODE_TABLE[Character.toLowerCase(ALPHABET[i])] = ((byte) i);
            }
        }
    }

    public static String encode(byte[] data) {
        char[] chars = new char[data.length * 8 / 5 + (data.length % 5 != 0 ? 1 : 0)];

        int i = 0;
        int j = 0;
        for (int index = 0; i < chars.length; i++) {
            if (index > 3) {
                int b = data[j] & 255 >> index;
                index = (index + 5) % 8;
                b <<= index;
                if (j < data.length - 1) {
                    b |= (data[(j + 1)] & 0xFF) >> 8 - index;
                }
                chars[i] = ALPHABET[b];
                j++;
            } else {
                chars[i] = ALPHABET[(data[j] >> 8 - (index + 5) & 0x1F)];
                index = (index + 5) % 8;
                if (index == 0) {
                    j++;
                }
            }
        }
        return new String(chars);
    }

    public static byte[] decode(String s)
            throws Exception {
        char[] stringData = s.toCharArray();
        byte[] data = new byte[stringData.length * 5 / 8];

        int i = 0;
        int j = 0;
        for (int index = 0; i < stringData.length; i++) {
            int val;
            try {
                val = DECODE_TABLE[stringData[i]];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new Exception("Illegal character");
            }
            if (val == 255) {
                throw new Exception("Illegal character");
            }
            if (index <= 3) {
                index = (index + 5) % 8;
                if (index == 0) {
                    int tmp97_94 = (j++);
                    byte[] tmp97_91 = data;
                    tmp97_91[tmp97_94] = ((byte) (tmp97_91[tmp97_94] | val));
                } else {
                    int tmp110_108 = j;
                    byte[] tmp110_107 = data;
                    tmp110_107[tmp110_108] = ((byte) (tmp110_107[tmp110_108] | val << 8 - index));
                }
            } else {
                index = (index + 5) % 8;
                int
                        tmp141_138 = (j++);
                byte[] tmp141_135 = data;
                tmp141_135[tmp141_138] = ((byte) (tmp141_135[tmp141_138] | val >> index));
                if (j < data.length) {
                    int tmp161_159 = j;
                    byte[] tmp161_158 = data;
                    tmp161_158[tmp161_159] = ((byte) (tmp161_158[tmp161_159] | val << 8 - index));
                }
            }
        }
        return data;
    }
}