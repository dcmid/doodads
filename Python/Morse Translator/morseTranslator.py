import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(15, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(32, GPIO.OUT)

mToE={'.-':'A', '-...':'B', '-.-.':'C', '-..':'D', '.':'E', '..-.':'F', '--.':'G', '....':'H', '..':'I', '.---':'J', '-.-':'K', '.-..':'L', '--':'M', '-.':'N', '---':'O', '.--.':'P', '--.-':'Q', '.-.':'R', '...':'S', '-':'T', '..-':'U', '...-':'V', '.--':'W', '-..-':'X', '-.--':'Y', '--..':'Z', '-----':'0', '.----':'1', '..---':'2', '...--':'3', '....-':'4', '.....':'5', '-....':'6', '--...':'7', '---..':'8', '----.':'9'}

#determine average dot and space lengths
def getAvgDaS():
	print "Please tap out the letter S '...' to help us get an idea of your dot lengths."
	tapSum=0
	spaceSum=0
	while GPIO.input(15):
		time.sleep(.01)
	for i in range(0,3):
		startTime=time.time()
		GPIO.output(32,True)
		while not GPIO.input(15):
			time.sleep(.01)
		tapSum+=time.time()-startTime
		GPIO.output(32,False)
		if i<2:
			startTime=time.time()
			while GPIO.input(15):
				time.sleep(.01)
			spaceSum+=time.time()-startTime
	return {'Dot Length':tapSum/3,'Space Length':spaceSum/2}


#determine if input is a dot or a dash based on pulse length
def getDoD(dotLength):
	startTime=time.time()
	GPIO.output(32,True)
	while not GPIO.input(15):
		time.sleep(.01)
	elapsed=time.time()-startTime
	GPIO.output(32,False)
	if elapsed>dotLength*2:
		return "-"
	return "."

#determine if pause breaks a dot/dash, letter, or word based on length
def getPause(pauseLength):
	startTime=time.time()
	while GPIO.input(15):
		if time.time()-startTime>pauseLength*15:
			return 'end'
		time.sleep(.01)
	elapsed=time.time()-startTime
	#a pause less than 2 units in duration is a dot break
	if elapsed<pauseLength*2:
		return 'dot or dash'
	#a pause greater than 2 units but less than 5 units is a letter break
	if elapsed<pauseLength*5:
		return 'letter'
	#a pause greater than 5 units but less than 15 units is a word break
	return 'word'

def translate(dotString):
	return mToE[dotString]