package kunalganglani.com.suveyji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserManagement extends AppCompatActivity implements View.OnClickListener{
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    Button bCreateuser,bDeleteuser,bViewuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        bCreateuser= (Button) findViewById(R.id.bCreateuser);
        bDeleteuser= (Button) findViewById(R.id.bDeleteuser);
        bViewuser= (Button) findViewById(R.id.bViewuser);

        bCreateuser.setOnClickListener(this);
        bDeleteuser.setOnClickListener(this);
        bViewuser.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bCreateuser:
                startActivity(new Intent(this,Register.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.bDeleteuser:
                startActivity(new Intent(this,DeleteUser.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.bViewuser:
                startActivity(new Intent(this,ViewUser.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;


        }
    }
}
