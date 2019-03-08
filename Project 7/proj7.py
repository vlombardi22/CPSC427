'''
Class: CPSC 427
Team Member 1: Maxwell Sherman
Team Member 2: Vincent Lombardi
Submitted By Maxwell Sherman
GU Username: msherman3
File Name: proj7.py
Generates all child states from an initial state of the 8-puzzle until a solution
Reference: An Eight-Puzzle Solver in Python, https://gist.github.com/flatline/8382021
Usage: python3 proj7.py
'''

from copy import deepcopy


class EightPuzzle:
    def __init__(self, parent):
        # state_lst now holds the root, the parent state
        self.state_lst = [[row for row in parent]]

    # displays all states in state_lst
    def display(self):
        index = 1
        goal = [[1, 2, 3],
                [8, 0, 4],
                [7, 6, 5]]

        for state in self.state_lst:
            print("index: " + str(index))  # displays which part of the tree is being printed
            index += 1
            # f_value = state[1] + self.h_value(state, goal)
            # print("f-value: " + str(f_value))
            print("g-value: " + str(state[1]))
            for row in state[0]:
                print(row)
            print()

    # returns (row,col) of value in state indexed by state_idx
    def find_coord(self, value, state):

        # print(state)
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

    def test_display(self, state):
        for row in state:
            print(row)
        print("")

    def best_first(self):
        start = self.state_lst[0]
        goal = [[1, 2, 3],
                [8, 0, 4],
                [7, 6, 5]]
        f_value_start = 0 + self.h_value(start, goal)
        open_queue = [(f_value_start, start, 0)]
        children = []
        closed_queue = []
        while not len(open_queue) == 0:

            cs = (open_queue[0][1], open_queue[0][2])  # take from the front of the queue (best scored state)
            open_queue = open_queue[1:]
            closed_queue.append(cs)  # appends the child that we actually use to the list
            if cs[0] == goal:
                # closed_queue.append(cs)
                return closed_queue

            new_moves = self.generate_states(cs)
            for state in new_moves:
                children.append(state)

            while len(children) != 0:
                child = children[0]
                children.pop(0)

                # if child[0] not in open_queue and child[0] not in closed_queue:
                if (child[0] not in [item[1] for item in open_queue] and
                        child[0] not in [item[1] for item in closed_queue]):
                    # print(child[0])
                    f_value = child[1] + self.h_value(child[0], goal)
                    open_queue.append((f_value, child[0], child[1]))
                    if child[0] == goal:
                        closed_queue.append(child)
                        return closed_queue
                # elif child[0] in simple_open_queue:
                """
                elif child[0] in [item[1] for item in open_queue]:
                    # index = open_queue.index(child[0])
                    if child[1] < open_queue[i][2]:
                        open_queue[index][2] = child[1]
                # elif child[0] in closed_queue:
                elif child[0] in [item[1] for item in closed_queue]:
                    index = closed_queue.index(child[0])
                    if child[1] < closed_queue[index][1]:
                        closed_queue.pop(index)
                        f_value = child[1] + self.h_value(child[0], goal)
                        open_queue.append((f_value, child[0], child[1]))
                        # open_queue.append(child)
                """
                # closed_queue.append(child)
                open_queue.sort()

            # add one with lowest score to self.state_lst
            # use this variable to find the g value too
        # self.state_lst = simple_closed_queue
        # return cs

    def g_value(self, state):
        # depth tree where this state is
        return 0

    def h_value(self, current_state, goal_state):
        wrong_count = 0
        for i, row in enumerate(current_state):
            #try:
            for j, value in enumerate(row):
                # print(str(value) + ' ?= ' + str(goal_state[i][j]) + ' ', end='')
                if value != goal_state[i][j]:
                    wrong_count += 1
            # print()
            #except:
            #    print('error: ' + str(current_state))
        return wrong_count

def main():
    # nested list representation of 8 puzzle. 0 is the blank.
    # This configuration is found on slide 8, E: Two Search Algorithms
    parent = [[2, 8, 3],
              [1, 6, 4],
              [7, 0, 5]]

    # initialize the list of states (state_lst) with the parent
    p = EightPuzzle(parent)

    # generate the states reachable from the parent, i.e., 0th state in state_lst
    p.state_lst = p.best_first()

    # display all states in state_lst
    p.display()


main()
