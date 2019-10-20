package com.bally;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;











import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Contacts.People;
import android.provider.MediaStore.Images.Media;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class BallyFabs extends ListActivity implements OnClickListener {
    /** Called when the activity is first created. */
	private ImageButton b1; 
	private Button b2; 
	private ImageButton b3;
	private Button b4;
	private TextView e_barcode;
	private EditText e_price;
	private boolean mode_edit=false;
	public static alertDataSource alrt;
	public static String iemi=null; 
	public static Context cnt=null;
	
	public static int shop_no=0;
	public static int trn_mode=0;
	
	private String jid=null;
	public static ArrayAdapter<String> adapter=null;
	private ListView lv=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        b1=(ImageButton)findViewById(R.id.button1);
        b1.setOnClickListener(this);
        //b2=(Button)findViewById(R.id.button2);
        //b2.setOnClickListener(this);
        b3=(ImageButton)findViewById(R.id.scan);
        b3.setOnClickListener(this);
        
        //b4=(Button)findViewById(R.id.edit);
        //b4.setOnClickListener(this);
        
        e_barcode=(TextView)findViewById(R.id.textView50);
        //e_price=(EditText)findViewById(R.id.editText2);
        
//        lv=(ListView)findViewById(R.id.list);
        
        //e_barcode.setEnabled(false);
        
        alrt=new alertDataSource(this);
        alrt.open();
        cnt=getApplicationContext();
        
/*        List<String> values = alrt.showAllValue(this); //new ArrayList<String>();//datasource.getAllComments();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.preference_category, values);//.preference_category
		
		setListAdapter(adapter);
*/		

        //getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		iemi = telephonyManager.getDeviceId().toString();
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.planets_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    spinner.setOnItemSelectedListener(new MyOnModeSelected());

		Spinner spinner1 = (Spinner) findViewById(R.id.spinner2);
	    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
	            this, R.array.shop_array, android.R.layout.simple_spinner_item);
	    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner1.setAdapter(adapter1);
	    spinner1.setOnItemSelectedListener(new MyOnShopSelected());
		
		
		
		/*lv.setOnItemLongClickListener(new OnItemLongClickListener() {

	        @Override
	        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	                int arg2, long arg3) {
	            // TODO Auto-generated method stub
	            position=arg2;
	            return false;
	        }
	    });*/


    }
 
    public class MyOnModeSelected implements OnItemSelectedListener {
    	@Override
        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          //Toast.makeText(parent.getContext(), "The planet is " +
          //    parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
          BallyFabs.trn_mode=pos+1;//parent.getItemAtPosition(pos).toString();
          //Toast.makeText(parent.getContext(),BallyFabs.trn_mode + "/", Toast.LENGTH_LONG).show();

          if(BallyFabs.trn_mode==2)
          {
          List<String> values = alrt.showAllValue(cnt); //new ArrayList<String>();//datasource.getAllComments();
  		adapter = new ArrayAdapter<String>(cnt,
  				android.R.layout.preference_category, values);//.preference_category
  		
  		setListAdapter(adapter);
          }
          else if(BallyFabs.trn_mode==1)
          {
              List<String> values = alrt.stock_showAllValue(cnt,BallyFabs.shop_no-1); 
        		adapter = new ArrayAdapter<String>(cnt,android.R.layout.preference_category, values);//.preference_category
        		setListAdapter(adapter);
          }


    	}
    	@Override
        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
      
    	}
    }

    public class MyOnShopSelected implements OnItemSelectedListener {
    	@Override
        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          //Toast.makeText(parent.getContext(), "The planet is " +
          //    parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
          BallyFabs.shop_no=pos+1;//parent.getItemAtPosition(pos).toString();
          //Toast.makeText(parent.getContext(),(BallyFabs.shop_no - 1)+ "/", Toast.LENGTH_LONG).show();
          
          if(BallyFabs.trn_mode==1)
          {
              List<String> values = alrt.stock_showAllValue(cnt,(BallyFabs.shop_no-1)); 
        		adapter = new ArrayAdapter<String>(cnt,android.R.layout.preference_category, values);//.preference_category
        		setListAdapter(adapter);
          }

    	}
    	@Override
        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
      
    	}
    }

    
    /*
    public class MyOnShopSelected implements OnItemSelectedListener {
    	@Override
        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          //Toast.makeText(parent.getContext(), "The planet is " +
          //    parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
          gps_4.jstat=parent.getItemAtPosition(pos).toString();
        }
    	@Override
        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }*/

    
    public void onClick(View view) {
    	ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
    	switch (view.getId()) {
		case R.id.button1:
			//send2srv("santanu");
			//fn();
			
			//send2srv();
			final ProgressDialog dialog = ProgressDialog.show(this, "", "Uploading. Please wait...", true,true);
			//Toast.makeText(getApplicationContext(), str, 3000).show();
	        Thread thread=new Thread(new Runnable(){

	        public void run(){
	        	try {
	        		
	        		//show_alert("Hello");
	        		if(BallyFabs.trn_mode==2)
	        			send2srv();
	        		else if(BallyFabs.trn_mode==1)
	        			stock_send2srv();
	        		//Toast.makeText(cnt, "Hello", 3000).show();
				} catch (Exception e) {
					// TODO: handle exception
					//Toast.makeText(getApplicationContext(), e.getMessage(), 3000).show();
					
					try {
						//Toast.makeText(cnt, e.getMessage(), 3000).show();
						show_alert(e.getMessage());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	        	
	            //LoadData();
	            runOnUiThread(new Runnable(){

	                @Override
	                public void run() {
	                    if(dialog.isShowing())
	                        dialog.dismiss();

	                }

	            });
	        }

	        });
	        thread.start();

			
			
			
			
			
			//refresh();
			
			//adapter.clear();
			
			break;
//		case R.id.button2:
//			if(mode_edit)
//			{
//				alrt.update(jid, e_barcode.getText().toString(), e_price.getText().toString());
//				adapter = new ArrayAdapter<String>(this,
//						android.R.layout.preference_category, alrt.showAllValue(this));
//				
//				setListAdapter(adapter);
//				e_barcode.setText("");
//				e_price.setText("");
//				e_price.requestFocus();
//				mode_edit=false;
//			}
//			else
//			{
//			mode_edit=false;
//			String str1=e_price.getText().toString();
//			String str2=e_barcode.getText().toString();
//			if(!(str2.equals("") & str1.equals("")))
//			{
//				if(str1.matches("[0-9]+[.][0-9]+") | str1.matches("[.][0-9]+") | str1.matches("[0-9]+"))
//				{
//					alrt.insertValue(e_barcode.getText().toString(), e_price.getText().toString());
//					//adapter.add(e_barcode.getText().toString() + "/" +(e_price.getText().toString()) + "/");
//					adapter.add(alrt.showValue(this));
//					e_barcode.setText("");
//					e_price.setText("");
//					e_price.requestFocus();
//				}
//				else
//					Toast.makeText(this, "Enter proper data in Price field", 3000).show();
//			}
//			else
//				Toast.makeText(this, "Can't leave any field blank", 3000).show();
//			}
//			break;
		case R.id.scan:
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "ONE_D_MODE");//QR_CODE_MODE//DATA_MATRIX_MODE
			intent.putExtra("RESULT_DISPLAY_DURATION_MS", 0L);
			//intent.putExtra("ENCODE_SHOW_CONTENTS", "true");
			intent.putExtra("PROMPT_MESSAGE", "");
			//intent.putExtra("SCAN_WIDTH", 3000L);
			//intent.putExtra("SCAN_HEIGHT", 3000L);
			
			startActivityForResult(intent, 0);
			break;
//		case R.id.edit:
//			e_barcode.setText("");
//			e_price.setText("");
//			e_price.requestFocus();
//			mode_edit=false;
//			break;
    	}
    	adapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	   if (requestCode == 0) {
    	      if (resultCode == RESULT_OK) {
    	         String contents = intent.getStringExtra("SCAN_RESULT");

    	         	e_barcode.setText(contents);
    	         	
					try
					{
	    	         	if(BallyFabs.trn_mode==2)
	    	         	{
							alrt.insertValue(contents, "0.00");
		    	         	adapter.add(alrt.showValue(this));
	    	         	}
	    	         	else if(BallyFabs.trn_mode==1)
	    	         	{
	    	         		if((BallyFabs.shop_no-1)>0)
	    	         		{
							alrt.stock_insertValue(String.format("%d",(BallyFabs.shop_no - 1)), contents);
		    	         	adapter.add(alrt.stock_showValue(this));
	    	         		}
	    	         		else
	    	         			Toast.makeText(this, "Select SHOP", 3000).show();
	    	         	}
					}
					catch (Throwable e) {
						Toast.makeText(this, e.getMessage(), 3000).show();
						// TODO: handle exception
					}
					//adapter.add(e_barcode.getText().toString() + "/" +(e_price.getText().toString()) + "/");
					

    	         // Handle successful scan
    	      } else if (resultCode == RESULT_CANCELED) {
    	         // Handle cancel
    	      }
    	   }
    }
    
	public static void show_alert(final String str)  throws IOException
	{
		Handler handler;
		handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
			Toast.makeText(cnt, str, Toast.LENGTH_LONG).show();
    		//vi.vibrate(100);
			}
			});
	}

    
    @Override
    public boolean onKeyLongPress(int keyCode, android.view.KeyEvent event) 
    {
		
    	return true;
    };
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	super.onListItemClick(l, v, position, id);
    	try{
    		//Cursor c = (Cursor)getListView().getItemAtPosition(position);
    		String c = (String)l.getItemAtPosition(position);
    		mode_edit=true;
    		int i=0;
    		i=c.indexOf(">",0);
    		jid=c.substring(0,i);
    		Toast.makeText(this, c.substring(0,i), 3000).show();
    		Cursor cr=null;
    		if(BallyFabs.trn_mode==2)
    		{
    			cr=alrt.showJobDet(jid);
        		new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Barcode Application")
                .setMessage("Want to delete " + cr.getString(1) + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	{
                        alrt.deleteValue(jid);
                        adapter.clear();
                        List<String> l=alrt.showAllValue(getApplicationContext());
                        for(int i=0;i<l.size();i++)
                        {
                        adapter.add(l.get(i));
                        }
                    	}
                    }
                })
                .setNegativeButton("No", null)
                .show();
    		}
    		else if(BallyFabs.trn_mode==1)
    		{
    			cr=alrt.stock_showJobDet(jid);
        		new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Barcode Application")
                .setMessage("Want to delete " + cr.getString(2) + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        {
                        	alrt.stock_deleteValue(jid);
                            adapter.clear();
                            List<String> l=alrt.stock_showAllValue(getApplicationContext(),BallyFabs.shop_no-1);
                            for(int i=0;i<l.size();i++)
                            {
                            adapter.add(l.get(i));
                            }
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    		}
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		Toast.makeText(this, e.getMessage(), 3000).show();
		}
    }
    
    /*public void refresh()
    {
    	ArrayAdapter<String> adapter = (ArrayAdapter<String>)getListAdapter();
    	//List<String> lst = alrt.showAllValue(this);
    	adapter.clear();
    	Cursor c=alrt.showAllRec(this);
    	c.moveToFirst();
		while(!c.isAfterLast())
		{
			adapter.add(c.getString(1) + "/" + c.getString(2) + "/");
			c.moveToNext();
		}
		c.close();
    	
    	//adapter.add(lst);
    }*/
    
    /*public void fn() 
    {
    	Thread th1;
    	sendRecv s1;
    	s1=new sendRecv(this);
		th1=new Thread(s1);
		th1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//while(sendRecv.comp!=0){};
		//refresh();
		ArrayAdapter<String> adapter = (ArrayAdapter<String>)getListAdapter();
    	//List<String> lst = alrt.showAllValue(this);
    	adapter.clear();
    }*/
    /*
	public static void show_alert(final String str)  throws IOException
	{
		Handler handler;
		handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			public void run() {
			Toast.makeText(cnt, str, Toast.LENGTH_LONG).show();
    		//vi.vibrate(100);
			}
			});
	}*/
	

	public void send2srv()//Bitmap bmp)
	{
	
		try
    		{
    			
    			byte[] readbuff = new byte[4]; 
    			/*
    			InetAddress remote = InetAddress.getByName("localhost");
       	        Socket c = new Socket("192.168.0.24",8000);//"180.151.96.155" "192.168.1.114"//10500);//59.93.248.125//
       	        */
    			
    		    InetAddress addr = InetAddress.getByName("180.151.96.155");//180.151.96.155
    		    int port = 9000;
    		    SocketAddress sockaddr = new InetSocketAddress(addr, port);
    		    Socket c = new Socket();
    		    int timeoutMs = 1000;   // 2 seconds
    		    c.connect(sockaddr, timeoutMs);
    		    
    		    

       	        OutputStream out1 = c.getOutputStream();
       	        InputStream in=c.getInputStream();
       	        alertRecord r=null; 
       	         int record_no=alrt.showCount();
       	         final ArrayAdapter<String> adapter = (ArrayAdapter<String>)getListAdapter();
       	         
       	         out1.write(String.format("%04d", record_no).getBytes());
       	         //show_alert(record_no + "/");
       	         for(int i=0;i<record_no;)
       	         {
       	        	 do{
	        	    	r=alrt.getRecord(this);
	        	    	}while(r==null);
	        	    	byte[] buf=new byte[50];
	        	    	buf = (iemi + "/" + r.getBarcode()+ "/" + r.getPrice() + "/" + "1/" ).getBytes();//Integer.toString(ctr).getBytes();
	        	    	out1.write(buf); 
	        	    	
	        	    	//show_alert(buf.toString());
       	        	 
       	        	 
	        	    	//out1.write(("santanu"+i).getBytes());
       	         //Toast.makeText(getApplicationContext(), "write", 3000).show();
       	         
       	         in.read(readbuff,0,4);
       	         
       	         //if(readbuff.equals("next".getBytes()))
       	         if("next".equals((new String(readbuff)).trim()))
       	         {
       	        	 //Toast.makeText(getApplicationContext(), new String(readbuff) + "N", 3000).show();
       	        	 i++;
       	        	 final String str_del=r.getId() + ">  " + r.getBarcode();
       	        	 	runOnUiThread(new Runnable() {
          	        	public void run() {
               	        	 adapter.remove(str_del);//+ "/" + r.getPrice() + "/");
          	        	 	}
          	         	});


       	        	 alrt.deleteValue(Integer.toString(r.getId()));
       	        	
       	         }
       	         else
       	         {
       	        	//Toast.makeText(getApplicationContext(), new String(readbuff) + "Y", 3000).show();
       	        	//i++;
       	         }
       	      //Thread.sleep(1000);
       	         }
       	         //Toast.makeText(getApplicationContext(), new String(readbuff), 3000).show();
       	         out1.close();
       	         in.close();
       	         c.close();

       	         
    		}
            catch (Exception e)
            {
            	try {
					show_alert(e.getMessage());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	//Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            } 
    }

	public void stock_send2srv()//Bitmap bmp)
	{
	
		try
    		{
    			
    			byte[] readbuff = new byte[4]; 
    			/*
    			InetAddress remote = InetAddress.getByName("localhost");
       	        Socket c = new Socket("192.168.0.24",8000);//"180.151.96.155" "192.168.1.114"//10500);//59.93.248.125//
       	        */
    			
    		    InetAddress addr = InetAddress.getByName("180.151.96.155");//180.151.96.155
    		    int port = 9000;
    		    SocketAddress sockaddr = new InetSocketAddress(addr, port);
    		    Socket c = new Socket();
    		    int timeoutMs = 1000;   // 2 seconds
    		    c.connect(sockaddr, timeoutMs);
    		    
    		    

       	        OutputStream out1 = c.getOutputStream();
       	        InputStream in=c.getInputStream();
       	        stockRecord r=null; 
       	         int record_no=alrt.stock_showCount();
       	         final ArrayAdapter<String> adapter = (ArrayAdapter<String>)getListAdapter();
       	         
       	         out1.write(String.format("%04d", record_no).getBytes());
       	         //show_alert(record_no + "/");
       	         for(int i=0;i<record_no;)
       	         {
       	        	 do{
	        	    	r=alrt.stock_getRecord(this);
	        	    	}while(r==null);
	        	    	byte[] buf=new byte[50];
	        	    	buf = (iemi + "/" + r.getBarcode()+ "/" + r.getShopno() + "/" + "2/" ).getBytes();//Integer.toString(ctr).getBytes();
	        	    	out1.write(buf); 
	        	    	
	        	    	//show_alert(buf.toString());
       	        	 
       	        	 
	        	    	//out1.write(("santanu"+i).getBytes());
       	         //Toast.makeText(getApplicationContext(), "write", 3000).show();
       	         
       	         in.read(readbuff,0,4);
       	         
       	         //if(readbuff.equals("next".getBytes()))
       	         if("next".equals((new String(readbuff)).trim()))
       	         {
       	        	 //Toast.makeText(getApplicationContext(), new String(readbuff) + "N", 3000).show();
       	        	 i++;
       	        	 final String str_del=r.getId() + ">  " + r.getBarcode();
       	        	 	runOnUiThread(new Runnable() {
          	        	public void run() {
               	        	 adapter.remove(str_del);//+ "/" + r.getPrice() + "/");
          	        	 	}
          	         	});


       	        	 alrt.stock_deleteValue(Integer.toString(r.getId()));
       	        	
       	         }
       	         else
       	         {
       	        	//Toast.makeText(getApplicationContext(), new String(readbuff) + "Y", 3000).show();
       	        	//i++;
       	         }
       	      //Thread.sleep(1000);
       	         }
       	         //Toast.makeText(getApplicationContext(), new String(readbuff), 3000).show();
       	         out1.close();
       	         in.close();
       	         c.close();

       	         
    		}
            catch (Exception e)
            {
            	try {
					show_alert(e.getMessage());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	//Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            } 
    }


}