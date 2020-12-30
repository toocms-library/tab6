package com.toocms.tab.map.location;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误码对应的错误信息
 * <p>
 * Author：Zero
 * Date：2018/6/14 9:54
 *
 * @version v1.0
 */
public class ErrorCode {

    public static final Map<Integer, String> errorCode = getErrorCode();

    private static final Map<Integer, String> getErrorCode() {
        if (errorCode != null) return errorCode;
        Map<Integer, String> errorCode = new HashMap<>();
        errorCode.put(0, "定位成功");
        errorCode.put(1, "一些重要参数为空，如context；请对定位传递的参数进行非空判断");
        errorCode.put(2, "定位失败，由于仅扫描到单个wifi，且没有基站信息；请重新尝试");
        errorCode.put(3, "获取到的请求参数为空，可能获取过程中出现异常；请对所连接网络进行全面检查，请求可能被篡改");
        errorCode.put(4, "请求服务器过程中的异常，多为网络情况差，链路不通导致；请检查设备网络是否通畅，检查通过接口设置的网络访问超时时间，建议采用默认的30秒");
        errorCode.put(5, "请求被恶意劫持，定位结果解析失败；您可以稍后再试，或检查网络链路是否存在异常");
        errorCode.put(6, "定位服务返回定位失败；请获取errorDetail（通过getLocationDetail()方法获取）信息并参考http://lbs.amap.com/faq/android/android-location/292进行解决");
        errorCode.put(7, "KEY鉴权失败；请仔细检查key绑定的sha1值与apk签名sha1值是否对应，或通过http://lbs.amap.com/faq/top/hot-questions/253查找相关解决办法");
        errorCode.put(8, "Android exception常规错误；请将errordetail（通过getLocationDetail()方法获取）信息通过http://id.amap.com/?ref=http%3A%2F%2Flbs.amap.com%2Fdev%2Fticket%2Fcreate%2F18反馈给我们");
        errorCode.put(9, "定位初始化时出现异常；请重新启动定位");
        errorCode.put(10, "定位客户端启动失败；请检查AndroidManifest.xml文件是否配置了APSService定位服务");
        errorCode.put(11, "定位时的基站信息错误；请检查是否安装SIM卡，设备很有可能连入了伪基站网络");
        errorCode.put(12, "缺少定位权限；请在设备的设置中开启app的定位权限");
        errorCode.put(13, "定位失败，由于未获得WIFI列表和基站信息，且GPS当前不可用；建议开启设备的WIFI模块，并将设备中插入一张可以正常工作的SIM卡，或者检查GPS是否开启；如果以上都内容都确认无误，请您检查App是否被授予定位权限");
        errorCode.put(14, "GPS 定位失败，由于设备当前 GPS 状态差；建议持设备到相对开阔的露天场所再次尝试");
        errorCode.put(15, "定位结果被模拟导致定位失败；如果您希望位置被模拟，请通过setMockEnable(true);方法开启允许位置模拟");
        errorCode.put(16, "当前POI检索条件、行政区划检索条件下，无可用地理围栏；建议调整检索条件后重新尝试，例如调整POI关键字，调整POI类型，调整周边搜区域，调整行政区关键字等");
        errorCode.put(18, "定位失败，由于手机WIFI功能被关闭同时设置为飞行模式；建议手机关闭飞行模式，并打开WIFI开关");
        errorCode.put(19, "定位失败，由于手机没插sim卡且WIFI功能被关闭；建议手机插上sim卡，打开WIFI开关");
        return errorCode;
    }
}
