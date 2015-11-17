
class Grid(object):
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
        """ adds an object to the grid, no bounds checking """
        prev = None
        if x in self.grid and y in self.grid[x]:
            prev = self.grid[x][y]
        self.grid[x][y] = obj
        return prev
