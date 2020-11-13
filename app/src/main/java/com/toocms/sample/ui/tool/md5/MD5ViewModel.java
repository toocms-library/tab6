package com.toocms.sample.ui.tool.md5;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.toocms.tab.base.BaseModel;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.bus.event.SingleLiveEvent;

/**
 * Author：Zero
 * Date：2020/10/27 16:51
 *
 * @version v1.0
 */
public class MD5ViewModel extends BaseViewModel<BaseModel> {

    public ObservableField<String> pack = new ObservableField<>();
    private UIChangeObservable uc = new UIChangeObservable();

    public MD5ViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable getUc() {
        return uc;
    }

    public BindingCommand md5 = new BindingCommand(() -> {
        uc.setText.setValue(EncryptUtils.encryptMD5ToString("com.toocms." + pack.get()));
    });

    public BindingCommand copy = new BindingCommand(() -> {
        ClipboardUtils.copyText(uc.setText.getValue());
        showToast("已复制");
    });

    public class UIChangeObservable {
        public SingleLiveEvent<String> setText = new SingleLiveEvent<>();
    }
}
