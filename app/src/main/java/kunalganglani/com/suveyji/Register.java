package kunalganglani.com.suveyji;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    Button bRegister, btnSelect;
    EditText etFName, etLName, etContactNumber, etUsername, etPassword, etCity, etAge, selectgender, etDob;
    RadioGroup etGender;
    ImageView imageViewVol;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bRegister = (Button) findViewById(R.id.bRegister);
        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);
        etCity = (EditText) findViewById(R.id.etCity);
        etAge = (EditText) findViewById(R.id.etAge);
        etDob= (EditText) findViewById(R.id.etDob);
        etGender = (RadioGroup) findViewById(R.id.etGender);
        etContactNumber = (EditText) findViewById(R.id.etContactnumber);
        etUsername =(EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        imageViewVol = (ImageView) findViewById(R.id.imageView);

        // Listener for Camera Button
        btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });



        // Listner for Register Button
        bRegister.setOnClickListener(this);
        //
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(Register.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageViewVol.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imageViewVol.setImageBitmap(bm);
    }


    @Override
    public void onClick(View v) {
        final String REGISTER_URL = "http://prakashupadhyay.com/SurveyApp/process.php";
        //final String REGISTER_URL = "http://10.0.2.2:8282/PocketSurvey/process.php";

        final ProgressDialog loading;
        loading = ProgressDialog.show(this,"Registering...","Please wait...",false,false);
        final Intent intent = new Intent(this, UserManagement.class);

        switch (v.getId()){
            case R.id.bRegister:
            {

                String uname = etUsername.getText().toString();
                String pwd = etPassword.getText().toString();
                String fname = etFName.getText().toString();
                String lname = etLName.getText().toString();//etName.getText().toString();
                String city = etCity.getText().toString();
                String age = etAge.getText().toString();
                String dob = etDob.getText().toString();

                int genderId= etGender.getCheckedRadioButtonId();
                RadioButton etGenderRadio = (RadioButton) findViewById(genderId);
                String gender= etGenderRadio.getText().toString();

                String contact = etContactNumber.getText().toString();

                imageViewVol.setDrawingCacheEnabled(true);

                // this is the important code :)
                // Without it the view will have a dimension of 0,0 and the bitmap will be null
                imageViewVol.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                imageViewVol.layout(0, 0, 800, 800);

                imageViewVol.buildDrawingCache(true);

                String photo = getStringImage(imageViewVol.getDrawingCache());
                imageViewVol.setDrawingCacheEnabled(false); // clear drawing cache

                JSONObject jSonObjData = new JSONObject();
                try
                {
                    JSONObject jsonNewVolObject = new JSONObject();
                    jsonNewVolObject.put("uname", uname);
                    jsonNewVolObject.put("pwd", pwd);
                    jsonNewVolObject.put("fname", fname);
                    jsonNewVolObject.put("lname", lname);
                    jsonNewVolObject.put("city", city);
                    jsonNewVolObject.put("age", age);
                    jsonNewVolObject.put("gender", gender);
                    jsonNewVolObject.put("dob", dob);
                    jsonNewVolObject.put("contact", contact);
                    jsonNewVolObject.put("photo", photo);

                    jSonObjData.put("action", "CreateVolunteer");
                    jSonObjData.put("data", jsonNewVolObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        REGISTER_URL, jSonObjData,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                loading.dismiss();
                                System.out.println("\n\n Response from Backend:\n" + response.toString());
                                // Navigating Back to User Management Screen.
                                startActivity(intent);
                                overridePendingTransition(R.anim.left_in, R.anim.right_out);

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
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



            }
            break;
            default: System.out.println("Not Matched..!!");
        }
    }
}
