package com.example.lmh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.lmh.util.BToast;
import com.example.lmh.util.SQLLink;

import java.util.ArrayList;
import java.util.List;

public class receiveMessage extends AppCompatActivity {
    LottieAnimationView search_btn;
    private LocationClient mLocationClient = null;
    String message;
    String locx,locy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new receiveMessage.MyLocationListener());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);
        initBtn();
        initGPS();
    }
    private void toast_good()
    {
        //TODO 匹配成功请调用这个
        BToast.showText(receiveMessage.this, "匹配成功", Toast.LENGTH_LONG,true);
    }

    private void toast_bad()
    {
        //TODO 匹配失败请调用这个
        BToast.showText(receiveMessage.this, "匹配失败",Toast.LENGTH_LONG,false);
    }
    private void initBtn(){

        search_btn=findViewById(R.id.listen_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_btn.playAnimation();
                //TODO  这里是按下按钮后，提取用户当前的天气，位置，时间等因素，在数据库里搜索是否有符合条件的信件

                message = SQLLink.select(locx,locy);
                if(message == null)toast_bad();
                else {
                    toast_good();
                    // Log.i("what",message);
                    //TODO  这里是符合条件以后，因为虚拟现实还没实现，所以直接跳转到了读信界面
                    new Thread() {
                        @Override
                        public void run() {
                            Intent sendIntent = new Intent(receiveMessage.this, takephoto.class);
                            sendIntent.putExtra("context", message);
                            startActivity(sendIntent);
                        }
                    }.start();
                }
            }
        });
    }

    private void initGPS()
    {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setAddrType("all");
        mLocationClient.setLocOption(option);
    }

    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  //TODO 这里可以获取经纬度
                                  locx = String.valueOf(location.getLatitude());
                                  locy = String.valueOf(location.getLongitude());
                              }
                          }
            );
        }

    }

}
