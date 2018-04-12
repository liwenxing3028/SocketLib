package com.dydl.electronicdemo.view.activity.electronic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dydl.electronicdemo.R;
import com.dydl.electronicdemo.view.activity.base.BaseActivity;
import com.dydl.electronicdemo.view.fragment.electronic.AcceptResultFragment;
import com.dydl.electronicdemo.view.fragment.electronic.BusinessFragment;
import com.dydl.electronicdemo.view.fragment.electronic.ContactFragment;
import com.dydl.electronicdemo.view.fragment.electronic.NowAuditFragment;
import com.dydl.electronicdemo.view.fragment.electronic.QueryUserFragment;
import com.dydl.electronicdemo.view.fragment.electronic.TransferFragment;
import com.dydl.electronicdemo.view.fragment.electronic.UploadPFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EleSignActivity extends BaseActivity {


    @BindView(R.id.tv_jbxx)
    TextView tvJbxx;
    @BindView(R.id.tv_ywxx1)
    TextView tvYwxx1;
    @BindView(R.id.tv_ywxx)
    TextView tvYwxx;
    @BindView(R.id.tv_lxxx1)
    TextView tvLxxx1;
    @BindView(R.id.tv_lxxx)
    TextView tvLxxx;
    @BindView(R.id.tv_sczj1)
    TextView tvSczj1;
    @BindView(R.id.tv_sczj)
    TextView tvSczj;
    @BindView(R.id.tv_xcsh1)
    TextView tvXcsh1;
    @BindView(R.id.tv_xcsh)
    TextView tvXcsh;
    @BindView(R.id.tv_sltz1)
    TextView tvSltz1;
    @BindView(R.id.tv_sltz)
    TextView tvSltz;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sbyh)
    TextView tvSbyh;
    @BindView(R.id.tv_sbyh1)
    TextView tvSbyh1;
    private TransferFragment transferFragment;
    private BusinessFragment businessFragment;
    private int title_code;
    private QueryUserFragment queryuserFragment;

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_elesign;
    }

    @Override
    protected void initActionBar() {

    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        title_code = intent.getIntExtra("title_code", 0);
        tvTitle.setText("业务类型--  " + title);


        queryuserFragment = new QueryUserFragment();
        addFragment(R.id.fl_content, queryuserFragment, "queryuser");
        tvSbyh.setTextColor(getResources().getColor(R.color.red));

    }

    public void replaceBusiness() {
        tvJbxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx1.setTextColor(getResources().getColor(R.color.red));
        tvLxxx.setTextColor(getResources().getColor(R.color.text333));
        tvLxxx1.setTextColor(getResources().getColor(R.color.text333));
        businessFragment = new BusinessFragment();
        replaceFragment(R.id.fl_content, businessFragment, "business");
    }
    public void replaceTransferQuery() {
        tvJbxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx.setTextColor(getResources().getColor(R.color.text333));
        tvYwxx1.setTextColor(getResources().getColor(R.color.text333));
        /*transferFragment = new TransferFragment();
        Bundle args = new Bundle();
        args.putString("requetStartTime", requetStartTime);
        args.putString("requetEndTime", requetEndTime);
        transferFragment.setArguments(args);*/
        replaceFragment(R.id.fl_content, transferFragment, "transfer");
    }
    public void replaceTransfer() {
        tvJbxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx.setTextColor(getResources().getColor(R.color.text333));
        tvYwxx1.setTextColor(getResources().getColor(R.color.text333));
        transferFragment = new TransferFragment();
        replaceFragment(R.id.fl_content, transferFragment, "transfer");
    }

    public void replaceContact() {
        tvJbxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx1.setTextColor(getResources().getColor(R.color.red));
        tvLxxx.setTextColor(getResources().getColor(R.color.red));
        tvLxxx1.setTextColor(getResources().getColor(R.color.red));
        replaceFragment(R.id.fl_content, new ContactFragment(), "contact");
    }

    public void replaceUpload() {
        tvJbxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx1.setTextColor(getResources().getColor(R.color.red));
        tvLxxx.setTextColor(getResources().getColor(R.color.red));
        tvLxxx1.setTextColor(getResources().getColor(R.color.red));
        tvSczj.setTextColor(getResources().getColor(R.color.red));
        tvSczj1.setTextColor(getResources().getColor(R.color.red));
        replaceFragment(R.id.fl_content, new UploadPFragment(), "upload");
    }

    public void replaceNowAudit() {
        tvJbxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx1.setTextColor(getResources().getColor(R.color.red));
        tvLxxx.setTextColor(getResources().getColor(R.color.red));
        tvLxxx1.setTextColor(getResources().getColor(R.color.red));
        tvSczj.setTextColor(getResources().getColor(R.color.red));
        tvSczj1.setTextColor(getResources().getColor(R.color.red));
        tvXcsh.setTextColor(getResources().getColor(R.color.red));
        tvXcsh1.setTextColor(getResources().getColor(R.color.red));
        replaceFragment(R.id.fl_content, new NowAuditFragment(), "nowAudit");
    }

    public void replaceAcceptResult() {
        tvJbxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx.setTextColor(getResources().getColor(R.color.red));
        tvYwxx1.setTextColor(getResources().getColor(R.color.red));
        tvLxxx.setTextColor(getResources().getColor(R.color.red));
        tvLxxx1.setTextColor(getResources().getColor(R.color.red));
        tvSczj.setTextColor(getResources().getColor(R.color.red));
        tvSczj1.setTextColor(getResources().getColor(R.color.red));
        tvXcsh.setTextColor(getResources().getColor(R.color.red));
        tvXcsh1.setTextColor(getResources().getColor(R.color.red));
        tvSltz.setTextColor(getResources().getColor(R.color.red));
        tvSltz1.setTextColor(getResources().getColor(R.color.red));
        replaceFragment(R.id.fl_content, new AcceptResultFragment(), "acceptResult");
    }

    public void replaceFinish() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }

}
