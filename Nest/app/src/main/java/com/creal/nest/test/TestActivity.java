package com.creal.nest.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nest.R;
import com.creal.nest.actions.AbstractAction;
import com.creal.nest.actions.GetVerificationCodeAction;
import com.creal.nest.actions.LoginAction;
import com.creal.nest.views.HeaderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

public class TestActivity extends Activity {

    private Spinner mInterface;
    private TextView mReq;
    private TextView mRes;
    private EditText mUrl;
    private AbstractAction mAction;
    String[] interfaces = new String[]{"获取验证码","登录"};
    private String mSelectedInterface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        mReq = (TextView)findViewById(R.id.id_request);
        mRes = (TextView)findViewById(R.id.id_response);
        mUrl = (EditText)findViewById(R.id.id_url);
        mInterface = (Spinner) findViewById(R.id.interface_list);
        ArrayAdapter<CharSequence> interfacesAdapter = new ArrayAdapter( this, android.R.layout.simple_spinner_item, interfaces);
        interfacesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mInterface.setAdapter(interfacesAdapter);
        mInterface.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onSelect(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    onSelect(-1);
                }
            });
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void onSelect(int position){
        if(position < 0){
            mAction = null;
            mUrl.setText("");
        }
        mSelectedInterface = interfaces[position];

    }

    private void initAction(){
        switch(mSelectedInterface){
            case "获取验证码":
                mAction = new GetVerificationCodeAction(this, "9999900030", "13866668888");
                mUrl.setText(mAction.getUrl());
                break;
            case "登录":
                mAction = new LoginAction(this, "9999900030", "8000");
                mUrl.setText(mAction.getUrl());
                break;
        }
    }

    public void onSendClick(View view){
        initAction();
        mAction.setUrl(mUrl.getText().toString());
        mAction.execute(new AbstractAction.UICallBack() {
            public void onSuccess(Object result) {
                updateUI(mAction);
            }

            public void onFailure(AbstractAction.ActionError error) {
                updateUI(mAction);
            }
        });
    }

    private void updateUI(AbstractAction action){
        JSONObject req = (JSONObject)action.getOriginalRequest();
        try {
            String request = "URL: " + action.getUrl() + "\nBody:\n" + req.toString(2);
            mReq.setText(request);
        } catch (JSONException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String request = "URL: " + action.getUrl()  + "\nBody:\n" + sw.toString();
            mReq.setText(request);
        }
        Object res = action.getOriginalResponse();
        if(res instanceof JSONObject){
            try {
                JSONObject jres = (JSONObject)res;
                String response = "Type: JSONObject" + "\nBody:\n" + jres.toString(2);
                mRes.setText(response);
            } catch (JSONException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String response = "Type: JSONObject" + "\nBody:\n" + sw.toString();
                mRes.setText(response);
            }
        }else if(res instanceof JSONArray){
            try {
                JSONArray jres = (JSONArray)res;
                String response = "Type: JSONArray" + "\nBody:\n" + jres.toString(2);
                mRes.setText(response);
            } catch (JSONException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String response = "Type: JSONArray" + "\nBody:\n" + sw.toString();
                mRes.setText(response);
            }
        }else if(res instanceof String){
            String response = "Type: String" + "\nBody:\n" + res;
            mRes.setText(response);
        }
    }
}
