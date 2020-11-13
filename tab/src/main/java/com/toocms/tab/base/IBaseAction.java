package com.toocms.tab.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.StringRes;

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

    public void showToast(String text);

    public void showToast(@StringRes int resId);

    public void showTip(@QMUITipDialog.Builder.IconType int iconType, CharSequence tipWord);

    public void hideTip();

    public void showItemsDialog(String title, String[] items, DialogInterface.OnClickListener listener);

    public void showSingleActionDialog(String title, String message, String actionStr, QMUIDialogAction.ActionListener actionlistener);

    public void showDialog(String title, String message, String lefeActionStr, QMUIDialogAction.ActionListener leftActionlistener, String rightActionStr, QMUIDialogAction.ActionListener rightActionlistener);

    public void showProgress();

    public void removeProgress();

    public void showEmpty();

    public void showFailed(String error, View.OnClickListener listener);

    public void startActivity(Class<? extends Activity> clz);

    public void startActivity(Class<? extends Activity> clz, Bundle bundle);

    public void finishActivity(Class<? extends Activity> clz);

    public void startSelectSignImageAty(OnResultCallbackListener<LocalMedia> listener, int... ratio);

    public void startSelectSignVideoAty(OnResultCallbackListener<LocalMedia> listener);

    public void startSelectSignAllAty(OnResultCallbackListener<LocalMedia> listener);

    public void startSelectSignAty(int chooseMode, int aspect_ratio_x, int aspect_ratio_y, int videoMaxSecond, int recordVideoSecond, OnResultCallbackListener<LocalMedia> listener);

    public void startSelectMultipleImageAty(List<LocalMedia> selectionMedia, int maxSelectNum, OnResultCallbackListener<LocalMedia> listener);

    public void startSelectMultipleVideoAty(List<LocalMedia> selectionMedia, int maxSelectNum, OnResultCallbackListener<LocalMedia> listener);

    public void startSelectMultipleAllAty(List<LocalMedia> selectionMedia, int maxSelectNum, OnResultCallbackListener<LocalMedia> listener);

    public void startSelectMultipleAty(int chooseMode, List<LocalMedia> selectionMedia, int maxSelectNum, int videoMaxSecond, int recordVideoSecond, OnResultCallbackListener<LocalMedia> listener);

    public void startFragment(Class<? extends BaseFragment> clz, boolean... isDestroyCurrent);

    public void startFragment(Class<? extends BaseFragment> clz, Bundle bundle, boolean... isDestroyCurrent);

    public void finishFragment();
}
