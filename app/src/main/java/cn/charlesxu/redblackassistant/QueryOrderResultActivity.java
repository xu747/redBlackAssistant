package cn.charlesxu.redblackassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QueryOrderResultActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_order_result);

        String resultJSONString = getIntent().getStringExtra("resultJSONString");

        textView = findViewById(R.id.textView);
        textView.setText(resultJSONString);
    }
}
