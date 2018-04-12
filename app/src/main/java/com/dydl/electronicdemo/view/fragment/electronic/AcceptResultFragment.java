package com.dydl.electronicdemo.view.fragment.electronic;

import android.widget.Button;

import com.dydl.electronicdemo.R;
import com.dydl.electronicdemo.view.activity.electronic.EleSignActivity;
import com.dydl.electronicdemo.view.fragment.base.BaseFragment;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;

/**
 * Created by lwx on 2018/3/9.
 */

public class AcceptResultFragment extends BaseFragment {


    @BindView(R.id.btn_over)
    Button btnOver;

    @Override
    public void operation() {

        RxView.clicks(btnOver).subscribe(o -> {
            ((EleSignActivity) getActivity()).replaceFinish();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_accept_result;
    }
}
