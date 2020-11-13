package com.toocms.tab.widget.payui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.toocms.tab.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付密码控件
 * <p>
 * Author：Zero
 * Date：2017/5/24 17:52
 *
 * @version v4.0
 */
public class PayPwdView extends View {

    private final int DEF_BORDER_COLOR = Color.parseColor("#C6C6C6");
    private final int DEF_DIVIDER_COLOR = Color.parseColor("#F3F3F3");

    private InputCallBack inputCallBack; // 输入完成的回调
    private PwdInputMethodView pwdInputMethodView; // 输入键盘

    private Paint mBorderPaint; // 边界画笔
    private Paint mDividerPaint; // 分割线画笔
    private Paint mDotPaint; // 掩盖点画笔
    private RectF mFrameRect; // 外边框的矩形

    private List<String> result; // 输入结果
    private int count; // 密码位数
    private int size; // 默认每一个的大小
    private int mBorderColor; // 边界颜色
    private int mDividerColor; // 分割线颜色
    private int mDotColor; // 掩盖点的颜色


    public PayPwdView(Context context) {
        super(context);
        init(null);
    }

    public PayPwdView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PayPwdView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    void init(AttributeSet attrs) {
        final float dp = getResources().getDisplayMetrics().density;
        setFocusable(true);
        setFocusableInTouchMode(true);
        result = new ArrayList<>();
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PayPwdView);
            mBorderColor = typedArray.getColor(R.styleable.PayPwdView_border_color, Color.parseColor("#C6C6C6"));
            mDividerColor = typedArray.getColor(R.styleable.PayPwdView_divider_color, Color.parseColor("#F7F7F7"));
            mDotColor = typedArray.getColor(R.styleable.PayPwdView_dot_color, Color.BLACK);
            count = typedArray.getInt(R.styleable.PayPwdView_count, 6);
            typedArray.recycle();
        } else {
            mBorderColor = DEF_BORDER_COLOR;
            mDotColor = DEF_DIVIDER_COLOR;
            count = 6; // 默认6位密码
        }
        size = (int) (dp * 30); // 默认30dp一格
        // 边框
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setStrokeWidth(4);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mBorderColor);
        // 分割线
        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setStrokeWidth(3);
        mDividerPaint.setStyle(Paint.Style.STROKE);
        mDividerPaint.setColor(mDividerColor);
        // 掩盖点
        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDotPaint.setStrokeWidth(3);
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setColor(mDotColor);
        mFrameRect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = measureWidth(widthMeasureSpec);
        int h = measureHeight(heightMeasureSpec);
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        int hsize = MeasureSpec.getSize(heightMeasureSpec);
        // 宽度没指定,但高度指定
        if (w == -1) {
            if (h != -1) {
                w = h * count; // 宽度=高*数量
                size = h;
            } else { // 两个都未知,默认宽高
                w = size * count;
                h = size;
            }
        } else { // 宽度已知
            if (h == -1) { // 高度未知
                h = w / count;
                size = h;
            }
        }
        setMeasuredDimension(Math.min(w, wsize), Math.min(h, hsize));
    }

    private int measureWidth(int widthMeasureSpec) {
        // 宽度
        int wmode = MeasureSpec.getMode(widthMeasureSpec);
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        // wrap_content
        if (wmode == MeasureSpec.AT_MOST) return -1;
        return wsize;
    }

    private int measureHeight(int heightMeasureSpec) {
        // 高度
        int hmode = MeasureSpec.getMode(heightMeasureSpec);
        int hsize = MeasureSpec.getSize(heightMeasureSpec);
        // wrap_content
        if (hmode == MeasureSpec.AT_MOST) return -1;
        return hsize;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击控件弹出输入键盘
            requestFocus();
            pwdInputMethodView.setVisibility(VISIBLE);
            return true;
        }
        return true;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            pwdInputMethodView.setVisibility(VISIBLE);
        } else {
            pwdInputMethodView.setVisibility(GONE);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int width = getWidth();
        final int height = getHeight();
        // 分割线
        for (int i = 1; i < count; i++) {
            final int x = i * size;
            canvas.drawLine(x, 0, x, height, mDividerPaint);
        }
        // 圆角矩形
        mFrameRect.set(0, 0, width, height);
        canvas.drawRect(mFrameRect, mBorderPaint);
        // 画掩盖点,
        // 这是前面定义的变量 private ArrayList<Integer> result;
        // 输入结果保存
        int dotRadius = size / 8; // 圆圈占格子的三分之一
        for (int i = 0; i < result.size(); i++) {
            final float x = (float) (size * (i + 0.5));
            final float y = size / 2;
            canvas.drawCircle(x, y, dotRadius, mDotPaint);
        }
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER;//输入类型为数字
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE;
        return new MyInputConnection(this, false);
    }

    public void setInputCallBack(InputCallBack inputCallBack) {
        this.inputCallBack = inputCallBack;
    }

    public void clearResult() {
        result.clear();
        invalidate();
    }

    /**
     * 设置输入键盘view
     *
     * @param inputMethodView
     */
    public void setInputMethodView(PwdInputMethodView inputMethodView) {
        this.pwdInputMethodView = inputMethodView;
        this.pwdInputMethodView.setInputReceiver(new PwdInputMethodView.InputReceiver() {
            @Override
            public void receive(String num) {
                if (num.equals("-1")) {
                    if (!result.isEmpty()) {
                        result.remove(result.size() - 1);
                        invalidate();
                    }
                } else {
                    if (result.size() < count) {
                        result.add(num);
                        invalidate();
                        ensureFinishInput();
                    }
                }
            }
        });
    }

    /**
     * 判断是否输入完成，输入完成后调用callback
     */
    void ensureFinishInput() {
        if (result.size() == count && inputCallBack != null) { // 输入完成
            StringBuffer sb = new StringBuffer();
            for (String i : result) {
                sb.append(i);
            }
            PayFragment fragment = (PayFragment) FragmentUtils.findFragment(((FragmentActivity) ActivityUtils.getTopActivity()).getSupportFragmentManager(), "Pay");
            if (fragment != null) fragment.dismiss();
            inputCallBack.onInputFinish(sb.toString());
        }
    }

    /**
     * 获取输入文字
     *
     * @return
     */
    public String getInputText() {
        if (result.size() == count) {
            StringBuffer sb = new StringBuffer();
            for (String i : result) {
                sb.append(i);
            }
            return sb.toString();
        }
        return null;
    }

    public interface InputCallBack {
        void onInputFinish(String result);
    }

    private class MyInputConnection extends BaseInputConnection {

        public MyInputConnection(View targetView, boolean fullEditor) {
            super(targetView, fullEditor);
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            // 接受输入法的文本
            return super.commitText(text, newCursorPosition);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            // 软键盘的删除键 DEL 无法直接监听，自己发送del事件
            if (beforeLength == 1 && afterLength == 0) {
                return super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }
}
