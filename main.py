# -*- coding: utf-8 -*-


from pprint import pprint

from classes.Objeto import Objeto

from classes.CyrusBeck import *


from classes.graphics import GraphWin

from classes.Poligono import Poligono


class Main:

    def main():
        one_win = GraphWin("pre", 800, 800)
        two_win = GraphWin("pos", 800, 800)
        # o.drawObjeto(win)

        p = Poligono()
        p.makePoligono()
        p.calcNormal()
        p.makeVet()

        p.drawPoligono([one_win,two_win])
        # p.drawPoligono(two_win)
        p.defineF()
        o = Objeto()
        o.makeObjeto()
        o.makeVet()
        p.defineW(o)
        o.drawObjeto(one_win)


        cb = CyrusBeck()
        novas  =  cb.algoritmo(p.w,o.objetoVet,p.f,p.normalVetor,o.objetos,p)

        novas.draw(two_win)
        one_win.getMouse()
        two_win.getMouse()



    main()


