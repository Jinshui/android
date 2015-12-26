package com.creal.nest.actions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.creal.nest.R;
import com.creal.nest.util.JSONUtil;
import com.creal.nest.util.PreferenceUtil;
import com.creal.nest.util.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Date;
import java.util.concurrent.Executor;

public abstract class AbstractAction<Result> extends ParallelTask<AbstractAction.ActionResult<Result>> implements JSONConstants{
    private static final String tag = "TT-AbstractRequest";
    //For Test only START
    private Object mOriginalRequest;
    public Object getOriginalRequest(){return mOriginalRequest;}
    private Object mOriginalResponse;
    public Object getOriginalResponse(){return mOriginalResponse;}
    private Throwable mOriginalError;
    public Object getOriginalError(){return mOriginalError;}
    //For Test only END

    private JsonObject mGsonObject;//This is used to generate md5 because gson-json keeps the order of the properties in the original string

    protected String mURL;
    protected String mServiceId;
    protected Context mAppContext;
    private UICallBack<Result> mUICallback;
    private BackgroundCallBack<Result> mBackgroundCallBack;
    private IBackgroundProcessor<Result> mBackgroundProcessor = new NetworkBackgroundProcessor();
    private boolean mSecurity = true;
    private boolean mCancelled = false;
    public static enum ErrorCode{
        APPLICATOIN_ERROR,
        INVALID_REQUEST,
        INVALID_RESPONSE,
        SERVER_ERROR,
        SECURITY_ERROR,
        NETWORK_TIMEOUT,
        NETWORK_DISCONNECTED,
        NETWORK_ERROR;
    }

    public static class ActionError{
        private ErrorCode mError;
        private String mMessage;
        public ActionError(ErrorCode error, String msg){
            mError = error;
            mMessage = msg;
        }
        public ErrorCode getError() {
            return mError;
        }
        public void setError(ErrorCode error) {
            this.mError = error;
        }
        public String getMessage() {
            return mMessage;
        }
        public void setMessage(String message) {
            this.mMessage = message;
        }
    }

    public static class ActionResult<T>{
        private ActionError mError;
        private T mObject;

        public ActionResult(T object){
            mObject = object;
        }
        public ActionResult(ActionError error){
            mError = error;
        }

        public ActionError getError() {
            return mError;
        }
        public boolean hasError() {
            return mError != null;
        }
        private T getObject(){
            return mObject;
        }
    }

    public static interface UICallBack<T>{
        public void onSuccess(T result);
        public void onFailure(ActionError error);
    }
    
    public static abstract class BackgroundCallBack<T>{
        public abstract void onSuccess(T result);
        public void onFailure(ActionError error){}
    }
    
    public static interface IBackgroundProcessor<T> {
    	public ActionResult<T> doInBackground();
    }

