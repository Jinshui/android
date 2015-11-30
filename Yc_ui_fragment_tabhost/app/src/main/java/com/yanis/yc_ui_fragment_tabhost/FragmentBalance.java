package com.yanis.yc_ui_fragment_tabhost;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class FragmentBalance extends Fragment {

	TabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.test_tabhost, container, false);
		mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		mTabHost.setup();
		setupTab(new TextView(getActivity()), "餐饮类");
		setupTab(new TextView(getActivity()), "娱乐类");
		setupTab(new TextView(getActivity()), "医疗类");
        setupTab(new TextView(getActivity()), "百货类");
        setupTab(new TextView(getActivity()), "百货类");
        setupTab(new TextView(getActivity()), "百货类");
        setupTab(new TextView(getActivity()), "百货类");
        setupTab(new TextView(getActivity()), "百货类");
        mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
		return view;
	}

	private void setupTab(final View view, final String tag) {
		View tabview = createTabView(getActivity(), tag);
		TabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return view;
			}
		});
		mTabHost.addTab(setContent);
	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.test_tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
}
