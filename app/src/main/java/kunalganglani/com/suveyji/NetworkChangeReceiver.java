package kunalganglani.com.suveyji;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;

import org.json.JSONObject;
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

//        final android.net.NetworkInfo wifi = connMgr
//                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        final android.net.NetworkInfo mobile = connMgr
//                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if (wifi.isAvailable() || mobile.isAvailable()) {
//            Log.d("NetworkAvailable ", "Connected");
//        }
//        else{
//            Log.d("NetworkAvailable ", "Disconnected");
//        }
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        boolean state =  (netInfo != null && netInfo.isConnected());
        String res = null;
        if (!state) {
            Log.d("NetworkAvailable ", "DisConnected");
            String URL = "content://com.example.provider.College/students";
            Uri students = Uri.parse(URL);
            Cursor c = context.getContentResolver().query(students, null, null, null, "name");
            if(c != null) {
                if (c.moveToFirst()) {
                    res = c.getString(1);
                }
                c.close();
                try {
                    JSONObject survey = new JSONObject(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            Log.d("NetworkAvailable ", "connected");
        }
    }
}