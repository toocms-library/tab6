package com.toocms.tab.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.toocms.tab.R;
import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.base.UIChangeLiveData.ParameterField;
import com.toocms.tab.bus.Messenger;
import com.toocms.tab.imageload.engine.GlideEngine;
import com.toocms.tab.configs.FileManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户交互页面
 * <p>
 * V基类
 *
 * @author Zero
 */
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends LifecycleFragment implements IBaseAction {

    protected V binding;
    protected VM viewModel;

    protected QMUITopBarLayout topBar;
    protected FrameLayout content;
    protected QMUIEmptyView emptyView;
    protected QMUITipDialog tipDialog;

    private PictureWindowAnimationStyle pictureStyle;

    private boolean showContent = true;

    // ================================== 回调函数 ==================================

    protected abstract void onFragmentCreated();

    protected abstract @LayoutRes
    int getLayoutResId();

    public abstract int getVariableId();

    protected abstract void viewObserver();

    // 非最后一层子类传入V和VM需重写该回调方法
    protected VM getViewModel() {
        return null;
    }

    // ================================== 初始化 ==================================

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.base_root_view, null);
        topBar = rootView.findViewById(R.id.root_topbar);
        emptyView = rootView.findViewById(R.id.root_empty_view);
        emptyView.hide();
        topBar.addLeftBackImageButton().setOnClickListener(v -> finishFragment());
        content = rootView.findViewById(R.id.root_content);
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutResId(), (ViewGroup) rootView, false);
        content.addView(binding.getRoot());
        return rootView;
    }

    @Override
    protected void onViewCreated(@NonNull View rootView) {
        resetShowContent();
        //初始化DataBinding和ViewModel
        initVVM();
        //注册ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack();
        //页面数据初始化方法
        onFragmentCreated();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        viewObserver();
        //注册RxBus
        viewModel.registerRxBus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Messenger.getDefault().unregister(viewModel);
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if (binding != null) {
            binding.unbind();
        }
    }

    // 注入绑定
    private void initVVM() {
        viewModel = getViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(getVariableId(), viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return new ViewModelProvider(fragment, ViewModelProvider.AndroidViewModelFactory.getInstance(TooCMSApplication.getInstance())).get(cls);
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
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(getActivity())
                    .setIconType(iconType)
                    .setTipWord(tipWord)
                    .create();
        }
        if (!tipDialog.isShowing()) {
            tipDialog.show();
        }
    }

    @Override
    public void hideTip() {
        if (tipDialog != null) if (tipDialog.isShowing()) tipDialog.dismiss();
    }

    @Override
    public void showItemsDialog(String title, String[] items, DialogInterface.OnClickListener listener) {
        new QMUIDialog.MenuDialogBuilder(getContext())
                .setTitle(title)
                .addItems(items, listener)
                .show();
    }

    @Override
    public void showSingleActionDialog(String title, String message, String actionStr, QMUIDialogAction.ActionListener actionlistener) {
        new QMUIDialog.MessageDialogBuilder(getContext())
                .setTitle(title)
                .setMessage(message)
                .addAction(actionStr, actionlistener)
                .show();
    }

    @Override
    public void showDialog(String title, String message, String lefeActionStr, QMUIDialogAction.ActionListener leftActionlistener, String rightActionStr, QMUIDialogAction.ActionListener rightActionlistener) {
        new QMUIDialog.MessageDialogBuilder(getContext())
                .setTitle(title)
                .setMessage(message)
                .addAction(lefeActionStr, leftActionlistener)
                .addAction(rightActionStr, rightActionlistener)
                .show();
    }

    @Override
    public void showProgress() {
        if (showContent) {
            if (!emptyView.isShowing()) emptyView.show(true);
        } else {
            showTip(QMUITipDialog.Builder.ICON_TYPE_LOADING, null);
        }
    }

    @Override
    public void removeProgress() {
        if (emptyView.isShowing()) emptyView.hide();
        hideTip();
    }

    @Override
    public void showEmpty() {
        emptyView.show(getString(R.string.base_str_empty), null);
    }

    @Override
    public void showFailed(String error, View.OnClickListener listener) {
        emptyView.show(false, error, null, getString(R.string.base_str_retry), listener);
    }

    @Override
    public void startActivity(Class<? extends Activity> clz) {
        ActivityUtils.startActivity(clz);
    }

    @Override
    public void startActivity(Class<? extends Activity> clz, @NonNull Bundle bundle) {
        ActivityUtils.startActivity(bundle, clz, R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finishActivity(Class<? extends Activity> clz) {
        ActivityUtils.finishActivity(clz);
    }

    @Override
    public void startSelectSignImageAty(OnResultCallbackListener<LocalMedia> listener, int... ratio) {
        int aspect_ratio_x = 1, aspect_ratio_y = 1;
        if (ratio != null && ratio.length != 0) {
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
        if (pictureStyle == null) {
            pictureStyle = new PictureWindowAnimationStyle();
            pictureStyle.ofAllAnimation(R.anim.slide_in_right, R.anim.slide_out_right);
        }
        PictureSelector.create(this)
                .openGallery(chooseMode)    // 扫描文件类型
                .setPictureWindowAnimationStyle(pictureStyle) // 相册启动退出动画
                .imageEngine(GlideEngine.createGlideEngine())   // 图片加载引擎
                .isWithVideoImage(true) // 图片和视频是否可以同选,只在ofAll模式下有效
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)  // 相册Activity方向
                .isOriginalImageControl(false)  // 不显示原图控制按钮
                .compressSavePath(FileManager.getCachePath())   //  压缩图片保存地址
                .renameCompressFile(System.currentTimeMillis() + ".0")    // 重命名压缩文件名
                .renameCropFileName(System.currentTimeMillis() + ".0")    // 重命名裁剪文件名
                .selectionMode(PictureConfig.SINGLE)    // 单选
                .isSingleDirectReturn(true) // 裁剪之后直接返回
                .isEnableCrop(true)   // 裁剪
                .isCompress(true) // 压缩
                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)    // 裁剪比例
                .isPreviewEggs(true)  // 预览图片时增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .videoMaxSecond(videoMaxSecond) // 显示多少秒以内的视频or音频
                .recordVideoSecond(recordVideoSecond)  //  视频录制秒数
                .forResult(listener);   // 回调
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
        if (pictureStyle == null) {
            pictureStyle = new PictureWindowAnimationStyle();
            pictureStyle.ofAllAnimation(R.anim.slide_in_right, R.anim.slide_out_right);
        }
        PictureSelector.create(this)
                .openGallery(chooseMode)    // 扫描文件类型
                .setPictureWindowAnimationStyle(pictureStyle) // 相册启动退出动画
                .imageEngine(GlideEngine.createGlideEngine())   // 图片加载引擎
                .isWithVideoImage(true) // 图片和视频可以同时选则
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)  // 相册Activity方向
                .isOriginalImageControl(false)  // 不显示原图控制按钮
                .maxSelectNum(maxSelectNum) // 最大选择数量
                .compressSavePath(FileManager.getCachePath())   //  压缩图片保存地址
                .renameCompressFile(System.currentTimeMillis() + ".0")    // 重命名压缩文件名
                .selectionMode(PictureConfig.MULTIPLE)    // 多选
                .isCompress(true) // 压缩
                .selectionData(selectionMedia) //  传入已选数据
                .isPreviewEggs(true)  // 预览图片时增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .videoMaxSecond(videoMaxSecond) // 显示多少秒以内的视频or音频
                .recordVideoSecond(recordVideoSecond)  //  视频录制秒数
                .forResult(listener);   // 回调
    }

    @Override
    public void startFragment(Class<? extends BaseFragment> clz, boolean... isDestroyCurrent) {
        startFragment(clz, null, isDestroyCurrent);
    }

    @Override
    public void startFragment(Class<? extends BaseFragment> clz, Bundle bundle, boolean... isDestroyCurrent) {
        ObjectUtils.requireNonNull(clz);
        BaseFragment fragment = ReflectUtils.reflect(clz).newInstance().get();
        if (ObjectUtils.isNotEmpty(bundle)) fragment.setArguments(bundle);
        if ((!ArrayUtils.isEmpty(isDestroyCurrent) && isDestroyCurrent[0])) {
            startFragmentAndDestroyCurrent(fragment);
        } else {
            startFragment(fragment);
        }
    }

    @Override
    public void finishFragment() {
        popBackStack();
    }

    // 注册ViewModel与View的契约UI回调事件
    protected void registerUIChangeLiveDataCallBack() {
        // 显示Tip
        viewModel.getUiChangeLiveData().getShowTipEvent().observe(this, params -> {
            int tipType = (int) params.get(ParameterField.TIP_TYPE);
            String tipWord = (String) params.get(ParameterField.TIP_TEXT);
            showTip(tipType, tipWord);
        });
        // 移除Tip
        viewModel.getUiChangeLiveData().getHideTipEvent().observe(this, v -> hideTip());
        // ItemDialog
        viewModel.getUiChangeLiveData().getShowItemsDialogEvent().observe(this, params -> {
            String title = (String) params.get(ParameterField.DIALOG_TITLE);
            String[] items = (String[]) params.get(ParameterField.DIALOG_ITEMS);
            DialogInterface.OnClickListener listener = (DialogInterface.OnClickListener) params.get(ParameterField.DIALOG_ITEMS_LISTENER);
            showItemsDialog(title, items, listener);
        });
        // SingleActionDialog
        viewModel.getUiChangeLiveData().getShowSingleActionDialogEvent().observe(this, params -> {
            String title = (String) params.get(ParameterField.DIALOG_TITLE);
            String message = (String) params.get(ParameterField.DIALOG_MESSAGE);
            String actionText = (String) params.get(ParameterField.DIALOG_LEFT_ACTION_TEXT);
            QMUIDialogAction.ActionListener actionListener = (QMUIDialogAction.ActionListener) params.get(ParameterField.DIALOG_LEFT_ACTION_LISTENER);
            showSingleActionDialog(title, message, actionText, actionListener);
        });
        // Dialog
        viewModel.getUiChangeLiveData().getShowDialogEvent().observe(this, params -> {
            String title = (String) params.get(ParameterField.DIALOG_TITLE);
            String message = (String) params.get(ParameterField.DIALOG_MESSAGE);
            String leftActionText = (String) params.get(ParameterField.DIALOG_LEFT_ACTION_TEXT);
            QMUIDialogAction.ActionListener leftActionListener = (QMUIDialogAction.ActionListener) params.get(ParameterField.DIALOG_LEFT_ACTION_LISTENER);
            String rightActionText = (String) params.get(ParameterField.DIALOG_RIGHT_ACTION_TEXT);
            QMUIDialogAction.ActionListener rightActionListener = (QMUIDialogAction.ActionListener) params.get(ParameterField.DIALOG_RIGHT_ACTION_LISTENER);
            showDialog(title, message, leftActionText, leftActionListener, rightActionText, rightActionListener);
        });
        // 显示加载条
        viewModel.getUiChangeLiveData().getShowProgressEvent().observe(this, v -> showProgress());
        // 移除加载条
        viewModel.getUiChangeLiveData().getRemoveProgressEvent().observe(this, v -> removeProgress());
        // 显示空视图
        viewModel.getUiChangeLiveData().getShowEmptyEvent().observe(this, v -> {
            showEmpty();
        });
        // 显示错误视图
        viewModel.getUiChangeLiveData().getShowFailedEvent().observe(this, params -> {
            String error = (String) params.get(ParameterField.ERROR_TEXT);
            View.OnClickListener listener = (View.OnClickListener) params.get(ParameterField.ERROR_LISTENER);
            showFailed(error, listener);
        });
        // 跳转单选图片
        viewModel.getUiChangeLiveData().getStartSelectSignAtyEvent().observe(this, params -> {
            int chooseMode = (int) params.get(ParameterField.SELECT_PICTURE_MODE);
            int aspect_ratio_x = (int) params.get(ParameterField.SELECT_PICTURE_RATIO_X);
            int aspect_ratio_y = (int) params.get(ParameterField.SELECT_PICTURE_RATIO_Y);
            int videoMaxSecond = (int) params.get(ParameterField.SELECT_PICTURE_VIDEO_MAX_SECOND);
            int recordVideoSecond = (int) params.get(ParameterField.SELECT_PICTURE_RECORD_VIDEO_SECOND);
            OnResultCallbackListener listener = (OnResultCallbackListener) params.get(ParameterField.SELECT_PICTURE_CALLBACK_LISTENER);
            startSelectSignAty(chooseMode, aspect_ratio_x, aspect_ratio_y, videoMaxSecond, recordVideoSecond, listener);
        });
        // 跳转多选图片
        viewModel.getUiChangeLiveData().getStartSelectMultipleAtyEvent().observe(this, params -> {
            int chooseMode = (int) params.get(ParameterField.SELECT_PICTURE_MODE);
            List<LocalMedia> selectionMedia = (List<LocalMedia>) params.get(ParameterField.SELECT_PICTURE_SELECTION_MEDIA);
            int maxSelectNum = (int) params.get(ParameterField.SELECT_PICTURE_MAX_SELECT_NUM);
            int videoMaxSecond = (int) params.get(ParameterField.SELECT_PICTURE_VIDEO_MAX_SECOND);
            int recordVideoSecond = (int) params.get(ParameterField.SELECT_PICTURE_RECORD_VIDEO_SECOND);
            OnResultCallbackListener listener = (OnResultCallbackListener) params.get(ParameterField.SELECT_PICTURE_CALLBACK_LISTENER);
            startSelectMultipleAty(chooseMode, selectionMedia, maxSelectNum, videoMaxSecond, recordVideoSecond, listener);
        });
        // 启动Fragment
        viewModel.getUiChangeLiveData().getStartFragmentEvent().observe(this, params -> {
            Class<? extends BaseFragment> clz = (Class<? extends BaseFragment>) params.get(ParameterField.FRAGMENT);
            Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
            boolean[] isDestroyCurrent = (boolean[]) params.get(ParameterField.DESTROY_CURRENT);
            startFragment(clz, bundle, isDestroyCurrent);
        });
        // 关闭Fragment
        viewModel.getUiChangeLiveData().getFinishFragmentEvent().observe(this, v -> finishFragment());
    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(getVariableId(), viewModel);
        }
    }

    protected BaseActivity getBaseActivity() {
        QMUIFragmentActivity activity = getBaseFragmentActivity();
        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }
        return null;
    }

    private void resetShowContent() {
        ThreadUtils.executeByCustomWithDelay(ThreadUtils.getSinglePool(),
                new ThreadUtils.SimpleTask<Object>() {
                    @Override
                    public Object doInBackground() {
                        return null;
                    }

                    @Override
                    public void onSuccess(Object result) {
                        showContent = false;
                    }
                },
                500,
                TimeUnit.MILLISECONDS);
    }
}
