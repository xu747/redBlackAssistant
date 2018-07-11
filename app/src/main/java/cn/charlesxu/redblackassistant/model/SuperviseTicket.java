package cn.charlesxu.redblackassistant.model;

import org.litepal.crud.DataSupport;

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
    private String leftTicket;
    private String purpose_codes;
    private String train_location;
    private String _json_att;
    private String REPEAT_SUBMIT_TOKEN;

    public String getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(String trainDate) {
        this.trainDate = trainDate;
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

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
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

    public String getLeftTicket() {
        return leftTicket;
    }

    public void setLeftTicket(String leftTicket) {
        this.leftTicket = leftTicket;
    }

    public String getPurpose_codes() {
        return purpose_codes;
    }

    public void setPurpose_codes(String purpose_codes) {
        this.purpose_codes = purpose_codes;
    }

    public String getTrain_location() {
        return train_location;
    }

    public void setTrain_location(String train_location) {
        this.train_location = train_location;
    }

    public String get_json_att() {
        return _json_att;
    }

    public void set_json_att(String _json_att) {
        this._json_att = _json_att;
    }

    public String getREPEAT_SUBMIT_TOKEN() {
        return REPEAT_SUBMIT_TOKEN;
    }

    public void setREPEAT_SUBMIT_TOKEN(String REPEAT_SUBMIT_TOKEN) {
        this.REPEAT_SUBMIT_TOKEN = REPEAT_SUBMIT_TOKEN;
    }
}
