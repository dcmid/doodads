import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(15, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(40, GPIO.OUT)


while True:
	try:
		startTime=time.time()
		while not GPIO.input(15):
			time.sleep(.05)
		elapsedTime=time.time()-startTime
		if elapsedTime>.05:
			print elapsedTime
			startTime=time.time()
			while time.time()<startTime+elapsedTime:
				GPIO.output(40,1)
				time.sleep(.05)
		GPIO.output(40,0)
		time.sleep(.05)
	except:
		break

GPIO.cleanup()