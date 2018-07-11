package cn.charlesxu.redblackassistant.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.charlesxu.redblackassistant.R;
import cn.charlesxu.redblackassistant.model.Passenger;
import cn.charlesxu.redblackassistant.model.SuperviseTicket;

public class SuperviseTicketAdapter extends RecyclerView.Adapter<SuperviseTicketAdapter.ViewHolder>{
    private List<SuperviseTicket> superviseTickets;
    private Activity mActivity;
    private ObjectAnimator animator;

    public SuperviseTicketAdapter(List<SuperviseTicket> superviseTickets,Activity mActivity){
        this.superviseTickets = superviseTickets;
        this.mActivity = mActivity;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView trainRange_textView,trainDate_textView,seatType_textView,stationTrainCode_textView,passengers_textView,superviseStatus_textView;
        ImageView statusRound_imageView;
        Button startStop_button,delete_button;

        ViewHolder(View view){
            super(view);
            trainRange_textView = view.findViewById(R.id.trainRange_textView);
            trainDate_textView = view.findViewById(R.id.trainDate_textView);
            seatType_textView = view.findViewById(R.id.seatType_textView);
            stationTrainCode_textView = view.findViewById(R.id.stationTrainCode_textView);
            passengers_textView = view.findViewById(R.id.passengers_textView);
            superviseStatus_textView = view.findViewById(R.id.superviseStatus_textView);
            statusRound_imageView = view.findViewById(R.id.statusRound_imageView);
            startStop_button = view.findViewById(R.id.startStop_button);
            delete_button = view.findViewById(R.id.delete_button);

            animator = ObjectAnimator.ofFloat(statusRound_imageView, "rotation", 0f, 359f);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervise_ticket_item, parent, false);
        final SuperviseTicketAdapter.ViewHolder holder = new SuperviseTicketAdapter.ViewHolder(view);

        holder.startStop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(superviseTickets.get(position).isPause()){
                    superviseTickets.get(position).setPause(false);
                    superviseTickets.get(position).save();
                    holder.startStop_button.setText("暂停");
                    animator.resume();
                }else {
                    superviseTickets.get(position).setPause(true);
                    superviseTickets.get(position).save();
                    holder.startStop_button.setText("开始");
                    animator.pause();
                }
            }
        });

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                superviseTickets.get(position).delete();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String trainRange = "区间:" + superviseTickets.get(position).getFromStationName() + "->" + superviseTickets.get(position).getToStationName();
        String trainDate = "日期:" + superviseTickets.get(position).getTrainDate();
        String seatType = "坐席:" + superviseTickets.get(position).getSeatType();
        String stationTrainCode = "车次:" + superviseTickets.get(position).getStationTrainCode();
        String passengersString = "乘客:" + superviseTickets.get(position).getPassengersName();
        String superviseStatusString = "正在进行第" + superviseTickets.get(position).getRequestCount() + "次抢票";
        String deleteString = "删除";

        String startStopString;
        if(superviseTickets.get(position).isPause()){
            startStopString = "开始";
            animator.setRepeatCount(-1);
            animator.setDuration(1000);
            animator.setInterpolator(new DecelerateInterpolator()); //设置匀速旋转，不卡顿
        }else {
            startStopString = "暂停";
            animator.setRepeatCount(-1);
            animator.setDuration(1000);
            animator.setInterpolator(new DecelerateInterpolator()); //设置匀速旋转，不卡顿
            animator.start();
        }

        holder.trainRange_textView.setText(trainRange);
        holder.trainDate_textView.setText(trainDate);
        holder.seatType_textView.setText(seatType);
        holder.stationTrainCode_textView.setText(stationTrainCode);
        holder.passengers_textView.setText(passengersString);
        holder.superviseStatus_textView.setText(superviseStatusString);
        holder.startStop_button.setText(startStopString);
        holder.delete_button.setText(deleteString);
    }

    @Override
    public int getItemCount() {
        return superviseTickets.size();
    }

}
