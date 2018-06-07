package cn.charlesxu.redblackassistant;

import android.app.Application;
import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.litepal.LitePal;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    private static Context context;

    //public static ClearableCookieJar cookieJar;
    public static OkHttpClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        context = getApplicationContext();
        //cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
//        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
//        client = new OkHttpClient.Builder()
//                .cookieJar(cookieJar)
//                .build();


        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(client);
    }

//    public static ClearableCookieJar cookieJar =
//            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));

    public static Context getContext() {
        return context;
    }
}
