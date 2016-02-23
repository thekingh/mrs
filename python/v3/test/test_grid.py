from nose.tools import with_setup
import numpy as np
from mrs.grid import Grid, Module, Node, Unit


class TestGrid(object):
    @classmethod
    def setup(cls):
        cls.a = np.array([[1,0],[1,1]])
        cls.g = Grid(cls.a, Module)

    @classmethod
    def teardown(cls):
        pass

    def test_where(self):
        g = self.g
        query = g.A[(0,1)]
        ans = g.where(query)
        assert ans[0] == 0
        assert ans[1] == 1

    def test_id_from_loc(self):
        g = self.g
        for i, item in np.ndenumerate(g.A):
            assert g.A[i] == g.get(i)


    def test_grid(self):
        g = self.g
        for i, item in np.ndenumerate(g.A):
            if i == (0,1):
                assert item == -1
            else:
                assert item != -1

    def test_grid_constructor_types(self):
        g = self.g
        a = self.a
        assert str(g.D[g.A[(0,0)]]) == "M"
        assert str(g.D[g.A[(0,0)]]) != "N"
        assert str(g.D[g.A[(0,0)]]) != "U"
        g = Grid(a, Unit)
        assert str(g.D[g.A[(0,0)]]) != "M"
        assert str(g.D[g.A[(0,0)]]) != "N"
        assert str(g.D[g.A[(0,0)]]) == "U"
        g = Grid(a, Node)
        assert str(g.D[g.A[(0,0)]]) != "M"
        assert str(g.D[g.A[(0,0)]]) == "N"
        assert str(g.D[g.A[(0,0)]]) != "U"

    def test_to_adj_list(self):
        print self.g.A
        ans = self.g.to_adj_list()
        print ans
        d = {16: [15, 17], 17: [16], 15: [16]}
        assert ans == d
        assert False

    def test_to_graph(self):
        graph = self.g.to_graph()
        print graph.nodes()
        print graph.edges(data=True)
        assert set(graph.nodes()) == set(self.g.D.keys())
        assert False

