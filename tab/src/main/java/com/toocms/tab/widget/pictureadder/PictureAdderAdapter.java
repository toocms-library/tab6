package com.toocms.tab.widget.pictureadder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.toocms.tab.R;
import com.toocms.tab.imageload.ImageLoader;

/**
 * Author：Zero
 * Date：2020/1/21 14:43
 *
 * @version v5.0
 */
public class PictureAdderAdapter extends RecyclerView.Adapter<PictureAdderAdapter.ViewHolder> {

    public static final String TAG = "PictureAdderView";
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private ObservableList<LocalMedia> list = new ObservableArrayList<>();
    private int selectMax = 9;

    /**
     * 点击添加图片跳转
     */
    private OnAddPictureClickListener mOnAddPictureClickListener;
    private OnItemClickListener mOnItemClickListener;

    public PictureAdderAdapter(Context context, OnAddPictureClickListener mOnAddPictureClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mOnAddPictureClickListener = mOnAddPictureClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_filter_picture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            holder.mImg.setImageResource(R.drawable.ic_add_image);
            holder.mImg.setOnClickListener(v -> mOnAddPictureClickListener.onAddPictureClick());
            holder.mIvDel.setVisibility(View.INVISIBLE);
        } else {
            holder.mIvDel.setVisibility(View.VISIBLE);
            holder.mIvDel.setOnClickListener(view -> {
                int index = holder.getAdapterPosition();
                delete(index);
            });
            LocalMedia media = list.get(position);
            if (media == null || TextUtils.isEmpty(media.getPath())) {
                return;
            }
            int chooseModel = media.getChooseModel();
            String path;
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }

            long duration = media.getDuration();
            holder.tvDuration.setVisibility(PictureMimeType.isHasVideo(media.getMimeType())
                    ? View.VISIBLE : View.GONE);
            if (chooseModel == PictureMimeType.ofAudio()) {
                holder.tvDuration.setVisibility(View.VISIBLE);
                holder.tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds
                        (R.drawable.picture_icon_audio, 0, 0, 0);
            } else {
                holder.tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds
                        (R.drawable.picture_icon_video, 0, 0, 0);
            }
            holder.tvDuration.setText(DateUtils.formatDurationTime(duration));
            if (chooseModel == PictureMimeType.ofAudio()) {
                holder.mImg.setImageResource(R.drawable.picture_audio_placeholder);
            } else {
                ImageLoader.loadFile2Image(path, holder.mImg, R.drawable.picture_image_placeholder);
            }
            //itemView 的点击事件
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(v -> {
                    int adapterPosition = holder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(adapterPosition, v);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    /**
     * 删除
     */
    public void delete(int position) {
        try {
            if (position != RecyclerView.NO_POSITION && list.size() > position) {
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(ObservableList<LocalMedia> list) {
        this.list = list;
    }

    public ObservableList<LocalMedia> getData() {
        return list == null ? new ObservableArrayList<>() : list;
    }

    public void remove(int position) {
        if (list != null) {
            list.remove(position);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        ImageView mIvDel;
        TextView tvDuration;

        public ViewHolder(View view) {
            super(view);
            mImg = view.findViewById(R.id.fiv);
            mIvDel = view.findViewById(R.id.iv_del);
            tvDuration = view.findViewById(R.id.tv_duration);
        }
    }

    public interface OnAddPictureClickListener {
        void onAddPictureClick();
    }
}
