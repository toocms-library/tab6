package com.toocms.sample.ui.widget.tagflow;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.widget.tagflowlayout.OnTagClickListener;
import com.toocms.tab.widget.tagflowlayout.bean.TagBean;

import java.util.Random;

/**
 * Author：Zero
 * Date：2021/2/19
 */
public class TagFlowLayoutViewModel extends BaseViewModel {

    private final String[] strings = new String[]{"Android", "iOS", "PHP", "Html", "TooCMS", "Zero"};
    private Random random = new Random();

    public ObservableList<TagBean> list = new ObservableArrayList<>();

    public TagFlowLayoutViewModel(@NonNull Application application) {
        super(application);
        for (int i = 0; i < 50; i++) {
            TagBean tagBean = new TagBean();
            tagBean.setName(strings[random.nextInt(strings.length)]);
            list.add(tagBean);
        }
    }

    public OnTagClickListener listener = new OnTagClickListener() {
        @Override
        public void onClick(View view, int position) {
            showToast(list.get(position).getName());
        }

        @Override
        public void onLongClick(View view, int position) {

        }
    };
}
