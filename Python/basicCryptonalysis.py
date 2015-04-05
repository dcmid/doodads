solution=""
#make list of all possible words
dictionary=open('dictionary.lst').read()
dictionary=dictionary.split('\n')

#make list (sorted longest len to shortest) of ciphered words
#ciphered = sorted(raw_input().split(), key=lambda x: len(x), reverse=True)
ciphered = raw_input().split()
#dictionary to contain decyphered letter reationships
key={}

#returns pattern of changing letters; similar to rhyme scheme (getLetterPattern(hello)=="ABCCD")
def getLetterPattern(word):
	letters={}
	letters.update({word[0]:'A'})
	nextLetter='B'
	pattern="A"
	for i in range(1,len(word)):
		if letters.has_key(word[i]):
			pattern+=letters.get(word[i])
		else:
			letters.update({word[i]:nextLetter})
			pattern+=nextLetter
			nextLetter=chr(ord(nextLetter)+1)
	return pattern

#finds word in dictionary with same pattern as given word, adds word to solution, and adds new letter relationships to key
def solveWord(ciphWord):
	global solution
	pattern=getLetterPattern(ciphWord)
	for word in dictionary:
		if len(word)==len(pattern) and getLetterPattern(word)==pattern:
			for a,b in zip(ciphWord,word):
				key.update({a:b})
			solution+=word+" "
			break

for word in ciphered:
	solveWord(word)

print solution