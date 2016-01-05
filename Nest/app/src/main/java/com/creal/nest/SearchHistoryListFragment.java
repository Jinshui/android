package com.creal.nest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.creal.nest.actions.ParallelTask;
import com.creal.nest.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchHistoryListFragment extends ListFragment {

    private List<String> mKeywords;
    private SearchHistoryAdapter mSearchHistoryAdapter;
    private SearchActivity mSearchActivity;

    public SearchHistoryListFragment(){
        mKeywords = new ArrayList<>();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSearchActivity = (SearchActivity)getActivity();
        mSearchHistoryAdapter = new SearchHistoryAdapter(getActivity(), mKeywords);
        setListAdapter(mSearchHistoryAdapter);
        Button button = new Button(getActivity());
        button.setText(R.string.search_history_clean);
        button.setPadding(0, 40, 0, 40);
        button.setBackgroundColor(Color.WHITE);
        button.setTextColor(Color.GRAY);
        button.setTextSize(18);
        getListView().addFooterView(button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cleanKeywords();
            }
        });
        refreshKeywords(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_history_fragment, null);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        String keyword = getListView().getItemAtPosition(position).toString();
        mSearchActivity.setKeyword(keyword);
    }

    public static class SearchHistoryAdapter extends ArrayAdapter<String> {
        private LayoutInflater inflater;
        public SearchHistoryAdapter(Context context, List<String> objects) {
            super(context, 0, objects);
            inflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView itemView;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.search_history_list_item, parent, false);
                itemView = (TextView) convertView.findViewById(R.id.id_txt_item_name);
                convertView.setTag(itemView);
            } else {
                itemView = (TextView) convertView.getTag();
            }
            itemView.setText(getItem(position));
            return convertView;
        }
    }

    public void cleanKeywords(){
        PreferenceUtil.removeKey(getActivity(), Constants.APP_SEARCH_KEYWORDS);
        mSearchHistoryAdapter.clear();
    }

    public void addKeyword(final Context context, final String keyword){
        if(TextUtils.isEmpty(keyword))
            return;
        new ParallelTask<Void>(){
            protected Void doInBackground(Void... params) {
                String keywords = PreferenceUtil.getString(context, Constants.APP_SEARCH_KEYWORDS, null);
                if(keywords == null){
                    keywords = keyword;
                }else{
                    List<String> keywordList = Arrays.asList(keywords.split(Constants.APP_KEYWORDS_SPLITTER));
                    StringBuilder sb = new StringBuilder(keyword).append("||");
                    for(int i = 0; i < keywordList.size() && i < Constants.MAX_CACHED_KEYWORD_SIZE - 1; i ++) {
                        String savedKeyword =  keywordList.get(i);
                        if (!keyword.equals(savedKeyword)) {
                            sb.append(savedKeyword).append("||");
                        }
                    }
                    keywords = sb.toString();
                }
                PreferenceUtil.saveString(context, Constants.APP_SEARCH_KEYWORDS, keywords);
                return null;
            }
        }.execute();
    }

    public void refreshKeywords(final Context context){
        new ParallelTask<List<String>>(){
            protected List<String> doInBackground(Void... params) {
                String keywords = PreferenceUtil.getString(context, Constants.APP_SEARCH_KEYWORDS, null);
                if(keywords != null){
                    return Arrays.asList(keywords.split(Constants.APP_KEYWORDS_SPLITTER));
                }
                return Collections.EMPTY_LIST;
            }
            protected void onPostExecute(List<String> keywords){
                mKeywords.clear();
                mKeywords.addAll(keywords);
                mSearchHistoryAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}