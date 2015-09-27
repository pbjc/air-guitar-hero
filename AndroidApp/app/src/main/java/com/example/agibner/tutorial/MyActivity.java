package com.example.agibner.tutorial;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.net.URISyntaxException;

public class MyActivity extends AppCompatActivity {
    private Socket mSocket;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Vibrator myVib;

    private View.OnTouchListener myListener  = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent me) {
            Log.i("Touch", "entering");
                // Want to be able to send things
                // ehhhhhhhhh.....bippity boppity

                switch (me.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        switch (view.getId()) {
                            case R.id.red:
                                mSocket.emit("colors", "red,on");
                                myVib.vibrate(20);
                                break;
                            case R.id.blue:
                                mSocket.emit("colors", "blue,on");
                                myVib.vibrate(20);
                                break;
                            case R.id.green:
                                mSocket.emit("colors", "green,on");
                                myVib.vibrate(20);
                                break;
                            case R.id.yellow:
                                mSocket.emit("colors", "yellow,on");
                                myVib.vibrate(20);
                                break;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        switch (view.getId()) {
                            case R.id.red:
                                mSocket.emit("colors", "red,off");
                                Log.i("sockets", "red on");
                                break;
                            case R.id.blue:
                                mSocket.emit("colors", "blue,off");
                                Log.i("sockets", "blue on");
                                break;
                            case R.id.green:
                                mSocket.emit("colors", "green,off");
                                Log.i("sockets", "green on");
                                break;
                            case R.id.yellow:
                                mSocket.emit("colors", "yellow,off");
                                Log.i("sockets", "yellow on");
                                break;
                        }
                        break;
                }
            return false;
        }
    };

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);


        try {
            mSocket = IO.socket("http://10.145.210.252:8080");
        } catch (URISyntaxException e) {
            Log.i("sockets", "failed to connect");
        }
        mSocket.connect();

        //Sets up touch handlers for all of the buttons
        Button green = (Button)findViewById(R.id.green);
        green.setOnTouchListener(myListener);

        Button red = (Button)findViewById(R.id.red);
        red.setOnTouchListener(myListener);

        Button blue = (Button)findViewById(R.id.blue);
        blue.setOnTouchListener(myListener);

        Button yellow = (Button)findViewById(R.id.yellow);
        yellow.setOnTouchListener(myListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }
}
