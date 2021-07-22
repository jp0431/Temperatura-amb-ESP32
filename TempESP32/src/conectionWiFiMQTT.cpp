#include "conectionWiFIMQTT.h"
#include "credentials.h"
#define MAC_SIZE 15
float humidity;
float temp;
float heatIndex;
char sMac[MAC_SIZE];

WiFiClient espClient;

PubSubClient client(espClient);
void vConectionWiFi(){
Serial.printf("Connecting to %s ",  ssid);
     WiFi.begin( ssid, password);
     while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print(".");
  }
  Serial.println(" CONNECTED");
}

String szGetMac()
{
    byte mac[6];
    String szMAC = "";
    char szMac[3];

    WiFi.macAddress(mac);
    //for (int i = 5; i >= 0; i--)
    for (int i = 0; i < 6; i++)
    {
        if (mac[i] > 0x0F)
            sprintf(szMac, "%2X", mac[i]);
        else
            sprintf(szMac, "0%X", mac[i]);
        szMAC += szMac;
    }

    return szMAC;
}
void missatgeRebut(char* topic, byte* payload, unsigned int length){
  String szTopic = String(topic), szPayload = "";
  Serial.print("Topic: ");
  Serial.println(topic);

  Serial.print("payload: ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
    szPayload += (char)payload[i];
  }
  Serial.println();
  if(szTopic=TOPIC_REQTemp){
    publish(TOPIC_TEMP, String(temp).c_str());
    publish(TOPIC_HEAT, String(heatIndex).c_str());

  }
}void vMqttConnect() {
     while (!client.connected()) {
    Serial.print("MQTT connecting ...");
    String clientId = szGetMac(); 
    if (client.connect(clientId.c_str(), mqtt_user, mqtt_password)) {
      Serial.println("connected");
      client.subscribe(TOPIC_REQTemp);
      
      }
    else{
      Serial.println("failed, status code = ");
      Serial.print(client.state());
      Serial.println("try again in 5 seconds");
      delay(5000);

    }
  }
}
void vSetupMqtt() {
 
  client.setServer(mqtt_server, mqtt_port);
  client.setCallback(missatgeRebut);

}

void setupConnections(){
  vConectionWiFi();
  szGetMac().toCharArray(sMac, MAC_SIZE);
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  vSetupMqtt();
  vMqttConnect();
}
void publish(const char *topic, const char *message){
  client.publish(topic, message);
  Serial.println("Message Published");
}

void vConnectingMqttTask(void * parameter){
   for (;;) {
    if (!client.connected()) {
      setupConnections();
    }
    client.loop();
   
    vTaskDelay(10);
  }
  vTaskDelete(NULL); 
}

void vTempHumi(void *parameter){
  DHT dht(19, DHTTYPE);
  for(;;){
      dht.begin();

   humidity = dht.readHumidity();
   temp=dht.readTemperature();
   if (isnan(humidity) || isnan(temp) ) {
    Serial.println(F("Failed to read from DHT sensor!"));
  }else{
  heatIndex = dht.computeHeatIndex(humidity, temp, false);
  String h = "Humidity: "+ String(humidity);
  Serial.println(h);
  String t = "Temperature: "+String(temp);
  Serial.println(t);
  String hic="Heat index: "+ String(heatIndex);
  Serial.println(hic);
  publish(TOPIC_TEMP, String(temp).c_str());
  delay(500);

  delay(500);
  publish(TOPIC_HEAT, String(heatIndex).c_str());
  delay(3600000 );
  }
  vTaskDelay(10);
  
}
vTaskDelete(NULL);

}
void vBotTg(void *parameter){
    DHT dht(19, DHTTYPE);

  for(;;){
          dht.begin();

   humidity = dht.readHumidity();
   temp=dht.readTemperature();
   if (isnan(humidity) || isnan(temp) ) {
    Serial.println(F("Failed to read from DHT sensor!"));
  }else{
  heatIndex = dht.computeHeatIndex(humidity, temp, false);
  String h = "Humidity: "+ String(humidity);
  Serial.println(h);
  String t = "Temperature: "+String(temp);
  Serial.println(t);
  publish(DADES_TEMP, String(temp).c_str());
  publish(DADES_HUMI, String(humidity).c_str());
  delay(21600000);
  }
}
}
