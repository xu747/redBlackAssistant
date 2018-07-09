package cn.charlesxu.redblackassistant.model;

import java.util.List;

public class OrderDB {
    private List<OrderDTOData> orderDBList;
    private String to_page;

    public String getTo_page() {
        return to_page;
    }

    public void setTo_page(String to_page) {
        this.to_page = to_page;
    }

    public List<OrderDTOData> getOrderDBList() {
        return orderDBList;
    }

    public void setOrderDBList(List<OrderDTOData> orderDBList) {
        this.orderDBList = orderDBList;
    }
}
