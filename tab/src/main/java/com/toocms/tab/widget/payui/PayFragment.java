package com.toocms.tab.widget.payui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.blankj.utilcode.util.StringUtils;
import com.toocms.tab.R;

/**
 * 输入支付密码封装类
 * Author：Zero
 * Date：2017/5/24 17:48
 *
 * @version v4.0
 */
public class PayFragment extends DialogFragment {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "EXTRA_CONTENT";

    private PayPwdView payPwdView;
    private PayPwdView.InputCallBack inputCallBack;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.layout_pay);
        dialog.setCanceledOnTouchOutside(false); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.gravity = Gravity.TOP;
        window.setAttributes(lp);

        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(EXTRA_TITLE) && !StringUtils.isEmpty(bundle.getString(EXTRA_TITLE))) {
                TextView title = dialog.findViewById(R.id.pay_title);
                title.setText(bundle.getString(EXTRA_TITLE));
            }
            if (bundle.containsKey(EXTRA_CONTENT) && !StringUtils.isEmpty(bundle.getString(EXTRA_CONTENT))) {
                TextView content = dialog.findViewById(R.id.pay_content);
                content.setText(bundle.getString(EXTRA_CONTENT));
            }
        }

        payPwdView = dialog.findViewById(R.id.payPwdView);
        PwdInputMethodView inputMethodView = dialog.findViewById(R.id.inputMethodView);
        payPwdView.setInputMethodView(inputMethodView);
        payPwdView.setInputCallBack(inputCallBack);

        dialog.findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
    }

    /**
     * 设置输入回调
     *
     * @param inputCallBack
     */
    public void setPaySuccessCallBack(PayPwdView.InputCallBack inputCallBack) {
        this.inputCallBack = inputCallBack;
    }
}
