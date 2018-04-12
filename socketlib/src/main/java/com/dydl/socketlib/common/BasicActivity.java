package com.dydl.socketlib.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.dydl.socketlib.R;
import com.dydl.socketlib.helper.OkStrHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lwx on 2018/3/12.
 */

public abstract class BasicActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mToolbarRight;
    private TextView mToolbarLeft;
    private ImageView mToolbarMineIcn;
    private boolean Tag = true;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StatusBarUtils.setWindowStatusBarColor(this, R.color.top_blue);
        try {
            int layoutResID = initView(savedInstanceState);
            if (layoutResID != 0) {//如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbarTitle = (TextView) findViewById(R.id.base_title);
        mToolbarRight = (TextView) findViewById(R.id.base_right);
        mToolbarLeft = (TextView) findViewById(R.id.base_change);
        mToolbarMineIcn = (ImageView) findViewById(R.id.base_mine);

        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(getTitle());
            //设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        initActionBar();
        initData();

    }

    protected abstract int initView(Bundle savedInstanceState);

    protected abstract void initActionBar();

    protected abstract void initData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        OkStrHelper.clearDisposable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
        if (null != getToolbar() && isShowBacking()) {
            if (Tag) {
                showBack();
            }
        }
    }

    public boolean ToolbarBack(boolean tag) {
        Tag = tag;
        return Tag;
    }

    public TextView getmToolbarTitle() {
        return mToolbarTitle;
    }

    public BasicActivity setmToolbarTitle(TextView mToolbarTitle) {
        this.mToolbarTitle = mToolbarTitle;
        return this;
    }

    public TextView getmToolbarRight() {
        return mToolbarRight;
    }

    public BasicActivity setmToolbarRight(TextView mToolbarRight) {
        this.mToolbarRight = mToolbarRight;
        return this;
    }

    public TextView getmToolbarLeft() {
        return mToolbarLeft;
    }

    public BasicActivity setmToolbarLeft(TextView mToolbarLeft) {
        this.mToolbarLeft = mToolbarLeft;
        return this;
    }

    public ImageView getmToolbarMineIcn() {
        return mToolbarMineIcn;
    }

    public BasicActivity setmToolbarMineIcn(ImageView mToolbarMineIcn) {
        this.mToolbarMineIcn = mToolbarMineIcn;
        return this;
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        } else {
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }

    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(com.dydl.socketlib.R.id.toolbar);
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(com.dydl.socketlib.R.drawable.titlebar_back);
        getToolbar().setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }


}
