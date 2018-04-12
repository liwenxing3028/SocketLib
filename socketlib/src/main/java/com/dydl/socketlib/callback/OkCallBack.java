package com.dydl.socketlib.callback;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dydl.socketlib.error.ApiException;
import com.dydl.socketlib.utils.DialogUtils;
import com.dydl.socketlib.utils.ToastUtils;


/**
 * Created by lwx on 2018/1/26.
 */

public class OkCallBack<T> implements OkListener<T> {


    private final DialogUtils dialogmanager;
    private String loading;
    private Context context;

    public OkCallBack(Context context) {
        dialogmanager = new DialogUtils(context);
        this.context = context;
    }

    public OkCallBack(Context context, String loading) {
        dialogmanager = new DialogUtils(context);
        this.loading = loading;
        this.context = context;
    }

    @Override
    public void onStart() {
        if (loading == "" || loading == null) {
            dialogmanager.dismissCreateLoadingDialog();
        } else {
            dialogmanager.createLoadingDialog(loading, false);
        }
    }

    @Override
    public void onError(ApiException e) {
        Toast.makeText(context, e.getDisplayMessage(), Toast.LENGTH_LONG).show();
        Log.e("1212", "onError: "+e.getCode() + "..."+"" +e.getDisplayMessage() +"..."+e.getMessage() );
        dialogmanager.dismissCreateLoadingDialog();
    }

    @Override
    public void onSuccess(T result) {
        dialogmanager.dismissCreateLoadingDialog();
    }





}
