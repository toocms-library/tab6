package com.toocms.tab.widget.payui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.toocms.tab.R;


/**
 * 密码输入法
 * <p>
 * Author：Zero
 * Date：2017/5/24 17:58
 *
 * @version v4.0
 */
public class PwdInputMethodView extends LinearLayout implements View.OnClickListener {

    private InputReceiver receiver;
    private Animation animIn;
    private Animation animOut;

    public PwdInputMethodView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        animIn = AnimationUtils.loadAnimation(context, R.anim.push_bottom_in);
        animOut = AnimationUtils.loadAnimation(context, R.anim.push_bottom_out);
        LayoutInflater.from(context).inflate(R.layout.layout_intput_method, this);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_0).setOnClickListener(this);
        findViewById(R.id.btn_del).setOnClickListener(this);
        findViewById(R.id.layout_hide).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        String num = (String) v.getTag();
        receiver.receive(num);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        switch (visibility) {
            case VISIBLE:
                startAnimation(animIn);
                break;
            case GONE:
                startAnimation(animOut);
                break;
        }
        super.onVisibilityChanged(changedView, visibility);
    }

    /**
     * 设置接收器
     *
     * @param receiver
     */
    public void setInputReceiver(InputReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * 输入接收器
     */
    public interface InputReceiver {
        void receive(String num);
    }
}
