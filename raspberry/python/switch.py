import tornado.web
import serial
import time

port = "/dev/ttyACM0"
arduino = serial.Serial(port, 9600)
         
class Switch(tornado.web.RequestHandler):
    def get(self, command):
		ret = "GET"
		time.sleep(2)
		try:
			arduino.write(command)
			VALUE_SERIAL=arduino.read(4)
        except Exception, err:
			ret = str(err)
            
        self.write(VALUE_SERIAL)
