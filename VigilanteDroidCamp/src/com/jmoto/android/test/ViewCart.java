package com.jmoto.android.test;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class ViewCart extends Activity {
	
	private static final String URL = "http://qr-service.appspot.com/test/ws?wsdl";
	private static final String GET_METHOD_NAME = "viewCart";
	private static final String GET_ARG_0 = "arg0_java_lang_String";
	private static final String NAMESPACE = "test.vnetcon.org";
	private static final String SOAP_ACTION = "viewCart";
	private TextView viewCart;
	TableLayout cartTableLayout;
	
	/** Called when the activity is first created. */
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.cart);
	      /*viewCart = (TextView) findViewById(R.id.txtViewCart);
	      viewCart.setText(call());
	      */
	      
	      String cart = call();
	      
	      /*String[] cart = call().split(expr); 
	      */
	      cartTableLayout = (TableLayout) findViewById(R.id.cartTableLayout);
	     
	      //Checkout (Build the cart)
	      Button check = (Button) findViewById(R.id.cart_check);
	      check.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			     for (int i = 0; i < 5; i++) {
					 View newTagView =  inflater.inflate(R.layout.tagview, null);
				     TextView newTextView = (TextView)newTagView.findViewById(R.id.newText);
				     newTextView.setText(" Product "+i);
				     cartTableLayout.addView(newTagView);
			     }
			}
	      });
	      
	   }
	   
	   /**
	    * Invoke the method to view the Shopping Cart
	    * @return
	    */
	   private String call()
	   {
		   String result = "";
		   
		   SoapObject request = new SoapObject(NAMESPACE, GET_METHOD_NAME);
		   //SoapPrimitive
		   Object responsesData = null; 
	       request.addProperty(GET_ARG_0, "jaimoto81");

	       SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	       envelope.setOutputSoapObject(request);
	       HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);                    
		   try {
			   androidHttpTransport.call(SOAP_ACTION, envelope);
			   responsesData =  envelope.getResponse();
			   result = responsesData.toString();
			   
		   } catch (IOException e) {
			   e.printStackTrace();
			   Log.e(e.toString(), e.toString());
		   } catch (Exception e) {
			   e.printStackTrace();
			   Log.e(e.toString(), e.toString());
		   }
		   return result;
	   }

}
