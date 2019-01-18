'''
Team Member #1: Maxwell Sherman
Team Member #2: N/A
Zagmail address for team member 1: msherman3@zagmail.gonzaga.edu
Project 2: This project demonstrates counting how many words are in a text file
Usage: python3 proj2.py
Due: 2019-01-25 18:00 PT
'''

import re

def openBook():
    while(True):
        bookFile = raw_input("Enter an input file name (must exist): ")
        try:
            bookFile = open(bookFile, 'r')
            break
        except:
            print("Invalid file name. Try again.")
    return bookFile

def tokenize(bookFile):
    tokenizedBook = ""
    for line in bookFile:
        tokenizedBook += (line[:-2] + " ") # replace CRLF with space
    tokenizedBook = re.sub(r"--", r" ", tokenizedBook) # replace pause with space
    tokenizedBook = re.sub(r"[^A-Za-z' ]", r"", tokenizedBook) # remove extraneous characters
    tokenizedBook = re.sub(r" [^Aa] ", r" ", tokenizedBook) # remove 1-word "words" except "a"
    tokenizedBook = re.sub(r" +", r" ", tokenizedBook) # remove extra spaces
    
    return tokenizedBook

def countWords(tokenizedBook):
    tokenizedBook = tokenizedBook.split()
    counter = 0
    for word in tokenizedBook:
        counter += 1
    return counter

def main():
    bookFile = openBook()
    
    tokenizedBook = tokenize(bookFile)
    
    wordCount = countWords(tokenizedBook)
    
    bookFile.close()
    
    # print tokenizedBook
    # print "\n"
    print("Book has " + str(wordCount) + " words.")

main()
