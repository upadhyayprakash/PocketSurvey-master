package kunalganglani.com.suveyji;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ViewUser extends AppCompatActivity {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    //String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        final String REGISTER_URL = "http://prakashupadhyay.com/SurveyApp/process.php";
        //final String REGISTER_URL = "http://10.0.2.2:8282/PocketSurvey/process.php";

        final ProgressDialog loading;
        loading = ProgressDialog.show(this,"Loading...","Please wait...",false,false);
        final Intent intent = new Intent(ViewUser.this, VolunteerDetail.class);

        final JSONObject jSonObjData = new JSONObject();
        try{
            jSonObjData.put("action", "GetVolunteers");
            jSonObjData.put("data", jSonObjData.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                REGISTER_URL, jSonObjData,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        System.out.println("\n\n Response from Backend:\n" + response);
                        try
                        {
                            String jsonStr = response.getString("arrRes");
                            JSONObject myjson = new JSONObject(jsonStr);
                            final JSONArray volInfoArr = myjson.getJSONArray("volunteerRecords");
                            int sizeVolArr = volInfoArr.length();
                            //ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
                            int vol_id=-1;
                            JSONObject volJSONObject = new JSONObject();
                            final String[] mobileArray = new String[sizeVolArr];
                            final String[] VolData = new String[7];
                            for (int i = 0; i < sizeVolArr; i++) {
                                volJSONObject = volInfoArr.getJSONObject(i);
                                String n1 = volJSONObject.get("vol_fname") + " " + volJSONObject.get("vol_lname");
                                mobileArray[i] = n1;
                            }
                            ArrayAdapter adapter = new ArrayAdapter<String>(ViewUser.this, R.layout.activity_listview, mobileArray);
                            //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);
                            final ListView listView = (ListView) findViewById(R.id.mobile_list);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(ViewUser.this, mobileArray[position], Toast.LENGTH_SHORT).show();
                                    try{
                                        JSONObject temp = volInfoArr.getJSONObject(position);

                                        Bundle mBundle = new Bundle();
                                        mBundle.putString("id", temp.get("vol_id").toString());
                                        mBundle.putString("uname", temp.get("vol_uname").toString());
                                        mBundle.putString("fname", temp.get("vol_fname").toString());
                                        mBundle.putString("lname", temp.get("vol_lname").toString());
                                        mBundle.putString("city", temp.get("vol_city").toString());
                                        mBundle.putString("age", temp.get("vol_age").toString());
                                        mBundle.putString("gender", temp.get("vol_gender").toString());
                                        mBundle.putString("dob", temp.get("vol_dob").toString());
                                        mBundle.putString("dor", temp.get("vol_dor").toString());
                                        mBundle.putString("gender", temp.get("vol_gender").toString());
                                        mBundle.putString("contact", temp.get("vol_contact").toString());
                                        try {
                                            byte[] decodedString = Base64.decode(temp.get("vol_photo").toString(), Base64.DEFAULT);
                                            String filename = "bitmap.png";
                                            FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                            decodedByte.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                            stream.close();
                                            decodedByte.recycle();
                                            mBundle.putString("profilePicNameStr",filename);

                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                        //mBundle.putString("photo", temp.get("vol_dor").toString());

//                                        VolData[0] = temp.get("vol_fname").toString();
//                                        VolData[1] = temp.get("vol_lname").toString();
//                                        VolData[2] = temp.get("vol_contact").toString();
//                                        VolData[3] = temp.get("vol_age").toString();
//                                        VolData[4] = temp.get("vol_gender").toString();
//                                        VolData[5] = temp.get("vol_city").toString();
//                                        VolData[6] = temp.get("registered_on").toString();

                                        intent.putExtras(mBundle);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }



                        //startActivity(intent);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                // System.out.println("\n\n Response from Backend:\n" + error.toString());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }






}