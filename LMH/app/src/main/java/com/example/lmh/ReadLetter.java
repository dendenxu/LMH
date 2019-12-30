package com.example.lmh;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class ReadLetter extends AppCompatActivity {

    TextView read;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_letter);

        read=findViewById(R.id.read);
        read.setText("夏天的飞鸟,飞到我的窗前唱歌,又飞去了. \n秋天的黄叶,它们没有什么可唱,只叹息一声,飞落在那里.");
        Typeface typeface= Typeface.createFromAsset(getAssets(),"方正清刻本悦宋简.TTF");

        read.setTypeface(typeface);

    }
}
