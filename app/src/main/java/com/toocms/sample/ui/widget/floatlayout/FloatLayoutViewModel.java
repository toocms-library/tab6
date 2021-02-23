package com.toocms.sample.ui.widget.floatlayout;

import android.app.Application;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.viewadapter.floatlayout.ViewAdapter;

import java.util.Random;

/**
 * Author：Zero
 * Date：2021/2/19
 */
public class FloatLayoutViewModel extends BaseViewModel {

    private final String[] strings = new String[]{"Android", "iOS", "PHP", "Html", "TooCMS", "Zero"};
    private Random random = new Random();

    public ObservableList<String> list = new ObservableArrayList<>();

    public FloatLayoutViewModel(@NonNull Application application) {
        super(application);
        for (int i = 0; i < 30; i++) {
            list.add(strings[random.nextInt(strings.length)]);
        }
    }

    public ViewAdapter.BindingCustom<String> solidStyle = (textView, s) -> {
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText(s);
    };

    public ViewAdapter.BindingCustom<String> hollowStyle = (textView, s) -> {
        textView.setBackground(ViewAdapter.getHollowBackgroundDrawable());
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText(s);
    };

    public ViewAdapter.OnFloatItemClickListener<String> listener = (textView, s) -> showToast(s);
}
