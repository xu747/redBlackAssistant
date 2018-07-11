package cn.charlesxu.redblackassistant;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import cn.charlesxu.redblackassistant.adapter.LeftticketAdapter;
import cn.charlesxu.redblackassistant.model.QueryLeftNewDTO;
import cn.charlesxu.redblackassistant.service.StationService;

public class leftTicketActivity extends AppCompatActivity {
    private List<QueryLeftNewDTO> queryLeftNewDTOList = new ArrayList<>();
    private StationService stationService = new StationService();
    private String leftTicketJSONString, trainDateString, purposeCodes, fromStationName, toStationName;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_ticket);

        leftTicketJSONString = getIntent().getStringExtra("leftTicketJSONString");
        trainDateString = getIntent().getStringExtra("trainDateString");
        purposeCodes = getIntent().getStringExtra("purposeCodes");
        fromStationName = getIntent().getStringExtra("fromStationName");
        toStationName = getIntent().getStringExtra("toStationName");

        leftTicketJSONTOClass(leftTicketJSONString);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        LeftticketAdapter adapter = new LeftticketAdapter(this,queryLeftNewDTOList );
        mRecyclerView.setAdapter(adapter);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.divid_line));
        mRecyclerView.addItemDecoration(divider);
    }

    void leftTicketJSONTOClass(String leftTicketJSONString) {
        JsonElement je = new JsonParser().parse(leftTicketJSONString);
        String resultString = je.getAsJsonObject().get("data").getAsJsonObject().get("result").toString();
        JsonArray resultJsonArray = je.getAsJsonObject().get("data").getAsJsonObject().get("result").getAsJsonArray();
        //resultString = resultString.substring(1, resultString.length() - 1);
        //String[] result = resultString.split(",");
        for (JsonElement jsonElement : resultJsonArray) {
            String s = jsonElement.getAsString();
            //s = s.substring(1, s.length() - 1);
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

    private class TestDividerItemDecoration extends RecyclerView.ItemDecoration{
        private Paint mPaint;
        public TestDividerItemDecoration(){
            mPaint=new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.RED);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if(parent.getChildAdapterPosition(view)!=0){
                outRect.top=1;
            }
        }
//        @Override
//        public void getItemOffsets(Rect outRect, View view,RecyclerView parent,RecyclerView.State state){
//            super.getItemOffsets(outRect,view,parent,state);
//            if(parent.getChildAdapterPosition(view)!=0){
//                outRect.bottom=1;
//            }
//        }

    }
    /*private SmartTable table;
    private List<QueryLeftNewDTO> queryLeftNewDTOList = new ArrayList<>();
    private StationService stationService = new StationService();
    private String leftTicketJSONString, trainDateString, purposeCodes, fromStationName, toStationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_ticket);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this, 14)); //设置全局字体大小

        leftTicketJSONString = getIntent().getStringExtra("leftTicketJSONString");
        trainDateString = getIntent().getStringExtra("trainDateString");
        purposeCodes = getIntent().getStringExtra("purposeCodes");
        fromStationName = getIntent().getStringExtra("fromStationName");
        toStationName = getIntent().getStringExtra("toStationName");

        //System.out.println("结果:" + leftTicketJSONString);
        leftTicketJSONTOClass(leftTicketJSONString);

        table = (SmartTable<QueryLeftNewDTO>) findViewById(R.id.table);

        Column<String> stationTrainCodeColumn = new Column<>("车次", "stationTrainCode");
        stationTrainCodeColumn.setFixed(true);

        Column<String> fromStationNameColumn = new Column<>("出发", "fromStationName");

        Column<String> toStationNameColumn = new Column<>("到达", "toStationName");

        Column<String> startTimeColumn = new Column<>("开点", "startTime");

        Column<String> arriveTimeColumn = new Column<>("到点", "arriveTime");

        Column<String> lishiColumn = new Column<>("历时", "lishi");

        Column<String> grNumColumn = new Column<>("高软", "grNum");

        Column<String> qtNumColumn = new Column<>("其他", "qtNum");

        Column<String> rwNumColumn = new Column<>("软卧", "rwNum");

        Column<String> rzNumColumn = new Column<>("软座", "rzNum");

        Column<String> tzNumColumn = new Column<>("特等座", "tzNum");

        Column<String> wzNumColumn = new Column<>("无座", "wzNum");

        Column<String> ywNumColumn = new Column<>("硬卧", "ywNum");

        Column<String> yzNumColumn = new Column<>("硬座", "yzNum");

        Column<String> zeNumColumn = new Column<>("二等座", "zeNum");

        Column<String> zyNumColumn = new Column<>("一等座", "zyNum");

        Column<String> swzNumColumn = new Column<>("商务座", "swzNum");

        Column<String> srrbNumColumn = new Column<>("动卧", "srrbNum");

        Column<String> isSupportCreditsColumn = new Column<>("支持积分兑换", "isSupportCredits");

        Column<String> isSupportCardColumn = new Column<>("支持身份证", "isSupportCard");

        Column<String> startStationNameColumn = new Column<>("始发", "startStationName");

        Column<String> endStationNameColumn = new Column<>("终到", "endStationName");

        TableData<QueryLeftNewDTO> tableData = new TableData<>("余票信息", queryLeftNewDTOList, stationTrainCodeColumn, fromStationNameColumn, startTimeColumn, toStationNameColumn, arriveTimeColumn, lishiColumn, zeNumColumn, zyNumColumn, swzNumColumn, grNumColumn, rwNumColumn, srrbNumColumn, ywNumColumn, rzNumColumn, yzNumColumn, wzNumColumn, qtNumColumn, tzNumColumn, isSupportCardColumn, isSupportCreditsColumn, startStationNameColumn, endStationNameColumn);
        tableData.setOnRowClickListener(new TableData.OnRowClickListener<QueryLeftNewDTO>() {
            @Override
            public void onClick(Column column, QueryLeftNewDTO queryLeftNewDTO, int col, int row) {
                if (queryLeftNewDTO.getSecretStr().equals("null") || queryLeftNewDTO.getSecretStr().equals("")) {
                    Toast.makeText(leftTicketActivity.this, "该车次无法订票", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(leftTicketActivity.this, "开始订车次:" + queryLeftNewDTO.getStationTrainCode(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), submitOrderActivity.class);
                    intent.putExtra("queryLeftNewDTO", queryLeftNewDTO);
                    intent.putExtra("secretStr", queryLeftNewDTO.getSecretStr());
                    intent.putExtra("trainDateString", trainDateString);
                    intent.putExtra("backTrainDateString", trainDateString);
                    intent.putExtra("tourFlag", "dc");
                    intent.putExtra("purposeCodes", purposeCodes);
                    intent.putExtra("fromStationName", fromStationName);
                    intent.putExtra("toStationName", toStationName);
                    startActivity(intent);
                }
            }
        });

        table.setTableData(tableData);
        table.setZoom(true, 0.5f, 2);
    }

    void leftTicketJSONTOClass(String leftTicketJSONString) {
        JsonElement je = new JsonParser().parse(leftTicketJSONString);
        String resultString = je.getAsJsonObject().get("data").getAsJsonObject().get("result").toString();
        JsonArray resultJsonArray = je.getAsJsonObject().get("data").getAsJsonObject().get("result").getAsJsonArray();
        //resultString = resultString.substring(1, resultString.length() - 1);
        //String[] result = resultString.split(",");
        for (JsonElement jsonElement : resultJsonArray) {
            String s = jsonElement.getAsString();
            //s = s.substring(1, s.length() - 1);
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

    private Activity getActivity() {
        return this;
    }*/
}
