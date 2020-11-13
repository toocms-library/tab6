package com.toocms.tab.binding.command;

/**
 * Author：Zero
 * Date：2020/10/16 16:36
 */

public interface BindingConsumer<T> {

    void call(T t);
}
