package com.toocms.sample.ui.expand.map;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.databinding.FgtWidgetMapBinding;
import com.toocms.sample.ui.base.BaseFgt;

/**
 * Author：Zero
 * Date：2020/12/28 16:44
 */
public class MapFgt extends BaseFgt<FgtWidgetMapBinding, MapViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("地图");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_widget_map;
    }

    @Override
    public int getVariableId() {
        return BR.mapViewModel;
    }

    @Override
    protected void viewObserver() {

    }
}
