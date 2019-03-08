'''
Class: CPSC 427
Team Member 1: Maxwell Sherman
Team Member 2: Vincent Lombardi
Submitted By Maxwell Sherman
GU Username: msherman3
File Name: proj7.py
Runs a best-first heuristic search for a solution from an initial state of an 8-puzzle
Reference: An Eight-Puzzle Solver in Python, https://gist.github.com/flatline/8382021
Usage: python3 proj7.py
'''

from copy import deepcopy

class EightPuzzle:
    # init method
    def __init__(self, start):
        # state_lst now holds the root, the parent state
        self.state_lst = [[row for row in start]]

    # displays all states in self.state_lst
    def display(self):
        index = 1
        for state in self.state_lst:
            print("index: " + str(index))  # displays which part of the tree is being printed
            index += 1
            print("g-value: " + str(state[1]))
            for row in state[0]:
                print(row)
            print()
    
    # returns (row,col) of value in state indexed by state_idx
    def find_coord(self, value, state):
        for row in range(3):
            for col in range(3):
                if state[row][col] == value:
                    return row, col
    
    # returns list of (row, col) tuples which can be swapped for blank
    # these form the legal moves of the state indexed by state_idx
    def get_new_moves(self, state):
        row, col = self.find_coord(0, state)  # get row, col of blank
        
        moves = []
        if col > 0:
            moves.append((row, col - 1))  # go left
        if row > 0:
            moves.append((row - 1, col))  # go up
        if col < 2:
            moves.append((row, col + 1))  # go right
        if row < 2:
            moves.append((row + 1, col))  # go down
        
        return moves
    
    # generates all child states for the state indexed by state_idx
    # in state_lst
    # appends child states to the list
    def generate_states(self, state):
        temp_lst = []
        g_val = state[1]
        # print(g_val)
        g_val += 1
        
        # get legal moves
        move_lst = self.get_new_moves(state[0])
        # blank is a tuple, holding coordinates of the blank tile
        blank = self.find_coord(0, state[0])
        # tile is a tuple, holding coordinates of the tile to be swapped
        # with the blank
        for tile in move_lst:
            # create a new state using deep copy
            # ensures that matrices are completely independent
            child = deepcopy(state[0])
            
            # move tile to position of the blank
            child[blank[0]][blank[1]] = child[tile[0]][tile[1]]
            # set tile position to 0
            child[tile[0]][tile[1]] = 0
            # append child state to the list of states.
            
            temp_lst.append((child, g_val))
        return temp_lst
    
    # runs a best-first heuristic search to find the goal state given the start state
    # start state is in self.state_lst[0], and goal is hard-coded
    def best_first(self, start, goal):
        # find the f-value of the starting state before looping
        f_value = 0 + self.h_value(start, goal)
        
        # open queue is states we have not yet viewed
        # each item in open_queue is a tuple of(f-value, state, and g-value)
        # f-value is first so the list can easily be sorted
        # it is instantiated with the staritng value
        open_queue = [(f_value, start, 0)]
        
        # children is a temporary list where
        # each item in children is a tuple of (state, g-value)
        # order doesn't matter
        children = []
        
        # closed queue is states we have viewed
        # it will be printed at the end to show the path taken to the goal
        # each item in closed_queue has the same structure as those in children
        closed_queue = []
        
        # loop through the open queue (if we find the goal, we'll break)
        while not len(open_queue) == 0:
            cs = (open_queue[0][1], open_queue[0][2])  # take from the front of the queue (best scored state)
            open_queue = open_queue[1:]  # dequeue
            closed_queue.append(cs)  # append that item to closed_queue
            
            # if we found the goal, break
            if cs[0] == goal:
                return closed_queue
            
            # else, generate the possible next moves
            children = self.generate_states(cs)
            
            # loop through these new possible state
            while len(children) != 0:
                child = children[0]
                children.pop(0)
                
                # if child has not been seen before, add it to open queue
                if (child[0] not in [item[1] for item in open_queue] and
                        child[0] not in [item[1] for item in closed_queue]):
                    f_value = child[1] + self.h_value(child[0], goal)
                    open_queue.append((f_value, child[0], child[1]))
                    if child[0] == goal:
                        closed_queue.append(child)
                        return closed_queue
                
                # if this possible move is already in the open queue
                # (not viewed but will be),
                # if it takes less moves to get here, update the g-value
                # of the one in open_queue
                elif child[0] in [item[1] for item in open_queue]:
                    # index = open_queue.index(child[0])
                    if child[1] < open_queue[i][2]:
                        open_queue[index][2] = child[1]
                
                # same situation, but if it's in the closed queue
                elif child[0] in [item[1] for item in closed_queue]:
                    index = closed_queue.index(child[0])
                    if child[1] < closed_queue[index][1]:
                        closed_queue.pop(index)
                        f_value = child[1] + self.h_value(child[0], goal)
                        open_queue.append((f_value, child[0], child[1]))
                
                # sort the open queue so the best item is at the front
                open_queue.sort()
    
    # finds the h-value of a given state compared to the goal state
    # heuristic: how many tiles are wrong (lower is better)
    def h_value(self, current_state, goal_state):
        wrong_count = 0
        for i, row in enumerate(current_state):
            for j, value in enumerate(row):
                if value != goal_state[i][j]:
                    wrong_count += 1
        return wrong_count

# main driver function
def main():
    start = [[2, 8, 3],
             [1, 6, 4],
             [7, 0, 5]]
    
    goal  = [[1, 2, 3],
             [8, 0, 4],
             [7, 6, 5]]
    
    # initialize the list of states (state_lst) with the parent
    p = EightPuzzle(start)

    # generate the states reachable from the parent, i.e., 0th state in state_lst
    p.state_lst = p.best_first(start, goal)

    # display all states in state_lst
    p.display()


main()
