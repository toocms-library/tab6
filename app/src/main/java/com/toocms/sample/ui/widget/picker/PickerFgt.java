package com.toocms.sample.ui.widget.picker;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtWidgetPickerBinding;
import com.toocms.sample.ui.base.BaseFgt;
import com.toocms.tab.widget.picker.OptionsPickerView;
import com.toocms.tab.widget.picker.TimePickerView;
import com.toocms.tab.widget.picker.builder.OptionsPickerBuilder;
import com.toocms.tab.widget.picker.builder.TimePickerBuilder;
import com.toocms.tab.widget.picker.configure.TimePickerType;

import java.util.Calendar;

/**
 * Author：Zero
 * Date：2021/4/2
 */
public class PickerFgt extends BaseFgt<FgtWidgetPickerBinding, PickerViewModel> {

    private TimePickerView datePicker;
    private TimePickerView timePicker;
    private TimePickerView pickerDialog;
    private OptionsPickerView<String> optionsPickerView;

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("选择器");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_widget_picker;
    }

    @Override
    public int getVariableId() {
        return BR.pickerViewModel;
    }

    @Override
    protected void viewObserver() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtils.getNowDate());
        viewModel.showDatePickerEvent.observe(this, aVoid -> {
            if (datePicker == null) {
                datePicker = new TimePickerBuilder(getContext(), (date, v) -> showToast(TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd"))))
                        .setTimeSelectChangeListener(date -> LogUtils.i("onDateSelectChanged"))
                        .setTitleText("日期选择")
                        .setDate(calendar)
                        .build();
            }
            datePicker.show();
        });
        viewModel.showTimePickerEvent.observe(this, aVoid -> {
            if (timePicker == null) {
                timePicker = new TimePickerBuilder(getContext(), (date, v) -> showToast(TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("HH:mm:ss"))))
                        .setTimeSelectChangeListener(date -> LogUtils.i("onDateSelectChanged"))
                        .setType(TimePickerType.TIME)
                        .setTitleText("时间选择")
                        .setDate(calendar)
                        .build();
            }
            timePicker.show();
        });
        viewModel.showPickerDialogEvent.observe(this, aVoid -> {
            if (pickerDialog == null) {
                pickerDialog = new TimePickerBuilder(getContext(), (date, v) -> showToast(TimeUtils.date2String(date)))
                        .setTimeSelectChangeListener(date -> LogUtils.i("onDateTimeSelectChanged"))
                        .setType(TimePickerType.ALL)
                        .setTitleText("日期时间选择")
                        .isDialog(true)
                        .setOutSideCancelable(true)
                        .setDate(calendar)
                        .build();
            }
            pickerDialog.show();
        });
        viewModel.showOptionsPickerEvent.observe(this, aVoid -> {
            optionsPickerView = new OptionsPickerBuilder(getContext(), (view, options1, options2, options3) -> {
                showToast(viewModel.sex[options1]);
                viewModel.sexSelectOption = options1;
                return false;
            })
                    .setTitleText("选择性别")
                    .setSelectOptions(viewModel.sexSelectOption)
                    .build();
            optionsPickerView.setPicker(viewModel.sex);
            optionsPickerView.show();
        });
    }
}
