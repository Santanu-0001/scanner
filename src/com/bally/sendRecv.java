package com.bally;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;



import android.content.Context;


public class sendRecv implements Runnable{
	private boolean cnt=true;
	private Context ct=null;
	private alertDataSource alrt;
	private String iemi;
	public DatagramSocket socket;// = new DatagramSocket(10300);
	private Thread th;
	DatagramPacket receivePacket=null;
	private DatagramPacket packet=null;
	InetAddress serverAddr=null;
	byte[] readbuff = new byte[4];
	InputStream in=null;
	public static int comp=0;
	public sendRecv(Context ct1)
	{
		ct=ct1;
		alrt=new alertDataSource(ct);
		alrt.open();
		
		iemi=BallyFabs.iemi;
		comp=1;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			InetAddress remote = InetAddress.getByName("localhost");
   	        Socket c = new Socket("180.151.96.155",9000);//"180.151.96.155" "192.168.1.114"//10500);//59.93.248.125//
   	        OutputStream out1 = c.getOutputStream();
   	        in=c.getInputStream();
   	        
            
            long ctr=1;
    	    	
    	    	int th_cnt=0;
    	    	byte[] buf=null;
    	    	byte[] recv_data="as".getBytes();
    	    	alertRecord r=null;
    	    	int rec_no=10;
    	    	int first_rec=0;
    	    	
    	    	//BallyFabs.show_alert("start");
    	    	
    	    	do
	    	    {
    	    		
    	    		
	        	    if(cnt)
	        	    {
	        	    	do{
	        	    	r=alrt.getRecord(ct);
	        	    	}while(r==null);
	        	    	buf=new byte[30];
	        	    	buf = (iemi + "/" + r.getBarcode()+ "/" + r.getPrice() + "/" ).getBytes();//Integer.toString(ctr).getBytes();
	        	    	rec_no=alrt.showCount();
	        	    	if(first_rec==0)
	        	    		out1.write(String.format("%04d", rec_no).getBytes());
	          	        out1.write(buf); 
	        	    	
	        	    	cnt=false;
	        	    }

	                if(th_cnt==0)
	                {
	        	    th = new Thread(new Runnable(){
						@Override
						public void run() {
							try {
								readbuff = new byte[4];
								in.read(readbuff,0,4);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
	        	    });
	        	    th.start();
	        	    th_cnt++;
	                }
	        	    Thread.sleep(1000);
	        	    
	        	    if("next".equals((new String(readbuff)).trim()))
	                {
	        	    	cnt=true;
	        	    	th.stop();
	        	    	ctr=1;
	        	    	th_cnt=0;
	        	    	rec_no--;
	        	    	if(first_rec==0)
	        	    		first_rec++;
	                }
	        	    else
	        	    	cnt=false;
	        	    
	        	    if(cnt)
	        	    	alrt.deleteValue(Integer.toString(r.getId()));
	        	    
	        	    ctr++;
	        	    
	        	    if(ctr==10)
	        	    {
	        	    	cnt=true;
	        	    	
	        	    	th.stop();
	        	    	c.close();
	        	    	c = new Socket("180.151.96.155",9000);
	        	    	ctr=1;
	        	    	th_cnt=0;
	        	    }
	        	    
	    	    }
	        	while(rec_no>=0);
    	    	comp=0;
    	    }
    		catch (Exception e) {
    			// TODO: handle exception
    			comp=0;
			}

	}

	
}
