package kunalganglani.com.suveyji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendMessage extends AppCompatActivity {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
    EditText etextMblNumber, etextMsg;
    Button btnSendSMS;
    String contactnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             contactnumber = extras.getString("contactnumberval");
        }

        etextMblNumber= (EditText)findViewById(R.id.etextMblNumber);
        etextMsg= (EditText)findViewById(R.id.etextMsg);
        btnSendSMS=(Button)findViewById(R.id.btnSendSMS);

        etextMblNumber.setText(""+contactnumber);
}

    public void sendSms(View view) {
        SmsManager sm = SmsManager.getDefault();
        String number = etextMblNumber.getText().toString();
        String msg = etextMsg.getText().toString();
        sm.sendTextMessage(number, null, msg, null, null);
    }
}
