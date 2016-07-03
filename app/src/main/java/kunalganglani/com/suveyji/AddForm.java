package kunalganglani.com.suveyji;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class AddForm extends AppCompatActivity {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private LinearLayout mLayout;
    private EditText mEditText, etformName;
    private Button mButton,sButton;
    public int type=0;

    private JSONArray optionsArr = new JSONArray();
    private JSONObject optionObj = new JSONObject();
    private JSONArray queryArr = new JSONArray();
    private JSONObject queryObj = new JSONObject();
    public int queryId = 1;

    JSONObject jSonObjData = new JSONObject();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);
        mLayout = (LinearLayout) findViewById(R.id.linearLayout);
        etformName = (EditText) findViewById(R.id.etformName);
        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);
        sButton = (Button) findViewById(R.id.button2);
        mButton.setOnClickListener(onClick());
        sButton.setOnClickListener(onClick2());
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Edit Text", "Text Area", "Radio Buttons", "Checkboxes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                switch (position) {
                    case 0:
                        type=0;
                        break;
                    case 1:
                        type=1;
                        break;
                    case 2:
                        type=2;
                        break;
                    case 3:
                        type=3;
                        break;

                }
                //Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });

        TextView textView = new TextView(this);
        textView.setText("Q> ");
    }

    private View.OnClickListener onClick2() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                final String REGISTER_URL = "http://prakashupadhyay.com/SurveyApp/process.php";
                //final String REGISTER_URL = "http://10.0.2.2:8282/PocketSurvey/process.php";

                final ProgressDialog loading;
                loading = ProgressDialog.show(AddForm.this,"Creating New Form...","Please wait...",false,false);

                int n = 4;
                JSONObject surFormObj1 = new JSONObject();
                try
                {
                    surFormObj1.put("title", etformName.getText().toString());
                    surFormObj1.put("themePic", "<image_data>");
                    surFormObj1.put("volId","1");
                    surFormObj1.put("queries", queryArr.toString());
                    jSonObjData.put("action", "CreateSurveyForm");
                    jSonObjData.put("data", surFormObj1.toString());
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

                RequestQueue requestQueue = Volley.newRequestQueue(AddForm.this);
                requestQueue.add(jsonObjReq);
//
            }
        };
    }
    private View.OnClickListener onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int m = 5;

                try
                {

                    switch(type){
                        case 0:
                            mLayout.addView(createNewEditText(mEditText.getText().toString()));

                            optionObj = new JSONObject();
                            optionObj.put("optId", 1);
                            optionObj.put("value", mEditText.getText().toString());
                            optionObj.put("type", "editBox");
                            optionsArr.put(optionObj);

                            queryObj = new JSONObject();
                            queryObj.put("qryId", queryId);
                            queryObj.put("title", mEditText.getText().toString());
                            queryObj.put("options", optionsArr.toString());
                            queryArr.put(queryObj);

                            optionsArr = new JSONArray();
                            queryId++;

                            mEditText.setText("");
                            break;
                        case 1:
                            mLayout.addView(createNewEditTextArea(mEditText.getText().toString()));
                            //mLayout.addView(createNewTextView(mEditText.getText().toString()));

                            optionObj = new JSONObject();
                            optionObj.put("optId", 1);
                            optionObj.put("value", mEditText.getText().toString());
                            optionObj.put("type", "textArea");
                            optionsArr.put(optionObj);

                            queryObj = new JSONObject();
                            queryObj.put("qryId", queryId);
                            queryObj.put("title", mEditText.getText().toString());
                            queryObj.put("options", optionsArr.toString());
                            queryArr.put(queryObj);

                            optionsArr = new JSONArray();
                            queryId++;

                            mEditText.setText("");
                            break;
                        case 2:
                            mLayout.addView(createNewRadioGroup(mEditText.getText().toString()));

                            optionObj = new JSONObject();
                            optionObj.put("optId", 1);
                            optionObj.put("value", "Yes");
                            optionObj.put("type", "radioButton");
                            optionsArr.put(optionObj);

                            optionObj = new JSONObject();
                            optionObj.put("optId", 2);
                            optionObj.put("value", "No");
                            optionObj.put("type", "radioButton");
                            optionsArr.put(optionObj);

                            queryObj = new JSONObject();
                            queryObj.put("qryId", queryId);
                            queryObj.put("title", mEditText.getText().toString());
                            queryObj.put("options", optionsArr.toString());
                            queryArr.put(queryObj);

                            optionsArr = new JSONArray();
                            queryId++;

                            mEditText.setText("");
                            break;
                        case 3:
                            mEditText.setText("");
                            break;
                    }

                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }


            }
        };
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
        editText.setHint("" + text);
        editText.setGravity(Gravity.CENTER);
        //editText.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        return editText;
    }
    private RadioGroup createNewRadioGroup(String text) {
        mLayout.addView(createNewTextView(mEditText.getText().toString()));
        final ActionBar.LayoutParams lparams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        //final EditText editText= new EditText(this);
//        editText.setLayoutParams(lparams);
//        editText.setHint("Q: " + text);

        RadioGroup group = new RadioGroup(this);
        group.setLayoutParams(lparams);
        group.setBackgroundResource(R.drawable.edittextstyle);
        group.setOrientation(RadioGroup.HORIZONTAL);
        RadioButton btn1 = new RadioButton(this);
        btn1.setText("Yes");
        group.addView(btn1);
        RadioButton btn2 = new RadioButton(this);
        group.addView(btn2);
        btn2.setText("No");
        return group;

    }

}