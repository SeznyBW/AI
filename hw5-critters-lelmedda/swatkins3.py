# Sezny Watkins & Loubna El MeddahEl Idrissi
# Fall 2021, AI
# Homework 5 - Critters! 2.0 (Party Edition)
# A copy cat critter that does whatever the last thing it encountered did

import critter
import color
import random

class Swatkins3(critter.Critter):
    last = critter.PARTY
    
    def interact(self, oppInfo):
        return Swatkins3.last 

    def getColor(self):
        return color.GREEN

    def getMove(self, info):
        moves = [critter.NORTH, critter.SOUTH, critter.EAST, critter.WEST]
        rand = random.randint(0, len(moves)-1)
        return moves[rand]

    def getChar(self):
        return "E"

    def interactionOver(self, won, oppFight):
        Swatkins3.last=oppFight
