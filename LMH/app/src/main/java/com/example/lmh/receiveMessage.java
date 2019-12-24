package com.example.lmh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class receiveMessage extends AppCompatActivity {
    LottieAnimationView search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);
        initBtn();



    }

    private void initBtn()
    {
        search_btn=findViewById(R.id.listen_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_btn.playAnimation();
                //TODO  这里是按下按钮后，提取用户当前的天气，位置，时间等因素，在数据库里搜索是否有符合条件的信件


                //TODO  这里是符合条件以后，因为虚拟现实还没实现，所以直接跳转到了读信界面
               new Thread()
               {
                   @Override
                   public void run()
                   {
                       try {
                           //TODO 模拟搜索的耗时
                           Thread.sleep(3000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       Intent sendIntent=new Intent(receiveMessage.this,ReadLetter.class);
                       startActivity(sendIntent);
                   }
               }.start();
            }
        });
    }
}
