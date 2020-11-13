package com.toocms.tab.binding;

/**
 * Author：Zero
 * Date：2020/10/17 14:03
 */
public interface OnItemBind<T> {

    void onItemBind(ItemBinding itemBinding, int position, T item);
}
