from pprint import pprint

from classes.graphics import *

from copy import deepcopy



class Poligono:

    normal = []
    ladoVetor = []
    normalVetor = []

    def makePoligono(self):
        self.n = 5 #input('numero de lados do poligono: ')
        self.lado = []
        self.pm = []
        self.lado.append(Line(Point(100, 100), Point(200, 100)))
        self.lado.append(Line(Point(200, 100), Point(200, 200)))
        self.lado.append(Line(Point(200, 200), Point(150, 250)))
        self.lado.append(Line(Point(150, 250), Point(100, 200)))
        self.lado.append(Line(Point(100, 200), Point(100, 100)))
        for i in range(int((self.n))):

            # if i==0 :
            #     x1 = input('digite a cordenada x do vertice inicial do lado '+ str(i+1) +' :')
            #     y1 = input('digite a cordenada y do vertice inicial do lado '+ str(i+1) +' :')
            # else:
            #     x1 = x2
            #     y1 = y2
            #
            # if(i <  int(self.n)-1  ):
            #     x2 = input('digite a cordenada x do vertice final do lado ' + str(i+1) + ' :')
            #     y2 = input('digite a cordenada y do vertice final do lado ' + str(i+1) + ' :')
            #     self.lado.append(Line(Point(x1,y1),Point(x2,y2)))
            # else:
            #     self.lado.append(Line(Point(x1, y1), Point(self.lado[0].p1.x, self.lado[0].p1.y)))

            self.pm.append(Point((self.lado[i].p1.x+self.lado[i].p2.x)/2,(self.lado[i].p1.y+self.lado[i].p2.y)/2))

    def calcNormal(self):
        for i in range(len(self.lado)):
            x1 = int(-1*(self.lado[i].p1.y - self.lado[i].p2.y))
            y1 = int(self.lado[i].p1.x - self.lado[i].p2.x )
            x2 = int(self.lado[i].p1.y - self.lado[i].p2.y )
            y2 = int(-1*(self.lado[i].p1.x - self.lado[i].p2.x))
            # print (str(x1) + " , " + str(y1) + "    " + str(x2) + " , " + str(y2))
            # pprint(self.pm[i])
            self.normal.append(
                 Line(Point(self.pm[i].x,self.pm[i].y),Point(x2+self.pm[i].x,y2+self.pm[i].y))
            )

    def makeVet(self):
        self.ladoVet = [int(len(self.lado))]
        for i in range(int(len(self.lado))):

            self.ladoVetor.append([])
            self.normalVetor.append([])

            self.ladoVetor[i].append(
                self.lado[i].p2.x - self.lado[i].p1.x
            )
            self.ladoVetor[i].append(
                self.lado[i].p2.y - self.lado[i].p1.y
            )

            self.normalVetor[i].append(
                self.normal[i].p2.x - self.normal[i].p1.x
            )
            self.normalVetor[i].append(
                self.normal[i].p2.y - self.normal[i].p1.y
            )


    f = []
    def defineF(self):
        for i in range(int(len(self.lado))):
            self.f.append(
                Point(self.lado[i].p1.x,self.lado[i].p1.y)
            )

    w = []
    def defineW(self, objeto):
        self.w.append([])

        for i in range(int(len(self.lado))):
            self.w.append([])
            self.w[i].append(
                objeto.objetos[0].p1.x - self.f[i].x
            )
            self.w[i].append(
                objeto.objetos[0].p1.y - self.f[i].y
            )





    def drawPoligono(self, janela = []):
        lado1 = deepcopy(self)
        pprint(self.lado)
        for i in range(len(self.lado)):
            self.lado[i].draw(janela[0])
            lado1.lado[i].draw(janela[1])

        # for i in range(len(self.normal)):
        #     self.normal[i].draw(janela)



