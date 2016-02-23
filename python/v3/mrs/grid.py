import numpy as np
import networkx as nx
from util import inbound_neighbors

import itertools

class Node (object):
    newid = itertools.count().next
    def __init__(self):
        self.id = Node.newid()
        self.neighbors = {}
    def __repr__(self):
        return "N"
        
class Module(Node):
    def __init__(self):
        super(Module, self).__init__()
        self.size = 2
        self.has_inside = False
        self.sub_modules = []

    def __repr__(self):
        return "M"

class Unit(Node):
    def __init__(self):
        super(Unit, self).__init__()
    def __repr__(self):
        return "U"

class Graph(object):

    @classmethod
    def performOp(graph, op):
        """ Performs the operation on the networkx graph """
        pass

class Grid(object):
    """Grid is a sparse 2d representation of a robot.
    
    Grid is accessed [x][y]. Grid is printed (0,0) botom left

    """
    def __init__(self, bools, obj):
        """ Initializes a new grid with objects of type obj

        Args:
            bools (nparray): numpy integer indicator array of 0 is no module 1 is module
            obj (type): type of object to put in grid
        """
        self.A = -np.ones_like(bools, dtype=np.dtype(int))
        self.D = {}
        self.E = {} #edge tuples???
        for i, item in np.ndenumerate(bools):
            if item == 1:
                new = obj()
                self.D[new.id] = new
                self.A[i]      = new.id

    def get(self, coord):
        """ Gets object id from coordinate (x, y) """
        return self.A[coord]

    def where(self, id):
        """ Searches grid for id returns first instance of id"""
        if id == -1:
            return None
        r = np.where(self.A == id)
        return r[0][0], r[1][0]

    def to_adj_list(self):
        adj = {d : {} for d in self.D.keys()}
        for i, node in np.ndenumerate(self.A):
            if node != -1:
                for n in inbound_neighbors(i, self.A):
                    coord = tuple(n)
                    if self.A[coord] != -1:
                        adj[node][self.A[coord]] = {'weight':1}
        return adj
    
    def to_graph(self):
        adj = self.to_adj_list()
        return nx.from_dict_of_dicts(adj)

    @classmethod
    def init_from_graph(cls, obj):
        pass

    def perform_ops(self, oplist):
        """Performs a list of ops in parallel on grid"""
        G = self.to_graph()
        for op in oplist:
            Graph.performOp(G, op)



