package cn.charlesxu.redblackassistant.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

public class Station extends DataSupport implements Serializable {
    @Column(unique = true)
    private int id;

    @Column(nullable = false)
    private String tgCode;

    @Column(nullable = false)
    private String stationName;

    @Column(nullable = false)
    private String pinYin;

    @Column(nullable = false)
    private String initialism;

    @Column(nullable = false)
    private String pinYinInitialism;

    @Column(nullable = true)
    private Date lastUseDateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTgCode() {
        return tgCode;
    }

    public void setTgCode(String tgCode) {
        this.tgCode = tgCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getInitialism() {
        return initialism;
    }

    public void setInitialism(String initialism) {
        this.initialism = initialism;
    }

    public String getPinYinInitialism() {
        return pinYinInitialism;
    }

    public void setPinYinInitialism(String pinYinInitialism) {
        this.pinYinInitialism = pinYinInitialism;
    }

    public Date getLastUseDateTime() {
        return lastUseDateTime;
    }

    public void setLastUseDateTime(Date lastUseDateTime) {
        this.lastUseDateTime = lastUseDateTime;
    }
}
