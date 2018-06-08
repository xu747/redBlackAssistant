package cn.charlesxu.redblackassistant;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.philliphsu.bottomsheetpickers.BottomSheetPickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import cn.charlesxu.redblackassistant.fragment.MainQueryFragment;
import cn.charlesxu.redblackassistant.model.PassengerDTO;
import cn.charlesxu.redblackassistant.model.QueryOrderData;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QueryOrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private RadioButton noCompleteOrderRadioButton,completeOrderRadioButton,queryByOrderDateRadioButton,queryBySetOffDateRadioButton,noSetOffRadioButton,historyOrderRadioButton;
    private TextView startDateTextView,endDateTextView;
    private Button queryButton;

    private String startDateString,endDateString;

    private OkHttpClient client = MyApplication.client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_order);

        noCompleteOrderRadioButton = findViewById(R.id.noCompleteOrder_radioButton);
        completeOrderRadioButton = findViewById(R.id.completeOrder_radioButton);
        queryByOrderDateRadioButton = findViewById(R.id.queryByOrderDate_radioButton);
        queryBySetOffDateRadioButton = findViewById(R.id.queryBySetOffDate_radioButton);
        noSetOffRadioButton = findViewById(R.id.noSetOff_radioButton);
        historyOrderRadioButton = findViewById(R.id.historyOrder_radioButton);

        startDateTextView = findViewById(R.id.startDate_textView);
        endDateTextView = findViewById(R.id.endDate_textView);

        queryButton = findViewById(R.id.query_button);


        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = createDialog();
                dialogFragment.show(getSupportFragmentManager(),"startDateDialog");
            }
        });

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = createDialog();
                dialogFragment.show(getSupportFragmentManager(),"endDateDialog");
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //查询已完成 | 未完成订单
                boolean isComplete = false;
                if(completeOrderRadioButton.isChecked()){
                    isComplete = true;
                }else if(noCompleteOrderRadioButton.isChecked()){
                    isComplete = false;
                }

                //查询未出行订单为G，查询历史订单为H
                String queryWhere = "G";
                if(historyOrderRadioButton.isChecked()){
                    queryWhere = "H";
                }else if(noSetOffRadioButton.isChecked()){
                    queryWhere = "G";
                }

                //按订票日期查询为1，按乘车日期查询为2
                int queryType = 1;
                if (queryBySetOffDateRadioButton.isChecked()){
                    queryType = 2;
                }else if (queryByOrderDateRadioButton.isChecked()){
                    queryType = 1;
                }

                queryOrder(isComplete,queryType,queryWhere,startDateString,endDateString);

            }
        });

    }


    public void queryOrder(final boolean isComplete, final int queryType, final String queryWhere, final String queryStartDate, final String queryEndDate){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String noCompleteOrderUrl = "https://kyfw.12306.cn/otn/queryOrder/queryMyOrderNoComplete";
                String completeOrderUrl = "https://kyfw.12306.cn/otn/queryOrder/queryMyOrder";

                //猜测是WEB请求时分页使用
                int pageIndex = 0;
                int pageSize = 8;

                //不明参数
                String _json_att = "";
                String come_from_flag = "my_order";
                String sequeue_train_name = "";

                FormBody body = null;
                Request request = null;

                //构造请求体
                if(!isComplete){
                    body = new FormBody.Builder()
                            .add("_json_att",_json_att)
                            .build();

                    request = new Request.Builder()
                            .addHeader("Host","kyfw.12306.cn")
                            .addHeader("Referer","https://kyfw.12306.cn/otn/queryOrder/init")
                            .post(body)
                            .url(noCompleteOrderUrl)
                            .build();
                }else {
                    body = new FormBody.Builder()
                            .add("come_from_flag",come_from_flag)
                            .add("pageIndex",String.valueOf(pageIndex))
                            .add("pageSize", String.valueOf(pageSize))
                            .add("query_where",queryWhere)
                            .add("queryStartDate", queryStartDate)
                            .add("queryEndDate",queryEndDate)
                            .add("queryType",String.valueOf(queryType))
                            .add("sequeue_train_name", sequeue_train_name)
                            .build();

                    request = new Request.Builder()
                            .addHeader("Host","kyfw.12306.cn")
                            .addHeader("Referer","https://kyfw.12306.cn/otn/queryOrder/init")
                            .post(body)
                            .url(completeOrderUrl)
                            .build();
                }

                try {
                    Response response = client.newCall(request).execute();
                    String responseDataString = response.body().string();
                    System.out.println("订单查询结果:" + responseDataString);

                    Intent intent = new Intent(getActivity(),QueryOrderResultActivity.class);
                    intent.putExtra("resultJSONString",responseDataString);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if(dialog.getTag().equals("startDateDialog")){
            startDateTextView.setText("乘车日期: " + DateFormat.getDateFormat(this).format(cal.getTime()));
            startDateString = processDate(year,monthOfYear + 1, dayOfMonth);
        }else {
            endDateTextView.setText("乘车日期: " + DateFormat.getDateFormat(this).format(cal.getTime()));
            endDateString = processDate(year,monthOfYear + 1, dayOfMonth);
        }


    }

    private DialogFragment createDialog() {
        BottomSheetPickerDialog dialog = null;
        boolean custom = false;
        boolean customDark = false;
        boolean themeDark = false;

        Calendar now = Calendar.getInstance();
        dialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        DatePickerDialog dateDialog = (DatePickerDialog) dialog;
        //dateDialog.setMinDate(now);
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

    private String processDate(int year,int month,int day){
        String dateString = "" + year;

        if(month < 10){
            dateString = dateString + "-" + "0" + month;
        }else {
            dateString = dateString + "-" + month;
        }

        if(day < 10){
            dateString = dateString + "-" + "0" + day;
        }else {
            dateString = dateString + "-" + day;
        }

        return dateString;
    }

    private Activity getActivity(){
        return this;
    }


}
