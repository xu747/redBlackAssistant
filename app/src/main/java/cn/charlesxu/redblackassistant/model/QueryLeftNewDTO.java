package cn.charlesxu.redblackassistant.model;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable(name="余票信息")
public class QueryLeftNewDTO {
    private String secretStr;
    private String buttonTextInfo;
    private String trainNo;

    @SmartColumn(id = 1,name = "车次")
    private String stationTrainCode;

    private String startStationTelecode;
    private String endStationTelecode;
    private String fromStationTelecode;
    private String toStationTelecode;


    @SmartColumn(id = 2,name = "出发站")
    private String fromStationName;

    @SmartColumn(id = 3,name = "到达站")
    private String toStationName;

    @SmartColumn(id = 4,name = "出发时间")
    private String startTime;

    @SmartColumn(id = 5,name = "到达时间")
    private String arriveTime;

    @SmartColumn(id = 6,name = "历时")
    private String lishi;

    private String canWebBuy;
    private String ypInfo;
    private String startTrainDate;
    private String trainSeatFeature;
    private String locationCode;
    private String fromStationNo;
    private String toStationNo;

    private String controlledTrainFlag;
    private String ggNum;

    @SmartColumn(id = 7,name = "高级软卧")
    private String grNum;
    @SmartColumn(id = 8,name = "其他")
    private String qtNum;
    @SmartColumn(id = 9,name = "软卧")
    private String rwNum;
    @SmartColumn(id = 10,name = "软座")
    private String rzNum;
    @SmartColumn(id = 11,name = "特等座")
    private String tzNum;
    @SmartColumn(id = 12,name = "无座")
    private String wzNum;

    private String ybNum;

    @SmartColumn(id = 13,name = "硬卧")
    private String ywNum;
    @SmartColumn(id = 14,name = "硬座")
    private String yzNum;
    @SmartColumn(id = 15,name = "二等座")
    private String zeNum;
    @SmartColumn(id = 16,name = "一等座")
    private String zyNum;
    @SmartColumn(id = 17,name = "商务座")
    private String swzNum;
    @SmartColumn(id = 18,name = "动卧")
    private String srrbNum;


    private String ypEx;
    private String seatTypes;

    @SmartColumn(id = 19,name = "支持积分兑换")
    private String isSupportCredits;

    @SmartColumn(id = 20,name = "支持身份证")
    private String isSupportCard;

    @SmartColumn(id = 21,name = "始发站")
    private String startStationName;

    @SmartColumn(id = 22,name = "终到站")
    private String endStationName;

    public String getSecretStr() {
        return secretStr;
    }

    public void setSecretStr(String secretStr) {
        this.secretStr = secretStr;
    }

    public String getButtonTextInfo() {
        return buttonTextInfo;
    }

