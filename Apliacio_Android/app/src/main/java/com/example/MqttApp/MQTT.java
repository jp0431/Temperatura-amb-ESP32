package com.example.MqttApp;
import android.content.Context;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import  org.eclipse.paho.client.mqttv3.*;

public  class MQTT {
    static  MqttAndroidClient client;  //  clienteMQTT este dispositivo
    static  Context context2;
    public static void conexionBroker(String MQTTHOST, String USERNAME, String PASSWORD, Context context) {

        context2=context;


        //para conextar al broker   //generamos un clienteMQTT
        String clientId = MqttClient.generateClientId();//noombre del celular
        client = new MqttAndroidClient(context, MQTTHOST, clientId);
        //para agregar los parametros
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        String payload = "1";
        byte[] encodedPayload = new byte[0];
        try {
            IMqttToken token = client.connect(options);//intenta la conexion
            token.setActionCallback(new IMqttActionListener() {

                @Override//metodo de conectado con exito
                public void onSuccess(IMqttToken asyncActionToken) {
                    // mensaje de conectado
                    Toast.makeText(context2, "Connectat ", Toast.LENGTH_SHORT).show();
                    subscripcion();
                    System.out.println("Connectat");
                    MqttMessage msgP = new MqttMessage(encodedPayload);

                    try {
                        client.publish("/reqTemp", msgP);
                        Toast.makeText(context2, "Publicat ", Toast.LENGTH_SHORT).show();

                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override//si falla la conexion
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // mensaje de que no se conecto
                    Toast.makeText(context2, "NO Connectat ", Toast.LENGTH_SHORT).show();
                    System.out.println(exception.getMessage());
                }


            });


        } catch (MqttException e) {
            e.printStackTrace();
        }


    }
    public static void subscripcion(){
        String topic = "/JAPASE/TEMP";
        String topic2 = "/JAPASE/HeatIndex";
        String topic3 ="/JAPASE/TEMP_BDN";
        String topic4="/JAPASE/HeatIndex_BDN";
        try{
            client.subscribe(topic, 0);
            client.subscribe(topic2, 0);
            client.subscribe(topic3, 0);
            client.subscribe(topic4, 0);
           Toast.makeText(context2, "Subscrit ", Toast.LENGTH_SHORT).show();
        } catch (MqttException e) {
            System.out.println(e.getMessage());
          Toast.makeText(context2, "NO Subscrit ", Toast.LENGTH_SHORT).show();
        }
    }
}
