//#include "credentials.h"
#include <WiFi.h>
#include "PubSubClient.h"
#include <ESPmDNS.h>
#include <WiFiClient.h>
#include "MQTTCredentials.h"
#include "topics.h"
#include "DHT.h"
#define DHTPIN 19

#define DHTTYPE DHT22  
void vConectionWiFi();
String szGetMac();
void missatgeRebut(char* topic, byte* payload, unsigned int length);
void vSetupMqtt();
void vMqttConnect();
void vConnectingMqttTask(void * parameter);
void setupConnections();
void publish(const char *topic, const char *message);
void vTempHumi(void * parameter);
void vTempHumi(void *parameter);
void vBotTg(void *parameter);