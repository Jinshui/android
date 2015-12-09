package com.shangjia;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.shangjia.service.TemporyLoginService;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class ChangePasswordFragment extends Fragment {

    TextView oldPassword ;
    TextView newPassword;
    TextView newPasswordAgain;
    Button mSubmitChange;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_changepassword, container, false);

        oldPassword = (TextView) view.findViewById(R.id.id_old_password);
        newPassword = (TextView) view.findViewById(R.id.id_new_password);
        newPasswordAgain = (TextView) view.findViewById(R.id.id_new_password_again);
        mSubmitChange = (Button)view.findViewById(R.id.submit_changepassword_button);

        mSubmitChange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                attemptChangePassword();
            }
        });
        return view;
    }

    private void attemptChangePassword() {

        // Reset errors.
        oldPassword.setError(null);
        newPassword.setError(null);
        newPasswordAgain.setError(null);

        // Store values at the time of the login attempt.
        String oldP = oldPassword.getText().toString();
        String newP = newPassword.getText().toString();
        String doubleNewP = newPasswordAgain.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(oldP)) {
            //TODO: verify old passWord.
            oldPassword.setError(getString(R.string.error_invalid_password));
            focusView = oldPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(newP)) {
            //TODO: valid the new passWord format.
            newPassword.setError(getString(R.string.error_null_new_password));
            focusView = newPassword;
            cancel = true;
        }else{
            if (TextUtils.isEmpty(doubleNewP)) {
                //TODO: verify old passWord.
                newPasswordAgain.setError(getString(R.string.error_null_new_password));
                focusView = newPasswordAgain;
                cancel = true;
            }else{
                if( !newP.equals(doubleNewP)){
                    newPasswordAgain.setError(getString(R.string.error_invalid_double_password));
                    focusView = newPasswordAgain;
                    cancel = true;
                }
            }
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            //TODO: change the real password;
            TemporyLoginService.changePassword();
            AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordFragment.this.getContext());
            builder.setTitle("修改密码成功!");
            builder.setMessage("成功修改密码!");
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            oldPassword.clearComposingText();
                            newPassword.clearComposingText();
                            newPasswordAgain.clearComposingText();
                        }
                    });
            Dialog dialog = builder.create();
            dialog.show();

        }
    }



}

