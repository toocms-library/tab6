package com.toocms.tab.expand.history;

import java.util.List;

public interface OnSearchResultListener<T> {

    void onResult(String key, List<T> historyList);
}
