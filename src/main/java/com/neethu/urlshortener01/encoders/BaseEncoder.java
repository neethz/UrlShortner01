package com.neethu.urlshortener01.encoders;

public class BaseEncoder {

    private final static String base62= new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

    public static String encode(int value){
        StringBuilder send = new StringBuilder();
        int num= value;
        while(num>0){

            send.insert(0,base62.charAt(num%62));
            num=num/62;
        }
        return send.toString();
    }

}
