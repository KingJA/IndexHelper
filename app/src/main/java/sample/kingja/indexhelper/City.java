package sample.kingja.indexhelper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kingja.indexhelper.FirstLetter;

/**
 * Description:TODO
 * Create Time:2017/4/24 10:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class City implements FirstLetter{
    private String name;
    private String pinyin;

    public City(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin.substring(0, 1);
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }



    public String getFirstLetter() {
        return pinyin;
    }

}
