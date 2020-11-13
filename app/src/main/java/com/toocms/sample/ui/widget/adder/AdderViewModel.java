package com.toocms.sample.ui.widget.adder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.luck.picture.lib.entity.LocalMedia;
import com.toocms.tab.base.BaseModel;
import com.toocms.tab.base.BaseViewModel;

/**
 * Author：Zero
 * Date：2020/11/3 14:09
 */
public class AdderViewModel extends BaseViewModel<BaseModel> {

    public ObservableList<LocalMedia> localMedia = new ObservableArrayList<>();

    public AdderViewModel(@NonNull Application application) {
        super(application);
    }
}
