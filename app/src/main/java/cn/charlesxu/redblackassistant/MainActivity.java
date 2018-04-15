package cn.charlesxu.redblackassistant;

import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.philliphsu.bottomsheetpickers.BottomSheetPickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import cn.charlesxu.redblackassistant.model.Station;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener{

    private static final String TAG = "MainActivity";

    private TextView mTextMessage,mText;
    private CheckBox isStudentCheckBox;
    private Button fromStationButton,endStationButton,queryButton;
    private ImageButton exchageButton;

    static Station fromStation,endStation;
    static int trainYear,trainMonth,trainDay;
    static boolean isStudentTicket;

    static String adultTicket = "ADULT";
    static String studentTicket = "0X00";

    private void init(){
        Station station_bjb = new Station();
        station_bjb.setId(0);
        station_bjb.setTgCode("BOP");
        station_bjb.setStationName("北京北");
        station_bjb.setPinYin("beijingbei");
        station_bjb.setInitialism("bjb");
        station_bjb.setPinYinInitialism("bjb");

        Station station_bjn = new Station();
        station_bjn.setId(0);
        station_bjn.setTgCode("BOP");
        station_bjn.setStationName("北京南");
        station_bjn.setPinYin("beijingnan");
        station_bjn.setInitialism("bjb");
        station_bjn.setPinYinInitialism("bjb");

        fromStation = station_bjb;
        endStation = station_bjn;

        Calendar now = Calendar.getInstance();
        trainYear = now.get(Calendar.YEAR);
        trainMonth = now.get(Calendar.MONTH) + 1;
        trainDay = now.get(Calendar.DAY_OF_MONTH);
        mText.setText("乘车日期: " + DateFormat.getDateFormat(this).format(now.getTime()));

        isStudentTicket = false;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.date_textView);
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = createDialog();
                dialog.show(getSupportFragmentManager(), TAG);
            }
        });

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fromStationButton = (Button) findViewById(R.id.fromStation_Button);
        endStationButton = (Button) findViewById(R.id.endStation_Button);
        exchageButton = (ImageButton) findViewById(R.id.exchange_imageButton);

        fromStationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StationActivity.class);
                startActivityForResult(intent,1);
            }
        });

        endStationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StationActivity.class);
                startActivityForResult(intent,2);
            }
        });

        exchageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exchangeStation();
            }
        });

        isStudentCheckBox = findViewById(R.id.isStudent_checkBox);
        isStudentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isStudentTicket = true;
                }else {
                    isStudentTicket = false;
                }
            }
        });

        queryButton = findViewById(R.id.query_button);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String ticketCategory;
                        if(isStudentTicket){
                            ticketCategory = studentTicket;
                        }else {
                            ticketCategory = adultTicket;
                        }
                        String trainMonthString = "01";
                        if(trainMonth < 10){
                            trainMonthString = "0" + trainMonth;
                        }
                        String queryUrl = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=" + trainYear + "-" + trainMonthString + "-" + trainDay + "&leftTicketDTO.from_station=" + fromStation.getTgCode() + "&leftTicketDTO.to_station=" + endStation.getTgCode() + "&purpose_codes=" + ticketCategory;
                        System.out.println("URL:" + queryUrl);

                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(queryUrl).build();
                        try {
                            Response response = client.newCall(request).execute();
                            String responseDataString = response.body().string();
                            System.out.println("结果："+responseDataString);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        init();
        getStationsFrom12306();
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mText.setText("乘车日期: " + DateFormat.getDateFormat(this).format(cal.getTime()));
        trainYear = year;
        trainMonth = monthOfYear + 1;
        trainDay = dayOfMonth;
    }

    private DialogFragment createDialog() {
        BottomSheetPickerDialog dialog = null;
        boolean custom = false;
        boolean customDark = false;
        boolean themeDark = false;

        Calendar now = Calendar.getInstance();
        dialog = DatePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        DatePickerDialog dateDialog = (DatePickerDialog) dialog;
        dateDialog.setMinDate(now);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.YEAR, 10);
        dateDialog.setMaxDate(max);
        dateDialog.setYearRange(1970, 2032);

        dialog.setThemeDark(themeDark);
        if (custom || customDark) {
            dialog.setAccentColor(0xFFFF4081);
            dialog.setBackgroundColor(custom? 0xFF90CAF9 : 0xFF2196F3);
            dialog.setHeaderColor(custom? 0xFF90CAF9 : 0xFF2196F3);
            dialog.setHeaderTextDark(custom);
        }

        return dialog;
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Station selectStation = (Station) data.getSerializableExtra("station");
                    fromStation = selectStation;
                    fromStationButton.setText(selectStation.getStationName());
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Station selectStation = (Station) data.getSerializableExtra("station");
                    endStation = selectStation;
                    endStationButton.setText(selectStation.getStationName());
                }
                break;
        }
    }

    public void exchangeStation(){
        Station tempStation = fromStation;
        fromStation = endStation;
        endStation = tempStation;
        fromStationButton.setText(fromStation.getStationName());
        endStationButton.setText(endStation.getStationName());
    }

    private void getStationsFrom12306(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://kyfw.12306.cn/otn/resources/js/framework/station_name.js").build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseDataString = response.body().string();

                    //处理station_name.js数据
                    responseDataString = responseDataString.substring(20,responseDataString.length()-2);
                    //System.out.println(responseDataString);
                    String[] rawStation = responseDataString.split("\\@");

                    //DataSupport.deleteAll(Station.class);
                    //List<Station> stations = new ArrayList<>();

                    for(int i = 1; i < rawStation.length; i++){
                        String[] stationStringData = rawStation[i].split("\\|");
                        Station station = new Station();
                        station.setPinYinInitialism(stationStringData[0]);
                        station.setStationName(stationStringData[1]);
                        station.setTgCode(stationStringData[2]);
                        station.setPinYin(stationStringData[3]);
                        station.setInitialism(stationStringData[4]);
                        station.setId(new Integer(stationStringData[5]));

                        List<Station> stations = DataSupport.where("tgCode = ?",station.getTgCode()).find(Station.class);
                        if(stations.isEmpty()){
                            station.save();
                        }
                    }
                    //DataSupport.saveOrUpdate(stations);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
