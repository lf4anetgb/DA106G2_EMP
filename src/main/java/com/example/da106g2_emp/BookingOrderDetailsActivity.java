package com.example.da106g2_emp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.da106g2_emp.booking.Booking;
import com.example.da106g2_emp.booking.BookingDetial;
import com.example.da106g2_emp.member.Member;
import com.example.da106g2_emp.tools.CommunicationTask;
import com.example.da106g2_emp.tools.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookingOrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BookingOrderInquiryActivity";
    private TextView tvShowBookingID, tvShowMemberID, tvShowStartTime, tvShowEndTime, tvCampsiteID;
    private CheckBox cbCheckIn, cbCheckOut;
    private CommunicationTask getBookingTask, getMemberNameTask, getCampsiteIDTask;

    private Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_order_details);
        tvShowBookingID = findViewById(R.id.tvShowBookingID);
        tvShowMemberID = findViewById(R.id.tvShowMemberID);
        tvShowStartTime = findViewById(R.id.tvShowStartTime);
        tvShowEndTime = findViewById(R.id.tvShowEndTime);
        tvCampsiteID = findViewById(R.id.tvCampsiteID);
        cbCheckIn = findViewById(R.id.cbCheckIn);
        cbCheckOut = findViewById(R.id.cbCheckOut);
        findViewById(R.id.btnSubmit).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle;
        bundle = getIntent().getExtras();
        String bundleIn = bundle.getString("bookingURL");
        getBookingTask = new CommunicationTask(bundleIn, "");
        getBookingTask.setRequstMethod("GET");
        String strIn = "";
        try {
            strIn = getBookingTask.execute().get();
        } catch (Exception e) {
            Util.showToast(this, "讀取錯誤");
            onBackPressed();
            return;
        }

        if ("false".equals(strIn)) {
            Util.showToast(this, "訂位編號錯誤");
            onBackPressed();
            return;
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        booking = gson.fromJson(strIn, Booking.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getMember");
        jsonObject.addProperty("member_id", booking.getMember_id());

        getMemberNameTask = new CommunicationTask(Util.URL + "Android/MemberServlet", jsonObject.toString());
        getMemberNameTask.execute();

        jsonObject = new JsonObject();
        jsonObject.addProperty("action", "findByBknumber");
        jsonObject.addProperty("bk_number", booking.getBk_number());

        getCampsiteIDTask = new CommunicationTask(Util.URL + "Android/BookingDetialServlet", jsonObject.toString());
        getCampsiteIDTask.execute();

        Member member = null;
        List<BookingDetial> bookingDetials = null;
        try {
            String memberIn = getMemberNameTask.get();
            String bookingDetialIn = getCampsiteIDTask.get();

            member = gson.fromJson(memberIn, Member.class);

            Type type = new TypeToken<List<BookingDetial>>() {
            }.getType();
            bookingDetials = gson.fromJson(bookingDetialIn, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        tvShowBookingID.setText(booking.getBk_number());
        tvShowMemberID.setText(member.getName());
        tvShowStartTime.setText(booking.getBk_start().toString());
        tvShowEndTime.setText(booking.getBk_end().toString());

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bookingDetials.size(); i++) {
            sb.append(bookingDetials.get(i).getCs_number()).append("\n");
        }
        tvCampsiteID.setText(sb.toString());

        //設定checkBox狀態
        cbCheckIn.setEnabled(booking.getBk_status() == 2);
        cbCheckIn.setChecked(booking.getBk_status() >= 4);
        cbCheckOut.setEnabled(booking.getBk_status() == 4);
        cbCheckOut.setChecked(booking.getBk_status() == 5);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.btnSubmit) return;

        int status = 0;
        if (cbCheckIn.isChecked()) status = 4;
        if (cbCheckOut.isChecked()) status = 5;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "updateBooking");
        jsonObject.addProperty("bk_status", String.valueOf(status));
        jsonObject.addProperty("bk_number", booking.getBk_number());

        getBookingTask = new CommunicationTask(Util.URL + "Android/BookingServlet", jsonObject.toString());

        boolean respond = false;
        try {
            respond = "true".equals(getBookingTask.execute().get());
            Util.showToast(this, respond ? "更改成功" : "更改失敗");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (respond) {
            onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getBookingTask != null) {
            getBookingTask.cancel(true);
            getBookingTask = null;
        }
        if (getMemberNameTask != null) {
            getMemberNameTask.cancel(true);
            getMemberNameTask = null;
        }
        if (getCampsiteIDTask != null) {
            getCampsiteIDTask.cancel(true);
            getCampsiteIDTask = null;
        }
    }

}
