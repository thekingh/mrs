import itertools

class Edge(object):
    newid = itertools.count().next
    def __init__(self, isExtended, isConnected, isVertical, N1, N2):
        self.id = Edge.newid()
        self.isExtended = isExtended
        self.isConnected = isConnected
        self.isVertical  = isVertical
        self.N1 = N1
        self.N2 = N2

