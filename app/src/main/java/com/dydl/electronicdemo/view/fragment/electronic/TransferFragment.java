package com.dydl.electronicdemo.view.fragment.electronic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dydl.electronicdemo.R;
import com.dydl.electronicdemo.utils.EmptyUtils;
import com.dydl.electronicdemo.view.activity.electronic.EleSignActivity;
import com.dydl.electronicdemo.view.fragment.base.BaseFragment;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by lwx on 2018/3/9.
 * 过户
 */

public class TransferFragment extends BaseFragment {


    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_name_id)
    TextView tvNameId;
    @BindView(R.id.tv_user_type)
    TextView tvUserType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_user_addr)
    TextView tvUserAddr;


    @Override
    public void operation() {

        RxView.clicks(btnNext).subscribe(o -> {

            ((EleSignActivity) getActivity()).replaceBusiness();
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_transfer;
    }

}
