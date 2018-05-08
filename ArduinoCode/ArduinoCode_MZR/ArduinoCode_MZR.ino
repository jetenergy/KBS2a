char data; //variable to store incoming data from JAVA
int nietmeerdoen = 1;
String iets;
int LEDpin = 12; //pin number LED is connected to

String RName = "MZR";

void setup() {
    pinMode(LEDpin, OUTPUT);
    Serial.begin(9600);
    Serial.setTimeout(50);
    Serial.print("Ready");
}

void loop() {
    //MAAK STRING VAN INPUT
    while(Serial.available() > 0){ //if data has been written to the Serial stream
        data=Serial.read();
        iets = iets + data;
        nietmeerdoen = 0;


        if(data == '1') {
            digitalWrite(LEDpin, HIGH);
        } else if(data == '0') {
            digitalWrite(LEDpin, LOW);
        } else {
            digitalWrite(LEDpin, HIGH);
        }

        delay(10);
    }

    if(iets.length() != 0) {
        //VoorbeeldCommando
        if(iets == "WhoDis"){
            Serial.print(RName);
        }

        //FINAL RESET
        iets = "";
    }
}


