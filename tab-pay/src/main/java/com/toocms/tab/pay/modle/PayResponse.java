package com.toocms.tab.pay.modle;

import com.toocms.tab.pay.TabPay;

/**
 * 支付结果
 * Author：Zero
 * Date：2021/5/10
 */
public class PayResponse {

    /**
     * 支付方式
     *
     * @see TabPay#ALIPAY,TabPay#WXPAY
     */
    public String payType;

    /**
     * 响应代码
     * <p>
     * 支付宝：9000 - 订单支付成功，8000 - 正在处理中，4000 - 订单支付失败，5000 - 重复请求，6001 - 用户中途取消，6002 - 网络连接出错，6004 - 支付结果未知
     * 微信：0 - 成功，-1 - 错误，-2 - 用户取消
     */
    public int responseCode;

    @Override
    public String toString() {
        return "PayResponse{" +
                "payType='" + payType + '\'' +
                ", responseCode=" + responseCode +
                '}';
    }
}
