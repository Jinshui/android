package com.creal.nest.model;

import android.util.Log;

import com.creal.nest.actions.ActionRespObject;
import com.creal.nest.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Help implements ActionRespObject<Help> {
    private static final String tag = "TT-Help";
    public static class Item extends BaseModel{
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        private String content;
        private String title;

        public static Item fromJSON(JSONObject json) throws JSONException {
            if (json == null)
                throw new IllegalArgumentException("JSONObject is null");
            Item item = new Item();
            if(json.has("id")){
                item.setId(json.getString("id"));
            }
            if(json.has("content")){
                item.setContent(json.getString("content"));
            }
            if(json.has("title")){
                item.setTitle(json.getString("title"));
            }
            return item;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItemList() {
        if(itemList == null)
            itemList = new ArrayList<>();
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    private String title;
    private List<Item> itemList;

    @Override
    public Help fillWithJSON(JSONObject helpJson) throws JSONException {
        if(helpJson.has("class_contents")){
            try{
                JSONArray items = helpJson.getJSONArray("class_contents");
                for(int i=0; i<items.length(); i++){
                    JSONObject item = items.getJSONObject(i);
                    getItemList().add(Item.fromJSON(item));
                }
            }catch(Exception e){
                Log.e(tag, "Failed to parse class_contents: " + e.getMessage());
            }
        }
        if(helpJson.has("title")){
            setTitle(helpJson.getString("title"));
        }
        return this;
    }
}
