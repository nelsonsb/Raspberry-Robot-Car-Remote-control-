package com.example.remotecontrol;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "controlremoto";

    TextView response;
    Boolean conected = false;
    //String ip = "robotpi";
    String ip = "192.168.0.106";

    int port = 9090;

    FloatingActionButton fab;

    // Botones
    Button forward;
    Button backward;
    Button left;
    Button right;
    Button stop;

    // Luz
    Switch frontLight;

    // Objetos
    EditText etIp;
    EditText etPort;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                mainFunction(conected);
            }
        });
        response = findViewById(R.id.tvResponse);
        etIp = findViewById(R.id.tvIp);
        ip = etIp.getText().toString();

        etPort = findViewById(R.id.tvPort);
        port = Integer.valueOf(etPort.getText().toString());

        forward = findViewById(R.id.btForward);
        backward = findViewById(R.id.btBackward);
        left= findViewById(R.id.btLeft);
        right = findViewById(R.id.btRight);
        stop = findViewById(R.id.btStop);

        frontLight = (Switch) findViewById(R.id.swFrontLight);

        /////////////////////////////////////////////////////

        forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
//                    forward(null);
                    comando = "forward";
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    comando = "stop";
                }
                return true;
            }
        });


        backward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    comando = "backward";
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    comando = "stop";
                }
                return true;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    comando = "left";
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    comando = "stop";
                }
                return true;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    comando = "right";
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    comando = "stop";
                }
                return true;
            }
        });

        frontLight.setChecked(true);
        frontLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    comando = "lgfon";
                } else {
                    comando = "lgfoff";
                }
            }
        });

        Log.d(TAG, "onCreate()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    Handler handler = new Handler();

    ControlRemotoCliente miHilo = null;

    public void changeFabButton(boolean val){
        Log.d(TAG, "changeFabButton() state : "+val);

        if (val){
            fab.setImageResource(android.R.drawable.ic_media_pause);
        }else {
            fab.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    public void mainFunction(boolean conected){
        Log.d(TAG, "mainFunction() state : "+conected);

        if (conected){
            miHilo.disconnect();
            changeFabButton(conected);
            enableButtons(conected);
        }else {
            ip = etIp.getText().toString();
            port = Integer.valueOf(etPort.getText().toString());

            Log.d(TAG, "ip + port : "+ip+" : "+port);

            miHilo = new ControlRemotoCliente();
            Thread thread = new Thread(miHilo);
            thread.start();
        }
    }

    public void enableButtons(boolean val){
        Log.d(TAG, "enableButtons() state : "+val);

        forward.setEnabled(val);
        backward.setEnabled(val);
        stop.setEnabled(val);
        left.setEnabled(val);
        right.setEnabled(val);
    }


    public void stop(View view){
        Log.d(TAG, "stop()");

        comando = "stop";
    }


    private Socket s;
    OutputStream out;
//    InputStream in;
    PrintWriter output;
//    BufferedReader input;

    String comando = "";

    Thread thread2 = new Thread(new Runnable() {

        @Override
        public void run() {
            try  {
                while(conected){
                    if(!comando.isEmpty()){
                        output.println(comando);
                        comando = "";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    /**
     *
     */
    public class ControlRemotoCliente implements Runnable {

        public void run(){

            try {

                Log.d(TAG, "ip + port 2 : "+ip+" : "+port);

                //Replace below IP with the IP of that device in which server socket open.
                //If you change port then change the port number in the server side code also.
                s = new Socket(ip, port);

                out = s.getOutputStream();
                output = new PrintWriter(out,true);

                conected = true;

            } catch (Exception e) {
                e.printStackTrace();
                conected = false;
            }finally {
                actualizaTodo();
                thread2.start();
            }
        }

        public void disconnect(){
            Log.d(TAG, "disconnect()");

            try {
                output.close();
                out.close();
                s.close();
                s = null;
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                conected = false;
               actualizaTodo();
            }
        }

        public void actualizaTodo(){

            handler.post(new Runnable() {
                @Override
                public void run() {
                    enableButtons(conected);
                    changeFabButton(conected);
                }
            });
        }
    }
}
