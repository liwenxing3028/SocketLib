package com.dydl.electronicdemo.view.activity.electronic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dydl.electronicdemo.R;
import com.dydl.electronicdemo.view.activity.base.BaseActivity;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ElectronicActivity extends BaseActivity {


    @BindView(R.id.btn_up_name)
    Button btnUpName;
    @BindView(R.id.btn_up_guohu)
    Button btnUpGuohu;
    @BindView(R.id.btn_up_stop)
    Button btnUpStop;
    @BindView(R.id.btn_up_star)
    Button btnUpStar;

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_electronic;
    }

    @Override
    protected void initActionBar() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.btn_up_name, R.id.btn_up_guohu, R.id.btn_up_stop, R.id.btn_up_star})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, EleSignActivity.class);
        switch (view.getId()) {
            case R.id.btn_up_name:
                intent.putExtra("title", "更名");
                intent.putExtra("title_code", 1);
                break;
            case R.id.btn_up_guohu:
                intent.putExtra("title", "过户");
                intent.putExtra("title_code", "2");
                break;
            case R.id.btn_up_stop:
                intent.putExtra("title", "暂停");
                intent.putExtra("title_code", "3");
                break;
            case R.id.btn_up_star:
                intent.putExtra("title", "回复暂停");
                intent.putExtra("title_code", "4");
                break;
        }
        startActivity(intent);
    }
}
