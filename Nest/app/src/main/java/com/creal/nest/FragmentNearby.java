package com.creal.nest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.model.Pagination;
import com.creal.nest.model.ShopCategory;
import com.creal.nest.views.HeaderView;

import java.util.ArrayList;
import java.util.List;

public class FragmentNearby extends Fragment {
    private static final String TAG = "XYK-FragmentNearby";
    private HorizontalScrollView mTabScrollView;
    private List<LinearLayout> mTabs;
    private View mContentView;
    private View mLoadingView;
    private TabHost mTabHost;
    private ViewPager mViewPager;

//    private String[] shopCategories = {"餐饮类","医疗类","娱乐类","百货类", "出租类", "旅游类", "文化类", "AA类"};
    List<ShopCategory> mShopCategories;
    private TabHost.TabContentFactory mEmptyTabContentFactory = new TabHost.TabContentFactory(){
        public View createTabContent(String tag) {
            return new TextView(getActivity());
        }
    };

    public FragmentNearby(){
        Log.d(TAG, "FragmentNearby()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        if (mContentView == null){
            mContentView = inflater.inflate(R.layout.fragment_nearby, container, false);
            HeaderView headerView = (HeaderView)mContentView.findViewById(R.id.header);
            headerView.setRightImage(R.drawable.header_search_icon);
            headerView.setTitle(R.string.main_tab_title_nearby);
            headerView.setTitleCenter();
            headerView.setRightBtnListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    getActivity().startActivity(intent);
                }
            });
            mTabScrollView = (HorizontalScrollView)mContentView.findViewById(R.id.tabs_scrollView);
            mLoadingView = mContentView.findViewById(R.id.id_category_loading);
            mViewPager = (ViewPager) mContentView.findViewById(R.id.nearbyViewpager);
            mViewPager.setOffscreenPageLimit(4);
            mTabs = new ArrayList<>();
            mTabHost = (TabHost) mContentView.findViewById(android.R.id.tabhost);
            mTabHost.setup();
            loadCategories();
        }else{
            ((ViewGroup)mContentView.getParent()).removeView(mContentView);
        }
        return mContentView;
    }


    private void loadCategories(){
        Log.d(TAG, "loadCategories");
        mLoadingView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        CommonPaginationAction getCategoryAction = new CommonPaginationAction(getActivity(), 1, 100, Constants.URL_GET_SHOP_CATEGORY, null, ShopCategory.class, "comclass");
        getCategoryAction.execute(
                new AbstractAction.UICallBack<Pagination<ShopCategory>>() {
                    public void onSuccess(Pagination<ShopCategory> result) {
                        updateUI(result.getItems());
                    }

                    public void onFailure(AbstractAction.ActionError error) {
//                        loadCategoryFromDB();
                    }
                }
        );
    }

    private void updateUI(final List<ShopCategory> categories){
        Log.d(TAG, "updateUI");
        mShopCategories = categories;
        mLoadingView.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        if(categories == null || categories.isEmpty()){
            Toast.makeText(getActivity(), R.string.load_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        for (ShopCategory category : categories) {
            LinearLayout tabIndicator = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.nearby_tab_view, null);
            TextView tvTab = (TextView) tabIndicator.findViewById(R.id.tabsText);
            tvTab.setText(category.getName());
            mTabs.add(tabIndicator);
            mTabHost.addTab(mTabHost.newTabSpec(category.getName()).setIndicator(tabIndicator).setContent(mEmptyTabContentFactory));
        }
        mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
        //点击tabhost中的tab时，要切换下面的viewPager
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getName().equals(tabId)) {
                        mViewPager.setCurrentItem(i);
                    }
                }
            }
        });

        //Pager
        mViewPager.setAdapter(new ShopPagerAdapter(getChildFragmentManager(), categories));
        //切换Pager时,要将当前对应的Tab滚动到可见位置
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                mTabHost.setCurrentTab(position);
                int moveLeft = (int) mTabs.get(position).getLeft() - (int) mTabs.get(1).getLeft();
                mTabScrollView.smoothScrollTo(moveLeft, 0);
            }
        });
    }

    /**
     * A simple pager adapter that represents 5
     * objects, in sequence.
     */
    private class ShopPagerAdapter extends FragmentStatePagerAdapter {
        public static final String tag = "XYK-ShopPagerAdapter";
        List<ShopCategory> mCategories;
        public ShopPagerAdapter(FragmentManager fm, List<ShopCategory> categories) {
            super(fm);
            mCategories = categories;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(tag, ": getItem: " + position);
            FragmentNearbyShopList fragment = new FragmentNearbyShopList();
            Bundle bundle = new Bundle();
            bundle.putParcelable(FragmentNearbyShopList.INTENT_EXTRA_CATEGORY, mCategories.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }
    }

    public void onResume(){
        Log.d(TAG, " onResume()");
        super.onResume();
    }
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, " onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }
    public void onViewStateRestored (Bundle savedInstanceState){
        Log.d(TAG, " onViewStateRestored()");
        super.onViewStateRestored(savedInstanceState);
    }
    public void onStart (){
        Log.d(TAG, " onStart()");
        super.onStart();
    }

    public void onPause (){
        Log.d(TAG,  " onPause()");
        super.onPause();
    }
    public void onStop (){
        Log.d(TAG,  " onStop()");
        super.onStop();
    }
    public void onDestroyView (){
        Log.d(TAG, " onDestroyView()");
        super.onDestroyView();
    }
    public void onDestroy (){
        Log.d(TAG, " onDestroy()");
        super.onDestroy();
    }
    public void onDetach (){
        Log.d(TAG, " onDetach()");
        super.onDetach();
    }
}
