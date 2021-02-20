package com.toocms.tab.widget.tagflowlayout;

import android.view.View;

public interface OnTagClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
