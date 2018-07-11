package cn.charlesxu.redblackassistant.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.charlesxu.redblackassistant.R;
import cn.charlesxu.redblackassistant.model.QueryLeftNewDTO;
import cn.charlesxu.redblackassistant.submitOrderActivity;

public class LeftTicketAdapter extends RecyclerView.Adapter<LeftTicketAdapter.ViewHolder> {

    private List<QueryLeftNewDTO> dataList;
    private Activity mActivity;
    private String trainDateString, purposeCodes, fromStationName, toStationName;

    public LeftTicketAdapter(List<QueryLeftNewDTO> dataList, String trainDateString, String purposeCodes, String fromStationName, String toStationName, Activity mActivity) {
        this.dataList = dataList;
        this.mActivity = mActivity;
        this.trainDateString = trainDateString;
        this.purposeCodes = purposeCodes;
        this.fromStationName = fromStationName;
        this.toStationName = toStationName;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView startStationTextView, startTimeTextView, trainIdTextView, arriveStationTextView, arriveTimeTextView, durationTimeTextView;
        private TextView leftSeat1TextView,leftSeat2TextView,leftSeat3TextView,leftSeat4TextView;

        ViewHolder(View itemView) {
            super(itemView);
            startStationTextView = itemView.findViewById(R.id.startStation_textView);
            startTimeTextView = itemView.findViewById(R.id.startTime_textView);
            trainIdTextView = itemView.findViewById(R.id.trainID_textView);
            arriveStationTextView = itemView.findViewById(R.id.arriveStation_textView);
            arriveTimeTextView = itemView.findViewById(R.id.arriveTime_textView);
            durationTimeTextView = itemView.findViewById(R.id.durationTime_textView);
            leftSeat1TextView = itemView.findViewById(R.id.leftSeat1_textView);
            leftSeat2TextView = itemView.findViewById(R.id.leftSeat2_textView);
            leftSeat3TextView = itemView.findViewById(R.id.leftSeat3_textView);
            leftSeat4TextView = itemView.findViewById(R.id.leftSeat4_textView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_ticket_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                QueryLeftNewDTO queryLeftNewDTO = dataList.get(position);

                if (queryLeftNewDTO.getSecretStr().equals("null") || queryLeftNewDTO.getSecretStr().equals("")) {
                    Toast.makeText(mActivity, "该车次无法订票", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "开始订车次:" + queryLeftNewDTO.getStationTrainCode(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mActivity, submitOrderActivity.class);
                    intent.putExtra("queryLeftNewDTO", queryLeftNewDTO);
                    intent.putExtra("secretStr", queryLeftNewDTO.getSecretStr());
                    intent.putExtra("trainDateString", trainDateString);
                    intent.putExtra("backTrainDateString", trainDateString);
                    intent.putExtra("tourFlag", "dc");
                    intent.putExtra("purposeCodes", purposeCodes);
                    intent.putExtra("fromStationName", fromStationName);
                    intent.putExtra("toStationName", toStationName);
                    mActivity.startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.startStationTextView.setText(dataList.get(position).getFromStationName());
        holder.startTimeTextView.setText(dataList.get(position).getStartTime());
        holder.trainIdTextView.setText(dataList.get(position).getStationTrainCode());
        holder.arriveStationTextView.setText(dataList.get(position).getToStationName());
        holder.arriveTimeTextView.setText(dataList.get(position).getArriveTime());
        holder.durationTimeTextView.setText("历时 " + dataList.get(position).getLishi());

        //动车、高铁
        if(dataList.get(position).getStationTrainCode().contains("G") || dataList.get(position).getStationTrainCode().contains("D") || dataList.get(position).getStationTrainCode().contains("C")){
            //如果是动车、高铁
            //检查是否为动卧列车
            if(dataList.get(position).getSrrbNum().equals("")){
                holder.leftSeat1TextView.setText("二等座:"+ dataList.get(position).getZeNum());
                holder.leftSeat2TextView.setText("一等座:"+ dataList.get(position).getZyNum());
                if(!dataList.get(position).getSwzNum().equals("")){
                    holder.leftSeat3TextView.setText("商务座:"+ dataList.get(position).getSwzNum());
                }else {
                    holder.leftSeat3TextView.setText("特等座:"+ dataList.get(position).getSwzNum());
                }
                //如果无座String为空
                if(dataList.get(position).getWzNum().equals("")){
                    holder.leftSeat4TextView.setVisibility(View.INVISIBLE);
                }else {
                    holder.leftSeat4TextView.setText("无座:"+ dataList.get(position).getWzNum());
                }
            }else {
                holder.leftSeat1TextView.setText("动卧:"+ dataList.get(position).getSrrbNum());
                holder.leftSeat2TextView.setText("高级软卧:"+dataList.get(position).getGrNum());
                holder.leftSeat3TextView.setText("二等座:"+ dataList.get(position).getZeNum());
                holder.leftSeat4TextView.setVisibility(View.INVISIBLE);
            }
        }else {
            //如果为普速列车
            holder.leftSeat1TextView.setText("硬座:"+ dataList.get(position).getYzNum());
            holder.leftSeat2TextView.setText("硬卧:"+dataList.get(position).getYwNum());
            holder.leftSeat3TextView.setText("软卧:"+ dataList.get(position).getRwNum());
            holder.leftSeat4TextView.setText("无座:"+ dataList.get(position).getWzNum());
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



}

