'''
Team Member #1: Maxwell Sherman
Team Member #2: Vincent Lombardi
Zagmail address for team member 1: msherman3@zagmail.gonzaga.edu
Project 2: This project demonstrates k-nearest neighbors by identifying varied handwritten numbers
Usage: python3 proj4.py
Due: 2019-02-08 18:00 PT
'''

from kNN import *
from os import listdir

'''
file_lst is the list of file names containing training or test data.  path is
the path from the code to where the data is stored.  train_matrix is an N X 1024
matrix of training/test data where each row is the contents of one of the
training files
'''
def make_matrix(path):
    file_lst = listdir(path)
    # m = len(file_lst)
    train_matrix = []
    for f in file_lst:
        train_matrix.append(img2vector(f, path))
    train_matrix = array(train_matrix)
    return train_matrix

'''
Each training, test file is a 32X32 matrix of 0's and 1's.  The goal is to
transform the matrix into a vector, where 32nd position of the vector begins
with the 0th item in the 1st row the matrix. This function and the following
one unpacks a 2-D array into a 1-D array.
'''
def img2vector(file_name,path):
    file_name = path+file_name
    f = open(file_name, 'r')
    
    # create a string, append each line of the file except newlines,
    # then convert string to a numpy array of ints
    vect = ''
    for line in f:
        vect += line[:-1]
    vect = array([int(char) for char in vect])
    f.close()
    return vect

'''
file_lst is the list of file names containing training or test data.
Every file name begins with a digit, as in 1_160.txt.  This function and
the following one extracts the initial digit and stores it in a list
'''
def make_labels(path):
    return [class_number(class_num) for class_num in listdir(path)]

'''
extract the class number from the file name and return it to make_labels
'''
def class_number(file_name):
    return file_name[:1]

'''
test all of the files in the test directory
'''
def test_classifier(test_path, train_matrix, train_labels, k):
    file_lst = listdir(test_path)
    test_labels = make_labels(test_path)
    error_count = 0
    success_count = 0
    
    # for each test file, classify it and compare the value to correct one (first digit of filename)
    for i in range(len(file_lst)):
        result = classify(img2vector(file_lst[i], test_path), train_matrix, train_labels, k)
        if result == test_labels[i]:
            success_count += 1
        else:
            error_count += 1
    print('Classifying complete.\n')
    
    print('Total Errors: ' + str(error_count))
    print('Error Rate: %.2f%%' % (100 * float(error_count)/float(error_count + success_count)))

'''
main driver function
'''
def main():
    k = 3
    train_path = 'trainingDigits/'
    test_path = 'testDigits/'
    
    print('Training...')
    train_labels = make_labels(train_path)
    train_matrix = make_matrix(train_path)
    print('Training complete.')
    
    print('Classifying...')
    test_classifier(test_path, train_matrix, train_labels, k)
    
main()
