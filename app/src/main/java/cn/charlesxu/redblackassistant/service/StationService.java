package cn.charlesxu.redblackassistant.service;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.charlesxu.redblackassistant.model.Station;

public class StationService {
    public String getStationNameByTgCode(String tgCode){
        List<Station> resultStationList = DataSupport.where("tgCode like ?",tgCode).find(Station.class);
        return resultStationList.get(0).getStationName();
    }
}
