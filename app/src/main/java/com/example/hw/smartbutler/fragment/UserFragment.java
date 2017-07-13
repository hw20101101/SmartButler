package com.example.hw.smartbutler.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw.smartbutler.R;
import com.example.hw.smartbutler.entity.MyUser;
import com.example.hw.smartbutler.ui.LoginActivity;
import com.example.hw.smartbutler.utils.HWLog;
import com.example.hw.smartbutler.utils.StaticClass;
import com.example.hw.smartbutler.utils.UtilTools;
import com.example.hw.smartbutler.view.CustomDialog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HW on 09/07/2017.
 * 个人中心
 */

public class UserFragment extends Fragment implements View.OnClickListener{

    private Button btn_change, btn_logout, btn_camera, btn_photo, btn_cancel;
    private TextView tv_editData;
    private EditText et_username, et_age, et_sex, et_desc;
    private CustomDialog photo_Dialog;
    //用户头像
    private CircleImageView img_profile;

    //用户头像设置相关
    public static final String PHOTO_IMAGE_FILE_NAME = "file.jpe";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int Photo_REQUEST_CODE = 101;
    public static final int PHOTOZOOM_REQUEST_CODE = 102;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

        btn_change = view.findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);

        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        tv_editData = view.findViewById(R.id.tv_editData);
        tv_editData.setOnClickListener(this);

        et_username = view.findViewById(R.id.et_username);
        et_sex = view.findViewById(R.id.et_sex);
        et_age = view.findViewById(R.id.et_age);
        et_desc = view.findViewById(R.id.et_desc);
        //设置控件不可编辑，也可以在xml中设置
        setEditTextEnabled(false);

        //用户头像
        img_profile = view.findViewById(R.id.img_profile);
        img_profile.setOnClickListener(this);

        //设置用户头像
        Bitmap bitmap = UtilTools.getImageFromShareUtils(getActivity());
        if (bitmap != null){
            img_profile.setImageBitmap(bitmap);
        }

        photo_Dialog = new CustomDialog(getActivity(), 0, 0, R.layout.dialog_photo, R.style.pop_anim_style, Gravity.BOTTOM, 0);
        //设置点击屏幕无效
        photo_Dialog.setCancelable(false);

        btn_camera = photo_Dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_photo = photo_Dialog.findViewById(R.id.btn_photo);
        btn_photo.setOnClickListener(this);
        btn_cancel = photo_Dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        /**
         *
         * 1.慕课网上的代码，首次进入APP点击跳过按钮时，并没有执行到 UserFragment，也没有调用到 MyUser；
         *
         * 2.而我的代码，首次进入APP点击跳过按钮时，执行到了 UserFragment，访问了 MyUser；
         *
         * 3.由于此时这里的 MyUser 为 null，所有报了空指针异常
         * */
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        if (user != null) {
            et_username.setText(user.getUsername());
            et_sex.setText(user.isSex() ? "男" : "女");
            et_age.setText(user.getAge() + "");
            et_desc.setText(user.getDesc());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.img_profile:
                photo_Dialog.show();
                break;

            case R.id.btn_camera:
                toCamera();
                break;

            case R.id.btn_photo:
                toPhoto();
                break;

            case R.id.btn_cancel:
                photo_Dialog.dismiss();
                break;

            case R.id.btn_change:
                saveUserData();
                break;

            case R.id.btn_logout:
                userLogout();
                break;

            case R.id.tv_editData:
                setEditTextEnabled(true);
                btn_change.setVisibility(View.VISIBLE);
                break;
        }
    }

    //跳转到相册
    private void toPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Photo_REQUEST_CODE);

        photo_Dialog.dismiss();
    }

    //跳转到相机
    private void toCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);

        photo_Dialog.dismiss();
    }

    //相机和相册的回调方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED){
            switch (requestCode){
                case CAMERA_REQUEST_CODE://相机
                    File file = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    photoZoom(Uri.fromFile(file));
                    break;

                case Photo_REQUEST_CODE://相册
                    photoZoom(data.getData());
                    break;

                case PHOTOZOOM_REQUEST_CODE://图片裁剪结果
                    handleAfterPhotoZoom(data);
                    break;
            }
        }
    }

    //图片裁剪
    private void photoZoom(Uri uri){
        if (uri == null){
            HWLog.e(" uri is null");
            return;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        //设置裁剪后图片的长宽比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        //设置裁剪后图片的尺寸
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTOZOOM_REQUEST_CODE);
    }

    //图片裁剪之后的处理
    private void handleAfterPhotoZoom(Intent data){
        if (data != null) {

            //给控件设置图片
            Bundle bundle = data.getExtras();
            if (bundle != null){
                Bitmap bitmap = bundle.getParcelable("data");
                img_profile.setImageBitmap(bitmap);
            }
        }
    }

    //用户退出登录
    private void userLogout(){
        //清除缓存的用户对象
        MyUser.logOut();
        //使 currentUser 为 null
        BmobUser currentUser = MyUser.getCurrentUser();

        //跳转到登录界面
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    //设置 EditText 的编辑状态
    private void setEditTextEnabled(boolean value){
        et_username.setEnabled(value);
        et_sex.setEnabled(value);
        et_age.setEnabled(value);
        et_desc.setEnabled(value);
    }

    //保存修改过的用户数据
    private void saveUserData(){
        String userName = et_username.getText().toString().trim();
        String sex = et_sex.getText().toString().trim();
        String age = et_age.getText().toString().trim();
        String desc = et_desc.getText().toString().trim();

        if (!TextUtils.isEmpty(userName)
                & !TextUtils.isEmpty(sex)
                & !TextUtils.isEmpty(age) ){

            MyUser user = new MyUser();
            user.setUsername(userName);
            user.setAge(Integer.parseInt(age));

            if (sex.equals("男")){
                user.setSex(true);
            } else {
                user.setSex(false);
            }

            if (TextUtils.isEmpty(desc)){
                user.setDesc(StaticClass.USER_DEDC_IS_NIL);
            } else {
                user.setDesc(desc);
            }

            BmobUser bmobUser = BmobUser.getCurrentUser();
            user.update(bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        setEditTextEnabled(false);
                        btn_change.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "修改失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getActivity(), StaticClass.EDIT_TEXT_NOT_NIL, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //保存用户头像
        UtilTools.saveImageToShareUtils(getActivity(), img_profile);
    }
}
