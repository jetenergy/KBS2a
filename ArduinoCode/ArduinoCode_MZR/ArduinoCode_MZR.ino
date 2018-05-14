char data; //variable to store incoming data from JAVA
String commando;

String RName = "MZR";

const int HSensor = A0;
const int WSensor = A1;
float HSenVal, WSenVal;

int x = 0;
int y = 0;

void setup() {
    Serial.begin(9600);
    Serial.setTimeout(50);
    Serial.print("Ready");
}

void loop() {
    //MAAK STRING VAN INPUT
    while(Serial.available() > 0){ //if data has been written to the Serial stream
        data=Serial.read();
        commando = commando + data;
        delay(10);
    }

    if(commando.length() != 0) {
        String func;
        int param1, param2;
        int between = 0;
        for (int i = 0; i < commando.length(); i++) {
            if (commando.substring(i, i+1) == "-") {
                func = commando.substring(0, i);
                between = i+1;
            }
            if (commando.substring(i, i+1) == ";") {
                param1 = commando.substring(between, i).toInt();
                param2 = commando.substring(i+1).toInt();
                break;
            }
        }
        //Serial.println(func);
        //Serial.println(param1);
        //Serial.println(param2);


        //VoorbeeldCommando
        if(func == "WhoDis"){
            Serial.print(RName);
        }

        if(func == "GaNaar") {
          gaNaar(param1, param2);
        }

        if(func == "WhereAreYou") {
          whereAmI();
        }

        //FINAL RESET
        commando = func = "";
        param1 = param2 = 0;
    }
}

void readSensor() {
    HSenVal = (float)analogRead(HSensor) * 5.0 / 1023.0;
    WSenVal = (float)analogRead(WSensor) * 5.0 / 1023.0;
}

void gaNaar(int paramX, int paramY) {
    x = paramX;
    y = paramY;
    whereAmI();
}

void whereAmI() {
    Serial.println(String(x) + ":" + String(y));
}

