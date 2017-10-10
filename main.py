# -*- coding: utf-8 -*-


from pprint import pprint

from classes.Objeto import Objeto

from classes.CyrusBeck import *


from classes.graphics import GraphWin

from classes.Poligono import Poligono


class Main:

    def main():

        win = GraphWin("pre",800,800)
        # o.drawObjeto(win)

        p = Poligono()
        p.makePoligono()
        p.calcNormal()
        p.makeVet()

        p.drawPoligono(win)
        p.defineF()
        o = Objeto()
        o.makeObjeto()
        o.makeVet()
        p.defineW(o)
        o.drawObjeto(win)


        cb = CyrusBeck()
        cb.algoritmo(p.w,o.objetoVet,p.f,p.normalVetor,o.objetos,p)

        win.getMouse()



    main()


