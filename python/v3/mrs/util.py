import numpy as np
def neighbors(c):
    x, y = c
    return np.array([(x - 1, y    ),
                     (x    , y - 1),
                     (x + 1, y    ),
                     (x    , y + 1)])
def inbounds(c, shape):
    for i, bound in enumerate(shape):
        if c[i] >= bound or c[i] < 0:
            return False
    return True

def inbound_neighbors(c, arr):
    """ Gets neighbors within the bounds of arr"""
    return [n for n in neighbors(c) if inbounds(n, arr.shape)]

class Direction(object):
    def __init__(self):
        pass

