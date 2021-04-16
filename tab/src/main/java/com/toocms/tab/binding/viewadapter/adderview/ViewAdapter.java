package com.toocms.tab.binding.viewadapter.adderview;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableList;

import com.luck.picture.lib.entity.LocalMedia;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.widget.pictureadder.PictureAdderView;

/**
 * Author：Zero
 * Date：2021/4/16
 */
public class ViewAdapter {

    @BindingAdapter(value = {"onDataChange"}, requireAll = false)
    public static void onDataChange(PictureAdderView pictureAdderView, BindingCommand<ObservableList<LocalMedia>> command) {
        pictureAdderView.getSelectList().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<LocalMedia>>() {
            @Override
            public void onChanged(ObservableList<LocalMedia> sender) {
            }

            @Override
            public void onItemRangeChanged(ObservableList<LocalMedia> sender, int positionStart, int itemCount) {
            }

            @Override
            public void onItemRangeInserted(ObservableList<LocalMedia> sender, int positionStart, int itemCount) {
                command.execute(sender);
            }

            @Override
            public void onItemRangeMoved(ObservableList<LocalMedia> sender, int fromPosition, int toPosition, int itemCount) {
            }

            @Override
            public void onItemRangeRemoved(ObservableList<LocalMedia> sender, int positionStart, int itemCount) {
                command.execute(sender);
            }
        });
    }
}
