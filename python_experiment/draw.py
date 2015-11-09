import itertools
from collections import deque

class GridArm(object):
    """ all arms start out horizontal """
    def __init__(self, vert = False):
        self.vert = vert

    def __repr__(self):
        v = 'Horizontal'
        if self.vert:
            v = 'Vertical'
        return '<' + v + ' GridArm object>'

    def __str__(self):
        if self.vert == True:
            return '|'
        else:
            return '-'


class Module(object):
    newid = itertools.count().next
    """ Neighbors are numbered N-E-S-W, 0-1-2-3 """
    def __init__(self):
        self.id = Module.newid()
        self.neighbors = {}

    def validate_neighbors(self):
        assert len(self.neighbors < 4)

    def alter_arm(self, arm, direction = 0, connection = None):
        """ alters an arm extention direction 0 is the identity function
        Args:
            arm (int): Arm to alter
            direction (int): valid input set(-1, 0, 1) -1 contracts arm 1 expands
            connection (bool): None is the identity function True sets connected
        """
        self.neighbors[arm][1] += direction
        if connection is not None:
            self.neighbors[arm][2] = connection

    def alter_both(self, arm, direction = 0, connection = None):
        N = self.neighbors[arm][0]
        N.alter_arm((arm + 2) % 4, direction, connection) # hacky opposite arm TODO
        self.alter_arm(arm, direction, connection)

    def __repr__(self):
        return '<Module: ' + str(self.id) + '>'

    def __str__(self):
        return 'O'

#################
### INTERFACE ###
#################

    def expand(self, arm):
        alter_both(self, arm, 1)

    def contract(self, arm):
        alter_both(self, arm, -1)

    def connect(self, arm):
        alter_both(self, arm, connection = True)
    
    def disconnect(self, arm):
        alter_both(self, arm, connection = False)

class Graph(object):
    """ A relational representation of identical modules
    Attributes:
        master (Module): The master node around which the robot is slaved. This
            is simply a semantic and the master node is displayed no differently
            its only application is as a base to generate the spatial
            representation from
    """
    def __init__(self, master = None):
        self.master = master

    def get_coords(self):
        def add_neighbors_to_queue(Q, node, visited):
            curr, curr_coord = node
            coord = list(curr_coord)
            for n in curr.neighbors:
                neighbor, extension, connection = curr.neighbors[n]
                if neighbor.id not in visited:
                    if n == 0: #North
                        coord[1] -= 1 + extension
                    if n == 1: #East
                        coord[0] += 1 + extension
                    if n == 2: #South
                        coord[1] += 1 + extension
                    if n == 4:
                        coord[0] -= 1 + extension
                    Q.append((neighbor, tuple(coord)))
                    visited.add(neighbor.id)
            return Q, visited

        def update_min(min_coords, curr):
            print curr
            to_return = [0,0]
            for i, m in enumerate(min_coords):
                if m > curr[i]:
                    to_return[i] = curr[i]
                else:
                    to_return[i] = min_coords[i]
            return to_return

            return min_coords
        def update_max(max_coords, curr):
            print curr
            to_return = [0,0]
            for i, m in enumerate(max_coords):
                if m < curr[i]:
                    to_return[i] = curr[i]
                else:
                    to_return[i] = max_coords[i]
            return to_return
        visited = set()
        current = self.master
        max_coords = (1,1)
        min_coords = (0,0)
        Q = [] #builds out list of coords
        i = 0
        Q.append((current, (0,0))) # coords x (left pos) , y (down pos)
        visited.add(current.id)
        while i < len(Q): #TODO check this is right
            node = Q[i]
            curr, curr_coord = node
            max_coords = update_max(max_coords, curr_coord)
            min_coords = update_min(min_coords, curr_coord)
            Q, visited = add_neighbors_to_queue(Q, node, visited)
            print Q
            i += 1
        print min_coords, max_coords
        return Q, min_coords, max_coords

    def to_grid(self):
        Q, min_coords, max_coords = self.get_coords()
        minx, miny = min_coords
        maxx, maxy = max_coords
        G = Grid(maxx - minx, maxy - miny)
        for node, (x, y) in Q:
            G.add_node(x - minx, y - miny, node)
        return G

class Grid(object):
    """grid is accessed [x][y] """
    def __init__(self, width, height):
        self.width = width
        self.height = height
        self.grid = {w : {} for w in range(width)}

    def display(self):
        s = ''
        for y in range(self.height):
            for x in range(self.width):
                if x in self.grid and y in self.grid[x]:
                    s += str(self.grid[x][y])
                else:
                    s += ' '
            s += '\n'
        print s

    def add_node(self, x, y, obj = None):
        prev = None
        if x in self.grid and y in self.grid[x]:
            prev = self.grid[x][y]
        self.grid[x][y] = obj
        return prev

def main():
#    g = Grid(5,5)
#    g.grid[3][3] = GridArm()
#    g.grid[2][1] = GridArm(True)
#    g.grid[1][2] = Module()
    
    a = Module()
    b = Module()
    a.neighbors[0] = (b, 0, 1)
    b.neighbors[2] = (a, 0, 1)
    gr = Graph(a)
    g = gr.to_grid()
    print g.grid
    g.display()
if __name__ == "__main__":
    main()