    public class NetworkBackgroundProcessor implements IBackgroundProcessor<Result>{
        private ActionResult<Result> mResult;
		public ActionResult<Result> doInBackground() {
	        String response = null;
	        try{
	            JSONObject jsonReq = createJSONRequest();
                mOriginalRequest = jsonReq;
	            Log.d(tag, "Sending JSON request to " + getUrl() + "\n" + jsonReq.toString(4));
//                if("URL_REGISTER".equals(mServiceId)){
//                    return mResult;
//                }
	            SyncHttpClient httpClient = new SyncHttpClient();
                final HttpEntity entity = new StringEntity(jsonReq.toString(), AsyncHttpResponseHandler.DEFAULT_CHARSET);
                httpClient.post(mAppContext, getUrl(), entity, RequestParams.APPLICATION_JSON, new JsonHttpResponseHandler(){
	    			@Override
	    			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
	    				try {
                            Header[] header = getRequestHeaders();
                            if(header != null)
                            for(int i=0; i<header.length; i++){
                                Log.d(tag, header[i].getName() + ": " +header[i].getValue());
                            }
                            mOriginalResponse = response;
	    	                Log.d(tag, "Received JSON response : " + response.toString(4));
		    				super.onSuccess(statusCode, headers, response);
                            mResult = parseJSONResponse (response);
						} catch (Exception e) {
							Log.e(tag, "Failed to process response message.", e);
			            	mResult = new ActionResult<Result>(new ActionError(ErrorCode.INVALID_RESPONSE, e.getMessage()));
						} 
	    			}
	    			public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        mOriginalResponse = response;
	    				super.onSuccess(statusCode, headers, response);
	    			}
	    			public void onSuccess(int statusCode, Header[] headers, String response) {
                        mOriginalResponse = response;
	    				super.onSuccess(statusCode, headers, response);
	    			}
	    			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                        mOriginalResponse = errorResponse;
                        mOriginalError = throwable;
	    				onFailure(statusCode, headers, errorResponse == null ? "" : errorResponse.toString(), throwable);
	    			}
	    			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse){
                        mOriginalResponse = errorResponse;
                        mOriginalError = throwable;
	    				onFailure(statusCode, headers, errorResponse == null? "" : errorResponse.toString(), throwable);
	    			}
	    			@Override
	    			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        mOriginalResponse = responseString;
                        mOriginalError = throwable;
	    				super.onFailure(statusCode, headers, responseString, throwable);
    	                Log.e(tag, "Received Error response : StatusCode: " + statusCode + ", Received response : " + responseString, throwable);
	    				try {
	    					if(statusCode >= HttpStatus.SC_BAD_REQUEST &&
		    						statusCode < HttpStatus.SC_INTERNAL_SERVER_ERROR){
		    					mResult = new ActionResult<Result>(new ActionError(ErrorCode.INVALID_REQUEST, responseString));
				            } else if(statusCode >= HttpStatus.SC_INTERNAL_SERVER_ERROR){
				            	mResult = new ActionResult<Result>(new ActionError(ErrorCode.SERVER_ERROR, responseString));
				            } else{
				            	mResult = new ActionResult<Result>(new ActionError(ErrorCode.NETWORK_ERROR, mAppContext.getString(R.string.network_error)));
				            }
						} catch (Exception e) {
							Log.e(tag, "Failed to process response message.", e);
			            	mResult = new ActionResult<Result>(new ActionError(ErrorCode.INVALID_RESPONSE, mAppContext.getString(R.string.unknown_error)));
						} 
	    			}

