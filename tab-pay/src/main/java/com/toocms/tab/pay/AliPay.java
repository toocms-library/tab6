package com.toocms.tab.pay;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.toocms.tab.bus.Messenger;
import com.toocms.tab.pay.modle.PayRequest;
import com.toocms.tab.pay.modle.PayResponse;

import java.util.Map;

/**
 * 支付宝接口封装类<br/>
 * Author Zero
 * Date 2014年12月14日
 * Version 1.0
 */
class AliPay implements Runnable {

    private Thread thread;
    private PayRequest payRequest;

    public AliPay(PayRequest payRequest) {
        thread = new Thread(this);
        this.payRequest = payRequest;
    }

    /**
     * 调用支付宝充值页面
     */
    public void pay() {
        thread.start();
    }

    @Override
    public void run() {
        // 构造PayTask 对象
        PayTask alipay = new PayTask(ActivityUtils.getTopActivity());
        // 调用支付接口，获取支付结果
        Map<String, String> result = alipay.payV2(payRequest.getSign(), true);
        LogUtils.e(TabPay.ALIPAY, result.toString());
        PayResponse response = new PayResponse();
        response.payType = TabPay.ALIPAY;
        try {
            response.responseCode = Integer.parseInt(result.get("resultStatus"));
        } catch (NumberFormatException exception) {
            response.responseCode = 0;
        }
        Messenger.getDefault().send(response, TabPay.TOKEN_PAY_RESPONSE);
    }
}
