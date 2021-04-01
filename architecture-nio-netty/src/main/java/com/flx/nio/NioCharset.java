package com.flx.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.SortedMap;

/**
 * @Author Fenglixiong
 * @Create 2021/3/31 2:16
 * @Description
 * 编码：字符串--->字节数组
 * 解码：字节数组--->字符串
 **/
public class NioCharset {

    public static void main(String[] args) throws CharacterCodingException {

        showAllCharset();

        charEncoder();

    }

    private static void charEncoder() throws CharacterCodingException {
        Charset gbk = Charset.forName("GBK");
        System.out.println("gbk = "+gbk);

        //获取编码器
        CharsetEncoder gbkEncoder = gbk.newEncoder();
        //获取解码器
        CharsetDecoder gbkDecoder = gbk.newDecoder();
        //创建字符串缓冲区
        CharBuffer cBuffer = CharBuffer.allocate(1024);
        cBuffer.put("你好，世界！");
        //编码
        cBuffer.flip();
        ByteBuffer bBuffer = gbkEncoder.encode(cBuffer);
        //解码
        CharBuffer decode = gbkDecoder.decode(bBuffer);
        System.out.println(decode.toString());
    }

    /**
     * 显示所有的编码
     */
    private static void showAllCharset() {
        SortedMap<String, Charset> charsetSortedMap = Charset.availableCharsets();

        charsetSortedMap.forEach((k,v)->{
            System.out.println("k = "+k+",v = "+v.name());
        });
    }

}
