package com.pfh.openeyes.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.pfh.openeyes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 */
public class Banner extends RelativeLayout {
    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;

    private List<String> mPicUrls;//图片的url
    private List<ImageView> mImageViewList;//viewpager中的imageview
    private int mCount;//有几项item
    private Context mContext;
    private LinearLayout pointContainer;//指示器的容器
    private ViewPager mViewPager;

    public Banner(Context context) {
        this(context,null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();

    }

    /**
     *
     */
    private void initView() {

        //底部的小圆点指示器的容器
        pointContainer = new LinearLayout(mContext);
        pointContainer.setOrientation(LinearLayout.HORIZONTAL);
        pointContainer.setPadding(10,10,10,10);
        LayoutParams lp_indicator = new LayoutParams(RWC, RWC);
        lp_indicator.addRule(ALIGN_PARENT_BOTTOM);
        addView(pointContainer,lp_indicator);

        //viewpger
        mViewPager = new ViewPager(mContext);
        LayoutParams lp_viewpager = new LayoutParams(RMP, RMP);
        addView(mViewPager,lp_viewpager);


    }

    public void setPicUrls(List<String> picUrls){
        this.mPicUrls = picUrls;
        mCount = picUrls.size();
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mPicUrls.size(); i++) {
            mImageViewList.add(new ImageView(mContext));
        }
        loadPicFromNet();

        initViewPager();

        initIndicator();

    }

    private void loadPicFromNet() {
        for (int i = 0; i < mImageViewList.size(); i++) {
            Glide.with(mContext)
                    .load(mPicUrls.get(i))
                    .into(mImageViewList.get(i));
        }

    }

    private void initViewPager() {
        mViewPager.setAdapter(new MyAdapter());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //更新Indicator

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 画指示器，这里是小圆点
     */
    private void initIndicator() {
        if (pointContainer != null){
            pointContainer.removeAllViews();
        }
        ImageView iv_indicator;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);

        for (int i = 0; i < mCount; i++) {
            iv_indicator = new ImageView(mContext);
            iv_indicator.setPadding(5,5,5,5);
            iv_indicator.setLayoutParams(lp);
            iv_indicator.setBackgroundResource(R.drawable.circle_indicator_shape);
            pointContainer.addView(iv_indicator);
        }
    }

    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViewList.get(position));
        }
    }
}
