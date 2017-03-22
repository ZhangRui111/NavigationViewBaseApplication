package com.example.john.lifeclock10;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.ImageView;

import java.io.File;

/**
 * Created by john on 2017/3/22.
 */

public class FragmentSettings extends TakePhotoFragment implements View.OnClickListener {

    private View view;

    //takePhoto
    private TakePhoto takePhoto;
    private CropOptions cropOptions;
    private CompressConfig compressConfig;
    private File file;
    private Uri imageUri;

    //Base UIs
    private Button takeFromCameraBtn, takeFromGalleyBtn;
    private ImageView ImageViewUserIcon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initPermission();
        initClickEvent();
        initData();
    }

    private void initView() {
        ImageViewUserIcon = (ImageView) view.findViewById(R.id.imageView_user_icon);
        takeFromCameraBtn = (Button) view.findViewById(R.id.button_take_from_camera);
        takeFromGalleyBtn = (Button) view.findViewById(R.id.button_take_from_galley);
    }

    private void initPermission() {
        takeFromCameraBtn.setOnClickListener(this);
        takeFromGalleyBtn.setOnClickListener(this);
    }

    private void initClickEvent() {

    }

    private void initData() {
        //初始化TakePhoto对象
        takePhoto = getTakePhoto();
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        //设置压缩参数
        compressConfig=new CompressConfig.Builder().setMaxSize(50*1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig,true);  //设置为需要压缩
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_take_from_camera:
                imageUri = getImageCropUri();
                takePhoto.onPickFromCaptureWithCrop(imageUri,cropOptions);
                break;
            case R.id.button_take_from_galley:
                imageUri = getImageCropUri();
                takePhoto.onPickFromGalleryWithCrop(imageUri,cropOptions);
                break;
            default:
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String iconPath = result.getImage().getOriginalPath();
        //Log.i("TAG", iconPath);
        Glide.with(this).load(iconPath).into(ImageViewUserIcon);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Toast.makeText(getActivity(), "Error:" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    private Uri getImageCropUri() {
        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }
}
