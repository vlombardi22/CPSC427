'''
Team Member #1: Maxwell Sherman
Team Member #2: Vincent Lombardi
Zagmail address for team member 1: msherman3@zagmail.gonzaga.edu
Project 2: This project demonstrates the functionality of k-nearest neighbors
Usage: python3 proj3.py
Due: 2019-02-08 18:00 PT
'''

from kNN import *

def classify_person(norm_data_matrix, range_vals, min_vals, labels_vector):
    labels = ['in large doses', 'in small doses', 'not at all']
    raw_labels = ['3', '2', '1']
    k = 3
    percent_video = float(input("percentage of time spent playing video games " +
                                    "over the past year?\n"))
    freq_flier_miles = float(input("Number of frequent flyer miles earned in " +
                                       "the past year?\n"))
    liters_ice_cream = float(input("Number of liters of ice cream eaten in " +
                                       "the past year?\n"))

    in_pt = array([freq_flier_miles, percent_video, liters_ice_cream])
    in_pt_norm = normalize_point(in_pt, min_vals, range_vals)
    
    result = classify(in_pt_norm, norm_data_matrix, labels_vector, k)
    potential = ""
    for i in range(3):
        if result == raw_labels[i]:
            potential = labels[i]
            break
                  
    print ("You will probably like this person: " + potential)

def main():
    data_matrix, labels_vector = file2matrix("datingTestSet2.txt")
    print(data_matrix)
    print()
    print(labels_vector)
    plot_data(data_matrix)
    norm_data_matrix, range_vals, min_vals = normalize(data_matrix)
    #test_classifier(norm_data_matrix, labels_vector)
    classify_person(norm_data_matrix, range_vals, min_vals, labels_vector)
    
    
main()
