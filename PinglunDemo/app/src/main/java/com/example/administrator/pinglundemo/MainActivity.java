package com.example.administrator.pinglundemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.pinglundemo.utils.PinglunCallBack;
import com.example.administrator.pinglundemo.utils.PinglunDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private PinglunDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show:
                dialog = new PinglunDialog("评论：", new PinglunCallBack() {
                    @Override
                    public void onPinglun(String content) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"评论:"+content,Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show(getSupportFragmentManager(), "dialog");
                break;
        }
    }
}
