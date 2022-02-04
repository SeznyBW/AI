# Sezny Watkins & Loubna El MeddahEl Idrissi
# Fall 2021, AI
# Homework 5 - Critters! 2.0 (Party Edition)
# Searches for the nearest critter of it's own type and goes towards it
# If interacting with a critter of it's own type, it heals
# Otherwise, it parties, unless its health is at 25, then it fights
# When fighting, it keeps track of each critters' most common moves, and makes a move that beats that

import critter
import color
import random


class Swatkins1(critter.Critter):

    strategy=dict([])
    locations=[]

    def __init__(self):
        self.location=[0,0]
        self.interacting=["S", color.RED]
        self.health=100
        self.karma=0
        self.last=critter.HEAL

        

    #find the most common attack the enemy uses
    #returns what will beat that
    #returns a random attack if it's never fought this enemy before
    def fight(self, chara, color):
        if chara in Swatkins1.strategy:
            enemy = Swatkins1.strategy[chara]
            if color in enemy:
                enemy=enemy[color]
                maxi = enemy[0]
                move = 0
                for x in range(1, 3):
                    if enemy[x]>maxi:
                        maxi=enemy[x]
                        move=x
                if move==0:
                    return critter.SCRATCH
                elif move==1:
                    return critter.POUNCE
                else:
                    return critter.ROAR
            else:
                enemy[color]=[0,0,0]
        else:
            Swatkins1.strategy[chara]=dict([(color, [0,0,0])])
        rand = random.randint(0, 2)
        if rand==0:
            return critter.ROAR
        elif rand==1:
            return critter.POUNCE
        else:
            return critter.SCRATCH


    #heals if it's facing another swatkins1
    #otherwise parties if it's not about to die
    #otherwise fights
    def interact(self, oppInfo):
        self.interacting=[oppInfo[4], oppInfo[5]]
        if oppInfo[4]=="S" and oppInfo[5]==color.RED:
            if self.last==critter.HEAL:
                self.last=critter.PARTY
                return critter.PARTY
            else:
                self.last=critter.HEAL
                return critter.HEAL
        elif self.health>25:
            self.last=critter.PARTY
            return critter.PARTY
        else:
            move=Swatkins1.fight(self, oppInfo[4], oppInfo[5])
            self.last=move
            return move


    def getColor(self):
        return color.RED
		

    #finds the nearest swatkins1 and goes towards it
    def getMove(self, info):
        if self.location in Swatkins1.locations:
            Swatkins1.locations.remove(self.location)
        self.location=[info[0], info[1]]
        if self.location in Swatkins1.locations:
            Swatkins1.locations.remove(self.location)
        if len(Swatkins1.locations)==0:
            Swatkins1.locations.append(self.location)
            return critter.EAST
        mindist = info[2]+info[3]
        coordinates=[0,0]
        for i in Swatkins1.locations:
            if i[0]>self.location[0]:
                if (i[0]-self.location[0])<((info[2]-i[0])+self.location[0]):
                    x=i[0]-self.location[0]
                else:
                    x=(info[2]-i[0])+self.location[0]
            else:
                if (self.location[0]-i[0])<((info[2]-self.location[0])+i[0]):
                    x=self.location[0]-i[0]
                else:
                    x=(info[2]-self.location[0])+i[0]
            if i[1]>self.location[1]:
                if (i[1]-self.location[1])<((info[2]-i[1])+self.location[1]):
                    y=i[1]-self.location[1]
                else:
                    y=(info[2]-i[1])+self.location[1]
            else:
                if (self.location[1]-i[1])<((info[2]-self.location[1])+i[1]):
                    y=self.location[1]-i[1]
                else:
                    y=(info[2]-self.location[1])+i[1]
            if (x+y)<mindist:
                mindist=x+y
                coordinates=i
        Swatkins1.locations.append(self.location)
        if (coordinates[0]>self.location[0]):
            return critter.EAST
        elif(coordinates[0]<self.location[0]):
            return critter.WEST
        elif(coordinates[1]>self.location[1]):
            return critter.SOUTH
        else:
            return critter.NORTH



    def getChar(self):
        return "S"


    #add the enemy's move to the tracker
    def interactionOver(self, won, oppFight):
        if self.interacting[0] in Swatkins1.strategy:
            enemy=Swatkins1.strategy[self.interacting[0]]
            if not (self.interacting[1] in enemy):
                enemy[self.interacting[1]]=[0,0,0]
        else:
            Swatkins1.strategy[self.interacting[0]]=dict([(self.interacting[1], [0,0,0])])
        if oppFight==critter.ROAR:
            Swatkins1.strategy[self.interacting[0]][self.interacting[1]][0]+=1
        if oppFight==critter.POUNCE:
            Swatkins1.strategy[self.interacting[0]][self.interacting[1]][1]+=1
        if oppFight==critter.SCRATCH:
            Swatkins1.strategy[self.interacting[0]][self.interacting[1]][2]+=1

	