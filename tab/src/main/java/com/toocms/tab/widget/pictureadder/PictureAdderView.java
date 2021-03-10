package com.toocms.tab.widget.pictureadder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.toocms.tab.R;
import com.toocms.tab.configs.FileManager;
import com.toocms.tab.imageload.engine.GlideEngine;

import java.util.List;

/**
 * 图片/视频添加控件
 * Author：Zero
 * Date：2020/1/21 14:38
 *
 * @version v5.0
 */
public class PictureAdderView extends RecyclerView implements PictureAdderAdapter.OnAddPictureClickListener, OnItemClickListener {

    private PictureAdderAdapter adapter;
    private PictureWindowAnimationStyle style;

    private ObservableList<LocalMedia> selectList = new ObservableArrayList<>();
    private int numColumns;
    private int spacing;
    private int chooseMode;
    private int maxSelectNum;
    private int videoMaxSecond;
    private int recordVideoSecond;

    public PictureAdderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parseStyle(context, attrs);
        init(context);
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PictureAdderView);
        spacing = (int) typedArray.getDimension(R.styleable.PictureAdderView_spacing, SizeUtils.dp2px(10));
        numColumns = typedArray.getInteger(R.styleable.PictureAdderView_numColumns, 4);
        chooseMode = typedArray.getInt(R.styleable.PictureAdderView_chooseMode, 1);
        maxSelectNum = typedArray.getInteger(R.styleable.PictureAdderView_maxSelectNum, 9);
        videoMaxSecond = typedArray.getInteger(R.styleable.PictureAdderView_videoMaxSecond, 120);
        recordVideoSecond = typedArray.getInteger(R.styleable.PictureAdderView_recordVideoSecond, 120);
    }

    private void init(@NonNull Context context) {
        style = new PictureWindowAnimationStyle();
        style.ofAllAnimation(R.anim.slide_in_right, R.anim.slide_out_right);
        setOverScrollMode(OVER_SCROLL_NEVER);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(context, numColumns, GridLayoutManager.VERTICAL, false);
        setLayoutManager(manager);
        addItemDecoration(new GridSpacingItemDecoration(numColumns, spacing, false));
        adapter = new PictureAdderAdapter(context, this);
        adapter.setOnItemClickListener(this);
        adapter.setSelectMax(maxSelectNum);
        setAdapter(adapter);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onAddPictureClick() {
        PictureSelector.create(ActivityUtils.getTopActivity())
                .openGallery(chooseMode)    // 扫描文件类型
                .setPictureWindowAnimationStyle(style) // 相册启动退出动画
                .imageEngine(GlideEngine.createGlideEngine()) // 图片加载引擎
                .isWithVideoImage(true) // 图片和视频可以同时选则
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)  // 相册Activity方向
                .isOriginalImageControl(false)  // 不显示原图控制按钮
                .maxSelectNum(maxSelectNum) // 最大选择数量
                .compressSavePath(FileManager.getCachePath())   //  压缩图片保存地址
                .renameCompressFile(System.currentTimeMillis() + ".0")    // 重命名压缩文件名
                .selectionMode(PictureConfig.MULTIPLE)    // 多选
                .isCompress(true) // 压缩
                .selectionData(selectList) // 传入已选数据
                .isPreviewEggs(true)  // 预览图片时增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .videoMaxSecond(videoMaxSecond) // 显示多少秒以内的视频or音频
                .recordVideoSecond(recordVideoSecond)  //  视频录制秒数
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        selectList.clear();
                        selectList.addAll(result);
                        adapter.setList(selectList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancel() {

                    }
                });   // 回调
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onItemClick(int position, View v) {
        if (selectList.size() > 0) {
            LocalMedia media = selectList.get(position);
            String mimeType = media.getMimeType();
            int mediaType = PictureMimeType.getMimeType(mimeType);
            switch (mediaType) {
                case PictureConfig.TYPE_VIDEO:  // 预览视频
                    PictureSelector.create(ActivityUtils.getTopActivity())
                            .themeStyle(R.style.picture_default_style)
                            .setPictureWindowAnimationStyle(style)// 自定义页面启动动画
                            .externalPictureVideo(media.getPath());
                    break;
                case PictureConfig.TYPE_AUDIO:  // 预览音频
                    PictureSelector.create(ActivityUtils.getTopActivity())
                            .externalPictureAudio(media.getPath().startsWith("content://") ? media.getAndroidQToPath() : media.getPath());
                    break;
                case PictureConfig.TYPE_IMAGE:    // 预览图片
                    PictureSelector.create(ActivityUtils.getTopActivity())
                            .themeStyle(R.style.picture_default_style) // xml设置主题
                            .setPictureWindowAnimationStyle(style)// 自定义页面启动动画
                            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)// 设置相册Activity方向
                            .imageEngine(GlideEngine.createGlideEngine()) // 图片加载引擎
                            .openExternalPreview(position, selectList);
                    break;
            }
        }
    }

    /**
     * 获取已选择的媒体文件
     *
     * @return
     */
    public ObservableList<LocalMedia> getSelectList() {
        return selectList;
    }
}
