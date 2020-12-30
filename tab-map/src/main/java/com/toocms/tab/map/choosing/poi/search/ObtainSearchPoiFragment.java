package com.toocms.tab.map.choosing.poi.search;

import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.toocms.tab.base.BaseFragment;
import com.toocms.tab.map.BR;
import com.toocms.tab.map.R;
import com.toocms.tab.map.databinding.FgtObtainSearchPoiBinding;

/**
 * Author：Zero
 * Date：2020/12/16 9:49
 */
public class ObtainSearchPoiFragment extends BaseFragment<FgtObtainSearchPoiBinding, ObtainSearchPoiViewModel> {

    @Override
    protected void onFragmentCreated() {
        viewModel.setCityCode(getArguments().getString("cityCode"));
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_obtain_search_edittext, null);
        EditText editText = frameLayout.findViewById(R.id.obtain_search_poi_search);
        Drawable drawable = QMUIResHelper.getAttrDrawable(getBaseFragmentActivity(), R.attr.app_map_search_flag_drawable_id);
        if (drawable != null)
            editText.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        topBar.setCenterView(frameLayout);
        editText.setOnKeyListener((v, keyCode, event) -> {
            switch (keyCode) {
                case KeyEvent.KEYCODE_ENTER:
                    viewModel.doSearch(editText.getText().toString());
                    KeyboardUtils.hideSoftInput(editText);
                    break;
            }
            return false;
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_obtain_search_poi;
    }

    @Override
    public int getVariableId() {
        return BR.obtainSearchPoiViewModel;
    }

    @Override
    protected void viewObserver() {
    }
}
