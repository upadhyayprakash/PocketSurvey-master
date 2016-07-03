package kunalganglani.com.suveyji;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Campaign1 extends AppCompatActivity implements View.OnClickListener {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
    TextView formtitle;
    String surveyData;
    EditText etName, etAge,etAddress,etLanguagesknown,etNoOfPeople,etNonEarning,etTotalIncome,etPlace;
    RadioGroup etGender,etMaritalstatus,etEducationalqualification,etPresentstatus,etInterest;
    Button bSubmit;

    ImageView imageViewScanned1, imageViewScanned2, imageViewScanned3;
    EditText etScannedFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign1);
        formtitle= (TextView) findViewById(R.id.formtitle);
        bSubmit= (Button) findViewById(R.id.bSubmit);
        etName= (EditText) findViewById(R.id.etName);
        etAge= (EditText) findViewById(R.id.etAge);
        etAddress= (EditText) findViewById(R.id.etAddress);
        etLanguagesknown= (EditText) findViewById(R.id.etLanguagesknown);
        etNoOfPeople= (EditText) findViewById(R.id.etNoOfPeople);
        etNonEarning= (EditText) findViewById(R.id.etNonEarning);
        etTotalIncome= (EditText) findViewById(R.id.etTotalIncome);
        etPlace= (EditText) findViewById(R.id.etPlace);
        etGender = (RadioGroup) findViewById(R.id.etGender);
        etMaritalstatus = (RadioGroup) findViewById(R.id.etMaritalstatus);
        etEducationalqualification = (RadioGroup) findViewById(R.id.etEducationalqualification);
        etPresentstatus = (RadioGroup) findViewById(R.id.etPresentstatus);
        etInterest = (RadioGroup) findViewById(R.id.etInterest);

        bSubmit.setOnClickListener(this);
        //int selectedId=etGender.getCheckedRadioButtonId();
        //RadioButton radioButton = (RadioButton) findViewById(selectedId);
        //String gender= radioButton.getText().toString();

        etScannedFlag = (EditText) findViewById(R.id.etScannedFlag);

        imageViewScanned1 = (ImageView) findViewById(R.id.imageViewScanned1);
        imageViewScanned2 = (ImageView) findViewById(R.id.imageViewScanned2);
        imageViewScanned3 = (ImageView) findViewById(R.id.imageViewScanned3);



    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    public void onClick(View v) {
        String form_title = formtitle.getText().toString();
        String name = etName.getText().toString();
        String surveyeeName = etName.getText().toString();
        String age = etAge.getText().toString();
        String address = etAddress.getText().toString();
        String languages_known= etLanguagesknown.getText().toString();
        String no_of_people= etNoOfPeople.getText().toString();
        String non_earning = etNonEarning.getText().toString();
        String total_income= etTotalIncome.getText().toString();
        String place= etPlace.getText().toString();


        int genderId= etGender.getCheckedRadioButtonId();
        RadioButton etGenderRadio = (RadioButton) findViewById(genderId);
        String gender= etGenderRadio.getText().toString();

        int maritalstatusId= etMaritalstatus.getCheckedRadioButtonId();
        RadioButton etMaritalstatusRadio = (RadioButton) findViewById(maritalstatusId);
        String marital_status= etMaritalstatusRadio.getText().toString();

        int educationId= etEducationalqualification.getCheckedRadioButtonId();
        RadioButton etEducationRadio = (RadioButton) findViewById(educationId);
        String educational_qualification= etEducationRadio.getText().toString();

        int presentstatusId= etPresentstatus.getCheckedRadioButtonId();
        RadioButton etPresentstatusRadio = (RadioButton) findViewById(presentstatusId);
        String present_status= etPresentstatusRadio.getText().toString();

        int interestId= etInterest.getCheckedRadioButtonId();
        RadioButton etInterestRadio = (RadioButton) findViewById(interestId);
        String interest= etInterestRadio.getText().toString();


        String scannedFlagStr = etScannedFlag.getText().toString();
        // Collecting the Images of Scanned Documents. Fixed No. of Images(only 3) for now.
        imageViewScanned1.setDrawingCacheEnabled(true);

        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will be null
        imageViewScanned1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        imageViewScanned1.layout(0, 0, 800, 800);

        imageViewScanned1.buildDrawingCache(true);

        String scannedData1 = getStringImage(imageViewScanned1.getDrawingCache());
        imageViewScanned1.setDrawingCacheEnabled(false); // clear drawing cache
        // ############
        imageViewScanned2.setDrawingCacheEnabled(true);

        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will be null
        imageViewScanned2.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        imageViewScanned2.layout(0, 0, 800, 800);

        imageViewScanned2.buildDrawingCache(true);

        String scannedData2 = getStringImage(imageViewScanned2.getDrawingCache());
        imageViewScanned2.setDrawingCacheEnabled(false); // clear drawing cache
        // ############
        imageViewScanned3.setDrawingCacheEnabled(true);

        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will be null
        imageViewScanned3.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        imageViewScanned3.layout(0, 0, 800, 800);

        imageViewScanned3.buildDrawingCache(true);

        String scannedData3 = getStringImage(imageViewScanned3.getDrawingCache());
        imageViewScanned3.setDrawingCacheEnabled(false); // clear drawing cache
        // ############


        final String REGISTER_URL = "http://prakashupadhyay.com/SurveyApp/process.php";
        //final String REGISTER_URL = "http://10.0.2.2:8282/PocketSurvey/process.php";


        JSONObject jSonObjData = new JSONObject();
        try {
            /* ########## Testing Create Forms/SaveFormData Service ########### */
            JSONArray queryArr = new JSONArray();
            JSONArray queryScannedArr = new JSONArray();

            JSONArray optionsArr1 = new JSONArray();

            JSONObject optionObj1 = new JSONObject();
            optionObj1.put("optId", 1);
            optionObj1.put("value", name);
            optionObj1.put("type", "textBox");
            optionsArr1.put(optionObj1);

            JSONObject queryObj1 = new JSONObject();
            queryObj1.put("qryId", 1);
            queryObj1.put("title", "Name of Student?");
            queryObj1.put("options", optionsArr1.toString());
            queryArr.put(queryObj1);

            JSONArray optionsArr2 = new JSONArray();

            JSONObject optionObj2 = new JSONObject();
            optionObj2.put("optId", 1);
            optionObj2.put("value", age);
            optionObj2.put("type", "textBox");
            optionsArr2.put(optionObj2);

            JSONObject queryObj2 = new JSONObject();
            queryObj2.put("qryId", 2);
            queryObj2.put("title", "Your Age?");
            queryObj2.put("options", optionsArr2.toString());
            queryArr.put(queryObj2);

            //-------------
            JSONArray optionsArr3 = new JSONArray();

            JSONObject optionObj3 = new JSONObject();
            optionObj3.put("optId", 1);
            optionObj3.put("value", gender);
            optionObj3.put("type", "radioButton");
            optionsArr3.put(optionObj3);

            JSONObject queryObj3 = new JSONObject();
            queryObj3.put("qryId", 3);
            queryObj3.put("title", "Select Gender?");
            queryObj3.put("options", optionsArr3.toString());
            queryArr.put(queryObj3);
            //------------
            JSONArray optionsArr4 = new JSONArray();

            JSONObject optionObj4 = new JSONObject();
            optionObj4.put("optId", 1);
            optionObj4.put("value", address);
            optionObj4.put("type", "textBox");
            optionsArr4.put(optionObj4);

            JSONObject queryObj4 = new JSONObject();
            queryObj4.put("qryId", 4);
            queryObj4.put("title", "Your Permananent Address?");
            queryObj4.put("options", optionsArr4.toString());
            queryArr.put(queryObj4);
            //-----------
            JSONArray optionsArr5 = new JSONArray();

            JSONObject optionObj5 = new JSONObject();
            optionObj5.put("optId", 1);
            optionObj5.put("value", marital_status);
            optionObj5.put("type", "radioButton");
            optionsArr5.put(optionObj5);

            JSONObject queryObj5 = new JSONObject();
            queryObj5.put("qryId", 5);
            queryObj5.put("title", "Marital Status?");
            queryObj5.put("options", optionsArr5.toString());
            queryArr.put(queryObj5);
            //-----------
            JSONArray optionsArr6 = new JSONArray();

            JSONObject optionObj6 = new JSONObject();
            optionObj6.put("optId", 1);
            optionObj6.put("value", educational_qualification);
            optionObj6.put("type", "radioButton");
            optionsArr6.put(optionObj6);

            JSONObject queryObj6 = new JSONObject();
            queryObj6.put("qryId", 6);
            queryObj6.put("title", "Educational Qualification?");
            queryObj6.put("options", optionsArr6.toString());
            queryArr.put(queryObj6);
            //------------
            JSONArray optionsArr7 = new JSONArray();

            JSONObject optionObj7 = new JSONObject();
            optionObj7.put("optId", 1);
            optionObj7.put("value", languages_known);
            optionObj7.put("type", "textBox");
            optionsArr7.put(optionObj7);

            JSONObject queryObj7 = new JSONObject();
            queryObj7.put("qryId", 7);
            queryObj7.put("title", "Languages Known?");
            queryObj7.put("options", optionsArr7.toString());
            queryArr.put(queryObj7);
            //-----------
            JSONArray optionsArr8 = new JSONArray();

            JSONObject optionObj8 = new JSONObject();
            optionObj8.put("optId", 1);
            optionObj8.put("value", present_status);
            optionObj8.put("type", "radioButton");
            optionsArr8.put(optionObj8);

            JSONObject queryObj8 = new JSONObject();
            queryObj8.put("qryId", 8);
            queryObj8.put("title", "Present Status?");
            queryObj8.put("options", optionsArr8.toString());
            queryArr.put(queryObj8);
            //-------------
            JSONArray optionsArr9 = new JSONArray();

            JSONObject optionObj9 = new JSONObject();
            optionObj9.put("optId", 1);
            optionObj9.put("value", no_of_people);
            optionObj9.put("type", "textBox");
            optionsArr9.put(optionObj9);

            JSONObject queryObj9 = new JSONObject();
            queryObj9.put("qryId", 9);
            queryObj9.put("title", "Total Number of People in Family?");
            queryObj9.put("options", optionsArr9.toString());
            queryArr.put(queryObj9);
            //-----------
            JSONArray optionsArr10 = new JSONArray();

            JSONObject optionObj10 = new JSONObject();
            optionObj10.put("optId", 1);
            optionObj10.put("value", non_earning);
            optionObj10.put("type", "textBox");
            optionsArr10.put(optionObj10);

            JSONObject queryObj10 = new JSONObject();
            queryObj10.put("qryId", 10);
            queryObj10.put("title", "Number of Non-Earning People?");
            queryObj10.put("options", optionsArr10.toString());
            queryArr.put(queryObj10);
            //-----------
            JSONArray optionsArr11 = new JSONArray();

            JSONObject optionObj11 = new JSONObject();
            optionObj11.put("optId", 1);
            optionObj11.put("value", total_income);
            optionObj11.put("type", "textBox");
            optionsArr11.put(optionObj11);

            JSONObject queryObj11 = new JSONObject();
            queryObj11.put("qryId", 11);
            queryObj11.put("title", "Total Monthly Family Income?");
            queryObj11.put("options", optionsArr11.toString());
            queryArr.put(queryObj11);
            //----------
            JSONArray optionsArr12 = new JSONArray();

            JSONObject optionObj12 = new JSONObject();
            optionObj12.put("optId", 1);
            optionObj12.put("value", interest);
            optionObj12.put("type", "radioButton");
            optionsArr12.put(optionObj12);

            JSONObject queryObj12 = new JSONObject();
            queryObj12.put("qryId", 12);
            queryObj12.put("title", "Interested in Computer Course?");
            queryObj12.put("options", optionsArr12.toString());
            queryArr.put(queryObj12);

            //----------
            JSONArray optionsArr13 = new JSONArray();

            JSONObject optionObj13 = new JSONObject();
            optionObj13.put("optId", 1);
            optionObj13.put("value", place);
            optionObj13.put("type", "radioButton");
            optionsArr13.put(optionObj13);

            JSONObject queryObj13 = new JSONObject();
            queryObj13.put("qryId", 13);
            queryObj13.put("title", "Place");
            queryObj13.put("options", optionsArr13.toString());
            queryArr.put(queryObj13);

            //JSONArray surFormObjArr = new JSONArray();

            // If ScannedFlag is Yes, then replace the queryArr content with following
            JSONObject queryScannedObj1 = new JSONObject();
            queryScannedObj1.put("scanId", 1);
            queryScannedObj1.put("scanPhoto", scannedData1);
            queryScannedArr.put(queryScannedObj1);
            //-------
            JSONObject queryScannedObj2 = new JSONObject();
            queryScannedObj2.put("scanId", 2);
            queryScannedObj2.put("scanPhoto", scannedData2);
            queryScannedArr.put(queryScannedObj2);
            //-------
            JSONObject queryScannedObj3 = new JSONObject();
            queryScannedObj3.put("scanId", 3);
            queryScannedObj3.put("scanPhoto", scannedData3);
            queryScannedArr.put(queryScannedObj3);


            String scannnedFlag = scannedFlagStr; // No or Yes

            JSONObject surFormObj1 = new JSONObject();
            surFormObj1.put("surFormId", 1);
            surFormObj1.put("surveyeeName",surveyeeName);
            surFormObj1.put("surdataScanned", scannnedFlag);
            //surFormObj1.put("surformTitle", form_title);
            surFormObj1.put("place", place);
            surFormObj1.put("volId","1");

            if(scannnedFlag.equalsIgnoreCase("No"))
                surFormObj1.put("queries", queryArr.toString());
            if(scannnedFlag.equalsIgnoreCase("Yes"))
                surFormObj1.put("queries", queryScannedArr.toString());

            // Check if device is disconnected from internet. If true, save the JSONObject in SQL
            if (!(AppStatus.getInstance(this).isOnline())) {
                Log.v("Home","You are offline !");
                surveyData = surFormObj1.toString();

                ContentValues values = new ContentValues();
                values.put(Draft.NAME, surveyData);

                Uri uri = getContentResolver().insert(Draft.CONTENT_URI, values);
                Toast.makeText(getBaseContext(),
                        "Data stored locally. Will be synced when online.", Toast.LENGTH_LONG).show();
            } else {
                Log.v("Home", "You are not online!!!!");
                jSonObjData.put("action", "SaveSurveyData");
                jSonObjData.put("data", surFormObj1.toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ProgressDialog loading;
        loading = ProgressDialog.show(this,"Saving Survey...","Please wait...",false,false);
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
                            if((int)(myjson.get("insertFormDataResponse"))==-1)
                            {
                                Toast.makeText(Campaign1.this, "Data Not Saved due to some Technical Reason..!!", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(Campaign1.this, "Survey Data Recorded Successfully..!!", Toast.LENGTH_LONG).show();
                                //intent.putExtra("volInfoStr",volJSONObject.toString() );
                                //startActivity(intent);
                            }


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
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

        //Toast.makeText(this,radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}