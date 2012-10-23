package com.jmoto.android.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
 
public class MainMenu extends Activity {
	
	public static final String APP_PREFERENCES = "appPrefs";
	
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      
      

      String[] items = { getResources().getString(R.string.view_s),
              getResources().getString(R.string.report_e) };
      ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,R.layout.menu_item, items);
      
      Button scan = (Button) findViewById(R.id.button2);
      scan.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
            startActivity(new Intent(MainMenu.this, Scan.class));
		}
      });
      
      Button help = (Button) findViewById(R.id.button3);
      help.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
            startActivity(new Intent(MainMenu.this, ViewCart.class));
		}
      });
      
   }
}