//SETUP MOTORS
#include<AFMotor.h>
AF_DCMotor motor1(1);
AF_DCMotor motor2(2);
AF_DCMotor motor3(3);


char data; //variable to store incoming data from JAVA
String commando; //Will be used to create command

//ROBOT NAME
String RName = "MZR";

const int HSensor = A0;
const int WSensor = A1;
float HSenVal, WSenVal;

int VerSensor = 0;
int HorSensor = 0;
int zwartGrens = 500;

int currentX = 0;
int currentY = 0;

int newX = 0;
int newY = 0;

int moveToX = 0;
int moveToY = 0;

int toMoveX = 0;
int toMoveY = 0;
int directionX = 1;
int directionY = 1;

int speed1 = 255;
int params[5];

void setup() {
  Serial.begin(9600);
  Serial.setTimeout(50);
    
  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  Serial.print("Ready");
  motor1.setSpeed(200);
  motor2.setSpeed(255);
}

void loop() {
  //MAAK STRING VAN INPUT
  while(Serial.available() > 0){ //if data has been written to the Serial stream
    data=Serial.read();
    commando = commando + data;
    delay(10);
  }

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
            Serial.println(func);
            commando.remove(0, i+1);
            break;
              
          }else{
            params[paramNR] = commando.substring(0, i).toInt();
            commando.remove(0, i+1);
            Serial.println(params[paramNR]);
            paramNR += 1;
            break;
              
          }
        }
      }
    }
    
    //VoorbeeldCommando
    if(func == "WhoDis"){
      Serial.print(RName);
    }

    if(func == "NextStop"){
      getRoute(params[0], params[1]);
      Serial.println("TEST");
    }

    if(func == "WhereAreYou") {
      whereAmI();
    }

    if(func == "Start"){
      motor2.run(FORWARD);
    }

    if(func == "Stop"){
      motor1.run(RELEASE);
    }

      if(func == "Reverse"){
        motor1.run(BACKWARD);
      }


      if(func == "SlowDown"){
        motor1.setSpeed(10);
      }
      if(func == "Faster"){
        speed1 = speed1+10;
        motor1.setSpeed(speed1);
      }

      //FINAL RESET
      commando = func = "";
      for(int i = 0; i < 5; i++){
        params[i] = 0;
      }
  }
}

void getRoute(int newX, int newY){
  delay(1000);
  moveToX = newX - currentX;
  moveToY = newY - currentY;
  
  moveInfo();
  }
  
  void moveInfo(){
  if(moveToX < 0){
     toMoveX = moveToX*-1;
    directionX = -1;
  }else{
    toMoveX = moveToX;
    directionX = 1;
  }
    if(moveToY < 0){
     toMoveY = moveToY*-1;
    directionY = -1;
  }else{
    toMoveY = moveToY;
    directionY = 1;
  }
  
  startMoving();
}

void startMoving(){
  if(directionX == 1&& toMoveX > 0){
    motor2.run(FORWARD);
  }else if(directionX == -1 && toMoveX > 0){
    motor2.run(BACKWARD);
  }
  
  if(directionY == 1 && toMoveY > 0){
    motor3.run(FORWARD);
  }else if(directionY == -1 && toMoveY > 0){
    motor3.run(BACKWARD);
  }
  moveSensor();
}

void moveSensor(){
  boolean checkZwartH = true;
  boolean checkZwartV = true;

  int zwartGrensH = 500;
  int zwartGrensV = 500;
  
  while((toMoveX + toMoveY)> 0){
  
    VerSensor = analogRead(0);
    HorSensor = analogRead(1);

    Serial.println(VerSensor);
    
    if(checkZwartH == true && HorSensor > zwartGrensH){
      if(toMoveX > 0){
        toMoveX = toMoveX - 1;
        currentX = currentX + directionX;
        whereAmI();
        checkZwartH = false;
      }
    }else if(HorSensor < zwartGrens){
      checkZwartH = true;
    }

    if(checkZwartV == true && VerSensor > zwartGrensV){
      Serial.println("TEST");
      if(toMoveY > 0){
        toMoveY = toMoveY -1;
        currentY = currentY + directionY;
        whereAmI();
        checkZwartV = false;
      }
    }else if(VerSensor < zwartGrens){
      checkZwartV =  true;
    }
  
    if(toMoveX == 0){
      motor2.run(RELEASE);
    }
  
    if(toMoveY == 0){
      motor2.run(RELEASE);
    }
  }

  if(toMoveY == 0 && toMoveX == 0){
    motor1.run(FORWARD);
    delay(1000);
    motor2.run(FORWARD);
    delay(100);
    motor2.run(RELEASE);
    //HIER MOET MOTOR2 NOG ZOEKEN NAAR EEN ZWARTE WANNEER NODIG
    motor1.run(BACKWARD);
    delay(1000);
    motor1.run(RELEASE);
    whereAmI();
  }


}


void whereAmI() {
  Serial.print("Right now I am at about x: ");
  Serial.print(currentX);
  Serial.print(" and y: "); 
  Serial.print(currentY);   
  Serial.println(".");
}

