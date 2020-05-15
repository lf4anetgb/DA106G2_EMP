package com.example.da106g2_emp.tools;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Util {

    //連線對象
//    public final static String URL = "http://10.0.2.2:8081/DA106G2/"; // 模擬器用
    public final static String URL = "https://da106g2.tk/DA106G2_Final01/"; // 實機用

    //測試連線用
    public static boolean networkConnected(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ((connectivityManager != null) ? (connectivityManager.getActiveNetworkInfo()) : null);
        return ((networkInfo != null) && (networkInfo.isConnected()));
    }

    //吐司
    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
