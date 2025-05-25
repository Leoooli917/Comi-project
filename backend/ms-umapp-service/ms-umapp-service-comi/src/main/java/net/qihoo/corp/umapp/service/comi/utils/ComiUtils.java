package net.qihoo.corp.umapp.service.comi.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComiUtils {
    private  static  List<String> initialList = new ArrayList<>(Arrays.asList(
            "场景一：",
            "场景二：",
            "场景三：",
            "场景四：",
            "场景五：",
            "场景六：",
            "场景七：",
            "场景八：",
            "一：",
            "二：",
            "三：",
            "四：",
            "五：",
            "六：",
            "七：",
            "八：",
            "1：",
            "2：",
            "3：",
            "4：",
            "5：",
            "6：",
            "7：",
            "8：",
            "场景1：",
            "场景2：",
            "场景3：",
            "场景4：",
            "场景5：",
            "场景6：",
            "场景7：",
            "场景8："
            ));
    private  static  List<String> roles = new ArrayList<>(Arrays.asList(
            "1.",
            "2.",
            "3.",
            "4.",
            "5.",
            "6."


            ));
    public  static String  cutSceneNoNeedStr(String value){
        if(value==null||value.length()==0){
            return value;
        }
        for(int i=0;i<initialList.size();i++){
            String start =initialList.get(i);
            if(value.startsWith(start)){

                return value.substring(start.length());

            }
        }





        return  value;

    }
    public static String getRoleName(String value) {

        if(value==null||value.length()==0){
            return "";
        }
        for(int i=0;i<roles.size();i++){
            String start =initialList.get(i);
            if(value.startsWith(start)){

                return value.substring(start.length());

            }

        }
        return "";
    }



}
