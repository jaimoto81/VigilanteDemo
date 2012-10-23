package com.jmoto.android.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class Splash extends Activity {
	
	protected boolean _active = true;
	protected int _splashTime = 5000; // time to display the splash screen in ms
	//MediaPlayer mp;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        /*mp = MediaPlayer.create(this, R.raw.intro);
        mp.start();*/
     // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    //setContentView(R.layout.main);
                    startActivity(new Intent("com.jmoto.android.test.MainMenu"));
                    //stop();
                }
            }
        };
        splashTread.start();
        
        
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("MyAPP", " ... pause" );
		//mp.pause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i("MyAPP", " ... restart" );
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//mp.start();
		Log.i("MyAPP", " ... resume" );
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("MyAPP", " ... arranco... " );
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//mp.pause();
	}
    
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//mp.release();
	}
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        _active = false;
	        Log.i("MyAPP", " ...  touch " + event.getPressure() );
			
	    }
	    return true;
	}
    
    
}