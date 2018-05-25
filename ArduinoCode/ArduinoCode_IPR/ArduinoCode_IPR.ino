int params[25];
int order[25];
char data; //variable to store incoming data from JAVA
String commando; //Will be used to create command


int Motor1Speed = 135;
//ROBOT NAME
String RName = "IPR";

int orderP = 0;

int EN1 = 6;  
int EN2 = 5;
int IN1 = 7;
int IN2 = 4;
  
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
            //Serial.println(params[paramNR]);
            paramNR += 1;
            break;
              
          }
        }
      }
    }
    if(func == "BPPOrder"){
      orderP = 0;
      for(int i = 0; i < (sizeof(order)/sizeof(int)); i++){
        order[i] = params[i];
        Serial.println(order[i]);
      }
    }
    
    
    if(func == "PakIn"){
      PakIn(params[0]);
    }

    //FINAL RESET
      commando = func = "";
      for(int i = 0; i < 25; i++){
        params[i] = 0;
      }
  }
}

void PakIn(int toDeliver){
  Serial.println("TEST1");
  Motor1(Motor1Speed, true);
  int leegGrens = 500;
  int LSensor = 0;
 
  boolean checkBand = true;
  while(toDeliver > 0){
    Serial.println("TEST2");
    LSensor = analogRead(0);
    Serial.println(LSensor);
    if(checkBand ==  true && LSensor < leegGrens){
      Serial.println("JE MOEDER");
      checkBand = false;
      delay(50);
      Motor1(0, false);
      int directionB = order[orderP];
      Serial.println(order[orderP]);
        if(directionB == 1){
          Motor2(255, true);
        }else if(directionB == -1){
          Motor2(255, false);
      }else{
        Serial.println("ERROR: Item has no direction"); 
      }
      Motor1(Motor1Speed, false);
      delay(300);
      Motor1(0, false);
          delay(2000);
          Motor2(0, false);
          toDeliver -= 1;
          Motor1(Motor1Speed, true);   
          orderP +=1; 
    }else if(LSensor > leegGrens){
      checkBand = true;
    }
  }
  Motor1(0, false);

}



void Motor1(int pwm, boolean reverse){
  analogWrite(EN1,pwm); //set pwm control, 0 for stop, and 255 for maximum speed
  if(reverse){ 
    digitalWrite(IN1, HIGH);    
  }else{
    digitalWrite(IN1, LOW);    
  }
}

void Motor2(int pwm, boolean reverse){
  analogWrite(EN2, pwm); //set pwm control, 0 for stop, and 255 for maximum speed
  if(reverse){
    digitalWrite(IN2, HIGH);
  }else{
    digitalWrite(IN2, LOW);
  }
}

