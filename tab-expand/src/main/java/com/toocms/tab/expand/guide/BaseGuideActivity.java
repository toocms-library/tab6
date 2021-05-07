package com.toocms.tab.expand.guide;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ArrayUtils;
import com.toocms.tab.base.BaseActivity;
import com.toocms.tab.expand.R;
import com.toocms.tab.widget.banner.SimpleGuideBanner;
import com.toocms.tab.widget.banner.anim.select.ZoomInEnter;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/4 11:46
 */
public abstract class BaseGuideActivity extends BaseActivity implements SimpleGuideBanner.OnJumpClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout_guide);
        initGuideView(ArrayUtils.asList(getGuideResourceList()), null);
        onActivityCreated();
    }

    /**
     * activity启动后的初始化
     */
    protected void onActivityCreated() {
    }

    /**
     * 获取引导页资源的集合
     *
     * @return
     */
    protected abstract Object[] getGuideResourceList();

    /**
     * 初始化引导页动画
     *
     * @param guidesResIdList  引导图片
     * @param transformerClass 引导图片切换的效果
     */
    public void initGuideView(List<Object> guidesResIdList, Class<? extends ViewPager.PageTransformer> transformerClass) {
        SimpleGuideBanner sgb = findViewById(R.id.guide);

        sgb.setIndicatorWidth(6).setIndicatorHeight(6).setIndicatorGap(12)
                .setIndicatorCornerRadius(3.5f)
                .setSelectAnimClass(ZoomInEnter.class)
                .setTransformerClass(transformerClass)
                .barPadding(0, 10, 0, 10)
                .setSource(guidesResIdList).startScroll();

        sgb.setOnJumpClickListener(this);
    }
}
