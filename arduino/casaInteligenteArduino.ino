#include <VirtualWire.h>

int ldr = A1;
int gasSensor = A2;
int lamp2 = A3;
int lm35 = A4;
int lamp1 = A5;
int val_ldr = 0;
int tempC = 0;
int mq2 = 0;
int buzzer = 8;
int irrigacao = 9;
float val_lm35 = 0;
char incomingByte; 
char statusLamp1 = '0';
char statusLamp2 = '0';
char statusArQuarto = '0';
char statusIrrigar = '0';

void setup() {
  pinMode(lamp1, OUTPUT);
  pinMode(lamp2, OUTPUT);  
  pinMode(buzzer, OUTPUT);  
  pinMode(irrigacao, OUTPUT);
    
  //Initialise the IO and ISR
  vw_set_ptt_inverted(true); //Required for DR3100
  vw_setup(2000); //Bits per sec
  vw_set_tx_pin(3);
  
  Serial.begin(9600);
}

void loop() {
  temperatura();
  gas();
  //iluminacaoLdr();
  if (Serial.available() > 0) {
    incomingByte = Serial.read();
    switch(incomingByte){
      case '1':{digitalWrite(lamp1,HIGH);statusLamp1='1';break;}
      case '2':{digitalWrite(lamp1,LOW);statusLamp1='0';break;}
      case '3':{temperatura();break;}
      case '4':{digitalWrite(lamp2,HIGH);statusLamp2='1';break;}
      case '5':{digitalWrite(lamp2,LOW);statusLamp2='0';break;}
      case '6':{statusArQuarto='1';arCondicionado();break;}
      case '7':{statusArQuarto='0';arCondicionado();break;}
      case '8':{irrigar();break;}
      case 's':{Serial.print(statusLamp1);Serial.print(statusLamp2);Serial.print(tempC);Serial.print(statusArQuarto);Serial.print(mq2);break;}
    }
    Serial.print(statusLamp1);
    Serial.print(statusLamp2);
    Serial.print(tempC);
    Serial.print(statusArQuarto);
    Serial.print(mq2);
  }     
}

void temperatura(){
  int val_lm35 = analogRead(lm35); 
  float voltage = val_lm35 * 5.0/1023.0;
  tempC = voltage * 100;
}

void iluminacaoLdr(){
  val_ldr = analogRead(ldr);
  if(val_ldr < 5){
    digitalWrite(lamp2, HIGH);
    statusLamp2 = '1';
  }else{
    digitalWrite(lamp2, LOW);
    statusLamp2 = '0';
  }
}

void arCondicionado(){
  char *msg;
  if(statusArQuarto == '1'){
    char *msg = "1";
    vw_send((uint8_t *)msg, strlen(msg));
    vw_wait_tx(); // Wait until the whole message is gone
  }
  if(statusArQuarto == '0'){
    char *msg = "2";
    vw_send((uint8_t *)msg, strlen(msg));
    vw_wait_tx(); 
  }
}

void gas(){
  //int mq2;
  mq2 = analogRead(gasSensor);
  //Serial.println(mq2,DEC);
  //Serial.println(mq2);
  //delay(100);
  if(mq2 > 800){
    digitalWrite(buzzer,HIGH);
  }else{
    digitalWrite(buzzer,LOW);
  }
}

void irrigar(){
  digitalWrite(irrigacao,HIGH);
  delay(1000);
  digitalWrite(irrigacao,LOW);
}
