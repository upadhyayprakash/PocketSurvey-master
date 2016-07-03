package kunalganglani.com.suveyji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DeleteUser extends AppCompatActivity {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
    }
}
