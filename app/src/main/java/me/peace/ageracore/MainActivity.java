package me.peace.ageracore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Kira on 2016/9/10.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void stringResult(View v){
        startActivity(new Intent(this,StringResultActivity.class));
    }

    public void jsonResult(View v){
        startActivity(new Intent(this,JsonResultActivity.class));
    }
}
