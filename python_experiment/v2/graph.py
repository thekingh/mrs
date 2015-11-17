
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
