package cn.charlesxu.redblackassistant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.charlesxu.redblackassistant.R;
import cn.charlesxu.redblackassistant.model.OrderDTOData;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<OrderDTOData> orderDTODataList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sequenceNoTextView, trainInfoTextView, orderTimeTextView, passengersTextView, ticketNumTextView, orderStatusTextView;

        public ViewHolder(View view) {
            super(view);
            sequenceNoTextView = view.findViewById(R.id.sequenceNo_textView);
            trainInfoTextView = view.findViewById(R.id.trainInfo_textView);
            orderTimeTextView = view.findViewById(R.id.orderTime_textView);
            passengersTextView = view.findViewById(R.id.passengers_textView);
            ticketNumTextView = view.findViewById(R.id.ticketNum_textView);
            orderStatusTextView = view.findViewById(R.id.orderStatus_textView);
        }
    }

    public OrderAdapter(List<OrderDTOData> orderDTODatas) {
        orderDTODataList = orderDTODatas;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        final OrderAdapter.ViewHolder holder = new OrderAdapter.ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                OrderDTOData orderDTOData = orderDTODataList.get(position);
                System.out.println(orderDTOData.getSequence_no());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        OrderDTOData orderDTOData = orderDTODataList.get(position);
        String sequenceNoTextString = "订单号:" + orderDTOData.getSequence_no();
        String trainInfoTextString = "车次信息:" + orderDTOData.getTrainInfo();
        String orderTimeTextString = "订单时间:" + orderDTOData.getOrder_date();
        String passengersTextString = "乘客:" + orderDTOData.getPassengers();
        String ticketNumTextString = "总张数:" + orderDTOData.getTicket_totalnum();
        String orderStatusTextString = "订单状态:XXXX";

        holder.sequenceNoTextView.setText(sequenceNoTextString);
        holder.trainInfoTextView.setText(trainInfoTextString);
        holder.orderTimeTextView.setText(orderTimeTextString);
        holder.passengersTextView.setText(passengersTextString);
        holder.ticketNumTextView.setText(ticketNumTextString);
        holder.orderStatusTextView.setText(orderStatusTextString);

        //holder.trainInfoTextView.setText(orderDTOData);
    }

    @Override
    public int getItemCount() {
        return orderDTODataList.size();
    }

}