    public void setButtonTextInfo(String buttonTextInfo) {
        this.buttonTextInfo = buttonTextInfo;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getStationTrainCode() {
        return stationTrainCode;
    }

    public void setStationTrainCode(String stationTrainCode) {
        this.stationTrainCode = stationTrainCode;
    }

    public String getStartStationTelecode() {
        return startStationTelecode;
    }

    public void setStartStationTelecode(String startStationTelecode) {
        this.startStationTelecode = startStationTelecode;
    }

    public String getEndStationTelecode() {
        return endStationTelecode;
    }

    public void setEndStationTelecode(String endStationTelecode) {
        this.endStationTelecode = endStationTelecode;
    }

    public String getFromStationTelecode() {
        return fromStationTelecode;
    }

    public void setFromStationTelecode(String fromStationTelecode) {
        this.fromStationTelecode = fromStationTelecode;
    }

    public String getToStationTelecode() {
        return toStationTelecode;
    }

    public void setToStationTelecode(String toStationTelecode) {
        this.toStationTelecode = toStationTelecode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getLishi() {
        return lishi;
    }

    public void setLishi(String lishi) {
        this.lishi = lishi;
    }

    public String getCanWebBuy() {
        return canWebBuy;
    }

    public void setCanWebBuy(String canWebBuy) {
        this.canWebBuy = canWebBuy;
    }

    public String getYpInfo() {
        return ypInfo;
    }

    public void setYpInfo(String ypInfo) {
        this.ypInfo = ypInfo;
    }

    public String getStartTrainDate() {
        return startTrainDate;
    }

    public void setStartTrainDate(String startTrainDate) {
        this.startTrainDate = startTrainDate;
    }

    public String getTrainSeatFeature() {
        return trainSeatFeature;
    }

    public void setTrainSeatFeature(String trainSeatFeature) {
        this.trainSeatFeature = trainSeatFeature;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getFromStationNo() {
        return fromStationNo;
    }

    public void setFromStationNo(String fromStationNo) {
        this.fromStationNo = fromStationNo;
    }

    public String getToStationNo() {
        return toStationNo;
    }

    public void setToStationNo(String toStationNo) {
        this.toStationNo = toStationNo;
    }

    public String getIsSupportCard() {
        return isSupportCard;
    }

    public void setIsSupportCard(String isSupportCard) {
        this.isSupportCard = isSupportCard;
    }

    public String getControlledTrainFlag() {
        return controlledTrainFlag;
    }

    public void setControlledTrainFlag(String controlledTrainFlag) {
        this.controlledTrainFlag = controlledTrainFlag;
    }

    public String getGgNum() {
        return ggNum;
    }

    public void setGgNum(String ggNum) {
        this.ggNum = ggNum;
    }

    public String getGrNum() {
        return grNum;
    }

    public void setGrNum(String grNum) {
        this.grNum = grNum;
    }

    public String getQtNum() {
        return qtNum;
    }

    public void setQtNum(String qtNum) {
        this.qtNum = qtNum;
    }

    public String getRwNum() {
        return rwNum;
    }

    public void setRwNum(String rwNum) {
        this.rwNum = rwNum;
    }

    public String getRzNum() {
        return rzNum;
    }

    public void setRzNum(String rzNum) {
        this.rzNum = rzNum;
    }

    public String getTzNum() {
        return tzNum;
    }

    public void setTzNum(String tzNum) {
        this.tzNum = tzNum;
    }

    public String getWzNum() {
        return wzNum;
    }

    public void setWzNum(String wzNum) {
        this.wzNum = wzNum;
    }

    public String getYbNum() {
        return ybNum;
    }

    public void setYbNum(String ybNum) {
        this.ybNum = ybNum;
    }

    public String getYwNum() {
        return ywNum;
    }

    public void setYwNum(String ywNum) {
        this.ywNum = ywNum;
    }

    public String getYzNum() {
        return yzNum;
    }

    public void setYzNum(String yzNum) {
        this.yzNum = yzNum;
    }

    public String getZeNum() {
        return zeNum;
    }

    public void setZeNum(String zeNum) {
        this.zeNum = zeNum;
    }

    public String getZyNum() {
        return zyNum;
    }

    public void setZyNum(String zyNum) {
        this.zyNum = zyNum;
    }

    public String getSwzNum() {
        return swzNum;
    }

    public void setSwzNum(String swzNum) {
        this.swzNum = swzNum;
    }

    public String getSrrbNum() {
        return srrbNum;
    }

    public void setSrrbNum(String srrbNum) {
        this.srrbNum = srrbNum;
    }

    public String getYpEx() {
        return ypEx;
    }

    public void setYpEx(String ypEx) {
        this.ypEx = ypEx;
    }

    public String getSeatTypes() {
        return seatTypes;
    }

    public void setSeatTypes(String seatTypes) {
        this.seatTypes = seatTypes;
    }

    public String getIsSupportCredits() {
        return isSupportCredits;
    }

    public void setIsSupportCredits(String isSupportCredits) {
        this.isSupportCredits = isSupportCredits;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }
}
