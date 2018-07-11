package cn.charlesxu.redblackassistant.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import cn.charlesxu.redblackassistant.R;
import cn.charlesxu.redblackassistant.model.Station;

import static android.app.Activity.RESULT_OK;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {
    private List<Station> stationList;
    private Activity mActivity;

    public StationAdapter(List<Station> stations, Activity mActivity) {
        stationList = stations;
        this.mActivity = mActivity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView stationNameText;

        ViewHolder(View view) {
            super(view);
            stationNameText = view.findViewById(R.id.stationName_textView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.stationNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Station station = stationList.get(position);
                station.setLastUseDateTime(new Date());
                System.out.println(station.getLastUseDateTime());
                station.save();
                Intent intent = new Intent();
                intent.putExtra("station", station);
                mActivity.setResult(RESULT_OK, intent);
                mActivity.finish();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Station station = stationList.get(position);
        holder.stationNameText.setText(station.getStationName());
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }
}
