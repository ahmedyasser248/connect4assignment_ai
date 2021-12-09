package com.example.connect4assignment_ai;

import eu.hansolo.tilesfx.tools.TreeNode;

import java.io.Console;
import java.util.BitSet;

public class GameRunner {

    BitSet EMPTY=new BitSet(2);
    BitSet RED=new BitSet(2);
    BitSet YELLOW=new BitSet(2);
    BitSet currentState;

    GameRunner(){
        this.RED.set(1);
        this.YELLOW.set(0);
        this.currentState = new BitSet(84);
    }

   connectFourAI computerAgent = new connectFourAI();

    BitSet makeMove(BitSet state,int i,int j,int player){
        if(player == 0){
            state.set(i*14+j*2);
        }else{
            state.set(i*14+j*2+1);
        }
        return state;
    }

    int[] computerMove(int i,int j,int k,boolean pruning){
        currentState = makeMove(currentState,i,j,1);
        computerAgent.minMax(currentState,0,k,pruning);


        treeNode.printTree(computerAgent.root);
        int[] indices = computerAgent.moveIndices(computerAgent.bestMoveState,currentState);
        currentState = makeMove(currentState,indices[0],indices[1],0);

        return indices;
    }
    void printState(BitSet state){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                BitSet currentSpace = state.get(i*14+j*2,i*14+j*2+2);
                if(currentSpace.equals(RED)){
                    System.out.print("R ");
                }
                if(currentSpace.equals(YELLOW)){
                    System.out.print("Y ");
                }
                if(currentSpace.equals(EMPTY)){
                    System.out.print("E ");
                }
            }
            System.out.println();
        }
    }


}