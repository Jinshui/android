package com.creal.nest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.creal.nest.R;
import com.creal.nest.views.HeaderView;

public class RegisterActivity extends Activity {

    private EditText mPhone;
    private EditText mVerificationCode;
    private EditText mPassword;
    private EditText mUserName;
    private EditText mUserID;
    private EditText mDetailAddress;
    private Spinner mProvince;
    private Spinner mCity;
    private CheckBox mCheckBoxAcceptTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.register_title);
        headerView.setTitleLeft();

        mProvince = (Spinner) findViewById(R.id.id_spinner_provinces);
        ArrayAdapter<CharSequence> provinceAdapter = ArrayAdapter.createFromResource( this, R.array.provinces, android.R.layout.simple_spinner_item);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProvince.setAdapter(provinceAdapter);
        mProvince.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        showToast("Spinner1: position=" + position + " id=" + id);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showToast("Spinner1: unselected");
                    }
                });
        mCity = (Spinner) findViewById(R.id.id_spinner_cities);
        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource( this, R.array.cities, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCity.setAdapter(cityAdapter);
        mCity.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        showToast("Spinner1: position=" + position + " id=" + id);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showToast("Spinner1: unselected");
                    }
                });

        mPhone = (EditText)findViewById(R.id.id_txt_phone);
        mVerificationCode = (EditText)findViewById(R.id.id_txt_verification_code);
        mPassword = (EditText)findViewById(R.id.id_txt_password);
        mUserName = (EditText)findViewById(R.id.id_txt_user_name);
        mUserID = (EditText)findViewById(R.id.id_txt_user_id);
        mDetailAddress = (EditText)findViewById(R.id.id_txt_detail_addr);
        mCheckBoxAcceptTerms = (CheckBox)findViewById(R.id.id_checkbox_accept_terms);
    }

    public void onGetVerificationCodeClick(View view){
//        Intent intent = new Intent(this, Spinner1.class);
//        startActivity(intent);
//        finish();
    }
    public void onRegisterClick(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onViewTermsClick(View view){

    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
