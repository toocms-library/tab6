package com.toocms.tab.binding.viewadapter.smartrefresh;

import androidx.databinding.BindingAdapter;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.toocms.tab.binding.command.BindingCommand;

import org.jetbrains.annotations.NotNull;

/**
 * Author：Zero
 * Date：2020/11/12 9:18
 */
public class ViewAdapter {

    //下拉刷新命令
    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(@NotNull SmartRefreshLayout smartRefreshLayout, final BindingCommand onRefreshCommand) {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            if (onRefreshCommand != null) {
                onRefreshCommand.execute();
            }
        });
    }

    //加载更多命令
    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(@NotNull SmartRefreshLayout smartRefreshLayout, final BindingCommand onLoadMoreCommand) {
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (onLoadMoreCommand != null) {
                onLoadMoreCommand.execute();
            }
        });
    }
}