                    protected Object parseResponse(byte[] responseBody) throws JSONException {
                        if (null == responseBody)
                            return null;
                        Object result = null;
                        //trim the string to prevent start with blank, and test if the string is valid JSON, because the parser don't do this :(. If JSON is not valid this will return null
                        String jsonString = getResponseString(responseBody, getCharset());
                        if (jsonString != null) {
                            jsonString = jsonString.trim();
                            if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
                                result = new JSONTokener(jsonString).nextValue();
                            }
                            mGsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
                        }
                        if (result == null) {
                            result = jsonString;
                        }
                        return result;
                    }
	    		});
	        } catch(Exception e) {
	            Log.e(tag, "Failed to process action : " + mServiceId + "\n" + response, e);
                mOriginalError = e;
	            mResult = new ActionResult<Result>(new ActionError(ErrorCode.NETWORK_ERROR, mAppContext.getString(R.string.network_error)));
	        }
	        if(mBackgroundCallBack != null){
	            if(mResult.hasError()){
	            	mBackgroundCallBack.onFailure(mResult.getError());
	            }else{
	            	mBackgroundCallBack.onSuccess(mResult.getObject());
	            }
	        }
	        return mResult;
		}
    }

    public void setBackgroundProcessor(IBackgroundProcessor<Result> processor){
    	mBackgroundProcessor = processor;
    }
    
    public AbstractAction(Context context){
    	this(context, null);
    }
    
    public AbstractAction(Context context, IBackgroundProcessor<Result> processor){
        mAppContext = context;
        if(processor != null)
        	mBackgroundProcessor = processor;
    }

    protected ActionResult<Result> doInBackground(Void...  params){
    	return mBackgroundProcessor.doInBackground();
    }

    protected final void onPostExecute(ActionResult<Result> result) {
        if(mCancelled){
            Log.i(tag, "Action has been cancelled: " + mServiceId);
            return;
        }
        if(mUICallback != null){
            if(result.hasError()){
            	mUICallback.onFailure(result.getError());
            }else{
            	mUICallback.onSuccess(result.getObject());
            }
        }
    }

    public void execute(){
    	execute(null, null);
    }
    
    public void execute(BackgroundCallBack<Result> backgroundCallBack){
    	execute(backgroundCallBack, null);
    }
    
    public void execute(UICallBack<Result> uiCallback){
    	execute(null, uiCallback);
    }

    @SuppressLint("NewApi")
	public void execute(BackgroundCallBack<Result> backgroundCallBack, UICallBack<Result> uiCallback){
    	executeOnExecutor(backgroundCallBack, uiCallback, null);
    }
    
    public void executeOnExecutor(BackgroundCallBack<Result> backgroundCallBack, Executor executor){
    	executeOnExecutor(backgroundCallBack, null, executor);
    }
    
    public void executeOnExecutor(UICallBack<Result> uiCallback, Executor executor){
    	executeOnExecutor(null, uiCallback, executor);
    }

    @SuppressLint("NewApi")
	public void executeOnExecutor(BackgroundCallBack<Result> backgroundCallBack, UICallBack<Result> uiCallback, Executor executor){
        mUICallback = uiCallback;
        mBackgroundCallBack = backgroundCallBack;
        if(executor != null){
        	super.executeOnExecutor(executor);
        }else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            super.execute();
        } else {
        	super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private ActionResult parseJSONResponse(JSONObject response){
        if(response == null){
            ActionError error = new ActionError(ErrorCode.SERVER_ERROR, "服务器未返回结果");
            return new ActionResult(error);
        }
        if(!response.has("response")){
            ActionError error = new ActionError(ErrorCode.SERVER_ERROR, "服务器返回空，请稍候重试");
            return new ActionResult(error);
        }
        try {
            response = response.getJSONObject("response");
            if(!response.has("flag")){
                ActionError error = new ActionError(ErrorCode.SERVER_ERROR, "服务器未返回响应代码");
                return new ActionResult(error);
            }
            int flag = response.getInt("flag");
            if(flag != 1){
                String errorMessage = "请求失败";
                if(response.has("msg")){
                    errorMessage = response.getString("msg");
                }
                ActionError error = new ActionError(ErrorCode.APPLICATOIN_ERROR, errorMessage);
                return new ActionResult(error);
            }
            if(!response.has("body")){
                ActionError error = new ActionError(ErrorCode.APPLICATOIN_ERROR, "服务器未返回结果内容");
                return new ActionResult(error);
            }
            JSONObject body = response.getJSONObject("body");
            String key = null;
//            if(body.has(KEY_KEY))
//                key = body.getString(KEY_KEY);
            if(mSecurity && response.has("signature") && response.has("timestr")){
                String signature = response.getString("signature");
                String timestr = response.getString("timestr");
                String bodyStr = mGsonObject.get("response").getAsJsonObject().get("body").toString();
                if(key == null)
                    key = PreferenceUtil.getString(mAppContext, KEY_KEY, DEFAULT_KEY);
                String clientSignature = Utils.md5(flag+bodyStr+timestr+key);
                if(!clientSignature.equals(signature)){
                    Log.e(tag, "数据校验未通过！！！");
                    ActionError error = new ActionError(ErrorCode.SECURITY_ERROR, "数据校验未通过！！！");
                    return new ActionResult(error);
                }
            }
            return new ActionResult(createRespObject(body));
        } catch (JSONException e) {
            Log.e(tag, "Failed to parse Json response for request: " + mServiceId, e);
            ActionError error = new ActionError(ErrorCode.SERVER_ERROR, "服务器数据格式不对： ");
            return new ActionResult(error);
        }
    }

    public void setSecurity(boolean security){
        this.mSecurity = security;
    }


    /**
     * Cancel the action, and will not call the callback.
     */
    public void cancel(){
        Log.i(tag, "Cancelling action: " + mServiceId);
        mCancelled = true;
        cancel(true);
    }

    private JSONObject createJSONRequest() throws JSONException {
        JSONObject request = new JSONObject();
        String timeStr = Utils.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
        JSONObject requestBody = new JSONObject();
        addRequestParameters(requestBody, timeStr);
        request.putOpt("body", requestBody);
        request.put("timestr", timeStr);
        String key = PreferenceUtil.getString(mAppContext, KEY_KEY, DEFAULT_KEY);
        String hash = requestBody.toString() + timeStr + key;
        String signature = Utils.md5(hash);
        request.put("signature", signature);

        JSONObject wrapper = new JSONObject();
        wrapper.put("request", request);
        return wrapper;
    }

    public String getUrl(){ return mURL; }
    public void setUrl(String url) { this.mURL = url;}

    protected abstract void addRequestParameters(JSONObject params, String timeStr) throws JSONException;

    protected abstract Result createRespObject(JSONObject response) throws JSONException;
}
