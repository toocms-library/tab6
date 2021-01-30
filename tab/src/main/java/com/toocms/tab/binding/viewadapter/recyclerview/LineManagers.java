package com.toocms.tab.binding.viewadapter.recyclerview;

import androidx.recyclerview.widget.RecyclerView;

public class LineManagers {
    protected LineManagers() {
    }

    public interface LineManagerFactory {
        RecyclerView.ItemDecoration create(RecyclerView recyclerView);
    }

    public static LineManagerFactory both() {
        return recyclerView -> new DividerLine(recyclerView.getContext(), DividerLine.LineDrawMode.BOTH);
    }

    public static LineManagerFactory both(int dividerSize) {
        return recyclerView -> new DividerLine(recyclerView.getContext(), dividerSize, DividerLine.LineDrawMode.BOTH);
    }

    public static LineManagerFactory both(int dividerSize, int dividerColor) {
        return recyclerView -> new DividerLine(recyclerView.getContext(), dividerSize, dividerColor, DividerLine.LineDrawMode.BOTH);
    }

    public static LineManagerFactory horizontal() {
        return recyclerView -> new DividerLine(recyclerView.getContext(), DividerLine.LineDrawMode.HORIZONTAL);
    }

    public static LineManagerFactory horizontal(int dividerSize) {
        return recyclerView -> new DividerLine(recyclerView.getContext(), dividerSize, DividerLine.LineDrawMode.HORIZONTAL);
    }

    public static LineManagerFactory horizontal(int dividerSize, int dividerColor) {
        return recyclerView -> new DividerLine(recyclerView.getContext(), dividerSize, dividerColor, DividerLine.LineDrawMode.HORIZONTAL);
    }

    public static LineManagerFactory vertical() {
        return recyclerView -> new DividerLine(recyclerView.getContext(), DividerLine.LineDrawMode.VERTICAL);
    }

    public static LineManagerFactory vertical(int dividerSize) {
        return recyclerView -> new DividerLine(recyclerView.getContext(), dividerSize, DividerLine.LineDrawMode.VERTICAL);
    }

    public static LineManagerFactory vertical(int dividerSize, int dividerColor) {
        return recyclerView -> new DividerLine(recyclerView.getContext(), dividerSize, dividerColor, DividerLine.LineDrawMode.VERTICAL);
    }
}
