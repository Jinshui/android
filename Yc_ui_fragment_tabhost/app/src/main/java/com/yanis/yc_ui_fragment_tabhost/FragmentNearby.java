package com.yanis.yc_ui_fragment_tabhost;

import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.yanis.yc_ui_fragment_tabhost.actions.AbstractAction;
import com.yanis.yc_ui_fragment_tabhost.actions.GetCategoryAction;
import com.yanis.yc_ui_fragment_tabhost.model.Pagination;
import com.yanis.yc_ui_fragment_tabhost.views.HeaderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentNearby extends Fragment {
    private static final String TAG = "XYK-FragmentNearby";
    private HorizontalScrollView mTabScrollView;
    private List<LinearLayout> mTabs;
    private View mLoadingView;
    private TabHost mTabHost;
    private ViewPager mViewPager;

    private String[] shopCategories = {"餐饮类","医疗类","娱乐类","百货类", "出租类", "旅游类", "文化类", "AA类"};
    private TabHost.TabContentFactory mEmptyTabContentFactory = new TabHost.TabContentFactory(){
        public View createTabContent(String tag) {
            return new TextView(getActivity());
        }
    };

    public FragmentNearby(){
        Log.d(TAG, "FragmentNearby()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        HeaderView headerView = (HeaderView)view.findViewById(R.id.header);
        headerView.setRightImage(R.drawable.header_search_icon);
        headerView.setTitle(R.string.main_tab_title_nearby);
        headerView.setTitleCenter();
        mTabScrollView = (HorizontalScrollView)view.findViewById(R.id.tabs_scrollView);
        mLoadingView = view.findViewById(R.id.id_category_loading);
        mViewPager = (ViewPager) view.findViewById(R.id.nearbyViewpager);
        mTabs = new ArrayList<LinearLayout>();
        mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup();
        updateUI(Arrays.asList(shopCategories));
        return view;
    }


    private void loadCategoryFromServer(){
        Log.d(TAG, "loadCategoryFromServer");
        mLoadingView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        GetCategoryAction getCategoryAction = new GetCategoryAction(getActivity(), 1, 100);
        getCategoryAction.execute(
                new AbstractAction.BackgroundCallBack<Pagination<String>>() {
                    public void onSuccess(Pagination<String> result) {
//                        mcategoryDAO.delete();
//                        mcategoryDAO.save(result.getItems());
                    }
                },
                new AbstractAction.UICallBack<Pagination<String>>() {
                    public void onSuccess(Pagination<String> result) {
                        updateUI(result.getItems());
                    }

                    public void onFailure(AbstractAction.ActionError error) {
//                        loadCategoryFromDB();
                    }
                }
        );
    }

    private void updateUI(final List<String> categories){
        Log.d(TAG, "updateUI");
        mLoadingView.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        if(categories == null || categories.isEmpty()){
            Toast.makeText(getActivity(), R.string.load_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        for (String category : categories) {
            LinearLayout tabIndicator = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.nearby_tab_view, null);
            TextView tvTab = (TextView) tabIndicator.findViewById(R.id.tabsText);
            tvTab.setText(category);
            mTabs.add(tabIndicator);
            mTabHost.addTab(mTabHost.newTabSpec(category).setIndicator(tabIndicator).setContent(mEmptyTabContentFactory));
        }
        mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
        //点击tabhost中的tab时，要切换下面的viewPager
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).equals(tabId)) {
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
     * A simple pager adapter that represents 5 {@link SlidePageFragment}
     * objects, in sequence.
     */
    private class ShopPagerAdapter extends FragmentStatePagerAdapter {
        public static final String tag = "XYK-ShopPagerAdapter";
        List<String> mCategories;
        public ShopPagerAdapter(FragmentManager fm, List<String> categories) {
            super(fm);
            mCategories = categories;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(tag, ": getItem: " + position);
            FragmentNearbyShopList fragment = new FragmentNearbyShopList();
            Bundle bundle = new Bundle();
            bundle.putString("category", mCategories.get(position));
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
