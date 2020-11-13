package com.toocms.tab.binding.viewadapter.badge;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.databinding.BindingAdapter;

import com.toocms.tab.widget.badge.Badge;
import com.toocms.tab.widget.badge.BadgeView;

import org.jetbrains.annotations.NotNull;

/**
 * 好像不太好用，暂时先保留
 * <p>
 * Author：Zero
 * Date：2020/11/13 12:07
 */
public class ViewAdapter {

    @BindingAdapter(value = {"badgeNum", "textColor", "textSize", "gravity", "backgroundColor"}, requireAll = false)
    public static void bindBadge(@NotNull View targetView,
                                 int badgeNum,
                                 @ColorInt int textColor,
                                 int textSize,
                                 int gravity,
                                 int backgroundColor) {
        Badge badge = new BadgeView(targetView.getContext()).bindTarget(targetView);
        badge.setBadgeNumber(badgeNum);
        badge.setBadgeTextColor(textColor == 0 ? BadgeView.DEFAULT_COLOR_BADGE_TEXT : textColor);
        badge.setBadgeTextSize(textSize == 0 ? BadgeView.DEFAULT_TEXT_SIZE : textSize, true);
        badge.setBadgeGravity(gravity == 0 ? Gravity.END | Gravity.TOP : gravity);
        badge.setBadgeBackgroundColor(backgroundColor == 0 ? BadgeView.DEFAULT_COLOR_BACKGROUND : backgroundColor);
    }

    @BindingAdapter(value = {"badgeText", "textColor", "textSize", "gravity", "backgroundColor"}, requireAll = false)
    public static void bindBadge(@NotNull View targetView,
                                 String badgeText,
                                 @ColorInt int textColor,
                                 int textSize,
                                 int gravity,
                                 int backgroundColor) {
        Badge badge = new BadgeView(targetView.getContext()).bindTarget(targetView);
        badge.setBadgeText(badgeText);
        badge.setBadgeTextColor(textColor == 0 ? BadgeView.DEFAULT_COLOR_BADGE_TEXT : textColor);
        badge.setBadgeTextSize(textSize == 0 ? BadgeView.DEFAULT_TEXT_SIZE : textSize, true);
        badge.setBadgeGravity(gravity == 0 ? Gravity.END | Gravity.TOP : gravity);
        badge.setBadgeBackgroundColor(backgroundColor == 0 ? BadgeView.DEFAULT_COLOR_BACKGROUND : backgroundColor);
    }
}
