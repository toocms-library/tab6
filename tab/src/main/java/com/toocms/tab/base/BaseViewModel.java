package com.toocms.tab.base;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.rxjava.rxlife.ScopeViewModel;
import com.toocms.tab.R;
import com.toocms.tab.base.UIChangeLiveData.ParameterField;
import com.trello.rxlifecycle4.LifecycleProvider;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VM基类
 * <p>
 * Author：Zero
 * Date：2020/10/11 10:51
 */
public class BaseViewModel<M extends BaseModel> extends ScopeViewModel implements IBaseViewModel, IBaseAction {

    protected M model;
    private UIChangeLiveData uiChangeLiveData;
    private WeakReference<LifecycleProvider> lifecycle;  //弱引用持有

    public BaseViewModel(@NonNull Application application) {
        this(application, null);
    }

    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.model = model;
    }

    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycle.get();
    }

    public UIChangeLiveData getUiChangeLiveData() {
        if (uiChangeLiveData == null) {
            uiChangeLiveData = new UIChangeLiveData();
        }
        return uiChangeLiveData;
    }

    // ============================== Action ===============================

    @Override
    public void showToast(String text) {
        ToastUtils.showShort(text);
    }

    @Override
    public void showToast(int resId) {
        ToastUtils.showShort(resId);
    }

    @Override
    public void showTip(@QMUITipDialog.Builder.IconType int iconType, CharSequence tipWord) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.TIP_TYPE, iconType);
        params.put(ParameterField.TIP_TEXT, tipWord);
        uiChangeLiveData.getShowTipEvent().postValue(params);
    }

    @Override
    public void hideTip() {
        uiChangeLiveData.getHideTipEvent().call();
    }

    @Override
    public void showItemsDialog(String title, String[] items, DialogInterface.OnClickListener listener) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.DIALOG_TITLE, title);
        params.put(ParameterField.DIALOG_ITEMS, items);
        params.put(ParameterField.DIALOG_ITEMS_LISTENER, listener);
        uiChangeLiveData.getShowItemsDialogEvent().postValue(params);
    }

    @Override
    public void showSingleActionDialog(String title, String message, String actionStr, QMUIDialogAction.ActionListener actionlistener) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.DIALOG_TITLE, title);
        params.put(ParameterField.DIALOG_MESSAGE, message);
        params.put(ParameterField.DIALOG_LEFT_ACTION_TEXT, actionStr);
        params.put(ParameterField.DIALOG_LEFT_ACTION_LISTENER, actionlistener);
        uiChangeLiveData.getShowSingleActionDialogEvent().postValue(params);
    }

    @Override
    public void showDialog(String title, String message, String lefeActionStr, QMUIDialogAction.ActionListener leftActionlistener, String rightActionStr, QMUIDialogAction.ActionListener rightActionlistener) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.DIALOG_TITLE, title);
        params.put(ParameterField.DIALOG_MESSAGE, message);
        params.put(ParameterField.DIALOG_LEFT_ACTION_TEXT, lefeActionStr);
        params.put(ParameterField.DIALOG_LEFT_ACTION_LISTENER, leftActionlistener);
        params.put(ParameterField.DIALOG_RIGHT_ACTION_TEXT, rightActionStr);
        params.put(ParameterField.DIALOG_RIGHT_ACTION_LISTENER, rightActionlistener);
        uiChangeLiveData.getShowDialogEvent().postValue(params);
    }

    @Override
    public void showProgress() {
        uiChangeLiveData.getShowProgressEvent().call();
    }

    @Override
    public void removeProgress() {
        uiChangeLiveData.getRemoveProgressEvent().call();
    }

    @Override
    public void showEmpty() {
        uiChangeLiveData.getShowEmptyEvent().call();
    }

    @Override
    public void showFailed(String error, View.OnClickListener listener) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.ERROR_TEXT, error);
        params.put(ParameterField.ERROR_LISTENER, listener);
        uiChangeLiveData.getShowFailedEvent().postValue(params);
    }

    @Override
    public void startActivity(Class<? extends Activity> clz) {
        startActivity(clz, null);
    }

    @Override
    public void startActivity(Class<? extends Activity> clz, Bundle bundle) {
        ActivityUtils.startActivity(bundle, clz, R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finishActivity(Class<? extends Activity> clz) {
        ActivityUtils.finishActivity(clz);
    }

    @Override
    public void startSelectSignImageAty(OnResultCallbackListener<LocalMedia> listener, int... ratio) {
        int aspect_ratio_x = 1, aspect_ratio_y = 1;
        if (!ArrayUtils.isEmpty(ratio)) {
            aspect_ratio_x = ratio[0];
            aspect_ratio_y = ratio[1];
        }
        startSelectSignAty(PictureMimeType.ofImage(), aspect_ratio_x, aspect_ratio_y, 120, 120, listener);
    }

    @Override
    public void startSelectSignVideoAty(OnResultCallbackListener<LocalMedia> listener) {
        startSelectSignAty(PictureMimeType.ofVideo(), 1, 1, 120, 120, listener);
    }

    @Override
    public void startSelectSignAllAty(OnResultCallbackListener<LocalMedia> listener) {
        startSelectSignAty(PictureMimeType.ofAll(), 1, 1, 120, 120, listener);
    }

    @Override
    public void startSelectSignAty(int chooseMode, int aspect_ratio_x, int aspect_ratio_y, int videoMaxSecond, int recordVideoSecond, OnResultCallbackListener<LocalMedia> listener) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.SELECT_PICTURE_MODE, chooseMode);
        params.put(ParameterField.SELECT_PICTURE_RATIO_X, aspect_ratio_x);
        params.put(ParameterField.SELECT_PICTURE_RATIO_Y, aspect_ratio_y);
        params.put(ParameterField.SELECT_PICTURE_VIDEO_MAX_SECOND, videoMaxSecond);
        params.put(ParameterField.SELECT_PICTURE_RECORD_VIDEO_SECOND, recordVideoSecond);
        params.put(ParameterField.SELECT_PICTURE_CALLBACK_LISTENER, listener);
        uiChangeLiveData.getStartSelectSignAtyEvent().postValue(params);
    }

    @Override
    public void startSelectMultipleImageAty(List<LocalMedia> selectionMedia, int maxSelectNum, OnResultCallbackListener<LocalMedia> listener) {
        startSelectMultipleAty(PictureMimeType.ofImage(), selectionMedia, maxSelectNum, 120, 120, listener);
    }

    @Override
    public void startSelectMultipleVideoAty(List<LocalMedia> selectionMedia, int maxSelectNum, OnResultCallbackListener<LocalMedia> listener) {
        startSelectMultipleAty(PictureMimeType.ofVideo(), selectionMedia, maxSelectNum, 120, 120, listener);
    }

    @Override
    public void startSelectMultipleAllAty(List<LocalMedia> selectionMedia, int maxSelectNum, OnResultCallbackListener<LocalMedia> listener) {
        startSelectMultipleAty(PictureMimeType.ofAll(), selectionMedia, maxSelectNum, 120, 120, listener);
    }

    @Override
    public void startSelectMultipleAty(int chooseMode, List<LocalMedia> selectionMedia, int maxSelectNum, int videoMaxSecond, int recordVideoSecond, OnResultCallbackListener<LocalMedia> listener) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.SELECT_PICTURE_MODE, chooseMode);
        params.put(ParameterField.SELECT_PICTURE_SELECTION_MEDIA, selectionMedia);
        params.put(ParameterField.SELECT_PICTURE_MAX_SELECT_NUM, maxSelectNum);
        params.put(ParameterField.SELECT_PICTURE_VIDEO_MAX_SECOND, videoMaxSecond);
        params.put(ParameterField.SELECT_PICTURE_RECORD_VIDEO_SECOND, recordVideoSecond);
        params.put(ParameterField.SELECT_PICTURE_CALLBACK_LISTENER, listener);
        uiChangeLiveData.getStartSelectMultipleAtyEvent().postValue(params);
    }

    @Override
    public void startFragment(Class<? extends BaseFragment> clz, boolean... isDestroyCurrent) {
        startFragment(clz, null, isDestroyCurrent);
    }

    @Override
    public void startFragment(Class<? extends BaseFragment> clz, Bundle bundle, boolean... isDestroyCurrent) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.FRAGMENT, clz);
        if (!ObjectUtils.isEmpty(bundle))
            params.put(ParameterField.BUNDLE, bundle);
        if (!ArrayUtils.isEmpty(isDestroyCurrent))
            params.put(ParameterField.DESTROY_CURRENT, isDestroyCurrent);
        uiChangeLiveData.getStartFragmentEvent().postValue(params);
    }

    @Override
    public void finishFragment() {
        uiChangeLiveData.getFinishFragmentEvent().call();
    }

    // ============================= 生命周期 =============================

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void registerRxBus() {
    }

    @Override
    public void removeRxBus() {
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.onCleared();
        }
    }
}
