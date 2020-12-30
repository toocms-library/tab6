package com.toocms.tab.pay;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.toocms.tab.pay.modle.PayRequest;

/**
 * 微信支付封装类 <br/>
 * <p>
 * Author Zero
 * Date 2015年4月27日
 */
class WxPay {

    private final String TAG = "WxPay";

    private PayRequest payRequest;

    public WxPay(PayRequest payRequest) {
        this.payRequest = payRequest;
    }

    /**
     * 调用微信支付页面
     */
    public void pay() {
        // 签名、调用微信支付页面
        LogUtils.i(TAG, payRequest.toString());
        PayReq payReq = new PayReq();
        payReq.appId = payRequest.getAppid();
        payReq.partnerId = payRequest.getPartnerid();
        payReq.prepayId = payRequest.getPrepayid();
        payReq.packageValue = payRequest.getPackage();
        payReq.nonceStr = payRequest.getNoncestr();
        payReq.timeStamp = String.valueOf(payRequest.getTimestamp());
        payReq.sign = payRequest.getSign();
        IWXAPI api = WXAPIFactory.createWXAPI(ActivityUtils.getTopActivity(), null);
        api.registerApp(payRequest.getAppid());
        api.sendReq(payReq);
    }
}
