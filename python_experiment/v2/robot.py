
class Robot(object):
    """ Modular robot class
    Attributes:
        N (int): Number of modules in robot
        master (Node): master node, start point of the spatial representation
        isSpatialCurrent (bool): whether the spatial rep reflects the current relational rep
        spatial (Grid): last generated spatial representation
    """
    def __init__(self, N, master = None):
        self.N = N
        self.master = master
        self.isSpatialCurrent = False
        self.spatial = None

    def generate_spatial(self):
        """ takes the relational robot and generates a spatial representation
        """
        pass

    def recalculate_neighbors(self):
        """ recalculate edges in relational representation using a spatial
        representation, generating one if necessary
        """
        if not isSpatialCurrent:
            self.generate_spatial()
            isSpatialCurrent = True
        self.spatial.recalculate_neighbors()

    def display(self):
        """ displays the robot, generating a spatial rep if necessary
        """
        if not isSpatialCurrent:
            self.generate_spatial()
            isSpatialCurrent = True
        self.spatial.display()

