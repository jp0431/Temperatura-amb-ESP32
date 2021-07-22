package com.example.MqttApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {
    String nombre_Dispositivo;   //string para obtener el nombre del dispositivo
    private TextView tvNombreDispositivo;//TexView para monitorear

    //parametros del broker la siguiente variable con el broker de shiftr.io
    static String MQTTHOST; //el broker
    static String USERNAME;//el token de acceso
    static String PASSWORD;    //la contraceña del token
    MqttAndroidClient client;  //  clienteMQTT este dispositivo
    TextView h;
    TextView pwd;
    TextView us;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtenemos el nombre del Dispositivo
       Button bt = (Button) findViewById(R.id.btCon);
      h=(TextView) findViewById(R.id.bkName);
      pwd=(TextView) findViewById((R.id.bkPwd));
      us=(TextView)findViewById(R.id.bkUs);
       bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MQTTHOST="tcp://"+h.getText().toString();
               USERNAME=us.getText().toString();
               PASSWORD=pwd.getText().toString();
               MQTT.conexionBroker(MQTTHOST, USERNAME, PASSWORD, getApplicationContext());///Conexion.//para conextar al broker
               newPage();
           }
       });

    }

public void newPage(){
    Intent intent = new Intent(this, DadesActivity.class);
    startActivity(intent);
}






    }


