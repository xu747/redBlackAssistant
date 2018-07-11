package cn.charlesxu.redblackassistant;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mozilla.javascript.NativeArray;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cn.charlesxu.redblackassistant.model.Passenger;
import cn.charlesxu.redblackassistant.model.PassengerDTOsData;
import cn.charlesxu.redblackassistant.model.QueryLeftNewDTO;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class submitOrderActivity extends AppCompatActivity {

    private String secretStr, trainDateString, backTrainDateString, purposeCodes, tourFlag, fromStationName, toStationName;
    private Handler handler = new Handler();
    private OkHttpClient client = MyApplication.client;
    private String globalRepeatSubmitToken;
    private Object ticketInfoForPassengerForm;
    private PassengerDTOsData passengerDTOsData;
    private QueryLeftNewDTO queryLeftNewDTO;
    private Gson gson = new Gson();
    private TextView titleTextView, trainDateTextView, stationTrainCodeTextView, stationAndTimeTextView;
    private Button bookTicketButton;
    private LinearLayout passengerLinearLayout;
    private RadioGroup ticketTypeRadioGroup;
    private List<CheckBox> passengerCheckBoxList = new LinkedList<>();
    private List<Passenger> passengerList = new LinkedList<>();
    private List<RadioButton> ticketTypeRadioButtonList = new LinkedList<>();
    private HashMap<String, String> seatTypeCodesMap = new LinkedHashMap<>();
    private String waitTime = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        queryLeftNewDTO = (QueryLeftNewDTO) getIntent().getSerializableExtra("queryLeftNewDTO");
        secretStr = getIntent().getStringExtra("secretStr");
        trainDateString = getIntent().getStringExtra("trainDateString");
        backTrainDateString = getIntent().getStringExtra("backTrainDateString");
        purposeCodes = getIntent().getStringExtra("purposeCodes");
        tourFlag = getIntent().getStringExtra("tourFlag");
        fromStationName = getIntent().getStringExtra("fromStationName");
        toStationName = getIntent().getStringExtra("toStationName");

        try {
            secretStr = java.net.URLDecoder.decode(secretStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        titleTextView = findViewById(R.id.title_textView);
        trainDateTextView = findViewById(R.id.trainDate_textView);
        stationTrainCodeTextView = findViewById(R.id.stationTrainCode_textView);
        stationAndTimeTextView = findViewById(R.id.stationAndTime_textView);
        passengerLinearLayout = findViewById(R.id.passenger_linearLayout);
        ticketTypeRadioGroup = findViewById(R.id.ticketType_radioGroup);
        bookTicketButton = findViewById(R.id.bookTicket_button);

        bookTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

        trainDateTextView.setText("出发日期：" + trainDateString);
        stationTrainCodeTextView.setText("车次：" + queryLeftNewDTO.getStationTrainCode());
        stationAndTimeTextView.setText(fromStationName + "站（" + queryLeftNewDTO.getStartTime() + "开）--> " + toStationName + "站（" + queryLeftNewDTO.getArriveTime() + "到）");

        addTicketTypeRadioButton();
        submitOrderRequest();

    }

    private void submitOrderRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String submitOrderRequestUrl = "https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest";

                FormBody body = new FormBody.Builder()
                        .add("secretStr", secretStr)
                        .add("train_date", trainDateString)
                        .add("back_train_date", backTrainDateString)
                        .add("tour_flag", tourFlag)
                        .add("purpose_codes", purposeCodes)
                        .add("query_from_station_name", fromStationName)
                        .add("query_to_station_name", toStationName)
                        .add("undefined", "")
                        .build();

                Request request = new Request.Builder()
                        .addHeader("Host", "kyfw.12306.cn")
                        .addHeader("Origin", "https://kyfw.12306.cn")
                        .addHeader("Referer", "https://kyfw.12306.cn/otn/leftTicket/init")
                        .post(body)
                        .url(submitOrderRequestUrl)
                        .build();

                Response response = null;
                JsonElement je = null;

                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response == null) {
                    return;
                }

                try {
                    je = new JsonParser().parse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (je.getAsJsonObject().get("status").toString().equals("false")) {
                    System.out.println(je.getAsJsonObject().get("messages").toString());
                    return;
                }


                String initDcUrl = "https://kyfw.12306.cn/otn/confirmPassenger/initDc";
                body = new FormBody.Builder()
                        .add("_json_att", "")
                        .build();

                request = new Request.Builder()
                        .addHeader("Host", "kyfw.12306.cn")
                        .addHeader("Origin", "https://kyfw.12306.cn")
                        .addHeader("Referer", "https://kyfw.12306.cn/otn/leftTicket/init")
                        .post(body)
                        .url(initDcUrl)
                        .build();

                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response == null) {
                    return;
                }

                Document document = null;

                try {
                    document = Jsoup.parse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Object globalRepeatSubmitTokenObj = null;
                Object ticketInfoForPassengerFormObj = null;

                Elements eles = document.getElementsByTag("script");
                for (Element ele : eles) {
                    String script = ele.toString();
                    if (script.contains("globalRepeatSubmitToken")) {
                        // 只取得script的內容
                        script = ele.childNode(0).toString();

                        // 使用ScriptEngine來parse
                        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
                        try {
                            engine.eval(script);
                        } catch (ScriptException e) {
                            e.printStackTrace();
                        }

                        // 取得你要的變數
                        globalRepeatSubmitTokenObj = engine.get("globalRepeatSubmitToken");
                        globalRepeatSubmitToken = globalRepeatSubmitTokenObj.toString();


                    } else if (script.contains("ticketInfoForPassengerForm")) {
                        // 只取得script的內容
                        script = ele.childNode(0).toString();

                        // 使用ScriptEngine來parse
                        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
                        try {
                            engine.eval(script);
                        } catch (ScriptException e) {
                            e.printStackTrace();
                        }

                        // 取得你要的變數
                        ticketInfoForPassengerForm = engine.get("ticketInfoForPassengerForm");

                    }

                }

                String getPassengerDTOsUrl = "https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs";
                body = new FormBody.Builder()
                        .add("_json_att", "")
                        .add("REPEAT_SUBMIT_TOKEN", globalRepeatSubmitTokenObj.toString())
                        .build();

                request = new Request.Builder()
                        .addHeader("Host", "kyfw.12306.cn")
                        .addHeader("Origin", "https://kyfw.12306.cn")
                        .addHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc")
                        .post(body)
                        .url(getPassengerDTOsUrl)
                        .build();

                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response == null) {
                    return;
                }

                try {
                    je = new JsonParser().parse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                passengerDTOsData = gson.fromJson(je.getAsJsonObject().get("data").toString(), PassengerDTOsData.class);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < passengerDTOsData.getNormal_passengers().size(); i++) {
                            CheckBox checkBox = new CheckBox(getActivity());
                            checkBox.setText(passengerDTOsData.getNormal_passengers().get(i).getPassenger_name());
                            passengerCheckBoxList.add(checkBox);
                            passengerList.add(passengerDTOsData.getNormal_passengers().get(i));
                            passengerLinearLayout.addView(checkBox);
                        }

                        Map<?, ?> map = (Map<?, ?>) ticketInfoForPassengerForm;
                        //getSeatTypeCodes
                        Map<?, ?> limitBuySeatTicketDTO = (Map<?, ?>) map.get("limitBuySeatTicketDTO");
                        NativeArray seatTypeCodes = (NativeArray) limitBuySeatTicketDTO.get("seat_type_codes");
                        for (Object object : seatTypeCodes) {
                            Map<?, ?> temp = (Map<?, ?>) object;
                            seatTypeCodesMap.put(temp.get("value").toString(), temp.get("id").toString());
                        }

                    }
                };
                handler.post(runnable);
            }
        }).start();
    }

    private void addTicketTypeRadioButton() {
        HashMap<String, String> hashMap = new HashMap<>();
        //if(!queryLeftNewDTO.getGrNum().equals("")){
        hashMap.put("高软", queryLeftNewDTO.getGrNum());
        //}
        //if(!queryLeftNewDTO.getQtNum().equals("")){
        hashMap.put("其他", queryLeftNewDTO.getQtNum());
        //}
        //if(!queryLeftNewDTO.getRwNum().equals("")){
        hashMap.put("软卧", queryLeftNewDTO.getGrNum());
        //}
        //if(!queryLeftNewDTO.getRzNum().equals("")){
        hashMap.put("软座", queryLeftNewDTO.getRzNum());
        //}
        //if(!queryLeftNewDTO.getTzNum().equals("")){
        hashMap.put("特等座", queryLeftNewDTO.getTzNum());
        //}
        //if(!queryLeftNewDTO.getWzNum().equals("")){
        hashMap.put("无座", queryLeftNewDTO.getWzNum());
        //}
        //if(!queryLeftNewDTO.getYwNum().equals("")){
        hashMap.put("硬卧", queryLeftNewDTO.getYwNum());
        //}
        //if(!queryLeftNewDTO.getYzNum().equals("")){
        hashMap.put("硬座", queryLeftNewDTO.getYzNum());
        //}
        //if(!queryLeftNewDTO.getZeNum().equals("")){
        hashMap.put("二等座", queryLeftNewDTO.getZeNum());
        //}
        //if(!queryLeftNewDTO.getZyNum().equals("")){
        hashMap.put("一等座", queryLeftNewDTO.getZyNum());
        //}
        //if(!queryLeftNewDTO.getSwzNum().equals("")){
        hashMap.put("商务座", queryLeftNewDTO.getSwzNum());
        //}
        //if(!queryLeftNewDTO.getSrrbNum().equals("")){
        hashMap.put("动卧", queryLeftNewDTO.getSrrbNum());
        //}

        for (String key : hashMap.keySet()) {
            RadioButton radioButton = new RadioButton(getActivity());
            if (hashMap.get(key).equals("有")) {
                radioButton.setText(key + "(" + hashMap.get(key) + "票)");
                ticketTypeRadioButtonList.add(radioButton);
                ticketTypeRadioGroup.addView(radioButton);
            } else if (hashMap.get(key).equals("") || hashMap.get(key).equals("无")) {

            } else {
                radioButton.setText(key + "(" + hashMap.get(key) + "张)");
                ticketTypeRadioButtonList.add(radioButton);
                ticketTypeRadioGroup.addView(radioButton);
            }

        }

    }

    private void submitOrder() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String checkOrderInfoUrl = "https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo";

                String passengerTicketString = "";
                String oldPassengerString = "";
                String seatTypeCodeString = null;

                for (int i = 0; i < passengerCheckBoxList.size(); i++) {
                    CheckBox checkBox = passengerCheckBoxList.get(i);
                    if (checkBox.isChecked()) {
                        Passenger passenger = passengerList.get(i);

                        //从第二个乘客开始，前面需要加_将各个乘客分割。
                        if (!passengerTicketString.equals("")) {
                            passengerTicketString += "_";
                        }

                        for (RadioButton radioButton : ticketTypeRadioButtonList) {
                            if (radioButton.isChecked()) {
                                String seatTypeNameString = radioButton.getText().toString().substring(0, radioButton.getText().toString().indexOf("("));
                                passengerTicketString += seatTypeCodesMap.get(seatTypeNameString) + ",";
                                seatTypeCodeString = seatTypeCodesMap.get(seatTypeNameString);
                                break;
                            }
                        }
                        passengerTicketString += "0,1,";

                        //注意：出现扣票失败时，先检查oldPassengerString尾部的1(或者3)。
                        passengerTicketString += passenger.getPassenger_name() + "," + passenger.getPassenger_id_type_code() + "," + passenger.getPassenger_id_no() + "," + passenger.getMobile_no() + "," + "N";
                        oldPassengerString += passenger.getPassenger_name() + "," + passenger.getPassenger_id_type_code() + "," + passenger.getPassenger_id_no() + "," + "1" + "_";

                    }
                }


                FormBody body = new FormBody.Builder()
                        .add("cancel_flag", "2")
                        .add("bed_level_order_num", "000000000000000000000000000000")
                        .add("passengerTicketStr", passengerTicketString)
                        .add("oldPassengerStr", oldPassengerString)
                        .add("tour_flag", "dc")
                        .add("randCode", "")
                        .add("whatsSelect", "1")
                        .add("_json_att", "")
                        .add("REPEAT_SUBMIT_TOKEN", globalRepeatSubmitToken)
                        .build();

                Request request = new Request.Builder()
                        .addHeader("Host", "kyfw.12306.cn")
                        .addHeader("Origin", "https://kyfw.12306.cn")
                        .addHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc")
                        .post(body)
                        .url(checkOrderInfoUrl)
                        .build();

                Response response = null;
                JsonElement je = null;

                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response == null) {
                    return;
                }

                try {
                    je = new JsonParser().parse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (je.getAsJsonObject().get("status").toString().equals("false")) {
                    System.out.println("checkOrderInfo错误 : " + je.getAsJsonObject().get("messages").toString());
                    return;
                }

                String getQueueCountUrl = "https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount";

                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat format2 = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH);
                Date trainDate = null;
                String trainDateFormatString = null;
                try {
                    trainDate = format1.parse(trainDateString);
                    trainDateFormatString = format2.format(trainDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (trainDate == null || trainDateFormatString == null) {
                    return;
                }

                Map<?, ?> map = (Map<?, ?>) ticketInfoForPassengerForm;
                String leftTicketString = (String) map.get("leftTicketStr");
                String trainLocationString = (String) map.get("train_location");
                String purposeCodesString = (String) map.get("purpose_codes");
                String keyCheckIsChangeString = (String) map.get("key_check_isChange");

                try {
                    leftTicketString = URLDecoder.decode(leftTicketString, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


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

                body = new FormBody.Builder()
                        .add("train_date", trainDateFormatString)
                        .add("train_no", queryLeftNewDTO.getTrainNo())
                        .add("stationTrainCode", queryLeftNewDTO.getStationTrainCode())
                        .add("seatType", seatTypeCodeString)
                        .add("fromStationTelecode", queryLeftNewDTO.getFromStationTelecode())
                        .add("toStationTelecode", queryLeftNewDTO.getToStationTelecode())
                        .add("leftTicket", leftTicketString)
                        .add("purpose_codes", purposeCodesString)
                        .add("train_location", trainLocationString)
                        .add("_json_att", "")
                        .add("REPEAT_SUBMIT_TOKEN", globalRepeatSubmitToken)
                        .build();

                request = new Request.Builder()
                        .addHeader("Host", "kyfw.12306.cn")
                        .addHeader("Origin", "https://kyfw.12306.cn")
                        .addHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc")
                        .post(body)
                        .url(getQueueCountUrl)
                        .build();

                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response == null) {
                    return;
                }

                try {
                    je = new JsonParser().parse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (je.getAsJsonObject().get("status").toString().equals("false")) {
                    System.out.println("getQueueCount错误 : " + je.getAsJsonObject().get("messages").toString());
                    return;
                }


                String confirmSingleForQueueUrl = "https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue";
                body = new FormBody.Builder()
                        .add("passengerTicketStr", passengerTicketString)
                        .add("oldPassengerStr", oldPassengerString)
                        .add("randCode", "")
                        .add("key_check_isChange", keyCheckIsChangeString)
                        .add("purpose_codes", purposeCodesString)
                        .add("leftTicketStr", leftTicketString)
                        .add("train_location", trainLocationString)
                        .add("choose_seats", "")
                        .add("seatDetailType", "000")
                        .add("whatsSelect", "1")
                        .add("roomType", "00")
                        .add("dwAll", "N")
                        .add("_json_att", "")
                        .add("REPEAT_SUBMIT_TOKEN", globalRepeatSubmitToken)
                        .build();

                request = new Request.Builder()
                        .addHeader("Host", "kyfw.12306.cn")
                        .addHeader("Origin", "https://kyfw.12306.cn")
                        .addHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                        .post(body)
                        .url(confirmSingleForQueueUrl)
                        .build();

                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response == null) {
                    return;
                }

                try {
                    je = new JsonParser().parse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (je.getAsJsonObject().get("status").toString().equals("false")) {
                    System.out.println("confirmSingleForQueue错误 : " + je.getAsJsonObject().get("data").toString());
                    return;
                }

                if (je.getAsJsonObject().get("data").getAsJsonObject().get("submitStatus").toString().equals("false")) {
                    String errMsg = je.getAsJsonObject().get("data").getAsJsonObject().get("errMsg").toString();
                    System.out.println("入队失败：" + errMsg);
                    return;
                }


                String queryOrderWaitTimeUrl = "https://kyfw.12306.cn/otn/confirmPassenger/queryOrderWaitTime?random=" + "&tourFlag=dc&_json_att=&REPEAT_SUBMIT_TOKEN=" + globalRepeatSubmitToken;

                request = new Request.Builder()
                        .addHeader("Host", "kyfw.12306.cn")
                        .addHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc")
                        .url(queryOrderWaitTimeUrl)
                        .build();

                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response == null) {
                    return;
                }

                try {
                    je = new JsonParser().parse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (je.getAsJsonObject().get("status").toString().equals("false")) {
                    System.out.println("queryOrderWaitTimeUrl错误 : " + je.getAsJsonObject().get("messages").toString());
                    return;
                }

                while (je.getAsJsonObject().get("data").getAsJsonObject().get("orderId").equals("null")) {
                    waitTime = "排队中，需要等待" + je.getAsJsonObject().get("data").getAsJsonObject().get("waitTime").toString() + "秒";
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), waitTime, Toast.LENGTH_SHORT).show();

                        }
                    };
                    handler.post(runnable);
                    try {
                        response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                final String orderIdString = je.getAsJsonObject().get("data").getAsJsonObject().get("orderId").toString();
                Runnable successRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "订单已成功下单！", Toast.LENGTH_SHORT).show();
                    }
                };
                handler.post(successRunnable);

                String resultOrderForDcQueueUrl = "https://kyfw.12306.cn/otn/confirmPassenger/resultOrderForDcQueue";
                body = new FormBody.Builder()
                        .add("orderSequence_no", orderIdString)
                        .add("REPEAT_SUBMIT_TOKEN", globalRepeatSubmitToken)
                        .build();

                request = new Request.Builder()
                        .addHeader("Host", "kyfw.12306.cn")
                        .addHeader("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc")
                        .post(body)
                        .url(resultOrderForDcQueueUrl)
                        .build();

                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response == null) {
                    return;
                }

                try {
                    je = new JsonParser().parse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (je.getAsJsonObject().get("status").toString().equals("false")) {
                    System.out.println("resultOrderForDcQueue错误 : " + je.getAsJsonObject().get("messages").toString());
                    return;
                }

                if (je.getAsJsonObject().get("data").getAsJsonObject().get("submitStatus").toString().equals("false")) {
                    String errMsg = je.getAsJsonObject().get("data").getAsJsonObject().get("errMsg").toString();
                    System.out.println("下单失败：" + errMsg);
                    return;
                }


            }
        }).start();
    }

    private Activity getActivity() {
        return this;
    }
}
