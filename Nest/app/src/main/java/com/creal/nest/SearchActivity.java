package com.creal.nest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.creal.nest.actions.CommonPaginationAction;
import com.creal.nest.model.Shop;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends FragmentActivity {

    private static final String TAG = "XYK-SearchActivity";
    private SearchHistoryListFragment mSearchHistoryFragment;
    private boolean isSearchResult = false;
    private EditText mKeyword;
    private ListFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mKeyword = (EditText)findViewById(R.id.id_text_keyword);
        mSearchHistoryFragment = (SearchHistoryListFragment)getSupportFragmentManager().findFragmentById(R.id.content);
        mCurrentFragment = mSearchHistoryFragment;
    }

    public void onSearchClick(View view){
        Log.d(TAG, "onSearchClick");
        isSearchResult = true;
        Map<String, String> parameters = new HashMap<>();
        CharSequence keyword = mKeyword.getText();
        if(!TextUtils.isEmpty(keyword)) {
            parameters.put("search", keyword.toString());
            mSearchHistoryFragment.addKeyword(this, keyword.toString());
        }
        CommonPaginationAction action = new CommonPaginationAction(this, 1, 10, Constants.URL_SEARCH, parameters, Shop.class, "cominfo");
        FragmentNearbyShopList shopListFragment = new FragmentNearbyShopList();
        shopListFragment.setPaginationAction(action);
        showFragment(shopListFragment);
    }

    public void setKeyword(String keyword){
        mKeyword.setText(keyword);
    }

    @Override
    public void onBackPressed() {
        onBackClick(null);
    }

    public void onBackClick(View view){
        Log.d(TAG, "onBackClick");
        if(isSearchResult) {
            isSearchResult = false;
            mSearchHistoryFragment.refreshKeywords(this);
            showFragment(mSearchHistoryFragment);
        }else {
            finish();
        }
    }

    private void showFragment(ListFragment listFragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(mCurrentFragment);
        fragmentTransaction.add(R.id.content, listFragment).commit();
        mCurrentFragment = listFragment;
    }
}