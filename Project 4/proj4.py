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

A simpler example:
ex = matrix([[1,2],[3,4],[5,6]])
vect = zeros((1,6))
for i in range(3):
    for j in range(2):
        vect[0,2*i+j] = sample[i,j]
vect now contains: [1,2,3,4,5,6]
'''
def img2vector(file_name,path):
    file_name = path+file_name
    # vect = zeros((1,1024)) #store zeros in a 1X1024 vector.
    f = open(file_name, 'r')
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

def timer_function(timer, m):
    #I use this to print a periodic message to the user so that s/he
    #knows that things are progressing well
    print('.', end='')

'''
test all of the files in the test directory
'''
def test_classifier(test_path, train_matrix, train_labels, k):
    file_lst = listdir(test_path)
    test_labels = make_labels(test_path)
    
    for i in range(len(file_lst)):
        result = classify(img2vector(file_lst[i], test_path), train_matrix, train_labels, k)
        # with open(test_path + file_lst[i]) as f:
        #     for line in f:
        #         print(line, end='')
        if result == test_labels[i]:
            print("success: " + result + ", " + test_labels[i])
        else:
            print("err " + result + ", " + test_labels[i])
        # print('\n')
    
    # print("Total Errors: " + str(errors))
    # print("Error Rate: " + str(float(errors)/float(m)))
         
def main():
    k = 3
    train_path = 'trainingDigits/'
    test_path = 'testDigits/'
    
    '''
    matrix = make_matrix(train_path)
    lst = make_labels(train_path)
    for i in range(5):
        print(lst[i])
        print(list(matrix[i]))
        print()
    '''
    train_labels = make_labels(train_path)
    # for i in range(200):
    #     print(train_labels[5])
    train_matrix = make_matrix(train_path)
    
    test_classifier(test_path, train_matrix, train_labels, k)
    
main()
