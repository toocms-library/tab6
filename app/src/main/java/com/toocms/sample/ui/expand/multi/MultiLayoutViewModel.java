package com.toocms.sample.ui.expand.multi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.CollectionUtils;
import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.base.MultiItemViewModel;
import com.toocms.tab.binding.ItemBinding;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.bus.event.SingleLiveEvent;
import com.toocms.tab.network.ApiTool;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/6 16:57
 */
public class MultiLayoutViewModel extends BaseViewModel {

    private static final String TYPE_ADVERTS = "TYPE_ADVERTS";  // 轮播图
    private static final String TYPE_NAVIGATIONS = "TYPE_NAVIGATIONS";  // 导航图标
    private static final String TYPE_SECTIONS1 = "TYPE_SECTIONS1";    // 板块1
    private static final String TYPE_SECTIONS4 = "TYPE_SECTIONS4";    // 板块4
    private static final String TYPE_SECTIONS5 = "TYPE_SECTIONS5";    // 板块5
    private static final String TYPE_RECOMMENDS = "TYPE_RECOMMENDS";    //  推荐商品

    public ObservableList<MultiItemViewModel> list = new ObservableArrayList<>();
    public SingleLiveEvent<Void> onRefreshFinish = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> onLoadMoreFinifh = new SingleLiveEvent<>();

    public MultiLayoutViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadData(boolean isShowLoading) {
        ApiTool.get("http://hotpotshop-api.uuudoo.com/Index/index")
                .asTooCMSResponse(Index.class)
                .onFinally(() -> {
                    // 发送刷新/加载更多完毕事件
                    onRefreshFinish.call();
                })
                .withViewModel(this)
                .showLoading(isShowLoading)
                .request(index -> {
                    list.clear();
                    MultiItemViewModel itemViewModel;
                    // 轮播图
                    itemViewModel = new AdvertItemViewModel(this, index.getAdverts());
                    itemViewModel.setItemType(TYPE_ADVERTS);
                    list.add(itemViewModel);
                    // 导航图标
                    itemViewModel = new NavigationItemViewModel(this, index.getNavs());
                    itemViewModel.setItemType(TYPE_NAVIGATIONS);
                    list.add(itemViewModel);
                    // 板块（可能为多个板块，需遍历）
                    List<Index.SectionsBean> sectionsBeans = index.getSections();
                    for (Index.SectionsBean section : sectionsBeans) {
                        // 多样式板块
                        switch (section.getLayout()) {
                            case "1":
                                itemViewModel = new Section1ItemViewModel(this, section);
                                itemViewModel.setItemType(TYPE_SECTIONS1);
                                break;
                            case "4":
                                itemViewModel = new Section4ItemViewModel(this, section);
                                itemViewModel.setItemType(TYPE_SECTIONS4);
                                break;
                            case "5":
                                itemViewModel = new Section5ItemViewModel(this, section);
                                itemViewModel.setItemType(TYPE_SECTIONS5);
                                break;
                        }
                        list.add(itemViewModel);
                    }
                    // 推荐（需遍历）
                    List<Index.RecommendsBean> recommendsBeans = index.getRecommends();
                    for (Index.RecommendsBean recommend : recommendsBeans) {
                        itemViewModel = new RecommendItemViewModel(this, recommend);
                        itemViewModel.setItemType(TYPE_RECOMMENDS);
                        list.add(itemViewModel);
                    }
                });
    }

    public BindingCommand onRefreshCommand = new BindingCommand(() -> loadData(false));

    public BindingCommand onLoadMoreCommand = new BindingCommand(() -> {
        for (int i = 0; i < 2; i++) {
            Index.RecommendsBean recommend = new Index.RecommendsBean();
            MultiItemViewModel itemViewModel = new RecommendItemViewModel(this, recommend);
            itemViewModel.setItemType(TYPE_RECOMMENDS);
            list.add(itemViewModel);
        }
        onLoadMoreFinifh.call();
    });

    public ItemBinding<MultiItemViewModel> itemBinding = ItemBinding.of((itemBinding, position, item) -> {
        switch (item.getItemType()) {
            case TYPE_ADVERTS:
                itemBinding.set(BR.advertItemViewModel, R.layout.fgt_expand_item_advert);
                break;
            case TYPE_NAVIGATIONS:
                itemBinding.set(BR.navigationItemViewModel, R.layout.fgt_expand_item_navigation);
                break;
            case TYPE_SECTIONS1:
                itemBinding.set(BR.section1ItemViewModel, R.layout.fgt_expand_item_section1);
                break;
            case TYPE_SECTIONS4:
                itemBinding.set(BR.section4ItemViewModel, R.layout.fgt_expand_item_section4);
                break;
            case TYPE_SECTIONS5:
                itemBinding.set(BR.section5ItemViewModel, R.layout.fgt_expand_item_section5);
                break;
            case TYPE_RECOMMENDS:
                itemBinding.set(BR.recommendItemViewModel, R.layout.fgt_expand_item_recommend);
                break;
        }
    });

    public GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            switch (list.get(position).getItemType()) {
                case TYPE_RECOMMENDS:
                    return 1;
                default:
                    return 2;
            }
        }
    };
}
