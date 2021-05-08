package com.example.android.tflitecamerademo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity {

    /**
     * 有关布局的一些声明
     */
    private Button mCamera;  //初始界面选择的相机按钮
    private Button mAlbum;   //初始界面选择的相册按钮
    private ImageButton mbaike;


    public static int flag;
    public static final int camera = 1;
    public static final int album = 2;

    private static final int CROP_PHOTO = 2;
    private static final int REQUEST_CODE_PICK_IMAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();  //初始化布局
        initEvent(); //初始化控件事件
    }

    /**
     * 初始化控价事件
     */
    private void initEvent() {

        //为拍照按钮设置监听
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果拍照按钮被点击，则转向拍照界面
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        //为相册按钮设置监听
        mbaike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = album;
                //如果相册按钮被点击，则转向图片预览界面
                Intent intent = new Intent(MainActivity.this, encyclopedia.class);
                startActivity(intent);
            }
        });
        mAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhone(v);
            }
        });
    }

    public void choosePhone(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        else
            choosePhoto();
    }

    void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            /**
             * 拍照的请求标志
             */
            case CROP_PHOTO:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri就是照片文件夹对应的uri
                         */
                        Intent intent = new Intent(MainActivity.this, ChoosePhoto.class);
                        intent.setData(imageUri);
                        startActivity(intent);


                    } catch (Exception e) {
                        Toast.makeText(this, "程序崩溃", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Log.i("tag", "失败");
                break;
            /**
             * 从相册中选取图片的请求标志
             */
            case REQUEST_CODE_PICK_IMAGE:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri是上一个Activity返回的
                         */
                        Uri uri = data.getData();
                        Intent intent = new Intent(MainActivity.this, ChoosePhoto.class);
                        intent.setData(uri);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("tag", e.getMessage());
                        Toast.makeText(this, "程序崩溃", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Log.i("liang", "失败");
                break;

            default:
                break;
        }
    }
    /**
     * 初始化布局
     */
    private void initView() {

        //绑定控件
        mCamera = (Button) this.findViewById(R.id.camera_btn);
        mAlbum = (Button) this.findViewById(R.id.album_btn);
        mbaike = findViewById(R.id.baike_btn);
    }
}
