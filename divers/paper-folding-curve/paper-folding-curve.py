import sys
import math
import argparse

class CustomFormatter(argparse.ArgumentDefaultsHelpFormatter, argparse.RawDescriptionHelpFormatter):
    pass

# Pour compter le nombre de virage total
def bin(order):
    c=0
    out=0
    while c < order:
        pas=pow(2, c)
        out+=pas
        c+=1
    return out

def path(order, s, e):
    start=s+1
    end=e
    modulo=2
    while s <= e:
        curve(order, start, end, modulo)
        start+=1
        s+=1
    sys.stderr.write("\n")
    
def curve(order, current, end, modulo):
    if current<=end+1:
        if (current % modulo):
            sys.stdout.write(("0", "1")[int(math.ceil(current / modulo)) % 2])
        else:
            curve(order-1, current, end, modulo*2)


# Creating a parser
tab = "\t\t\t"
e = 'Get a stripe of paper.\nFold it into half.\n'
e += 'Fold it into half again in the same direction.\n'
e += 'Fold it the third time into half, in the same direction.\n'
e += 'Then open it, so that every fold is at a right angle.\n'
e += 'Viewing it from the edge you could see a pattern like this:\n'
e += '   _ \n'
e += '|_| |_\n'
e += '     _|\n'
e += "\n"
e += "Example:\n"
e += '--order 3 --start 3 --end 6\n'
e += '--order 5 --start 16 --end 18\n'
e += '--order 10 --start 999 --end 1011\n'
e += '--order 20 --start 100 --end 111\n'
e += '--order 50 --start 562949953421300 --end 562949953421400\n'
e += "\n"
e += "Output:\n"
e += '1100\n'
e += '110\n'
e += '1110010001101\n'
e += '100111001000\n'
e += '10001100100111011001110010011101100011001001110110011100100011011000110010011101100111001001110110001\n'

t = "Paper-folding curve.\n"
t += "Coding Games\n"
t += "https://www.codingame.com/training/community/paper-folding-curve\n"
t += "create by java_coffee_cup\n"
t += "resolved by Xeno_PXD\n"


parser = argparse.ArgumentParser(description=t, 
                                 epilog=e, formatter_class=CustomFormatter)

# Adding arguments
parser.add_argument('-o', '--order', dest='order', nargs=1, type=int, help='An integer N for the order of the curve')
parser.add_argument('-s', '--start', dest='s', metavar=('START'), nargs=1, type=int, help='A starting index')
parser.add_argument('-e', '--end', dest='e', metavar=('END'), nargs=1, type=int, help='A ending index')

# Parsing arguments
args = parser.parse_args()
nb=bin(args.order[0])
if args.e[0]>nb:
    args.e[0]=nb
if args.s[0]<0:
    args.s[0]=0
#print(str(args.order) +" "+ str(args.s) +" "+ str(args.e))
path(args.order[0], args.s[0], args.e[0])