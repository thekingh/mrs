import itertools

class Node (object):
    newid = itertools.count().next
    def __init__(self):
        self.id = Node.newid()
        self.neighbors = {}
        
class Module(Node):
    def __init__(self):
        super(Module, self).__init__()
        self.size = 2
        self.has_inside = False

class Unit(Node):
    def __init__(self):
        super(Unit, self).__init__()

if __name__ == "__main__":
    pass


