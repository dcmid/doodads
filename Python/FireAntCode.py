#import RPi.GPIO as GPIO
# GPIO.setmode(GPIO.BOARD)
import sys, tty, termios, time
#from termcolor import colored, cprint

# GPIO.setup(3, GPIO.OUT)
# GPIO.setup(5, GPIO.OUT)
# leftMotor = GPIO.PWM(3, 214.6)
# rightMotor = GPIO.PWM(5, 214.6)

# leftMotor.start(0)
# rightMotor.start(0)

def getch():
    fd = sys.stdin.fileno()
    old_settings = termios.tcgetattr(fd)
    try:
        tty.setraw(fd)
        ch = sys.stdin.read(1)
    finally:
        termios.tcsetattr(fd, termios.TCSADRAIN, old_settings)
    return ch

def motors_forward():
    print "FORWARD"
    set("servo", "130")

def motors_reverse():
    print "REVERSE"
    set("servo", "50")
	
def motors_stop():
    print "STOP"
    set("servo", "90")

# def motors_left():
#     print "LEFT"
#     leftMotor.ChangeDutyCycle(50)
#     rightMotor.ChangeDutyCycle(0)

# def motors_right():
#     print "RIGHT"
#     leftMotor.ChangeDutyCycle(0)
#     rightMotor.ChangeDutyCycle(50)

def set(property, val):
    try:
        f = open("/sys/class/rpi-pwm/pwm0/" + property, 'w')
        f.write(val)
        f.close()
    except:
        print("Error writing to: " + property + " value: " + val)


# leftMotor.ChangeDutyCycle(0)
# rightMotor.ChangeDutyCycle(0)

# bold = "\033[1m"
# reset = "\033[0;0m"
# cprint("FireAnt Control Program", 'blue')

set("delayed", "0")
set("mode", "servo")
set("servo_max", "180")
set("active", "1")
set("servo", "90")


currentChar = "";

while True:
    char = getch()
    if(char!=currentChar):
        currentChar = char
        if(char == "w"):
            motors_forward()
    
        elif(char == "s"):
            motors_reverse()
        
        # elif(char == "d"):
        #     motors_right()
            
        # elif(char == "a"):
        #     motors_left()
    
        elif(char == "x"):
            print "Program Ended"
            motors_stop()
            break

        else:
            motors_stop()

    	   	

#GPIO.cleanup()
