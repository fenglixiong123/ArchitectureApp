package com.flx.ark.loader.custom;

/**
 * @Author: Fenglixiong
 * @Date: 2020/10/28 15:25
 * @Description: 加解密类加载
 */
public class SimpleEncrypt {

    private static final String plain = "Hello,ClassLoader";

    private static final byte ENCRYPT_FACTOR = (byte) 0xff;

    public static void main(String[] args) {
        byte[] bytes = plain.getBytes();
        byte[] encrypt = new byte[bytes.length];
        for (int i=0;i<bytes.length;i++){
            encrypt[i] = (byte) (bytes[i]^ENCRYPT_FACTOR);
        }
        System.out.println(new String(encrypt));

        byte[] decrypt = new byte[bytes.length];
        for (int i=0;i<bytes.length;i++){
            decrypt[i] = (byte) (encrypt[i]^ENCRYPT_FACTOR);
        }
        System.out.println(new String(decrypt));
    }

}
