package cn.charlesxu.redblackassistant.model;

import java.util.List;

public class PassengerDTOsData {
    private boolean isExist;
    private String exMsg;
    private List<String> two_isOpenClick;
    private List<String> other_isOpenClick;
    private List<Passenger> normal_passengers;
    private List<Passenger> dj_passengers;

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public String getExMsg() {
        return exMsg;
    }

    public void setExMsg(String exMsg) {
        this.exMsg = exMsg;
    }

    public List<String> getTwo_isOpenClick() {
        return two_isOpenClick;
    }

    public void setTwo_isOpenClick(List<String> two_isOpenClick) {
        this.two_isOpenClick = two_isOpenClick;
    }

    public List<String> getOther_isOpenClick() {
        return other_isOpenClick;
    }

    public void setOther_isOpenClick(List<String> other_isOpenClick) {
        this.other_isOpenClick = other_isOpenClick;
    }

    public List<Passenger> getNormal_passengers() {
        return normal_passengers;
    }

    public void setNormal_passengers(List<Passenger> normal_passengers) {
        this.normal_passengers = normal_passengers;
    }

    public List<Passenger> getDj_passengers() {
        return dj_passengers;
    }

    public void setDj_passengers(List<Passenger> dj_passengers) {
        this.dj_passengers = dj_passengers;
    }
}
