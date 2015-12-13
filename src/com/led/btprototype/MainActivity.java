/******************* (C) COPYRIGHT 2015 FYDZ **************************
 * 
 *  
 * 描述    ：风云蓝牙继电器控制终端       
 *  
 * 创建时间：2015年11月28日22:47:21
 * 程序版本：V1.3  L
 *
 * 作者    ：FX   QQ：379334525
 * 风云电子论坛    ：http://bbs.fydzkj.net
 * 风云电子淘宝    ：https://shop124800030.taobao.com/
 * 风云电子学习交流群   214437717   
 * 
***********************************************************************************/
package com.led.btprototype;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.led.btprototype.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.RawContacts.Data;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends Activity {

	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	BluetoothAdapter myBtAdapter;

	public static BluetoothDevice btdevice;
	public static BluetoothSocket btsocket;

	public static DataOutputStream dos = null;
	public static DataInputStream dis = null;

	static boolean connectflag = false;
	 

	Button bt1;
	public static MyHandler my;
	
	ToggleButton tb1;
	ToggleButton tb2;
	ToggleButton tb3;
	ToggleButton tb4;
	ToggleButton tb5;
	ToggleButton tb6;
	ToggleButton tb7;
	ToggleButton tb8;
	TextView tv1;
	
 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bt1 = (Button) findViewById(R.id.button1);
		tb1 = (ToggleButton) findViewById(R.id.toggleButton1);
		tb2 = (ToggleButton) findViewById(R.id.toggleButton2);
		tb3 = (ToggleButton) findViewById(R.id.toggleButton3);
		tb4 = (ToggleButton) findViewById(R.id.toggleButton4);
		tb5 = (ToggleButton) findViewById(R.id.toggleButton5);
		tb6 = (ToggleButton) findViewById(R.id.toggleButton6);
		tb7 = (ToggleButton) findViewById(R.id.toggleButton7);
		tb8 = (ToggleButton) findViewById(R.id.toggleButton8);
		tv1 = (TextView) findViewById(R.id.textView1);
		
	 
		
		bt1.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				Intent serverIntent = new Intent(MainActivity.this,
						DeviceListActivity.class);
				startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);

			}
		});
		
		
		
		tb1.setOnClickListener(new OnClickListener() {		
			public void onClick(View arg0) {
				if(connectflag)
				{
					try {
						dos.write(0x01);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}				
			}
		});
		tb2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(connectflag)
				{
					try {
						

						dos.write(0x02);

					} catch (IOException e) {

						e.printStackTrace();
					}
					
				}
			}
		});
		
		tb3.setOnClickListener(new OnClickListener() {		
			public void onClick(View arg0) {
				if(connectflag)
				{
					try {
						dos.write(0x03);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}				
			}
		});
		
		tb4.setOnClickListener(new OnClickListener() {		
			public void onClick(View arg0) {
				if(connectflag)
				{
					try {
						dos.write(0x04);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}				
			}
		});
		
		tb5.setOnClickListener(new OnClickListener() {		
			public void onClick(View arg0) {
				if(connectflag)
				{
					try {
						dos.write(0x05);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}				
			}
		});
		
		tb6.setOnClickListener(new OnClickListener() {		
			public void onClick(View arg0) {
				if(connectflag)
				{
					try {
						dos.write(0x06);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}				
			}
		});
		
		tb7.setOnClickListener(new OnClickListener() {		
			public void onClick(View arg0) {
				if(connectflag)
				{
					try {
						dos.write(0x07);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}				
			}
		});
		
		tb8.setOnClickListener(new OnClickListener() {		
			public void onClick(View arg0) {
				if(connectflag)
				{
					try {
						dos.write(0x08);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}				
			}
		});
		

		tv1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				 Uri uri = Uri.parse("https://shop124800030.taobao.com/");  
	                Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
	                startActivity(intent);  
			}
		});
		
		myBtAdapter = BluetoothAdapter.getDefaultAdapter();
		if (myBtAdapter == null) {
			Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_SHORT).show();
			finish();
			return;

		}

	}





	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {

		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {

				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				btdevice = myBtAdapter.getRemoteDevice(address);
				// Attempt to connect to the device
				// mChatService.connect(device);
				Log.d("bluetooth", address);

				try {

					// Attempt to connect to the device
					btsocket = btdevice.createRfcommSocketToServiceRecord(uuid);

					btsocket.connect();

					// get the Data I/O Stream
					dos = new DataOutputStream(btsocket.getOutputStream());
					dis = new DataInputStream(btsocket.getInputStream());

					connectflag = true;
					Toast.makeText(this, "已连接", Toast.LENGTH_SHORT).show();

			
				} catch (IOException e) {

					Toast.makeText(this, "连接失败", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

			}
			break;

		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				Log.d("bluetooth", "result_ok");
				// setupChat();
			} else {
				// User did not enable Bluetooth or an error occured

				Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_SHORT).show();
				finish();

			}
		}
	}

	public class MyHandler extends Handler {
		public void handleMessage(Message msg) {// 消息处理
			int d1 = msg.what;// 获取消息内容
			if (d1 == 1) {
				
				
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
