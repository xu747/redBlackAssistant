package cn.charlesxu.redblackassistant;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import cn.charlesxu.redblackassistant.adapter.OrderAdapter;
import cn.charlesxu.redblackassistant.model.OrderDB;
import cn.charlesxu.redblackassistant.model.OrderDTOData;
import cn.charlesxu.redblackassistant.model.QueryOrderData;

public class QueryOrderResultActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;

    private Gson gson = new Gson();
    private List<OrderDTOData> orderDBList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_order_result);

        String resultJSONString = getIntent().getStringExtra("resultJSONString");
        if (!resultJSONString.equals("")) {
            JsonElement je = new JsonParser().parse(resultJSONString);
            if(!(je.getAsJsonObject().get("data") == null)){
                JsonObject orderData = je.getAsJsonObject().get("data").getAsJsonObject();
                if (orderData.getAsJsonArray("orderDBList") == null) {
                    QueryOrderData queryOrderData = gson.fromJson(orderData, QueryOrderData.class);
                    orderDBList = queryOrderData.getOrderDTODataList();
                } else {
                    OrderDB orderDB = gson.fromJson(orderData, OrderDB.class);
                    orderDBList = orderDB.getOrderDBList();
                }
            }
        }

        //Init RecyclerView
        orderRecyclerView = findViewById(R.id.order_recyclerView);
        orderRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        orderRecyclerView.setAdapter(new OrderAdapter(orderDBList));

        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.divid_line));
        orderRecyclerView.addItemDecoration(divider);
    }
}
