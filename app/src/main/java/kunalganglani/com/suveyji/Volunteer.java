package kunalganglani.com.suveyji;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class Volunteer extends AppCompatActivity implements View.OnClickListener {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        Button bCampaign1,bCampaign2,bCampaign3;

        mLayout = (LinearLayout) findViewById(R.id.linearLayout); // Container for Dynamic Buttons

        bCampaign1= (Button) findViewById(R.id.bCampaign1);
        //bCampaign2= (Button) findViewById(R.id.bCampaign2);
        bCampaign3= (Button) findViewById(R.id.bCampaign3);
        bCampaign1.setOnClickListener(this);
        //bCampaign2.setOnClickListener(this);
        bCampaign3.setOnClickListener(this);
        try
        {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("volInfoStr"));
            TextView textView = (TextView)findViewById(R.id.editText);
            textView.setText("Hi, "+jsonObject.get("vol_fname").toString()+" !");
            /*
            byte[] decodedString = Base64.decode(jsonObject.get("vol_photo").toString(),Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


            ImageView profileView = (ImageView)findViewById(R.id.imageViewVolPic);

            profileView.setImageBitmap(decodedByte);
            */

            String filename = getIntent().getStringExtra("profilePicNameStr");
            Bitmap bmp = null;
            try {
                FileInputStream is = this.openFileInput(filename);
                bmp = BitmapFactory.decodeStream(is);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ImageView profileView = (ImageView)findViewById(R.id.imageViewVolPic);

            profileView.setImageBitmap(bmp);



        }
        catch (Exception e) {
            e.printStackTrace();
        }

        final String REGISTER_URL = "http://prakashupadhyay.com/SurveyApp/process.php";

//        final String REGISTER_URL = "http://10.0.2.2:8282/PocketSurvey/process.php";

        final Intent intent = new Intent(Volunteer.this, SurformDetails.class);

        final JSONObject jSonObjData = new JSONObject();
        try{
            jSonObjData.put("action", "GetSurveyForms");
            jSonObjData.put("data", jSonObjData.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                REGISTER_URL, jSonObjData,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //loading.dismiss();
                        System.out.println("\n\n Response from Backend:\n" + response);
                        try
                        {
                            String jsonStr = response.getString("arrRes");
                            JSONObject myjson = new JSONObject(jsonStr);
                            final JSONArray formInfoArr = myjson.getJSONArray("surveyForms");
                            int sizeFormArr = formInfoArr.length();
                            //ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
                            int vol_id=-1;
                            JSONObject formJSONObject = new JSONObject();
                            final String[] mobileArray = new String[sizeFormArr];
                            final String[] VolData = new String[7];
                            for (int i = 0; i < sizeFormArr; i++) {
                                formJSONObject = formInfoArr.getJSONObject(i);
                                String n1 = formJSONObject.get("surform_title").toString();
                                mobileArray[i] = n1;
                            }
                            ArrayAdapter adapter = new ArrayAdapter<String>(Volunteer.this, R.layout.activity_listview, mobileArray);
                            //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);
                            final ListView listView = (ListView) findViewById(R.id.mobile_list);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(Volunteer.this, mobileArray[position], Toast.LENGTH_SHORT).show();
                                    try{
                                        JSONObject temp = formInfoArr.getJSONObject(position);

                                        Bundle mBundle = new Bundle();
                                        mBundle.putString("id", temp.get("surform_id").toString());

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
                //loading.dismiss();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bCampaign1:
                startActivity(new Intent(this,Campaign1.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

           //// case R.id.bCampaign2:
//                startActivity(new Intent(this,Campaign1.class));
                //break;

            case R.id.bCampaign3:
//                startActivity(new Intent(this,Campaign1.class));
                break;

            // case R.id.tvRegisterLink:
            //   startActivity(new Intent(this,Register.class));
            // break;

        }
    }
}