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
        bookFile = input("Enter an input file name (must exist): ")
        try:
            bookFile = open(bookFile, 'r')
            break
        except:
            print("Invalid file name. Try again.")
    return bookFile

# takes a raw text file of a book and tokenizes it into a single line of all lowercase words
# words are included only if they contain just alpha characters and apostrophes
# this is not an accurate word count! it's just done this way for consistency in grading
def tokenize(bookFile):
    tokenizedBook = bookFile.read() # entire book into string
    
    tokenizedBook = tokenizedBook.replace("\n", " ").replace("\r", " ") # replace CRLFs with spaces
    tokenizedBook = tokenizedBook.lower() # make everything lowercase
    tokenizedBook = re.sub(r" [^ai] ", r" ", tokenizedBook) # remove 1-word "words" except "a"
    
    tokenizedBook = tokenizedBook.split() # split book into a list of words
    
    tokenizedBook = [word for word in tokenizedBook if not re.search(r"[^a-z' ]", word)] # filter out words with non-alpha or apostrophe characters
    tokenizedBook = [word for word in tokenizedBook if not word[0] == "'" and not word[-1] == "'"] # filter out words that start and end with apostrophe
    
    return tokenizedBook

# takes a tokenized corpus and returns a dict of how many words start with each letter
def countWords(tokenizedBook):
    wordCountDict = {}
    for word in tokenizedBook:
        if word[0] in wordCountDict:
            wordCountDict[word[0]] += 1
        else:
            wordCountDict[word[0]] = 1
    return wordCountDict

# prints everything from a dictionary in order and formatted nicely
# done by dumping every dict value into a list and sorting it
def fancyPrint(wordCountDict):
    printList = []
    for letter in wordCountDict:
        printList.append((letter, wordCountDict[letter]))
    printList.sort()
    for letter in printList:
        print(letter[0].upper() + ": " + str(letter[1]))

# main driver function
def main():
    bookFile = openBook()
    
    tokenizedBook = tokenize(bookFile)
    
    wordCountDict = countWords(tokenizedBook)
    
    bookFile.close()
    
    fancyPrint(wordCountDict)

main()
