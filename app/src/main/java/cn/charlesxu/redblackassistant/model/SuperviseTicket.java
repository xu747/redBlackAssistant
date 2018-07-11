package cn.charlesxu.redblackassistant.model;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SuperviseTicket extends DataSupport {
//                train_date: Thu Jun 28 2018 00:00:00 GMT+0800 (China Standard Time)
//                train_no: 5l0000D37962
//                stationTrainCode: D379
//                seatType: O
//                fromStationTelecode: HGH
//                toStationTelecode: FHH
//                leftTicket: iatYiA9outqo%2F%2B0F3NWdwadU9unfqgLHl1d7AxM9jIxoq8Pc
//                purpose_codes: 00
//                train_location: H2
//                _json_att:
//                REPEAT_SUBMIT_TOKEN: e4e0325b422772e49e3a64dad36e7ba1

//    body = new FormBody.Builder()
//                        .add("train_date", trainDateFormatString)
//                        .add("train_no", queryLeftNewDTO.getTrainNo())
//                        .add("stationTrainCode", queryLeftNewDTO.getStationTrainCode())
//                        .add("seatType", seatTypeCodeString)
//                        .add("fromStationTelecode", queryLeftNewDTO.getFromStationTelecode())
//                        .add("toStationTelecode", queryLeftNewDTO.getToStationTelecode())
//                        .add("leftTicket", leftTicketString)
//                        .add("purpose_codes", purposeCodesString)
//                        .add("train_location", trainLocationString)
//                        .add("_json_att", "")
//                        .add("REPEAT_SUBMIT_TOKEN", globalRepeatSubmitToken)
//                        .build();

    private String trainDate;
    private String trainDateFormat;
    private String trainNo;
    private String stationTrainCode;
    private String fromStationTelecode;
    private String toStationTelecode;
    private String fromStationName;
    private String toStationName;
    private String seatType;
    private String passengersName;
    private int requestCount;
    private boolean isPause;


    public String getPassengersName() {
        return passengersName;
    }

    public void setPassengersName(String passengersName) {
        this.passengersName = passengersName;
    }

    public String getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(String trainDate) {
        this.trainDate = trainDate;
    }

    public String getTrainDateFormat() {
        return trainDateFormat;
    }

    public void setTrainDateFormat(String trainDateFormat) {
        this.trainDateFormat = trainDateFormat;
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

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }
}
