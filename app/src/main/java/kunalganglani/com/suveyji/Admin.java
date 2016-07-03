package kunalganglani.com.suveyji;

import android.content.Intent;
 import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity implements View.OnClickListener {


    Button bUsermanage, bViewdata,bAddform,bAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bUsermanage= (Button) findViewById(R.id.bUsermanage);
        bViewdata= (Button) findViewById(R.id.bViewdata);
        bAddform= (Button) findViewById(R.id.bAddform);
        bAnalytics=(Button) findViewById(R.id.bAnalytics);
        bUsermanage.setOnClickListener(this);
        bViewdata.setOnClickListener(this);
        bAddform.setOnClickListener(this);
        bAnalytics.setOnClickListener(this);



    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bUsermanage:
                    startActivity(new Intent(this,UserManagement.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.bViewdata:
                startActivity(new Intent(this,ViewData.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.bAddform:
                startActivity(new Intent(this,AddForm.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.bAnalytics:
                startActivity(new Intent(this,Analytics.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

        }
    }
}
