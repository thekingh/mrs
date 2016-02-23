class OpType:
    EXTEND, CONNECT, CONTRACT, DISCONNECT = range(4)

class Op(object):
    def __init__(self, op, loc, isExt):
        self.op    = op
        self.loc   = loc
        self.isExt = isExt

