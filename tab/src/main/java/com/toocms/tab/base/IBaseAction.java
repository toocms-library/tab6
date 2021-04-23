package com.toocms.tab.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.StringRes;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/10/29 17:47
 */
interface IBaseAction {

    /**
     * 显示Toast
     *
     * @param text 显示的文字
     */
    void showToast(String text);

    /**
     * 显示Toast
     *
     * @param resId 显示的文字的资源Id
     */
    void showToast(@StringRes int resId);

    /**
     * 显示提示框（不调用隐藏方法不会自动消失）
     *
     * @param iconType icon类型
     *                 {@link QMUITipDialog.Builder#ICON_TYPE_NOTHING} - 不显示任何icon
     *                 {@link QMUITipDialog.Builder#ICON_TYPE_LOADING} - 显示 Loading 图标
     *                 {@link QMUITipDialog.Builder#ICON_TYPE_SUCCESS} - 显示成功图标
     *                 {@link QMUITipDialog.Builder#ICON_TYPE_FAIL} - 显示失败图标
     *                 {@link QMUITipDialog.Builder#ICON_TYPE_INFO} - 显示信息图标
     * @param tipWord  提示的文字
     */
    void showTip(@QMUITipDialog.Builder.IconType int iconType, CharSequence tipWord);

    /**
     * 隐藏提示框
     */
    void hideTip();

    /**
     * 显示列表类型的Dialog
     *
     * @param title    标题
     * @param items    列表数据
     * @param listener 列表项点击监听
     */
    void showItemsDialog(String title, String[] items, DialogInterface.OnClickListener listener);

    /**
     * 显示带一个按钮的Dialog
     *
     * @param title          标题
     * @param message        显示的信息
     * @param actionStr      按钮显示的文字
     * @param actionlistener 按钮的监听
     */
    void showSingleActionDialog(String title, String message, String actionStr, QMUIDialogAction.ActionListener actionlistener);

    /**
     * 显示带两年个按钮的Dialog
     *
     * @param title               标题
     * @param message             显示的信息
     * @param lefeActionStr       左侧按钮显示的文字
     * @param leftActionlistener  左侧按钮的监听
     * @param rightActionStr      右侧按钮显示的文字
     * @param rightActionlistener 右侧按钮的监听
     */
    void showDialog(String title, String message, String lefeActionStr, QMUIDialogAction.ActionListener leftActionlistener, String rightActionStr, QMUIDialogAction.ActionListener rightActionlistener);

    /**
     * 显示加载条
     */
    void showProgress();

    /**
     * 移除加载条
     */
    void removeProgress();

    /**
     * 显示空视图
     */
    void showEmpty();

    /**
     * 显示异常视图
     *
     * @param error    异常信息
     * @param listener 重试按钮点击监听
     */
    void showFailed(String error, View.OnClickListener listener);

    /**
     * 显示异常视图
     *
     * @param error      异常信息
     * @param buttonText 按钮文字
     * @param listener   重试按钮点击监听
     */
    void showFailed(String error, String buttonText, View.OnClickListener listener);

    /**
     * 移除空视图和异常视图
     */
    void removeEmptyAndFailed();

    /**
     * 启动Activity
     *
     * @param clz Activity类
     */
    void startActivity(Class<? extends Activity> clz);

    /**
     * 启动Activity
     *
     * @param clz    Activity类
     * @param bundle 传输参数
     */
    void startActivity(Class<? extends Activity> clz, Bundle bundle);

    /**
     * 关闭Activity
     *
     * @param clz Activity类
     */
    void finishActivity(Class<? extends Activity> clz);

    /**
     * 启动带裁剪功能的图片单选页面
     *
     * @param listener 选择结果回调
     * @param ratio    可选项，裁剪框的宽高比例（默认1:1）
     */
    void startSelectSignImageAty(OnResultCallbackListener<LocalMedia> listener, int... ratio);

    /**
     * 启动视频单选页面
     *
     * @param listener 选择结果回调
     */
    void startSelectSignVideoAty(OnResultCallbackListener<LocalMedia> listener);

