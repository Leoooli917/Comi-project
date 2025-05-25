package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class ScanIsbnResp {
    private long id;
    private String name;
    private String subname;
    private String author;
    private String translator;
    private String publishing;
    private String published;
    private String designed;
    private String code;
    private String douban;
    private String doubanScore;
    private String brand;
    private String weight;
    private String size;
    private String pages;
    private String photoUrl;
    private String localPhotoUrl;
    private String price;
    private String froms;
    private String num;
    private String createTime;
    private String uptime;
    private String authorIntro;
    private String description;
    private String tags;

    @Override
    public String toString() {
        return "ScanIsbnResp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subname='" + subname + '\'' +
                ", author='" + author + '\'' +
                ", translator='" + translator + '\'' +
                ", publishing='" + publishing + '\'' +
                ", published='" + published + '\'' +
                ", designed='" + designed + '\'' +
                ", code='" + code + '\'' +
                ", douban='" + douban + '\'' +
                ", doubanScore='" + doubanScore + '\'' +
                ", brand='" + brand + '\'' +
                ", weight='" + weight + '\'' +
                ", size='" + size + '\'' +
                ", pages='" + pages + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", localPhotoUrl='" + localPhotoUrl + '\'' +
                ", price='" + price + '\'' +
                ", froms='" + froms + '\'' +
                ", num='" + num + '\'' +
                ", createTime='" + createTime + '\'' +
                ", uptime='" + uptime + '\'' +
                ", authorIntro='" + authorIntro + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
