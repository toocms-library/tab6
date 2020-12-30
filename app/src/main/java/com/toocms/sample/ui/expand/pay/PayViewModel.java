package com.toocms.sample.ui.expand.pay;

import android.app.Application;

import androidx.annotation.NonNull;

import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.network.ApiTool;
import com.toocms.tab.pay.TabPay;
import com.toocms.tab.pay.modle.PayRequest;

/**
 * Author：Zero
 * Date：2020/12/12 16:40
 */
public class PayViewModel extends BaseViewModel {

    public PayViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onResume() {
        super.onResume();
        TabPay.payStatusCallback(() -> {
            // 调用检测支付状态接口
            showSingleActionDialog("结果", "通过请求接口获取支付状态", "确定", (dialog, index) -> dialog.dismiss());
        });
    }

    // 支付宝支付
    public BindingCommand alipay = new BindingCommand(() -> pay("http://ttt.toocms.com/PayDemo/alipay"));
    // 微信支付
    public BindingCommand wxpay = new BindingCommand(() -> pay("http://ttt.toocms.com/PayDemo/wchatPay"));

    /**
     * 获取支付信息接口
     *
     * @param url 请求url
     */
    private void pay(String url) {
        ApiTool.post(url)
                .asTooCMSResponse(PayRequest.class)
                .withViewModel(this)
                .request(payRequest -> TabPay.pay(payRequest));
    }
}
