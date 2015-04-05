import time
import morseTranslator

morse=''
translated=''
space='dot or dash'
letter=''
word=[]
sentence=[]
avgLengths = morseTranslator.getAvgDaS()
print avgLengths

while morseTranslator.GPIO.input(15):
	time.sleep(.01)

letter+=morseTranslator.getDoD(avgLengths['Dot Length'])
while True:
	space=morseTranslator.getPause(avgLengths['Space Length'])
	if space=='dot or dash':
		letter+=morseTranslator.getDoD(avgLengths['Dot Length'])
	elif space=='letter':
		word.append(letter)
		letter=''
		letter+=morseTranslator.getDoD(avgLengths['Dot Length'])
	elif space=='word':
		word.append(letter)
		sentence.append(word)
		letter=''
		word=[]
		letter+=morseTranslator.getDoD(avgLengths['Dot Length'])
	else:
		word.append(letter)
		sentence.append(word)
		break

for wo in sentence:
	for le in wo:
		try:
			morse+=le+' '
			translated+=morseTranslator.translate(le)
		except:
			translated+='?'
	translated+=' '
	morse+='\t'
print morse
print translated

morseTranslator.GPIO.cleanup()