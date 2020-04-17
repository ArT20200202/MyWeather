package com.example.myweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText editText_userName;
    private EditText editText_password;
    private Button button_Login, button_Login_Static;
    private TextView textView_policy;
    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //android 5.0以上版本可用setStatusBarColor将状态栏设为透明
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();//拿到当前活动的decorView
            //改变系统UI显示，将活动的布局显示在状态栏上面
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//将状态栏设置成透明
        }
        setContentView(R.layout.activity_login);

        init();

        //ClickableSpan类实现部分文字链接
        SpannableString ss = new SpannableString("登录即代表阅读并同意服务协议");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View textView) {
                Toast.makeText(Login.this, "You clicked the policy.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
                ds.setTypeface(Typeface.DEFAULT_BOLD);//可点击文字部分加粗
            }
        };
        ss.setSpan(clickableSpan, 10, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_policy.setText(ss);
        textView_policy.setMovementMethod(LinkMovementMethod.getInstance());

        //输入密码时，login button开始变换背景颜色
        editText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //anim第二次启动时，按钮启动时间很长，尚未解决。切换background的速度很快，问题可能是anim重新创建了一次，
                //可以在login的位置放置两个button，用setVisible方法加判断条件，每次只显示一个按钮
                //这样就可以用start(),stop()方法来控制动画，不用重新创建
                if (!editText_userName.toString().equals("") && !s.toString().equals("")) {
                    setAnimation();
                }
                if(s.toString().equals("")) {
                    AnimationStop();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init() {
        editText_userName = (EditText) findViewById(R.id.edittext_username);
        editText_password = (EditText) findViewById(R.id.edittext_password);
        button_Login = (Button) findViewById(R.id.button_login);
        button_Login_Static = (Button) findViewById(R.id.button_login_staic);
        textView_policy = (TextView) findViewById(R.id.textview_policy);
        //动态Button初始化
        anim = (AnimationDrawable) button_Login.getBackground();
        anim.setEnterFadeDuration(1300);
        anim.setExitFadeDuration(1300);
    }

    //实现login按钮背景颜色动态变化
    private void setAnimation() {
        //login处设置两个重叠button,输入密码时，显示动态button，密码未输入时显示静态button。
        button_Login.setVisibility(View.VISIBLE);
        button_Login_Static.setVisibility(View.GONE);
        anim.start();//动态Button背景动画开始展示
        //动态Button的点击动作
        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editText_userName.getText().toString();
                String password = editText_password.getText().toString();
                if (userName.equals("admin") && password.equals("123456")) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Username or password is invalid.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void AnimationStop() {
        button_Login_Static.setVisibility(View.VISIBLE);
        button_Login.setVisibility(View.GONE);
        anim.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
