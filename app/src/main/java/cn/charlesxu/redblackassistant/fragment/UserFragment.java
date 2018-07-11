package cn.charlesxu.redblackassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import cn.charlesxu.redblackassistant.BaseFragment;
import cn.charlesxu.redblackassistant.LoginActivity;
import cn.charlesxu.redblackassistant.MyApplication;
import cn.charlesxu.redblackassistant.QueryOrderActivity;
import cn.charlesxu.redblackassistant.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserFragment extends BaseFragment {
    private TextView userTextView;
    private Button queryOrderButton;

    private Handler handler = new Handler();

    private OkHttpClient client = MyApplication.client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        userTextView = view.findViewById(R.id.user_textView);
        userTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

        queryOrderButton = view.findViewById(R.id.query_order_button);
        queryOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQueryOrderActivity();
            }
        });

        getUserName();
        return view;
    }

    public void checkUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String checkUserUrl = "https://kyfw.12306.cn/otn/login/checkUser";

                Request request = new Request.Builder()
                        .url(checkUserUrl)
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String responseDataString = response.body().string();
                    System.out.println("CheckUser: " + responseDataString);
                    JsonElement je = new JsonParser().parse(responseDataString);
                    String resultString = je.getAsJsonObject().get("data").getAsJsonObject().get("flag").toString();
                    if (resultString.equals("false")) {
                        initLoginActivity();
                    } else {
                        getUserName();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getUserName() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String initMy12306Url = "https://kyfw.12306.cn/otn/index/initMy12306";

                Request request = new Request.Builder()
                        .url(initMy12306Url)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String initMy12306HTML = response.body().string();

                    Document doc = Jsoup.parse(initMy12306HTML);
                    //选择所在节点
                    Elements elements = doc.select("span.login-txt");
                    //打印<a>标签里面的title
                    final String username = elements.get(0).getElementById("login_user").text();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (username.equals("")) {
                                userTextView.setText("用户未登录");
                            } else {
                                userTextView.setText(username);
                            }

                        }
                    };
                    handler.post(runnable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void initLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    public void initQueryOrderActivity() {
        Intent intent = new Intent(getActivity(), QueryOrderActivity.class);
        startActivity(intent);
    }

    boolean isCreated = true;

    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {
           getUserName();
        }
    }
}
