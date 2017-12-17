import sys, os

hangman=[]
hangman.append("+--+\n|   \n|   \n|\\")
hangman.append("+--+\n|  o\n|   \n|\\")
hangman.append("+--+\n|  o\n|  |\n|\\")
hangman.append("+--+\n|  o\n| /|\n|\\")
hangman.append("+--+\n|  o\n| /|\\\n|\\")
hangman.append("+--+\n|  o\n| /|\\\n|\\/")
hangman.append("+--+\n|  o\n| /|\\\n|\\/ \\")

def replaceAll(string, old, new):
    string2 = string
    for c in string:
        string2 = string.replace(old, new)
        string=string2
    return string

def getNbrErrors(word, chars):
    counter=0
    for cc in chars:
        trouve=True
        if cc != " ":
            trouve=False
            for cw in word:
                if cw.upper() == cc.upper():
                    trouve=True
                    word = replaceAll(word, cw, ' ')
            if trouve==False: 
                counter += 1
    return counter

def completeWord(word, chars):
    out=""
    for cw in word:
        if cw != " ":
            trouve=False
            for cc in chars:
                if cc != " ":
                    if cw.upper() == cc.upper():
                        trouve=True
        else:
            trouve=True
            
        if trouve==False: 
            out = out + "_"
        else:
            out = out + cw
    return out; 

os.system('start /wait cmd /c')
word = input("Entrez un mot à devinez : ")
os.system('cls')

counter=0
chars = ""
print(hangman[counter])
while counter<6:
    char = input("Entrez une lettre : ")
    chars = chars + " " + char
    counter=getNbrErrors(word, chars)
    sys.stdout.write(hangman[counter]+"\n")  
    sys.stdout.write(completeWord(word, chars)+"\n")
print("Perdu !")
