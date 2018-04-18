package cn.charlesxu.redblackassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bin.david.form.core.SmartTable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.util.ArrayList;
import java.util.List;

import cn.charlesxu.redblackassistant.model.QueryLeftNewDTO;
import cn.charlesxu.redblackassistant.service.StationService;

public class leftTicketActivity extends AppCompatActivity {

    private SmartTable table;
    private List<QueryLeftNewDTO> queryLeftNewDTOList = new ArrayList<>();
    private StationService stationService = new StationService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_ticket);

        String leftTicketJSONString = getIntent().getStringExtra("leftTicketJSONString");
        //System.out.println("结果:" + leftTicketJSONString);
        leftTicketJSONTOClass(leftTicketJSONString);

        table = findViewById(R.id.table);
        table.addData(queryLeftNewDTOList,false);
    }

    void leftTicketJSONTOClass(String leftTicketJSONString){
        JsonElement je = new JsonParser().parse(leftTicketJSONString);
        String resultString = je.getAsJsonObject().get("data").getAsJsonObject().get("result").toString();
        resultString = resultString.substring(1,resultString.length()-1);
        String[] result = resultString.split(",");
        for(String s :result){
            s = s.substring(1,s.length()-1);
            String[] ticket = s.split("\\|");
            //for(int i = 0; i < ticket.length; i++){
            //    if (ticket[i] .equals("")){
            //        ticket[i] = "----";
            //    }
            //}
            QueryLeftNewDTO queryLeftNewDTO = new QueryLeftNewDTO();
            queryLeftNewDTO.setSecretStr(ticket[0]);
            queryLeftNewDTO.setButtonTextInfo(ticket[1]);
            queryLeftNewDTO.setTrainNo(ticket[2]);
            queryLeftNewDTO.setStationTrainCode(ticket[3]);
            queryLeftNewDTO.setStartStationTelecode(ticket[4]);
            queryLeftNewDTO.setEndStationTelecode(ticket[5]);
            queryLeftNewDTO.setFromStationTelecode(ticket[6]);
            queryLeftNewDTO.setToStationTelecode(ticket[7]);
            queryLeftNewDTO.setStartTime(ticket[8]);
            queryLeftNewDTO.setArriveTime(ticket[9]);
            queryLeftNewDTO.setLishi(ticket[10]);
            queryLeftNewDTO.setCanWebBuy(ticket[11]);
            queryLeftNewDTO.setYpInfo(ticket[12]);
            queryLeftNewDTO.setStartTrainDate(ticket[13]);
            queryLeftNewDTO.setTrainSeatFeature(ticket[14]);
            queryLeftNewDTO.setLocationCode(ticket[15]);
            queryLeftNewDTO.setFromStationNo(ticket[16]);
            queryLeftNewDTO.setToStationNo(ticket[17]);
            queryLeftNewDTO.setIsSupportCard(ticket[18]);
            queryLeftNewDTO.setControlledTrainFlag(ticket[19]);
            queryLeftNewDTO.setGgNum(ticket[20]);
            queryLeftNewDTO.setGrNum(ticket[21]);
            queryLeftNewDTO.setQtNum(ticket[22]);
            queryLeftNewDTO.setRwNum(ticket[23]);
            queryLeftNewDTO.setRzNum(ticket[24]);
            queryLeftNewDTO.setTzNum(ticket[25]);
            queryLeftNewDTO.setWzNum(ticket[26]);
            queryLeftNewDTO.setYbNum(ticket[27]);
            queryLeftNewDTO.setYwNum(ticket[28]);
            queryLeftNewDTO.setYzNum(ticket[29]);
            queryLeftNewDTO.setZeNum(ticket[30]);
            queryLeftNewDTO.setZyNum(ticket[31]);
            queryLeftNewDTO.setSwzNum(ticket[32]);
            queryLeftNewDTO.setSrrbNum(ticket[33]);
            queryLeftNewDTO.setYpEx(ticket[34]);
            queryLeftNewDTO.setSeatTypes(ticket[35]);
            queryLeftNewDTO.setIsSupportCredits(ticket[36]);

            queryLeftNewDTO.setStartStationName(stationService.getStationNameByTgCode(queryLeftNewDTO.getStartStationTelecode()));
            queryLeftNewDTO.setEndStationName(stationService.getStationNameByTgCode(queryLeftNewDTO.getEndStationTelecode()));
            queryLeftNewDTO.setFromStationName(stationService.getStationNameByTgCode(queryLeftNewDTO.getFromStationTelecode()));
            queryLeftNewDTO.setToStationName(stationService.getStationNameByTgCode(queryLeftNewDTO.getToStationTelecode()));

            queryLeftNewDTOList.add(queryLeftNewDTO);
        }
    }
}
