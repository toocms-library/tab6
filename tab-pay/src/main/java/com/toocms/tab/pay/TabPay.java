package com.toocms.tab.pay;

import android.text.TextUtils;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.toocms.tab.binding.command.BindingConsumer;
import com.toocms.tab.bus.Messenger;
import com.toocms.tab.pay.listener.PayStatusCallback;
import com.toocms.tab.pay.modle.PayRequest;
import com.toocms.tab.pay.modle.PayResponse;

/**
 * 支付封装类
 * <p>
 * Author Zero
 * Date 2016/5/21 10:41
 */
public class TabPay {

    public static final String ALIPAY = "Alipay"; // 支付宝支付标识
    public static final String WXPAY = "WxPay"; // 微信支付标识
    static final String TOKEN_PAY_RESPONSE = "TOKEN_PAY_RESPONSE";

    @Deprecated
    static String payType; // 支付方式
    @Deprecated
    static boolean isPayState; // 是否为支付状态的标识
    @Deprecated
    static boolean isAlipaySec; // 当支付方式为支付宝时是否为第二次回调onResume

    /**
     * 支付
     *
     * @param payRequest 接口返回的支付实体类
     */
    @Deprecated
    public static void pay(PayRequest payRequest) {
        if (!TextUtils.isEmpty(payRequest.getAppid())) {  // 微信
            payType = WXPAY;
            isPayState = true;
            new WxPay(payRequest).pay();
        } else if (!TextUtils.isEmpty(payRequest.getSign())) {  // 支付宝
            payType = ALIPAY;
            isPayState = true;
            new AliPay(payRequest).pay();
        } else {
            ToastUtils.showShort("暂不支持的支付方式");
        }
    }

    /**
     * 支付
     *
     * @param recipient  接收者
     * @param payRequest 接口返回的支付实体类
     * @param callback   支付结果回调
     */
    public static void payV2(Object recipient, PayRequest payRequest, BindingConsumer<PayResponse> callback) {
        if (!TextUtils.isEmpty(payRequest.getAppid())) {  // 微信
            new WxPay(payRequest).pay();
        } else if (!TextUtils.isEmpty(payRequest.getSign())) {  // 支付宝
            new AliPay(payRequest).pay();
        } else {
            ToastUtils.showShort("暂不支持的支付方式");
        }
        // 注册支付结果消息接收回调
        Messenger.getDefault().register(recipient, TOKEN_PAY_RESPONSE, PayResponse.class, callback);
    }

    /**
     * 支付状态回调
     *
     * @param callback
     */
    @Deprecated
    public static void payStatusCallback(PayStatusCallback callback) {
        // 先判断是否为支付状态
        if (isPayState) {
            // 其次判断支付类型是否为支付宝
            if (StringUtils.equals(payType, ALIPAY)) {
                // 最后判断是否为第二次回调onResume
                // 如果不是则将第二次回调的变量改为true以至于下次判断是为第二次
                // 然后return掉onResume方法
                if (!isAlipaySec) {
                    isAlipaySec = true;
                    return;
                }
            }
            // 执行到这步判断支付方式
            // 如果为微信的话直接回调
            // 如果为支付宝的话判断是否为第二次
            // 最后将支付状态改为false，支付宝第二次回调同样改为false
            if (StringUtils.equals(payType, WXPAY) || (StringUtils.equals(payType, ALIPAY) && isAlipaySec)) {
                isPayState = false;
                isAlipaySec = false;
                callback.callback();
            }
        }
    }
}
