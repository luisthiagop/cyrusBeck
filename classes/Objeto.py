from graphics import *

class Objeto:
    objetos = []


    def makeObjeto(self):
        self.n = 1#input('numero de objetos(linhas): ')
        # for i in range(int(self.n)):
        x1 = 10#input('digite x1: ')
        y1 = 10#input('digite y1: ')
        x2 = 300#input('digite x2: ')
        y2 = 300#input('digite y2: ')
        self.objetos.append(Line(Point(x1,y1),Point(x2,y2)))

    objetoVet = []
    def makeVet(self):
        self.objetoVet.append([])

        for i in range(int(len(self.objetos))):
            self.objetoVet[i].append(
                self.objetos[i].p2.x-self.objetos[i].p1.x
            )
            self.objetoVet[i].append(
                self.objetos[i].p2.y - self.objetos[i].p1.y
            )



    def drawObjeto(self, janela):
        for i in range(len(self.objetos)):
            self.objetos[i].draw(janela)



