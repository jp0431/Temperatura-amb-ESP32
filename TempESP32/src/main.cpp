#include "conectionWiFIMQTT.h"

#include "topics.h"
#define DHTPIN 19

#define DHTTYPE DHT22  
DHT dht(DHTPIN, DHTTYPE);


void setup() {
  Serial.begin(9600);
  Serial.println(__FILE__);
  setupConnections();
  dht.begin();



  xTaskCreate(
    vConnectingMqttTask,    
    "Connecting MQTT Task", 
    8192, 
    NULL,  
    1,     
    NULL); 

  xTaskCreate(
    vTempHumi,    /* Task function. */
   "vTempHumi", /* name of task. */
    //10000,                   /* Stack size of task */
    5000,  /* Stack size of task */
    NULL,  /* parameter of the task */
    2,     /* priority of the task */
    NULL);
    xTaskCreate(
    vBotTg,    
   "vBotTg",
    //10000,                 
    5000, 
    NULL,  
    3,     
    NULL);
}

void loop() {
  // Wait a few seconds between measurements.

}