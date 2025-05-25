package net.qihoo.corp.ms.umapp.common.util.safety;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.util.safety
 * @ClassName: MsUmappAESUtil
 * @Description:
 * @date 2022-10-11 17:14:49
 */
public class MsUmappAESUtil {

    /**
     * 对字符串进行sha1加密处理
     *
     * @param text
     * @return 加密后的字符串
     */
    private static String sha1(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(text.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(shaHex);
            }

            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取字符串的md5值
     *
     * @param text
     * @return
     */
    private static String getMd5(String text) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5bytes = md5.digest(text.getBytes());
            return bytesToHex(md5bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }

    /**
     * AES CBC PKCS#7填充解密
     *
     * @param appKey appKey
     * @param iv     填充向量
     * @param text   加密过的字符串
     * @return 解密后的字符串
     */
    private static String aesCBCDecrypt(String appKey, String iv, String text) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(appKey.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(text));
            return new String(original);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取11位时间戳
     *
     * @return
     */
    private static String getCurrentTimeStamp() {
        String timeStamp = System.currentTimeMillis() + "";
        return timeStamp.substring(0, 10);
    }

    /**
     * 对数组按数据字典进行排序
     *
     * @param arr 要排序的数据
     * @return 按字典排序后的字符串
     */
    private static String sort(String[] arr) {
        Arrays.sort(arr);
        StringBuilder builder = new StringBuilder();
        for (String s : arr) {
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     * 解密单点登录传过来的加密串
     *
     * @param appkey    用来解密推送的数据，获取方式见api文档
     * @param token     创建应用时任意填写的，用来生成signature
     * @param signature 签名信息
     * @param timeStamp 11位时间戳
     * @param nonce     随机字符串
     * @param text      加密字符串
     * @return 解密后的字符串，可以拿到event和ticket用于后续操作
     */
    public static String decrypt(String appkey, String token, String signature, String timeStamp, String nonce, String text) throws Exception {
        timeStamp = timeStamp == null ? timeStamp = getCurrentTimeStamp() : timeStamp;
        String[] arr = {token, timeStamp, nonce, text};
        String sortedString = sort(arr);
        String sha1String = sha1(sortedString);
        if (!sha1String.equals(signature)) {
            throw new Exception("签名校验错误");
        }
        String iv = getMd5(appkey).substring(0, 16);
        String decryptedText = aesCBCDecrypt(appkey, iv, text);
        return decryptedText;
    }


    // 编码
//    private static final String ENCODING = "UTF-8";
    //算法
//    private static final String ALGORITHM = "AES";
    // 默认的加密算法
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param data
     * @return String
     * @throws Exception
     * @author tyg
     * @date 2018年6月28日下午2:50:35
     */
    public static String encode(String KEY,String OFFSET,String data) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("ASCII"), "AES");
            IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
            return new String(Base64.getEncoder().encode(encrypted));//此处使用BASE64做转码。
        }catch (Exception e){
            return "";
        }


    }

    /**
     * 解密
     *
     * @param data
     * @return String
     * @throws Exception
     * @author tyg
     * @date 2018年6月28日下午2:50:43
     */
    public static String decode(String KEY,String data) {
        return aesCBCDecrypt(KEY,getMd5(KEY).substring(0, 16),data);
    }
}
