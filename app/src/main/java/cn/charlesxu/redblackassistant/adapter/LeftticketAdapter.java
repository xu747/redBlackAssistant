package cn.charlesxu.redblackassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.charlesxu.redblackassistant.R;
import cn.charlesxu.redblackassistant.model.QueryLeftNewDTO;

public class LeftticketAdapter extends RecyclerView.Adapter<LeftticketAdapter.MyViewHolder> {

    private List<QueryLeftNewDTO> dataList;

    private LayoutInflater layoutInflater;

    public LeftticketAdapter(Context context, List<QueryLeftNewDTO> dataList) {
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.leftticket_item, parent, false);
//        RecyclerView.LayoutParams layoutParams=(RecyclerView.LayoutParams)itemView.getLayoutParams();
//        layoutParams.bottomMargin=1;
//        itemView.setLayoutParams(layoutParams);
//        MyViewHolder holder=new MyViewHolder(itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.startStation.setText(dataList.get(position).getStartStationName());
        holder.startTime.setText(dataList.get(position).getStartTime());
        holder.trainId.setText(dataList.get(position).getStationTrainCode());
        holder.arriveStation.setText(dataList.get(position).getEndStationName());
        holder.arriveTime.setText(dataList.get(position).getArriveTime());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView startStation;
        private TextView startTime;
        private TextView trainId;
        private TextView arriveStation;
        private TextView arriveTime;



        MyViewHolder(View itemView) {
            super(itemView);
            startStation = itemView.findViewById(R.id.startStation_textView);
            startTime = itemView.findViewById(R.id.startTime_textView);
            trainId = itemView.findViewById(R.id.trainID_textView);
            arriveStation = itemView.findViewById(R.id.arriveStation_textView);
            arriveTime = itemView.findViewById(R.id.arriveTime_textView);

        }
    }

}

