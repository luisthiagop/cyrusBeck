from pprint import pprint

import numpy

from classes.graphics import *

from classes.graphics import GraphWin

from copy import copy


class CyrusBeck:

    tU = 1
    tL = 0
    t = 0
    novalinha = Line(Point(0,0),Point(0,0))

    def algoritmo(self,w,d,f,n,linhas,poligono):

        # dv = np.dot(d,n)
        cont =0

        for i in range(int(len(n))):
            cont=cont+1
            dv = numpy.dot(n[i], d[0])
            wv = numpy.dot(w[i],n[i])
            pprint(wv)

            if(dv ==0):
                continue

            else:
                t = -1*(wv/dv)

                if (dv>0):
                    if(t>1):
                        print ( "Segmento Rejeitado")
                        break
                    else:
                        self.tL = max(t,self.tL)
                else:
                    if (t<0):
                        print ("Segmento Rejeitado")
                        break
                    else:
                        self.tU = min(t,self.tU)

        if (self.tL >= self.tU):
            print ("Segmento Rejeitado")
        elif( (cont == int(len(n)))):
            self.novalinha = Line(
                Point(
                    linhas[0].p1.x+(linhas[0].p2.x-linhas[0].p1.x)*self.tL,
                    linhas[0].p1.y + (linhas[0].p2.y - linhas[0].p1.y) * self.tL
                ),
                Point(
                    linhas[0].p1.x + (linhas[0].p2.x - linhas[0].p1.x) * self.tU,
                    linhas[0].p1.y + (linhas[0].p2.y - linhas[0].p1.y) * self.tU
                )
            )

        return self.novalinha






    def main(self):
        return