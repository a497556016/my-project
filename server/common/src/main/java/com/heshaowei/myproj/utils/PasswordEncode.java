package com.heshaowei.myproj.utils;


import java.security.MessageDigest;
import java.util.UUID;

public class PasswordEncode {
    /**
     * MD5方法
     *
     * @param text 明文
     * @param salt 密钥
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String text, String salt) {
        try {
            String dataStr = text + salt;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key  密钥
     * @param md5  密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) {
        //根据传入的密钥进行验证
        String md5Text = encrypt(text, key);
        if (md5Text.equalsIgnoreCase(md5)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        String salt = UUID.randomUUID().toString();
        String encodePwd = PasswordEncode.encrypt("123456", salt);
        System.out.println(salt);
        System.out.println(encodePwd);
        System.out.println(verify("123456", salt, encodePwd));
    }
}
