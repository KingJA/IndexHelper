package sample.kingja.indexhelper;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Description:TODO
 * Create Time:2017/4/24 10:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class City implements Comparable<City> {
    private String name;
    private String pinyin;
    private String firstLetter;

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

    @Override
    public int compareTo(@NonNull City city) {
        if (this.pinyin.compareTo(city.getPinyin()) > 0) {
            return 1;
        }
        if (this.pinyin.compareTo(city.getPinyin()) < 0) {
            return -1;
        }
        return 0;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
}
