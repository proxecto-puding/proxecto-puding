void setup() {
  
  Serial.begin(9600);
}

void loop() {
  
  String data = Serial.readString();
  delay(1000);
  Serial.print(data);
}
