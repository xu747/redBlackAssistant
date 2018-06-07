package cn.charlesxu.redblackassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import cn.charlesxu.redblackassistant.BaseFragment;
import cn.charlesxu.redblackassistant.LoginActivity;
import cn.charlesxu.redblackassistant.MainActivity;
import cn.charlesxu.redblackassistant.MyApplication;
import cn.charlesxu.redblackassistant.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import static cn.charlesxu.redblackassistant.MyApplication.cookieJar;

public class UserFragment extends BaseFragment {
    private TextView userTextView;

    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.user_fragment,container,false);
        userTextView = view.findViewById(R.id.user_textView);
        userTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

        return view;
    }

    public void checkUser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String checkUserUrl = "https://kyfw.12306.cn/otn/login/checkUser";

//                OkHttpClient client = new OkHttpClient.Builder()
//                        .cookieJar(cookieJar)
//                        .build();

                OkHttpClient client = MyApplication.client;

                Request request = new Request.Builder()
                        .url(checkUserUrl)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseDataString = response.body().string();
                    System.out.println("CheckUser: " + responseDataString);
                    JsonElement je = new JsonParser().parse(responseDataString);
                    String resultString = je.getAsJsonObject().get("data").getAsJsonObject().get("flag").toString();
                    if(resultString.equals("false")){
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }else {
                        String initMy12306Url = "https://kyfw.12306.cn/otn/index/initMy12306";

                        client = MyApplication.client;

                        request = new Request.Builder()
                                .url(initMy12306Url)
                                .build();

                        response = client.newCall(request).execute();
                        String initMy12306HTML = response.body().string();

                        Document doc = Jsoup.parse(initMy12306HTML);
                        //选择所在节点
                        Elements elements = doc.select("span.login-txt");
                        //打印<a>标签里面的title
                        final String username = elements.get(0).getElementById("login_user").text();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                userTextView.setText(username);
                            }
                        };
                        handler.post(runnable);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
