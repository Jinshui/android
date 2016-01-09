package com.creal.nest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.creal.nest.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repair extends BaseModel implements ActionRespObject<Repair>{

    public static final Creator<Repair> CREATOR
            = new Creator<Repair>() {
        public Repair createFromParcel(Parcel in) {
            return new Repair(in);
        }

        public Repair[] newArray(int size) {
            return new Repair[size];
        }
    };

    public static class Step implements Parcelable {

        public static final Creator<Step> CREATOR = new Creator<Step>() {
            public Step createFromParcel(Parcel in) {
                return new Step(in);
            }

            public Step[] newArray(int size) {
                return new Step[size];
            }
        };

        public String desc;
        public String date;

        public Step(Parcel in) {
            desc = in.readString();
            date = in.readString();
        }

        public Step(String desc, String date) {
            this.date = date;
            this.desc = desc;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(desc);
            dest.writeString(date);
        }
    }

    public static enum State {
        UNPROCESSED,
        UNRESOLVED,
        RESOLVED,
        UNRESOLVABLE;

        public static State from(int state) {
            switch (state) {
                case 1:
                    return UNPROCESSED;
                case 2:
                    return UNRESOLVED;
                case 3:
                    return RESOLVED;
                case 4:
                    return UNRESOLVABLE;
            }
            return UNRESOLVABLE;
        }

        public static String toString(State state){
            if(state == null)
                return "等待处理...";
            switch (state) {
                case UNPROCESSED:
                    return "等待处理...";
                case UNRESOLVED:
                    return "进行中...";
                case RESOLVED:
                    return "已完成。";
                case UNRESOLVABLE:
                    return "拒处理。";
            }
            return "";
        }
    }

    private String title;
    private State state;
    private String time;
    private String summary;
    private String dealOpinion;
    private String dealTime;
    private List<Step> stepList;


    public Repair() {
    }

    public Repair(Parcel in) {
        super(in);
        title = in.readString();
        state = State.valueOf(in.readString());
        time = in.readString();
        summary = in.readString();
        dealOpinion = in.readString();
        dealTime = in.readString();
//        in.readTypedList( getStepList(), Step.CREATOR);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(title);
        dest.writeString(state.name());
        dest.writeString(time);
        dest.writeString(summary);
        dest.writeString(dealOpinion);
        dest.writeString(dealTime);
//        dest.writeTypedList(getStepList());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDealOpinion() {
        return dealOpinion;
    }

    public void setDealOpinion(String dealOpinion) {
        this.dealOpinion = dealOpinion;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }


    public List<Step> getStepList() {
        if (stepList == null)
            stepList = new ArrayList<>();
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public boolean isDone() {
        return state == State.RESOLVED || state == State.UNRESOLVABLE;
    }

    public static Repair fromJSON(JSONObject json) throws JSONException {
        if (json == null)
            throw new IllegalArgumentException("JSONObject is null");
        Repair repair = new Repair();
        if(json.has("id")){
            repair.setId(json.getString("id"));
        }
        if(json.has("title")){
            repair.setTitle(json.getString("title"));
        }
        if(json.has("state") && json.getString("state").length() > 0){
            repair.setState(State.from(json.getInt("state")));
        }
        if(json.has("time")){
            repair.setTime(json.getString("time"));
        }
        if(json.has("deal_opinion")){
            repair.setDealOpinion(json.getString("deal_opinion"));
        }
        if(json.has("deal_time")){
            repair.setDealTime(json.getString("deal_time"));
        }
        if(json.has("description")){
            repair.setSummary(json.getString("description"));
        }

        return repair;
    }

    @Override
    public Repair fillWithJSON(JSONObject jsonObject) throws JSONException {
        return fromJSON(jsonObject);
    }
}