    /**
     * 启动包含图片和视频类型的单选页面
     *
     * @param listener 选择结果回调
     */
    void startSelectSignAllAty(OnResultCallbackListener<LocalMedia> listener);

    /**
     * 启动单选页面
     *
     * @param chooseMode        扫描文件类型
     *                          {@link PictureMimeType#ofAll()} - 所有类型
     *                          {@link PictureMimeType#ofImage()} - 图片类型
     *                          {@link PictureMimeType#ofVideo()} - 视频类型
     * @param aspect_ratio_x    裁剪比例X
     * @param aspect_ratio_y    裁剪比例Y
     * @param videoMaxSecond    显示多少秒以内的视频，0 - 不限制
     * @param recordVideoSecond 视频录制秒数
     * @param listener          获取数据回调
     */
    void startSelectSignAty(int chooseMode, int aspect_ratio_x, int aspect_ratio_y, int videoMaxSecond, int recordVideoSecond, OnResultCallbackListener<LocalMedia> listener);

    /**
     * 启动图片多选页面
     *
     * @param selectionMedia 已选的资源列表
     * @param maxSelectNum   最大选择数量
     * @param listener       获取数据回调
     */
    void startSelectMultipleImageAty(List<LocalMedia> selectionMedia, int maxSelectNum, OnResultCallbackListener<LocalMedia> listener);

    /**
     * 启动视频多选页面
     *
     * @param selectionMedia 已选的资源列表
     * @param maxSelectNum   最大选择数量
     * @param listener       获取数据回调
     */
    void startSelectMultipleVideoAty(List<LocalMedia> selectionMedia, int maxSelectNum, OnResultCallbackListener<LocalMedia> listener);

    /**
     * 启动包含图片和视频类型的多选页面
     *
     * @param selectionMedia 已选的资源列表
     * @param maxSelectNum   最大选择数量
     * @param listener       获取数据回调
     */
    void startSelectMultipleAllAty(List<LocalMedia> selectionMedia, int maxSelectNum, OnResultCallbackListener<LocalMedia> listener);

    /**
     * 启动多选页面
     *
     * @param chooseMode        扫描文件类型
     *                          {@link PictureMimeType#ofAll()} - 所有类型
     *                          {@link PictureMimeType#ofImage()} - 图片类型
     *                          {@link PictureMimeType#ofVideo()} - 视频类型
     * @param selectionMedia    已选数据
     * @param maxSelectNum      最大选择数量
     * @param videoMaxSecond    显示多少秒以内的视频，0 - 不限制
     * @param recordVideoSecond 视频录制秒数
     * @param listener          获取数据回调
     */
    void startSelectMultipleAty(int chooseMode, List<LocalMedia> selectionMedia, int maxSelectNum, int videoMaxSecond, int recordVideoSecond, OnResultCallbackListener<LocalMedia> listener);

    /**
     * 启动Fragment
     *
     * @param clz              Fragment类
     * @param isDestroyCurrent 可选项，是否关闭当前Fragment（默认不关闭）
     */
    void startFragment(Class<? extends BaseFragment> clz, boolean... isDestroyCurrent);

    /**
     * 启动Fragment
     *
     * @param clz              Fragment类
     * @param bundle           传输的参数
     * @param isDestroyCurrent 可选项，是否关闭当前Fragment（默认不关闭）
     */
    void startFragment(Class<? extends BaseFragment> clz, Bundle bundle, boolean... isDestroyCurrent);

    /**
     * 启动Fragment并等待返回值
     *
     * @param clz         Fragment类
     * @param requestCode 请求标识
     */
    void startFragmentForResult(Class<? extends BaseFragment> clz, int requestCode);

    /**
     * 启动Fragment并等待返回值
     *
     * @param clz         Fragment类
     * @param bundle      传输的参数
     * @param requestCode 请求标识
     */
    void startFragmentForResult(Class<? extends BaseFragment> clz, Bundle bundle, int requestCode);

    /**
     * 关闭Fragment
     */
    void finishFragment();

    /**
     * 设置Fragment返回结果
     *
     * @param resultCode 结果值
     * @param data       传输数据
     */
    void setFragmentResult(int resultCode, Intent data);
}
