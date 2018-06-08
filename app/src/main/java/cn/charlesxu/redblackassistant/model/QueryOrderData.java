package cn.charlesxu.redblackassistant.model;

import java.util.List;

public class QueryOrderData {
    private String order_total_number;
    private boolean show_catering_button;
    private List<OrderDTOData> OrderDTODataList;

    public String getOrder_total_number() {
        return order_total_number;
    }

    public void setOrder_total_number(String order_total_number) {
        this.order_total_number = order_total_number;
    }

    public boolean isShow_catering_button() {
        return show_catering_button;
    }

    public void setShow_catering_button(boolean show_catering_button) {
        this.show_catering_button = show_catering_button;
    }

    public List<OrderDTOData> getOrderDTODataList() {
        return OrderDTODataList;
    }

    public void setOrderDTODataList(List<OrderDTOData> orderDTODataList) {
        OrderDTODataList = orderDTODataList;
    }
}
