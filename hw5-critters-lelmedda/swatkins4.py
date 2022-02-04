# Sezny Watkins & Loubna El MeddahEl Idrissi
# Fall 2021, AI
# Homework 5 - Critters! 2.0 (Party Edition)
# Keeps track of the oponents' most common moves
# If it's healing or partying, it parties
# Otherwise it does a move that will combat them

import critter
import color
import random


class Swatkins4(critter.Critter):

    strategy=dict([])
    moves=[critter.PARTY, critter.PARTY, critter.POUNCE, critter.SCRATCH, critter.ROAR]
    directions=[critter.NORTH, critter.SOUTH, critter.EAST, critter.WEST]

    def __init__(self):
        self.interacting=["W", color.PURPLE]
        self.health=100
        self.karma=0
        

    #find the most common move the enemy uses
    #returns what will beat that
    #returns a random move if it's never fought this enemy before
    def fight(self, chara, color):
        if chara in Swatkins4.strategy:
            enemy = Swatkins4.strategy[chara]
            if color in enemy:
                enemy=enemy[color]
                maxi = enemy[0]
                move = Swatkins4.moves[0]
                for x in range(1, 3):
                    if enemy[x]>maxi:
                        maxi=enemy[x]
                        move=Swatkins4.moves[x]
                return move
            else:
                enemy[color]=[0,0,0,0,0]
        else:
            Swatkins4.strategy[chara]=dict([(color, [0,0,0,0,0])])
        return Swatkins4.moves[random.randrange(5)]
        


    #keeps track of who it's fighting, then calls fight
    #unless it's facing itself, then it heals
    def interact(self, oppInfo):
        if oppInfo[4]=="W" and oppInfo[5]==color.PURPLE:
            return critter.HEAL
        self.interacting=[oppInfo[4], oppInfo[5]]
        return Swatkins4.fight(self, oppInfo[4], oppInfo[5])


    def getColor(self):
        return color.PURPLE
		

    #move randomly
    def getMove(self, info):
        return Swatkins4.directions[random.randrange(4)]



    def getChar(self):
        return "W"


    #add the enemy's move to the tracker
    def interactionOver(self, won, oppFight):
        if self.interacting[0] in Swatkins4.strategy:
            enemy=Swatkins4.strategy[self.interacting[0]]
            if not (self.interacting[1] in enemy):
                enemy[self.interacting[1]]=[0,0,0,0,0]
        else:
            Swatkins4.strategy[self.interacting[0]]=dict([(self.interacting[1], [0,0,0,0,0])])
        if oppFight==critter.HEAL:
            Swatkins4.strategy[self.interacting[0]][self.interacting[1]][0]+=1
        if oppFight==critter.PARTY:
            Swatkins4.strategy[self.interacting[0]][self.interacting[1]][1]+=1
        if oppFight==critter.ROAR:
            Swatkins4.strategy[self.interacting[0]][self.interacting[1]][2]+=1
        if oppFight==critter.POUNCE:
            Swatkins4.strategy[self.interacting[0]][self.interacting[1]][3]+=1
        if oppFight==critter.SCRATCH:
            Swatkins4.strategy[self.interacting[0]][self.interacting[1]][4]+=1

	