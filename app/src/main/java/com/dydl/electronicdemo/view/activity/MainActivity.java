package com.dydl.electronicdemo.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dydl.electronicdemo.R;
import com.dydl.electronicdemo.module.ExamBean;
import com.dydl.socketlib.OkSocket;
import com.dydl.socketlib.callback.OkCallBack;
import com.dydl.socketlib.helper.OkParams;
import com.dydl.socketlib.utils.DateFormatUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        OkParams okParams = new OkParams();
        okParams.put("1", "ZN232")
                .put("2", "37401000101")
                .put("3", "")
                .put("4", DateFormatUtils.getCurrentTime(DateFormatUtils.PATTERN_STANDARD12W))
                .put("5", "sysadmin");
        Log.e("1212", okParams.toGetParams());


        OkSocket.<ExamBean>sendMsgBean(
                okParams,//上传数据
                6000,//端口号
                ExamBean.class,
                new OkCallBack<ExamBean>(this, "") {
                    @Override
                    public void onSuccess(ExamBean result) {
                        super.onSuccess(result);
                        Log.e("1212", result.get_$0() + "...." + result.get_$1() + "...." +
                                result.get_$2() + "...." +
                                result.get_$3().get(0).get_$0() + "...."
                        );
                    }
                }
        );

        OkSocket.sendMsgString(
                okParams,//上传数据
                6000,//端口号
                new OkCallBack<String>(this, "") {
                    @Override
                    public void onSuccess(String result) {
                        super.onSuccess(result);
                        Log.e("1212", result);
                    }
                }
        );

    }
}
