package com.toocms.tab.pay;

import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.toocms.tab.base.BaseActivity;
import com.toocms.tab.bus.Messenger;
import com.toocms.tab.pay.modle.PayResponse;

/**
 * 微信支付回调页
 * Author：Zero
 * Date：2021/5/10
 */
public class WXPayCallbackActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI wxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxapi = WXAPIFactory.createWXAPI(this, SPUtils.getInstance().getString(WxPay.SP_KEY_WXPAY));
        wxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            LogUtils.e(baseResp.errCode);
            PayResponse response = new PayResponse();
            response.payType = TabPay.WXPAY;
            response.responseCode = baseResp.errCode;
            Messenger.getDefault().send(response, TabPay.TOKEN_PAY_RESPONSE);
        }
        finish();
    }
}
