class OpType:
    EXTEND, CONNECT, CONTRACT, DISCONNECT = range(4)

class Op(object):
    def __init__(self, op, locs):
        self.op    = op
        self.locs  = locs
        self.isExt = isExt

