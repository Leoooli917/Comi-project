package net.qihoo.corp.ms.umapp.common.util.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.util.data
 * @ClassName: MsUmappGenerateCodeUtil
 * @Description:
 * @date 2022-10-11 17:32:07
 */
public class MsUmappGenerateCodeUtil {

    // 随机生成六位数字
    public static String generateSmsCode() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += random.nextInt(10);
        }
        return result;
    }

    /**
     * 生成unxTime时间戳
     */
    public static String generateUnixTime(String strDateTime) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long epoch;
        Date d = new Date();
        String t = df.format(d);
        epoch = df.parse(t).getTime() / 1000;
        return String.valueOf(epoch);
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String generateUUID() {
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] generateUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = generateUUID();
        }
        return ss;
    }
}
