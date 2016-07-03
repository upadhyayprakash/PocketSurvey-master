package kunalganglani.com.suveyji;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

public class DataDetail extends AppCompatActivity {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);

        mLayout = (LinearLayout) findViewById(R.id.linearLayout); // In this we'll append all our Data dynamically.

        Intent in = getIntent();
        Bundle b = in.getExtras();
        String name3 = b.getString("name");
        int surdataId = -1;
        try
        {
            surdataId = Integer.parseInt(b.getString("surdataId"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        String surdataScanned = b.getString("surdataScanned");

        final String REGISTER_URL = "http://prakashupadhyay.com/SurveyApp/process.php";

//        final String REGISTER_URL = "http://10.0.2.2:8282/PocketSurvey/process.php";

        final ProgressDialog loading;
        loading = ProgressDialog.show(this,"Loading...","Please wait...",false,false);

        final JSONObject jSonObjData = new JSONObject();
        try{
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("surdataId", surdataId);
            jsonobject.put("scannedFlag", surdataScanned);
            jSonObjData.put("action", "GetSurveyDataDetailsById");
            jSonObjData.put("data", jsonobject.toString());

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                REGISTER_URL, jSonObjData,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        //msgResponse.setText(response.toString());
                        loading.dismiss();
                        System.out.println("\n\n Response from Backend:\n" + response.toString());
                        try
                        {
                            String jsonStr = response.getString("arrRes");
                            JSONObject myjson = new JSONObject(jsonStr);
                            final JSONArray surdataInfoArr = myjson.getJSONArray("surdataDetails");
                            int sizeSurdataArr = surdataInfoArr.length();

                            JSONObject surformJSONObject = new JSONObject();
                            JSONObject questionJSONObject = new JSONObject();
                            JSONObject optionJSONObject = new JSONObject();

                            for (int i = 0; i < sizeSurdataArr; i++)
                            {
                                surformJSONObject = surdataInfoArr.getJSONObject(i);
                                //mLayout.addView(createNewEditText(surformJSONObject.getString("surdata_surveyee")));
                                mLayout.addView(createNewTextView("Survey Form Title:"));
                                mLayout.addView(createNewEditText(surformJSONObject.getString("surform_title")));
                                //mLayout.addView(createNewEditText(surformJSONObject.getString("surdata_place")));

                                //JSONObject abc = new JSONObject(surformJSONObject.getString("questionArray"));
                                final JSONArray questionArr = surformJSONObject.getJSONArray("questionArray");
                                int sizeQArr = questionArr.length();
                                for (int j = 0; j < sizeQArr; j++)
                                {
                                    questionJSONObject = questionArr.getJSONObject(j);
                                    //mLayout.addView(createNewEditText(questionJSONObject.getString("query_id")));
                                    if(questionJSONObject.getString("query_title").endsWith( ":" )) {
                                        mLayout.addView(createNewTextView(questionJSONObject.getString("query_title")));
                                    }
                                    else
                                    mLayout.addView(createNewEditText(questionJSONObject.getString("query_title")));

                                    final JSONArray optionArr = questionJSONObject.getJSONArray("optionArray");
                                    int sizeOptionArr = optionArr.length();
                                    for (int k = 0; k < sizeOptionArr; k++) {
                                        optionJSONObject = optionArr.getJSONObject(k);
                                        //mLayout.addView(createNewEditText(optionJSONObject.getString("option_id")));
                                        //str.substring(0,str.length()-1);
//                                        optionJSONObject.getString("option_response").substring(0,str.length()-1)
                                        String str=optionJSONObject.getString("option_response");
                                        //str.substring(0,str.length()-1);
                                        mLayout.addView(createNewEditText(str));
                                        //mLayout.addView(createNewEditText(optionJSONObject.getString("option_type")));
                                    }

                                }
                                mLayout.addView(createNewTextView("Date:"));
                                mLayout.addView(createNewEditText(surformJSONObject.getString("surdata_date")));
                                mLayout.addView(createNewTextView("Survey Taken By:"));
                                mLayout.addView(createNewEditText(surformJSONObject.getString("vol_fname")+" "+surformJSONObject.getString("vol_lname")));



                            }

                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }

                        /*
                        mLayout.addView(createNewTextView("Surveyee Name"));
                        mLayout.addView(createNewEditText("Samay"));

                        String arrRadioOptions[] = {"Male", "Female"};
                        mLayout.addView(createNewRadioGroup("Gender", arrRadioOptions));

                        mLayout.addView(createNewEditTextArea("Address"));
                        */

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                loading.dismiss();
                System.out.println("\n\n Response from Backend:\n" + error.toString());
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


//        String lname = b.getString("lname");
//        String age = b.getString("age");
//        String contact = b.getString("contact");
//        String gender = b.getString("gender");
//        String city = b.getString("city");
//        String reg = b.getString("reg");

        //TextView textView10 = (TextView)findViewById(R.id.Nameval);
//        TextView textView11 = (TextView)findViewById(R.id.ageval);
//        TextView textView12 = (TextView)findViewById(R.id.genderval);
//        TextView textView13 = (TextView)findViewById(R.id.contactnumberval);
//        TextView textView14 = (TextView)findViewById(R.id.cityval);
//        TextView textView15 = (TextView)findViewById(R.id.registeredonval);


        //textView10.setText(name3);

    }

    private TextView createNewTextView(String text) {
        final ActionBar.LayoutParams lparams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        // lparams.setMargins(0,20,0,0);
        textView.setHint("" + text);
        return textView;
    }
    private EditText createNewEditTextArea(String text) {
//        android:ellipsize="start"

        final ActionBar.LayoutParams lparams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        final EditText editText= new EditText(this);
        editText.setLayoutParams(lparams);
        editText.setBackgroundResource(R.drawable.edittextstyle);
        editText.setHint("\n " + text);
        editText.setGravity(Gravity.CENTER);
        //editText.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        editText.setLines(5);
        editText.setHorizontallyScrolling(false);
        editText.setSingleLine(false);
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

        editText.setGravity(1);
        return editText;
    }
    private EditText createNewEditText(String text) {
        final ActionBar.LayoutParams lparams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        //lparams.setMargins(0,20,0,0);
        final EditText editText= new EditText(this);
        editText.setBackgroundResource(R.drawable.edittextstyle);
        editText.setLayoutParams(lparams);
        editText.setFocusable(false);
        editText.setHint("" + text);
        editText.setGravity(Gravity.CENTER);
        //editText.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        return editText;
    }
    private RadioGroup createNewRadioGroup(String text, String arr[]) {
        mLayout.addView(createNewTextView(text));
        final ActionBar.LayoutParams lparams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        //final EditText editText= new EditText(this);
//        editText.setLayoutParams(lparams);
//        editText.setHint("Q: " + text);

        RadioGroup group = new RadioGroup(this);
        group.setLayoutParams(lparams);
        group.setBackgroundResource(R.drawable.edittextstyle);
        group.setOrientation(RadioGroup.HORIZONTAL);

        RadioButton btn1;
        for(int i=0;i<arr.length;i++)
        {
            btn1 = new RadioButton(this);
            btn1.setText(arr[i]);
            group.addView(btn1);
        }

        return group;

    }
}