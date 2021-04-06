package com.toocms.sample.ui.widget.picker;

import android.app.Application;

import androidx.annotation.NonNull;

import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.bus.event.SingleLiveEvent;

/**
 * Author：Zero
 * Date：2021/4/2
 */
public class PickerViewModel extends BaseViewModel {

    public String[] sex;

    public int sexSelectOption;

    public SingleLiveEvent<Void> showDatePickerEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showTimePickerEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showPickerDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showOptionsPickerEvent = new SingleLiveEvent<>();

    public PickerViewModel(@NonNull Application application) {
        super(application);
        sex = new String[]{"男", "女"};
    }

    public BindingCommand showDatePickerCommand = new BindingCommand(() -> showDatePickerEvent.call());

    public BindingCommand showTimePickerCommand = new BindingCommand(() -> showTimePickerEvent.call());

    public BindingCommand showPickerDialogCommand = new BindingCommand(() -> showPickerDialogEvent.call());

    public BindingCommand showOptionsPickerCommand = new BindingCommand(() -> showOptionsPickerEvent.call());
}
