//ROBOT NAME
String RName = "MZR";

char data; //Wordt gebruikt om data uit JAVA op te slaan
String commando; //Wordt gebruikt om een commando te vormen
int params[5]; // Argumenten van commando

//Motor snelheden
int Motor1Aan = 150;
int Motor1Uit = 0;

int Motor2Aan = 205;
int Motor2Onder = 110;
int Motor2Uit = 0;

int Motor3Aan = 128;
int Motor3Uit = 0;

//MOTOR PINS INSTELLEN
int EN2 = 6;  
int EN1 = 5;
int IN2 = 7;
int IN1 = 4;

int EN3 = 8;
int IN3 = 9;

//Sensors en waardes
const int HSensor = A0;
const int WSensor = A1;

int VerSensor = 0;
int HorSensor = 0;

//Locatie waardes
int currentX = -1;
int currentY = -1;

int newX = 0;
int newY = 0;

int toMoveX = 0;
int toMoveY = 0;
int directionX = 1;
int directionY = 1;

int oldDirection = 0;

boolean IPR = false;

void setup() {
  Serial.begin(9600);
  Serial.setTimeout(50);
  Serial.print("Ready");
  pinMode(A0, INPUT);
  pinMode(A1, INPUT);

  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
}

void loop() {
  //MAAK STRING VAN INPUT
  while(Serial.available() > 0){ //if data has been written to the Serial stream
    data=Serial.read();
    commando = commando + data;
    delay(10);
  }

      VerSensor = analogRead(0);
    HorSensor = analogRead(1);
//    Serial.print("HOR: ");
 //     Serial.println(HorSensor);

  if(commando.length() != 0) {
    //COMMAND;ARG1;ARG2;ARG3;ARG4;ARG5;
    //ARGS ARE OPTIONAL
    String func = "Nothing";
    int paramNR = 0;
      
    while( commando.length() > 1){ 
      for(int i = 0; i<commando.length(); i++){
        if(commando.substring(i, i+1) == ";"){
            
          if(func == "Nothing"){
            func = commando.substring(0, i);
            //Serial.println(func);
            commando.remove(0, i+1);
            break;
              
          }else{
            params[paramNR] = commando.substring(0, i).toInt();
            commando.remove(0, i+1);
            //Serial.println(params[paramNR]);
            paramNR += 1;
            break;
              
          }
        }
      }
    }
    
    //Wie is dit
    if(func == "WhoDis"){
      Serial.print(RName);
    }

    //Volgende stop zoeken.
    if(func == "NextStop"){
      getRoute(params[0], params[1]);
    }

    if(func == "WhereAreYou") {
      Request();
    }

    if(func == "ARMTEST"){
      Motor1(Motor1Aan, true);
      delay(1800);
      Motor1(Motor1Aan, false);
      delay(1800);
      Motor1(Motor1Uit, false);
      Serial.println("TEST");
    }

     if(func == "BAKTEST"){
      Motor2(Motor2Aan, true);
      delay(1000);
      Motor2(Motor2Onder, false);
      delay(1000);
      Motor2(Motor2Uit, false);
      Serial.println("TEST");
    }


     if(func == "KARTEST"){
      Motor3(Motor3Aan, true);
      delay(1000);
      Motor3(Motor3Aan, false);
      delay(1000);
      Motor3(Motor3Uit, false);
      //Serial.println("TEST");
    }

      //FINAL RESET
      commando = func = "";
      for(int i = 0; i < 5; i++){
        params[i] = 0;
      }
  }
}

void getRoute(int newX, int newY){

  //Berekenen hoever alles iss
  toMoveX = newX - currentX;
  toMoveY = newY - currentY;

  if(newX == 5){ 
    IPR = true;
  }else{
    IPR = false;
  }
  //Serial.println("TEST1");
  moveInfo();
}
  
  void moveInfo(){
    //Data van vorige stap omzetten naar richten en aantal stappen
  if(toMoveX < 0){
     toMoveX = toMoveX*-1;
    directionX = -1;
  }else{
    toMoveX = toMoveX;
    directionX = 1;
  }
    if(toMoveY < 0){
     toMoveY = (toMoveY*-1);
    directionY = -1;
  }else{
    toMoveY = toMoveY;
    directionY = 1;
  }
  //Serial.println("TEST2");
  startMoving();
}

