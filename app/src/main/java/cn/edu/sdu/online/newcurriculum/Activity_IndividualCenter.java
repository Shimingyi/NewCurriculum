package cn.edu.sdu.online.newcurriculum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.sdu.online.app.Main;

public class Activity_IndividualCenter extends Activity implements
        OnClickListener {

    private ImageView ivHead, ivSex;
    private EditText edWord, edTarget, edAge;
    private TextView edMajor, edName, edId, tvID;
    private Button btnEdit;
    private Boolean editable = false;
    private PopupWindow popupWindow;
    private ImageButton ibMan, ibWoman;
    private static int sexNum = -1;
    private SharedPreferences dataStore;
    private SharedPreferences.Editor dataStoreEditor;

    private Bitmap head;// 头像Bitmap
    @SuppressLint("SdCardPath")
    private final static String path = "/sdcard/sduonline/myHead/";// sd路径

    // Sharedpreferences裡面的TAG
    private static final String NAME = "stuname";
    private static final String AGE = "stuage";
    private static final String MAJOR = "specialty";
    private static final String ID_NUM = "stuid";
    private static final String WORD = "stuword";
    private static final String TARGET = "stutarget";
    private static final String SEX = "stusex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_individualcenter);

        dataStore = Main.getApp().getDataStore();

        findView();

        btnEdit.setOnClickListener(this);
        ivHead.setOnClickListener(this);
        ivSex.setOnClickListener(this);

        // 设置保存在SharedPreferences里面的 想说的话，目标等
        setSavedSharedPreferences();

        // 设置名字，年龄专业，学号
        /*
         * edMajorAge.setText(); edName.setText(); edId.setText();
		 */

        // 设置性别图像
        ivSex.setImageDrawable(getResources().getDrawable(
                R.drawable.individual_center_female));
        // 设置头像
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            ivHead.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }

    }

    private void findView() {
        ivHead = (ImageView) findViewById(R.id.individual_center_iv_head);
        ivSex = (ImageView) findViewById(R.id.individual_center_iv_sex);
        btnEdit = (Button) findViewById(R.id.individual_center_bt_edit);
        // 学号
        edId = (TextView) findViewById(R.id.individual_center_et_id);
        tvID = (TextView) findViewById(R.id.individual_center_tv_id);
        // 姓名
        edName = (TextView) findViewById(R.id.individual_center_et_name);
        // 专业
        edMajor = (TextView) findViewById(R.id.individual_center_et_major);
        // 年龄
        edAge = (EditText) findViewById(R.id.individual_center_et_age);
        // 想说的话
        edWord = (EditText) findViewById(R.id.individual_center_et_word);
        // 近期目标
        edTarget = (EditText) findViewById(R.id.individual_center_et_aim);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        dataStore = Main.getApp().getDataStore();
    }

    // “编辑”按钮触发事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.individual_center_bt_edit:
                if (!editable) {
                    Toast.makeText(getApplicationContext(), "开始编辑",
                            Toast.LENGTH_LONG).show();

                    // 設置Edittext為可編輯狀態

                    ivSex.setVisibility(View.VISIBLE);

                    // 想对自己说的话
                    edWord.setEnabled(true);
                    edWord.setFocusableInTouchMode(true);
                    edWord.setFocusable(true);
                    edWord.requestFocus();
                    // 近期目标
                    edTarget.setEnabled(true);
                    edTarget.setFocusable(true);
                    edTarget.setFocusableInTouchMode(true);
                    edTarget.requestFocus();

                    editable = true;


                    btnEdit.setText("完成");

                } else {
                    // 结束编辑
                    edTarget.setFocusable(false);
                    edWord.setFocusable(false);

                    editable = false;

                    saveEditContent();
                    btnEdit.setText("编辑");
                }
                break;
            // 点击头像后触发事件
            case R.id.individual_center_iv_head:
                if (editable) {
                    new android.app.AlertDialog.Builder(this)
                            .setTitle("头像选择")
                            .setNegativeButton("相册选取",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.cancel();
                                            Intent intent1 = new Intent(
                                                    Intent.ACTION_PICK, null);
                                            intent1.setDataAndType(
                                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                    "image/*");
                                            startActivityForResult(intent1, 1);
                                        }
                                    })
                            .setPositiveButton("相机拍照",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.cancel();
                                            String status = Environment
                                                    .getExternalStorageState();
                                            if (status
                                                    .equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
                                                Intent intent2 = new Intent(
                                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                                intent2.putExtra(
                                                        MediaStore.EXTRA_OUTPUT,
                                                        Uri.fromFile(new File(
                                                                Environment
                                                                        .getExternalStorageDirectory(),
                                                                "head.jpg")));
                                                startActivityForResult(intent2, 2);// 采用ForResult打开
                                            }
                                        }
                                    }).show();
                }
                break;
            case R.id.individual_center_iv_sex:
                // 性别按钮，弹出popupwindow
                if (editable) {
                    if (popupWindow == null) {
                        initPopupwindow();
                        popupWindow.showAsDropDown(v, 30, 0);
                    } else {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
                break;
            case R.id.individualcenter_sex_male:
                ivSex.setImageDrawable(getResources().getDrawable(
                        R.drawable.individual_center_female));
                sexNum = 1;
                popupWindow.dismiss();
                popupWindow = null;
                break;
            case R.id.individualcenter_sex_female:
                ivSex.setImageDrawable(getResources().getDrawable(
                        R.drawable.individual_center_male));
                sexNum = 0;
                popupWindow.dismiss();
                popupWindow = null;
                break;

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        ivHead.setImageBitmap(head);// 用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    /**
     * 调用系统的裁剪
     *
     * @param uri
     */

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    // 保存头像
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void initPopupwindow() {
        popupWindow = new PopupWindow();
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.individualcenter_popwindow,
                null);
        popupWindow.setContentView(layout);

        ibMan = (ImageButton) layout
                .findViewById(R.id.individualcenter_sex_male);
        ibWoman = (ImageButton) layout
                .findViewById(R.id.individualcenter_sex_female);
        ibMan.setOnClickListener(this);
        ibWoman.setOnClickListener(this);

        popupWindow.setHeight(android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    // 保存编辑好的想说话的，目标
    private void saveEditContent() {
        dataStoreEditor = dataStore.edit();
        // 想说的话
        if (!TextUtils.isEmpty(edWord.getText())) {
            dataStoreEditor.putString(WORD, edWord.getText().toString());
        }
        // 近期目标
        if (!TextUtils.isEmpty(edTarget.getText())) {
            dataStoreEditor.putString(TARGET, edTarget.getText().toString());
        }
        // 性别
        dataStoreEditor.putInt(SEX, sexNum);
        dataStoreEditor.commit();
        setSavedSharedPreferences();
    }

    // 读取用户信息并显示
    private void setSavedSharedPreferences() {

        String name = dataStore.getString(NAME, "");
        String age = dataStore.getString(AGE, "");
        String major = dataStore.getString(MAJOR, "");
        String idnum = dataStore.getString(ID_NUM, "");
        String word = dataStore.getString(WORD, null);
        String target = dataStore.getString(TARGET, null);
        int sex = dataStore.getInt(SEX, sexNum);
        Log.i("sex:" + sex, "setSavedSharedPreferences");

        if (idnum.equals("")) {
            tvID.setVisibility(View.GONE);
            edId.setVisibility(View.GONE);
        } else {
            edId.setVisibility(View.VISIBLE);
            tvID.setVisibility(View.VISIBLE);
            edId.setText(idnum);

        }
        if (age.equals("")) {
            edAge.setVisibility(View.GONE);
        } else {
            edAge.setVisibility(View.VISIBLE);
            edAge.setText(age + "岁");
        }
        edName.setText(name);
        edMajor.setText(major);
        edTarget.setText(target);
        edWord.setText(word);
        if (sex == -1) {
            ivSex.setImageDrawable(getResources().getDrawable(R.drawable.individual_center_sex));
        } else if (sex == 1) {
            ivSex.setImageDrawable(getResources().getDrawable(
                    R.drawable.individual_center_female));
        } else if (sex == 0) {
            ivSex.setImageDrawable(getResources().getDrawable(
                    R.drawable.individual_center_male));
        }

    }

    // popupwindow在点击窗体外时会消失
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // TODO Auto-generated method stub

        if (popupWindow != null && popupWindow.isShowing()) {

            popupWindow.dismiss();

            popupWindow = null;

        }

        return super.onTouchEvent(event);

    }

}