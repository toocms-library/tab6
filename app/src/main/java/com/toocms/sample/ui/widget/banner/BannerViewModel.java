package com.toocms.sample.ui.widget.banner;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.blankj.utilcode.util.ToastUtils;
import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.tab.base.BaseModel;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.ItemBinding;
import com.toocms.tab.bus.event.SingleLiveEvent;
import com.toocms.tab.network.ApiTool;
import com.toocms.tab.widget.banner.BannerItem;
import com.toocms.tab.widget.banner.base.BaseBanner;

/**
 * Author：Zero
 * Date：2020/11/2 10:07
 */
public class BannerViewModel extends BaseViewModel<BaseModel> {

    public ObservableList<BannerItem> bannerItems = new ObservableArrayList<>();

    public BannerViewModel(@NonNull Application application) {
        super(application);
    }

    public BaseBanner.OnItemClickListener<BannerItem> onItemClickListener = (view, item, position) -> {
        ToastUtils.showShort("点击了第" + position + "项，url：" + item.getImgUrl());
    };

    @Override
    public void onStart() {
        super.onStart();
        ApiTool.get("http://yunzhuang-api.uuudoo.com/Index/index")
                .asTooCMSResponse(Banner.class)
                .onStart(disposable -> bannerItems.clear())
                .withViewModel(this)
                .request(banner -> {
                    for (Banner.AdvertsBean advert : banner.getAdverts()) {
                        BannerItem item = new BannerItem();
                        item.setImgUrl(advert.getAbs_url());
                        bannerItems.add(item);
                    }
                });
    }
}
