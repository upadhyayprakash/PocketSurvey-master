package kunalganglani.com.suveyji;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class ViewData extends AppCompatActivity {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        final String REGISTER_URL = "http://prakashupadhyay.com/SurveyApp/process.php";
        //final String REGISTER_URL = "http://10.0.2.2:8282/PocketSurvey/process.php";

        final ProgressDialog loading;
        loading = ProgressDialog.show(this,"Loading...","Please wait...",false,false);
        final Intent intent = new Intent(ViewData.this, DataDetail.class);

        final JSONObject jSonObjData = new JSONObject();
        try{
            jSonObjData.put("action", "GetSurveyDatas");
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
                            final JSONArray volInfoArr = myjson.getJSONArray("surveyRecords");
                            int sizeVolArr = volInfoArr.length();
                            //ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
                            int vol_id=-1;
                            JSONObject volJSONObject = new JSONObject();
                            final String[] mobileArray = new String[sizeVolArr];
                            final String[] VolData = new String[7];
                            for (int i = 0; i < sizeVolArr; i++) {
                                volJSONObject = volInfoArr.getJSONObject(i);
                                String n1 = volJSONObject.get("surdata_surveyee").toString();
                                mobileArray[i] = n1;
                            }
                            ArrayAdapter adapter = new ArrayAdapter<String>(ViewData.this, R.layout.activity_listview, mobileArray);
                            //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);
                            final ListView listView = (ListView) findViewById(R.id.mobile_list);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(ViewData.this, mobileArray[position], Toast.LENGTH_SHORT).show();
                                    try{
                                        JSONObject temp = volInfoArr.getJSONObject(position);

                                        Bundle mBundle = new Bundle();
                                        mBundle.putString("name", temp.get("surdata_surveyee").toString());
                                        mBundle.putString("date", temp.get("surdata_date").toString());
                                        mBundle.putString("place", temp.get("surdata_place").toString());
                                        mBundle.putString("surdataId", temp.get("surdata_id").toString());
                                        mBundle.putString("surdataScanned", temp.get("surdata_scanned").toString());
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