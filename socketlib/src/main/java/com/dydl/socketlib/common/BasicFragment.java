package com.dydl.socketlib.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dydl.socketlib.OkSocket;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lwx on 2018/3/12.
 */

public abstract class BasicFragment extends Fragment {


    protected BasicActivity mActivity;
    private Unbinder unbind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (BasicActivity) this.getActivity();
        /*mActivity.navigationBarStatusBar(mActivity, true);*/
        View bodyView = inflater.inflate(getLayoutId(), container, false);
        unbind =ButterKnife.bind(this, bodyView);
        operation();
        return bodyView;
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();//注销
    }

    //定义为抽象方法，实现类必须重写该方法
    public abstract void operation();


    public abstract int getLayoutId();

}
