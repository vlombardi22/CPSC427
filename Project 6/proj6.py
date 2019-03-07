
'''
Class: CPSC 427
Team Member 1: Maxwell Sherman
Team Member 2: Vincent Lombardi
Submitted By Maxwell Sherman
GU Username: msherman3
File Name: proj6.py
Generates all child states from an initial state of the 8-puzzle until a solution
Reference: An Eight-Puzzle Solver in Python, https://gist.github.com/flatline/8382021
Usage: python3 proj6.py
'''

from copy import deepcopy


class EightPuzzle:
    def __init__(self, parent):
        # state_lst now holds the root, the parent state
        self.state_lst = [[row for row in parent]]

    # displays all states in state_lst
    def display(self):
        index = 1
        for state in self.state_lst:
            print(index)  # displays which part of the tree is being printed
            index += 1
            for row in state:
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

        # get legal moves
        move_lst = self.get_new_moves(state)
        # blank is a tuple, holding coordinates of the blank tile
        blank = self.find_coord(0, state)
        # tile is a tuple, holding coordinates of the tile to be swapped
        # with the blank
        for tile in move_lst:
            # create a new state using deep copy
            # ensures that matrices are completely independent

            child = deepcopy(state)

            # move tile to position of the blank
            child[blank[0]][blank[1]] = child[tile[0]][tile[1]]
            # set tile position to 0
            child[tile[0]][tile[1]] = 0
            # append child state to the list of states.

            temp_lst.append(child)
        return temp_lst

    def test_display(self, state):
        for row in state:
            print(row)
        print()

    def breadth_first(self, start):
        goal = [[1, 2, 3],
                [8, 0, 4],
                [7, 6, 5]]
        index = 1
        closed_queue = []
        children = []
        open_queue = [start]
        print(index)
        self.test_display(start)

        while len(open_queue) != 0:
            cur = open_queue[0]
            open_queue = open_queue[1:]
            if cur == goal:
                return True

            closed_queue.append(cur)
            new_moves = self.generate_states(cur)
            for state in new_moves:
                children.append(state)

            while len(children) != 0:
                child = children[0]
                children = children[1:]
                # self.test_display(child)
                if child not in open_queue and child not in closed_queue:
                    index += 1
                    print(index)
                    self.test_display(child)
                    open_queue.append(child)
                    if child == goal:
                        return True
        return False


def main():
    # nested list representation of 8 puzzle - 0 is the blank
    # this configuration is found on slide 8, E: Two Search Algorithms
    start = [[2, 8, 3],
             [1, 6, 4],
             [7, 0, 5]]

    # initialize the list of states (state_lst) with the parent
    p = EightPuzzle(start)

    # new version
    p.breadth_first(start)

    # display all states in state_lst
    # p.display()


main()

