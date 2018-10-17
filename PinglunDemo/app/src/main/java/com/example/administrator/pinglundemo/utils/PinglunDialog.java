package com.example.administrator.pinglundemo.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.pinglundemo.R;

/**
 * Created by Administrator on 2018/10/17.
 */

@SuppressLint("ValidFragment")
public class PinglunDialog extends DialogFragment{
    private PinglunCallBack callBack;//回调
    private String hintStr;//提示文字
    private Dialog dialog;
    private EditText etPinglun;

    public PinglunDialog(){
    }
    public PinglunDialog(String hintStr, PinglunCallBack callBack){
        this.hintStr = hintStr;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(), R.style.PinglunDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentview = View.inflate(getActivity(), R.layout.pinglun_dialog, null);
        dialog.setContentView(contentview);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        etPinglun = (EditText) contentview.findViewById(R.id.dialog_comment_content);
        etPinglun.setHint(hintStr);
        final TextView tv_send = (TextView) contentview.findViewById(R.id.dialog_comment_send);
        etPinglun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tv_send.setBackgroundResource(R.drawable.corners_review_cansend);
                } else {
                    tv_send.setBackgroundResource(R.drawable.corners_review_send);
                }

            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPinglun.getText().toString())) {
                    Toast.makeText(getActivity(),"输入内容为空",Toast.LENGTH_LONG).show();
                    return;
                } else {
                    callBack.onPinglun(etPinglun.getText().toString());
                }
            }
        });
        etPinglun.setFocusable(true);
        etPinglun.setFocusableInTouchMode(true);
        etPinglun.requestFocus();
        final Handler hanler = new Handler();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                hanler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideSoftkeyboard();
                    }
                }, 200);

            }
        });
        return dialog;
    }
    public void hideSoftkeyboard() {
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {

        }
    }
}
