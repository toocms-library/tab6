package com.toocms.tab.crash;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.toocms.tab.R;
import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.base.BaseActivity;

public final class CrashActivity extends BaseActivity {

    @SuppressLint("PrivateResource")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_aty_crash);

        //Close/restart button logic:
        //If a class if set, use restart.
        //Else, use close and just finish the app.
        //It is recommended that you follow this logic if implementing a custom error activity.
        QMUIRoundButton restartButton = findViewById(R.id.crash_restart_button);
        QMUIRoundButton moreInfoButton = findViewById(R.id.crash_more_info_button);

        String stackTrace = CrashStore.getAllErrorDetailsFromIntent(CrashActivity.this, getIntent(), null).replaceAll("<br/>", "\n");

        final CrashConfig config = CrashStore.getConfigFromIntent(getIntent());

        if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
            restartButton.setText(R.string.crash_restart_app);
            restartButton.setOnClickListener(v -> CrashStore.restartApplication(CrashActivity.this, config));
        } else {
            restartButton.setOnClickListener(v -> CrashStore.closeApplication(CrashActivity.this, config));
        }

        if (config.isShowErrorDetails()) {
            moreInfoButton.setOnClickListener(v -> {
                //We retrieve all the error data and show it
                new QMUIDialog.MessageDialogBuilder(this)
                        .setTitle(R.string.crash_details_title)
                        .setMessage(stackTrace)
                        .addAction(0, R.string.crash_details_close, QMUIDialogAction.ACTION_PROP_NEGATIVE, (dialog, index) -> dialog.dismiss())
                        .addAction(R.string.crash_details_copy, (dialog, index) -> {
                            ClipboardUtils.copyText(getString(R.string.crash_details_clipboard_label), stackTrace);
                            ToastUtils.showShort(R.string.crash_details_copied);
                            dialog.dismiss();
                        }).show();
            });
        } else {
            moreInfoButton.setVisibility(View.GONE);
        }

        Integer defaultErrorActivityDrawableId = config.getErrorDrawable();
        ImageView errorImageView = (findViewById(R.id.crash_image));

        if (defaultErrorActivityDrawableId != null) {
            errorImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), defaultErrorActivityDrawableId, getTheme()));
        }
    }

    @Override
    public void onBackPressed() {
    }
}
