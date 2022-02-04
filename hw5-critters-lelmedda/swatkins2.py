# Sezny Watkins & Loubna El MeddahEl Idrissi
# Fall 2021, AI
# Homework 5 - Critters! 2.0 (Party Edition)
# Uses q-learning to decide what to do when in an interaction.
# Just moves randomly

import critter
import color
import random


class Swatkins2(critter.Critter):
    Q=[dict([('none', 0)]), dict([('none', 0)]), dict([('none', 0)])]
    actions=[critter.HEAL, critter.PARTY, critter.ROAR, critter.POUNCE, critter.SCRATCH]

    def __init__(self):
        self.health=100
        self.karma=0
        self.prevkarma=0
        self.prevhealth=100
        self.interacting=["M", color.BLUE]
        self.move=critter.HEAL

    def interact(self, oppInfo):
        self.interacting=[oppInfo[4], oppInfo[5]]
        #if we get a random num less than epsilon, return a random action
        if random.random()<.01:
            action=Swatkins2.actions[random.randrange(5)]
            self.move=action
            return action
        
        #otherwise, find the right state in the Q list and return the action with the highest Q value
        if self.health==25:
            statei=Swatkins2.Q[0]
        elif self.health==50:
            statei=Swatkins2.Q[1]
        else:
            statei=Swatkins2.Q[2]
        if oppInfo[4] in statei:
            state=statei[oppInfo[4]]
            if oppInfo[5] in state:
                state=state[oppInfo[5]]
                #if the state already exists, find the best Q value
                maxq=state[0]
                action=Swatkins2.actions[0]
                for x in range(1, 5):
                    if state[x]>maxq:
                        maxq=state[x]
                        action=Swatkins2.actions[x]
                statei['none'] = (.5 * statei['none']) + (.5 * (.99*maxq))
                self.move=action
                return action

            #otherwise, add the state and reurn a random move
            else:
                state[oppInfo[5]]=[0,0,0,0,0]
        else:
            statei[oppInfo[4]]=dict([(oppInfo[5], [0,0,0,0,0])])
        
        statei['none'] = (.5 * statei['none'])
        action=Swatkins2.actions[random.randrange(5)]
        self.move=action
        return action
            
        


    def getColor(self):
        return color.BLUE
		

    #moves randomly
    def getMove(self, info):
        directions = [critter.NORTH, critter.EAST, critter.SOUTH, critter.WEST]
        return(directions[random.randrange(3)])

    def getChar(self):
        return "M"


    def interactionOver(self, won, oppFight):
        if self.prevhealth==25:
            state=Swatkins2.Q[0]
        elif self.prevhealth==50:
            state=Swatkins2.Q[1]
        else:
            state=Swatkins2.Q[2]
        #get the max val of the next state
        if self.health==0:
            nextmax=0
        else:
            if self.prevhealth==25:
                state=Swatkins2.Q[0]
            elif self.prevhealth==50:
                state=Swatkins2.Q[1]
            else:
                state=Swatkins2.Q[2]
            nextmax=state['none']
        #get the reward
        reward = self.karma-self.prevkarma
        
        #set the new q value
        qval=state[self.interacting[0]][self.interacting[1]][Swatkins2.actions.index(self.move)]
        qval=(.5*qval) + (.5 * (reward + (.99 * nextmax)))

        #update the prev values
        self.prevkarma=self.karma
        self.prevhealth=self.health

	