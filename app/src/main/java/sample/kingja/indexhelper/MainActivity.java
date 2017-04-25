package sample.kingja.indexhelper;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kingja.indexhelper.FirstLetter;
import com.kingja.indexhelper.IndexView;
import com.kingja.indexhelper.PinyinTool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<City> citys = new ArrayList<>();
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        fillData();
        final ListView lv_index = (ListView) findViewById(R.id.lv_index);
        IndexView indexView = (IndexView) findViewById(R.id.indexView);
        final TextView tv_index = (TextView) findViewById(R.id.tv_index);
        final CityIndexAdapter cityIndexAdapter = new CityIndexAdapter(this, citys);
        lv_index.setAdapter(cityIndexAdapter);
        indexView.setOnIndexSelectedListener(new IndexView.OnIndexSelectedListener() {
            @Override
            public void onIndexSelected(int index, String letter) {
                int position = cityIndexAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    lv_index.setSelection(position);
                }
                tv_index.setVisibility(View.VISIBLE);
                tv_index.setText(letter);

            }

            @Override
            public void onIndexSelectedCompleted(int index, String letter) {
                tv_index.setVisibility(View.GONE);
            }
        });

    }

    private void fillData() {
        PinyinTool pinyinTool = new PinyinTool();
        for (int i = 0; i < Constants.citys.length; i++) {
            citys.add(new City(Constants.citys[i], pinyinTool.toPinYin(Constants.citys[i])));
        }
        Collections.sort(citys, new CityComparator());
    }

    private class CityComparator implements Comparator<FirstLetter> {
        @Override
        public int compare(FirstLetter lhs, FirstLetter rhs) {
            String a = lhs.getFirstLetter().substring(0, 1);
            String b = rhs.getFirstLetter().substring(0, 1);
            return a.compareTo(b);
        }
    }
}
