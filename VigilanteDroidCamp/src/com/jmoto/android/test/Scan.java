package com.jmoto.android.test;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jmoto.android.test.model.Product;

public class Scan extends Activity {

	
	private static final String URL = "http://qr-service.appspot.com/test/ws?wsdl";
	private static final String GET_METHOD_NAME = "GetValue";
	private static final String GET_ARG_0 = "arg0_java_lang_String";
	private static final String ADD_METHOD_NAME = "addProductToCart";
	private static final String ADD_ARG_0 = "arg0_java_lang_String";
	private static final String ADD_ARG_1 = "arg1_java_lang_String";
	private static final String ADD_ARG_2 = "arg2_int";
	
	
	private static final String NAMESPACE = "test.vnetcon.org";
	private static final String SOAP_ACTION = "GetValue";
	private String result = "";
	private TextView textProduct;
	private TextView textPrice;
	private EditText qty;
	Product product;
	private String user;
	
	
	
	
	/** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.scan);
      textProduct = (TextView) findViewById(R.id.textProd);
      textPrice = (TextView) findViewById(R.id.textPrice);
      qty = (EditText)findViewById(R.id.editQty);
      //TODO buscar el usuario logueado...
      user = "jaimoto81";
	      
      //SCAN THE PRODUCT
      Button scan = (Button) findViewById(R.id.btnScanCode);
      scan.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			//Invocación del BarScaner
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		    startActivityForResult(intent, 0);
		}
      });
      
      //ADD TO CART
      Button addToCart = (Button) findViewById(R.id.btn_addToCart);
      addToCart.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			String addResult = callAddProduct();
			if(addResult.contains("error")){
				showDialog(R.string.result_failed,addResult);
			}else{
				showDialog(R.string.result_succeeded,"Agregado con Éxito");
				product = new Product();
				textProduct.setText(product.name);
				textPrice.setText("");
				qty.setText("");
			}
		}
      });
      
      //VIEW CART
      Button btnViewCart = (Button) findViewById(R.id.btnViewCart);
      btnViewCart.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
            startActivity(new Intent(Scan.this, ViewCart.class));
		}
      });
   }
   
   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	   if (requestCode == 0) {
	       if (resultCode == RESULT_OK) {
		    	result = intent.getStringExtra("SCAN_RESULT");
		         //String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		    	 
		    	//Consultar los detalles del producto via WS
				String product_code = call(result);
				product = new Product(product_code);
				product.id = result;
				//showDialog(R.string.result_succeeded, "Contents: " + product_code);
				textProduct.setText(product.name);
				textPrice.setText("$"+product.price);
				qty.setText("1");
		    	 
		   } else if (resultCode == RESULT_CANCELED) {
			   showDialog(R.string.result_failed, getString(R.string.result_failed_why));
		   }
	   }
   }
   
   private void showDialog(int title, CharSequence message) {
	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
	   builder.setTitle(title);
	   builder.setMessage(message);
	   builder.setPositiveButton("OK", null);
	   builder.show();
   }

   /**
    * @return
    */
   private String call(String code)
   {
	   String result = "";
	   
	   SoapObject request = new SoapObject(NAMESPACE, GET_METHOD_NAME);
	   //SoapPrimitive
	   Object responsesData = null; 
       request.addProperty(GET_ARG_0, code);

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
	   // Get the SAOP Envelope back and the extract the body
	   /*SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
	   String  str = resultsRequestSOAP.getProperty("name").toString();
	   int count = resultsRequestSOAP.getPropertyCount();
	   Object o = resultsRequestSOAP.getProperty(0);*/
	   return result;
   }
   
   /**
    * Invoke the WS operation for Add a Product to the Cart
    * @return
    */
   private String callAddProduct()
   {
	   String result = "";
	   SoapObject request = new SoapObject(NAMESPACE, ADD_METHOD_NAME);
	   //SoapPrimitive
	   Object responsesData = null; 
       request.addProperty(ADD_ARG_0, user);
       request.addProperty(ADD_ARG_1, product.id);
       request.addProperty(ADD_ARG_2, qty.getText().toString());
       
       //request.addProperty("nname", "Braun");
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
	   // Get the SAOP Envelope back and the extract the body
	   /*SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
	   String  str = resultsRequestSOAP.getProperty("name").toString();
	   int count = resultsRequestSOAP.getPropertyCount();
	   Object o = resultsRequestSOAP.getProperty(0);*/
	   return result;
   }


}




