package com.dydl.electronicdemo.view.fragment.electronic;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dydl.electronicdemo.R;
import com.dydl.electronicdemo.utils.PictureUtils;
import com.dydl.electronicdemo.view.activity.electronic.EleSignActivity;
import com.dydl.electronicdemo.view.fragment.base.BaseFragment;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lwx on 2018/3/9.
 */

public class UploadPFragment extends BaseFragment {

    private static final int RESULT_OK = 100;
    @BindView(R.id.btn_last)
    Button btnLast;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.iv_front)
    ImageView ivFront;
    @BindView(R.id.ll_front)
    LinearLayout llFront;
    @BindView(R.id.iv_reverse)
    ImageView ivReverse;
    @BindView(R.id.ll_reverse)
    LinearLayout llReverse;
    private String imagePath;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;

    @Override
    public void operation() {

        RxView.clicks(btnNext).subscribe(o -> {
            ((EleSignActivity) getActivity()).replaceNowAudit();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_uploadp;
    }


    @OnClick({R.id.ll_front, R.id.ll_reverse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_front:
                showChoosePicDialog();
                break;
            case R.id.ll_reverse:

                break;
        }
    }

    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case CHOOSE_PICTURE: // 选择本地照片
                    Intent openAlbumIntent = new Intent(
                            Intent.ACTION_GET_CONTENT);
                    openAlbumIntent.setType("image/*");
                    startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                    break;
                case TAKE_PICTURE: // 拍照
                    Intent openCameraIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    tempUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "image.jpg"));
                    // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    startActivityForResult(openCameraIntent, TAKE_PICTURE);
                    break;
            }
        });
        builder.create().show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            //photo = PictureUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            uploadPic(photo);
            photo = PictureUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            ivFront.setImageBitmap(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        imagePath = PictureUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));

        Log.e("imagePath", imagePath + "");
        if (imagePath != null) {
            // 拿着imagePath上传了
            // 联网
            //new UpdateUserAvatarProtocol(String.valueOf(us_id),imagePath).doRequest(MyApplication.HL,this);
            //构造参数列表
            //String Token = (String) PrefUtils.get(MyApplication.getContext(), "Token", "");

        }
    }
}
