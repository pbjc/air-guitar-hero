package com.example.agibner.tutorial;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MyActivity extends AppCompatActivity {
    WebSocketClient mWebSocketClient;
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
                                mWebSocketClient.send("red,off");
                                Log.i("sockets", "red off");
                                break;
                            case R.id.blue:
                                mWebSocketClient.send("blue,off");
                                Log.i("sockets", "blue off");
                                break;
                            case R.id.green:
                                mWebSocketClient.send("green,off");
                                Log.i("sockets", "green off");
                                break;
                            case R.id.yellow:
                                mWebSocketClient.send("yellow,off");
                                Log.i("sockets", "yellow off");
                                break;
                        }
                    case MotionEvent.ACTION_UP:
                        switch (view.getId()) {
                            case R.id.red:
                                //message
                                mWebSocketClient.send("red,on");
                                Log.i("sockets", "Red on");
                                break;
                            case R.id.blue:
                                mWebSocketClient.send("blue,on");
                                Log.i("sockets", "blue on");
                                break;
                            case R.id.green:
                                mWebSocketClient.send("green,on");
                                Log.i("sockets", "green on");
                                break;
                            case R.id.yellow:
                                mWebSocketClient.send("yellow,on");
                                Log.i("sockets", "yellow on");
                                break;
                        }
                        break;
                }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        connectWebSocket();

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


    private void connectWebSocket() {
        URI uri;
        Log.i("ConnectingWebSocket", "entering");
        try {
            uri = new URI("ws://echo.websocket.org");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                Log.i("Websocket", "Received Message " + s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

}
