package com.toocms.sample.ui.expand.multi;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.toocms.tab.base.MultiItemViewModel;
import com.toocms.tab.widget.banner.BannerItem;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/6 17:14
 */
public class AdvertItemViewModel extends MultiItemViewModel<MultiLayoutViewModel> {

    public ObservableList<BannerItem> bannerItems = new ObservableArrayList<>();

    public AdvertItemViewModel(@NonNull MultiLayoutViewModel viewModel, List<Index.AdvertsBean> list) {
        super(viewModel);
        for (Index.AdvertsBean advertsBean : list) {
            BannerItem item = new BannerItem();
            item.setImgUrl(advertsBean.getAbs_url());
            bannerItems.add(item);
        }
    }
}
