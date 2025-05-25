package net.qihoo.corp.umapp.service.sharebook.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import java.util.Date;
import java.util.Locale;

/**
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class BookTags  implements Comparable<BookTags>{
    private int id;
    private String name;
    private int tagCount;


    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 创建时间
     */
    private Date createTime;



    @Override
    public int compareTo(BookTags o) {
        String regex = "^\\w.*";
        String regexNum = "/^[0-9]*$/";
        if (this.getName().matches(regexNum)) {
            return -10000;
        }
//        else {
            return (this.getName().matches(regex)? this.getName():genPinYin(this.getName())).toLowerCase(Locale.ROOT)
                    .compareTo(( o.getName().matches(regex) ? o.getName() : genPinYin(o.getName())).toLowerCase(Locale.ROOT));
//        }
    }

    public String getFirstPin() {
        String upperCase = genPinYin(name).toUpperCase(Locale.ROOT);
        return upperCase.length()>1?upperCase.substring(0,1):upperCase;
    }

    private String genPinYin(String input) {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        //多音字预先转换 这里可以处理一下多音字
        char[] chars =  input.trim().toCharArray();
        StringBuilder output = new StringBuilder();
        try {
            for (char c : chars) {
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    output.append(temp[0]);
                } else {
                    output.append(Character.toString(c));
                }
            }
        } catch (Exception e) {
            System.err.println("拼音转换出现未知异常：" + input);
        }
        return output.toString();
    }


}
