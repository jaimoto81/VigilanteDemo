package co.bquest.vigilantealfa;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import co.bquest.vigilantealfa.activities.MapActivity;

import com.quickblox.core.QBCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.result.Result;
import com.quickblox.module.auth.QBAuth;

public class SplashActivity extends Activity implements  QBCallback, View.OnClickListener {

	private ProgressBar progressBar;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(this);

        // ================= QuickBlox ===== Step 1 =================
        // Initialize QuickBlox application with credentials.
        // Getting app credentials -- http://quickblox.com/developers/Getting_application_credentials
        QBSettings.getInstance().fastConfigInit("1025", "9uWqM55p5MmYO7D", "RbQq8L2vGvnd2N2");

        // ================= QuickBlox ===== Step 2 =================
        // Authorize application with user.
        // You can create user on admin.quickblox.com, Users module or through QBUsers.signUp method
        QBAuth.createSession("jaimoto81", "quickblox77", this);
       
        
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
    
    @Override
    public void onClick(View view) {
    	Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    @Override
    public void onComplete(Result result) {
        progressBar.setVisibility(View.GONE);

        if (result.isSuccess()) {
            // Show Map activity
            finish();
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        } else {

            // Show errors
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Error(s) occurred. Look into DDMS log for details, " +
                    "please. Errors: " + result.getErrors()).create().show();
        }

    }

    @Override
    public void onComplete(Result result, Object context) { }
}
