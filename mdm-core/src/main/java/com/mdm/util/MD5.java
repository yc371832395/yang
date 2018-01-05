package com.mdm.util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    /**
     * 默认的密码字符串组合
     */
    private static char hexDigits[] = {'a', 'b', 'c', 'd', 'e', 'f', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5'};
    private static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            nsaex.printStackTrace();
        }
    }

    /**
     * 生成文件的md5校验值
     */
    public static String getFileMD5String(File file) throws Exception {
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                messagedigest.update(buffer, 0, numRead);
            }
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null)
                fis.close();
        }

    }

    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }

    /**
     * 生成标准 备md5串
     */
    public static String getStandardMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString();
            // 16位的加密
            // return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println(getStandardMd5("123456"));
//        BufferedWriter br = null;
//        try {
//            if (args.length == 0) {
//                System.err.println("please input the license file path!");
//                System.exit(-1);
//            } else {
//                long begin = System.currentTimeMillis();
//                String license = args[0];
//                File big = new File(license);
//                String keyPath = big.getParent() + File.separator + "key.dat";
//                File key = new File(keyPath);
//                String md5 = getFileMD5String(big);
//                br = new BufferedWriter(new FileWriter(key));
//                br.write(md5);
//                br.flush();
//                long end = System.currentTimeMillis();
//                System.out.println("make key end, cost time:" + ((end - begin) / 1000) + "s,key file is : "
//                        + key.getAbsolutePath());
//                System.exit(0);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(-1);
//        } finally {
//            if (br != null) {
//                br.close();
//            }
//        }
    }
}
