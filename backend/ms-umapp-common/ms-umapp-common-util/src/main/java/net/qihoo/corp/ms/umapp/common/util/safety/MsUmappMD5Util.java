package net.qihoo.corp.ms.umapp.common.util.safety;

import java.security.MessageDigest;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.util.safety
 * @ClassName: MsUmappMD5Util
 * @Description:
 * @date 2022-10-11 17:17:08
 */
public class MsUmappMD5Util {
    public final static String MD5(String s, String salt) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = (s + salt).getBytes("UTF-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        String signData = "lipengfei6苹果手机2021-11-23 18:49:21";
        String sign = MsUmappMD5Util.MD5(signData, "LOTTERY");

        String dept = "统一数字空间&统计及迭代展区";
        String deptRep = dept.replace("&", "").replace("展区", "");
        System.out.println(dept);
        System.out.println(deptRep);
        System.out.println(sign);
    }
}
