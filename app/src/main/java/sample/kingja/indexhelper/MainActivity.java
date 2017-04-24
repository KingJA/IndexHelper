package sample.kingja.indexhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.kingja.indexhelper.PinyinTool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<City> citys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv_index = (ListView) findViewById(R.id.lv_index);
        PinyinTool pinyinTool = new PinyinTool();

        for (int i = 0; i < Constants.citys.length; i++) {
            citys.add(new City(Constants.citys[i], pinyinTool.toPinYin(Constants.citys[i])));
        }
        Collections.sort(citys, new CityComparator());
        CityIndexAdapter cityIndexAdapter = new CityIndexAdapter(this, citys);
        lv_index.setAdapter(cityIndexAdapter);
    }

    private class CityComparator implements Comparator<City> {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }
}
