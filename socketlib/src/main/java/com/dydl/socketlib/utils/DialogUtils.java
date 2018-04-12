package com.dydl.socketlib.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dydl.socketlib.R;
import com.github.ybq.android.spinkit.SpinKitView;

public class DialogUtils {

    private Context mContext;
    private Dialog loadingDialog;

    public DialogUtils(Context context) {
        this.mContext = context;
    }

   /* *
     * 得到自定义的progressDialog
     *
     * @return*/

    public void createLoadingDialog(String msg, boolean cancel) {

        if (loadingDialog != null && loadingDialog.isShowing()) {
            return;
        }

        // 首先得到整个View
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.loading_dialog_view, null);
        // 获取整个布局
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.dialog_view);
        // 页面中的Img
        SpinKitView img = (SpinKitView) view.findViewById(R.id.spin_kit);
        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);

        tv_msg.setText(msg);

        // 创建自定义样式的Dialog
        loadingDialog = new Dialog(mContext, R.style.loading_dialog);
        // 设置返回键无效
        loadingDialog.setCancelable(cancel);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        //loadingDialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        final View decorView = loadingDialog.getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiFlags);
        final int finalUiFlags = uiFlags;
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {

            boolean hasMenuKey = ViewConfiguration.get(mContext).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (hasBackKey) {
                decorView.setSystemUiVisibility(finalUiFlags);
            }
        });
        loadingDialog.show();
    }


    public void dismissCreateLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }



}
