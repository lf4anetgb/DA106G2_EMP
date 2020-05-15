package com.example.da106g2_emp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.da106g2_emp.staff.Staff;
import com.example.da106g2_emp.tools.CommunicationTask;
import com.example.da106g2_emp.tools.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.concurrent.ExecutionException;

public class BookingOrderInquiryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BookingOrderInquiryActivity",
            PACKAGE = "com.google.zxing.client.android";

    private EditText etInputBookingID;
    private Button btnQRcodeScanning, btnInquire;
    private CommunicationTask communicationTask;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_order_inquiry);
        etInputBookingID = findViewById(R.id.etInputBookingID);
        btnInquire = findViewById(R.id.btnInquire);
        btnQRcodeScanning = findViewById(R.id.btnQRcodeScanning);
        findViewById(R.id.btnSignOut).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!Util.networkConnected(this)) {
            Util.showToast(this, R.string.not_connected);
            return;
        }
        bundle = getIntent().getExtras();
        String bundleIn = bundle.getString("staff");
        Staff staff = new Gson().fromJson(bundleIn, Staff.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "isAccountExisted");
        jsonObject.addProperty("staff_id", staff.getStaff_id());
        jsonObject.addProperty("fun_num", "SFN0000004");

        communicationTask = new CommunicationTask(Util.URL + "Android/AuthorityServlet", jsonObject.toString());

        try {
            String jsonIn = communicationTask.execute().get();

            if (!Boolean.valueOf(jsonIn)) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        etInputBookingID.setVisibility(View.VISIBLE);
        btnInquire.setVisibility(View.VISIBLE);
        btnQRcodeScanning.setVisibility(View.VISIBLE);

        btnInquire.setOnClickListener(this);
        btnQRcodeScanning.setOnClickListener(this);
    }

    private void showFuntion() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (communicationTask != null) {
            communicationTask.cancel(true);
            communicationTask = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignOut: {
                bundle.remove("staff");
                onBackPressed();
                return;
            }

            case R.id.btnQRcodeScanning: {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                try {
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException ex) {
                    showDownloadDialog();
                }
                return;
            }

            case R.id.btnInquire: {
                if (etInputBookingID.getText() == null || etInputBookingID.getText().toString().length() <= 0) {
                    Util.showToast(this, "訂位編號錯誤");
                    return;
                }
                StringBuffer sb = new StringBuffer(Util.URL).append("Android/BookingServlet?bk_number=")
                        .append(etInputBookingID.getText().toString());
                bundle.putString("bookingURL", sb.toString());
                Intent intent = new Intent(BookingOrderInquiryActivity.this, BookingOrderDetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            }
        }
    }

    //跳出對話窗問是否要下載
    private void showDownloadDialog() {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(this);
        downloadDialog.setTitle("找不到掃描器");
        downloadDialog.setMessage("請下載並安裝掃描器！");

        //Yes按鈕
        downloadDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("market://search?q=pname:" + PACKAGE);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Log.e(ex.toString(), "沒Play商城哭哭喔");
                }
            }
        });

        //No按鈕
        downloadDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            String message = "";
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                StringBuffer sb = new StringBuffer(Util.URL).append("Android/BookingServlet?bk_number=").append(contents);
                bundle.putString("bookingURL", sb.toString());
                Intent intent_ = new Intent(BookingOrderInquiryActivity.this, BookingOrderDetailsActivity.class);
                intent_.putExtras(bundle);
                startActivity(intent_);
                return;
            } else if (resultCode == RESULT_CANCELED) {
                Util.showToast(this, "讀取錯誤");
            }
        }
        Util.showToast(this, "讀取錯誤");
    }
}