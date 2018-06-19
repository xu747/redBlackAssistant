package cn.charlesxu.redblackassistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.charlesxu.redblackassistant.adapter.StationAdapter;
import cn.charlesxu.redblackassistant.model.Station;

public class StationActivity extends AppCompatActivity {

    private List<Station> popularStationList = new ArrayList<>();
    private List<Station> recentStationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        initPopularStation();
        initRecentStation();

        //Init popularStationRecyclerView
        RecyclerView popularStationRecyclerView = (RecyclerView) findViewById(R.id.popularStation_recyclerView);
        popularStationRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        popularStationRecyclerView.setAdapter(new StationAdapter(popularStationList, this));

        //Init recentStationRecyclerView
        RecyclerView recentStationRecyclerView = findViewById(R.id.recentStation_recyclerView);
        recentStationRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        final StationAdapter recentStationAdapter = new StationAdapter(recentStationList, this);
        recentStationRecyclerView.setAdapter(recentStationAdapter);

        SearchView searchView = findViewById(R.id.station_searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        final TextView recentStationTextView = findViewById(R.id.recentStation_textView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recentStationTextView.setText("查询结果");
                List<Station> resultStationList = new ArrayList<>();
                resultStationList = DataSupport.where("stationName like ? or pinYinInitialism like ?", query + "%", query + "%").find(Station.class);
                recentStationList.clear();
                recentStationList.addAll(resultStationList);
                recentStationAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                System.out.println("查询" + query);
                if (query.isEmpty()) {
                    recentStationTextView.setText("最近查询车站");
                    initRecentStation();
                    recentStationAdapter.notifyDataSetChanged();
                } else {
                    recentStationTextView.setText("查询结果");
                    List<Station> resultStationList = new ArrayList<>();
                    resultStationList = DataSupport.where("stationName like ? or pinYinInitialism like ?", query + "%", query + "%").find(Station.class);
                    recentStationList.clear();
                    recentStationList.addAll(resultStationList);
                    recentStationAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

    }

    void initPopularStation() {
        //设置热门站点
        String[] popularStationName = {"北京", "上海", "广州", "天津", "重庆", "成都", "长沙", "哈尔滨", "杭州"};
        String queryString = "stationName = ";
        for (int i = 0; i < popularStationName.length; i++) {
            if (i == 0) {
                queryString += "'" + popularStationName[i] + "'";
            } else {
                queryString += " or stationName = " + "'" + popularStationName[i] + "'";
            }
        }
        popularStationList = DataSupport.where(queryString).find(Station.class);
    }

    void initRecentStation() {
        recentStationList.clear();
        recentStationList.addAll(DataSupport.order("lastUseDateTime desc").limit(6).find(Station.class));
    }
}
