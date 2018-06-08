package cn.charlesxu.redblackassistant.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import cn.charlesxu.redblackassistant.MyApplication;
import cn.charlesxu.redblackassistant.R;
import cn.charlesxu.redblackassistant.db.MyPoint;
import cn.charlesxu.redblackassistant.utils.HTTPSUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CaptchaFragment extends DialogFragment {

    private ImageView imageView;
    private Handler handler;
    private Runnable runnableUi;
    private Paint paint;
    private Canvas canvas;
    private int startX,startY;
    private Bitmap captchaBitmap,bitmapCopy,bitmapYanzheng;
    private HTTPSUtils utils;
    private MyPoint points = new MyPoint();
    private StringBuilder sb = new StringBuilder();
    private OkHttpClient client = MyApplication.client;
    private int destoryData;
    CallBackValue callBackValue;

    //定义一个回调接口
    public interface CallBackValue{
        public void SendMessageValue(String strValue);
    }

    public Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public void getCaptchaPicture(){
        String captchaUrl = "https://kyfw.12306.cn/passport/captcha/captcha-image";
        Request request = new Request.Builder()
                .addHeader("login_site","E")
                .addHeader("module","login")
                .addHeader("rand","sjrand")
                .get()
                .url(captchaUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            byte[] captchaPicture = response.body().bytes();
            captchaBitmap = BitmapFactory.decodeByteArray(captchaPicture, 0, captchaPicture.length);
            sb = new StringBuilder();
            handler.post(runnableUi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkCaptcha(){
        String checkCaptchaUrl = "https://kyfw.12306.cn/passport/captcha/captcha-check";
        String captchaPassCode = sb.toString().substring(0,sb.toString().length()-1);
        System.out.println("验证码："+captchaPassCode);
        FormBody body = new FormBody.Builder()
                .add("answer", captchaPassCode)
                .add("login_site","E")
                .add("rand", "sjrand")
                .add("_json_att","")
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(checkCaptchaUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseDataString = response.body().string();
            //System.out.println(responseDataString);
            JsonElement je = new JsonParser().parse(responseDataString);
            String resultString = je.getAsJsonObject().get("result_code").toString();
            System.out.println("resultString" + resultString);
            callBackValue.SendMessageValue(resultString);
//            if(resultString.equals("\"4\"")){
//                System.out.println("成功");
//
//            }else {
//                getCaptchaPicture();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * fragment与activity产生关联是  回调这个方法
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        callBackValue =(CallBackValue) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton("确认", new positiveListener())
                .setNegativeButton("取消", new negativeListener())
                .setCancelable(false);

        OkHttpClient client = MyApplication.client;

        // 设置主题的构造方法
        // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.captcha_dialog, null);
        builder.setView(view);
        // Do Someting,eg: TextView tv = view.findViewById(R.id.tv);

        imageView = view.findViewById(R.id.captchaPicture_imageView);

        bitmapYanzheng = drawableToBitmap(getResources().getDrawable(R.drawable.ic_china_railways));

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) motionEvent.getX() / 2;
                        startY = (int) motionEvent.getY() / 2;
                        sb.append(startX + "," +startY);
                        sb.append(",");
                        System.out.println(sb.toString());
                        break;
                    //用户手指正在滑动
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        //每次绘制完毕之后，本次绘制的结束坐标变成下一次绘制的初始坐标
//                        canvas.drawCircle(startX, startY,20,paint);
                        startX = x;
                        startY = y;
                        break;
                    //用户手指离开屏幕
                    case MotionEvent.ACTION_UP:
                        canvas.drawBitmap(bitmapYanzheng, startX, startY, paint);
                        imageView.setImageBitmap(bitmapCopy);
                        break;
                }
                return true;
            }
        });

        handler = new Handler();
        runnableUi = new Runnable(){
            @Override
            public void run() {
                //更新界面
                imageView.setImageBitmap(captchaBitmap);
                imageView.buildDrawingCache();
                Bitmap bitmap1= imageView.getDrawingCache();
                bitmapCopy = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), bitmap1.getConfig());
                paint = new Paint();
                canvas = new Canvas(bitmapCopy);
                canvas.drawBitmap(bitmap1, new Matrix(), paint);
                imageView.setImageBitmap(bitmapCopy);
            }

        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                getCaptchaPicture();
            }
        }).start();

        return builder.create();
    }


    private class positiveListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    checkCaptcha();
                }
            }).start();

        }
    }

    private class negativeListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            getActivity().onBackPressed();
        }
    }
}
