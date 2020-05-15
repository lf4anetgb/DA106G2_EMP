package com.example.da106g2_emp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.da106g2_emp.staff.Staff;
import com.example.da106g2_emp.tools.CommunicationTask;
import com.example.da106g2_emp.tools.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText etAccount, etPassword;
    private TextView tvLogInMessage;
    private CommunicationTask communicationTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etAccount = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
        tvLogInMessage = findViewById(R.id.tvLogInMessage);

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString(), password = etPassword.getText().toString();
                Staff staff = isStaff(account, password);
                if (account.length() <= 0 || password.length() <= 0 || staff == null) {
                    tvLogInMessage.setText(R.string.err_account_password);
                    return;
                }

                Intent intent = new Intent(MainActivity.this, BookingOrderInquiryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("staff", new Gson().toJson(staff));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.imgvLoginMagic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAccount.setText("a002");
                etPassword.setText("123456");
            }
        });
    }

    private Staff isStaff(String account, String password) {
        if (!Util.networkConnected(this)) {
            tvLogInMessage.setText("目前沒有連線");
            return null;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getLogin");
        jsonObject.addProperty("sf_account", account);
        jsonObject.addProperty("sf_password", password);
        communicationTask = new CommunicationTask(Util.URL + "Android/StaffServlet", jsonObject.toString());

        try {
            String result = communicationTask.execute().get();
            return new Gson().fromJson(result, Staff.class);

        } catch (Exception e) {
            Log.d(TAG, "倒大楣啦！錯誤：" + e.toString());
            return null;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (communicationTask != null) {
            communicationTask.cancel(true);
            communicationTask = null;
        }
        etAccount.setText("");
        etPassword.setText("");
    }
}