void startMoving(){
  //Starten met bewegen afhankelijk van richting
  if(directionX == 1&& toMoveX > 0){
    Motor3(Motor3Aan, true);
  }else if(directionX == -1 && toMoveX > 0){
    Motor3(Motor3Aan, false);
  }
  //Starten met bewegen afhankelijk van richting
  if(directionY == 1 && toMoveY > 0){
    Motor2(Motor2Aan, true);
    delay(100);
  }else if(directionY == -1 && toMoveY > 0){
    Motor2(110, false);
    delay(100);
  }
    delay(200);
  //Serial.println("TEST3");
  moveSensor();
}

void moveSensor(){
  //wordt gebruikt zodat de sensor niet constant opnieuw een stuk code triggered
  boolean checkZwartH = false;
  boolean checkZwartV = false;

  int zwartGrensH = 700;
  int zwartGrensV = 700;
  
  
  while((toMoveX + toMoveY)> 0){
    
    VerSensor = analogRead(0);
    HorSensor = analogRead(1);
//    Serial.print("VER: ");
//    Serial.println(HorSensor);
    //Wanneer een sensor zwart ziet.

    if(checkZwartH == true && HorSensor < zwartGrensH){
//            Serial.println("ZWART");
//      Serial.print("Nog te gaan: ");
//      Serial.println(toMoveX);
      delay(50);
      if(toMoveX > 0){
        toMoveX = toMoveX - 1;

        currentX = currentX + directionX;
        //Zet checken uit
        checkZwartH = false;
      }
    }else if(HorSensor > zwartGrensH){
      //Zet checken weer aan
      checkZwartH = true;
    }

    if(checkZwartV == true && VerSensor > zwartGrensV){

      if(toMoveY > 0){
        toMoveY = toMoveY -1;
//        Serial.print("ZWART");
//        Serial.println(toMoveY);
        currentY = currentY + directionY;
        checkZwartV = false;
      }
    }else if(VerSensor < zwartGrensV){
      checkZwartV =  true;
    }
  
    if(toMoveX == 0){
      Motor3(Motor3Uit, false);
      //Serial.println("MOTOR 3 UIT");
    }
  
    if(toMoveY == 0){
      if(directionY == -1){
          Motor2(100, true);
          delay(20);
          Motor2(Motor2Uit, false);
      }else{
        Motor2(Motor2Uit, false);
      }
    }
  }

  if(toMoveY == 0 && toMoveX == 0){
    if(IPR){
      //Serial.println("TEST IPR");
      Motor1(Motor1Aan, true);
      delay(1800);
      Motor1(Motor1Uit, false);

      Motor2(Motor2Aan, false);
      delay(450);
      Motor2(Motor2Uit, true);

      Motor1(Motor1Aan, false);
      delay(1800);
      Motor1(Motor2Uit, true);


      Motor2(Motor2Aan, true);
      while(analogRead(0) > 500){
        
      }
      Motor2(Motor2Uit,false);
      currentY = 0;
    
    }else{   
      //Beweging ARM. Arm uit > omhoog > arm terug > naar beneden
      Motor1(Motor1Aan, true);
      delay(900);
      Motor1(Motor1Uit, false);

      Motor2(Motor2Aan, true);
      delay(400);
      Motor2(Motor2Uit, false);
      
      Motor1(Motor1Aan, false);
      delay(900);
      Motor1(Motor1Uit, false);
      
      Motor2(Motor2Onder, false);
          while(analogRead(0) > 500){
        
      }
      Motor2(Motor2Aan, true);
      delay(20);
      Motor2(Motor2Uit, true);
      
    }


    Request();
  }
}


void Request() {
  Serial.println("NextStop");
}

//ARM OP EN NEER
void Motor1(int pwm, boolean forward){
  analogWrite(EN1,pwm); //set pwm control, 0 for stop, and 255 for maximum speed
  if(forward){ 
    digitalWrite(IN1, HIGH);    
  }else{
    digitalWrite(IN1, LOW);    
  }
}

//ARM UITSTEKEN
void Motor2(int pwm, boolean forward){
  analogWrite(EN2, pwm); //set pwm control, 0 for stop, and 255 for maximum speed
  if(forward){
    digitalWrite(IN2, HIGH);
   // Serial.println("TEST");
  }else{
    digitalWrite(IN2, LOW);
  }
}

//KAR
void Motor3(int pwm, boolean forward){
  analogWrite(EN3, pwm); //set pwm control, 0 for stop, and 255 for maximum speed
  if(!forward){
    digitalWrite(IN3, HIGH);
  }else{
    digitalWrite(IN3, LOW);
  }
}
