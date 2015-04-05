import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(33, GPIO.OUT)
GPIO.setup(35, GPIO.OUT)
GPIO.setup(37, GPIO.OUT)

red=GPIO.PWM(33, 100)
green=GPIO.PWM(35, 100)
blue=GPIO.PWM(37, 100)
red.start(0)
green.start(0)
blue.start(0)

while 1:
	try:
		for r in range(0, 101, 5):
			red.ChangeDutyCycle(r)
			time.sleep(.05)
		for g in range(0, 101, 5):
			green.ChangeDutyCycle(g)
			time.sleep(.05)
		for b in range(0, 101, 5):
			blue.ChangeDutyCycle(b)
			time.sleep(.05)
		for r in range(100, 0, -5):
			red.ChangeDutyCycle(r)
			time.sleep(.05)
		for g in range(100, 0, -5):
			green.ChangeDutyCycle(g)
			time.sleep(.05)
		for b in range(100, 0, -5):
			blue.ChangeDutyCycle(b)
			time.sleep(.05)
	except:
		break
GPIO.cleanup()