package cn.charlesxu.redblackassistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,14)); //设置全局字体大小

        String leftTicketJSONString = getIntent().getStringExtra("leftTicketJSONString");
        //System.out.println("结果:" + leftTicketJSONString);
        leftTicketJSONTOClass(leftTicketJSONString);

        table = (SmartTable<QueryLeftNewDTO>) findViewById(R.id.table);

        Column<String> stationTrainCodeColumn = new Column<>("车次","stationTrainCode");
        stationTrainCodeColumn.setFixed(true);

        Column<String> fromStationNameColumn = new Column<>("出发","fromStationName");

        Column<String> toStationNameColumn = new Column<>("到达","toStationName");

        Column<String> startTimeColumn = new Column<>("开点","startTime");

        Column<String> arriveTimeColumn = new Column<>("到点","arriveTime");

        Column<String> lishiColumn = new Column<>("历时","lishi");

        Column<String> grNumColumn = new Column<>("高软","grNum");

        Column<String> qtNumColumn = new Column<>("其他","qtNum");

        Column<String> rwNumColumn = new Column<>("软卧","rwNum");

        Column<String> rzNumColumn = new Column<>("软座","rzNum");

        Column<String> tzNumColumn = new Column<>("特等座","tzNum");

        Column<String> wzNumColumn = new Column<>("无座","wzNum");

        Column<String> ywNumColumn = new Column<>("硬卧","ywNum");

        Column<String> yzNumColumn = new Column<>("硬座","yzNum");

        Column<String> zeNumColumn = new Column<>("二等座","zeNum");

        Column<String> zyNumColumn = new Column<>("一等座","zyNum");

        Column<String> swzNumColumn = new Column<>("商务座","swzNum");

        Column<String> srrbNumColumn = new Column<>("动卧","srrbNum");

        Column<String> isSupportCreditsColumn = new Column<>("支持积分兑换","isSupportCredits");

        Column<String> isSupportCardColumn = new Column<>("支持身份证","isSupportCard");

        Column<String> startStationNameColumn = new Column<>("始发","startStationName");

        Column<String> endStationNameColumn = new Column<>("终到","endStationName");

        TableData<QueryLeftNewDTO> tableData = new TableData<>("余票信息",queryLeftNewDTOList,stationTrainCodeColumn,fromStationNameColumn,startTimeColumn,toStationNameColumn,arriveTimeColumn,lishiColumn,zeNumColumn,zyNumColumn,swzNumColumn,grNumColumn,rwNumColumn,srrbNumColumn,ywNumColumn,rzNumColumn,yzNumColumn,wzNumColumn,qtNumColumn,tzNumColumn,isSupportCardColumn,isSupportCreditsColumn,startStationNameColumn,endStationNameColumn);
        tableData.setOnRowClickListener(new TableData.OnRowClickListener<QueryLeftNewDTO>() {
            @Override
            public void onClick(Column column, QueryLeftNewDTO queryLeftNewDTO, int col, int row) {
                if(queryLeftNewDTO.getSecretStr().equals("null")){
                    Toast.makeText(leftTicketActivity.this,"该车次无法订票",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(leftTicketActivity.this,"车次:"+queryLeftNewDTO.getStationTrainCode(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        table.setTableData(tableData);
        table.setZoom(true,0.5f,2);
    }

    void leftTicketJSONTOClass(String leftTicketJSONString){
        JsonElement je = new JsonParser().parse(leftTicketJSONString);
        String resultString = je.getAsJsonObject().get("data").getAsJsonObject().get("result").toString();
        resultString = resultString.substring(1,resultString.length()-1);
        String[] result = resultString.split(",");
        for(String s :result){
            s = s.substring(1,s.length()-1);
            String[] ticket = s.split("\\|");
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
