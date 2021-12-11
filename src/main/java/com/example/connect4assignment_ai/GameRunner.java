package com.example.connect4assignment_ai;

import eu.hansolo.tilesfx.tools.TreeNode;
import javafx.scene.paint.Color;

import java.io.Console;
import java.util.BitSet;

public class GameRunner {

    BitSet EMPTY=new BitSet(2);
    BitSet RED=new BitSet(2);
    BitSet YELLOW=new BitSet(2);
    BitSet currentState;
    BitSet currentMoveScore;
    EvaluationHeuristic evaluationHeuristic = new EvaluationHeuristic();

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


        treeNode.traversePreOrder(computerAgent.root);
        int[] indices = computerAgent.moveIndices(computerAgent.bestMoveState,currentState);
        currentState = makeMove(currentState,indices[0],indices[1],0);
        int[]score =evaluationHeuristic.countFours(currentState,evaluationHeuristic.YELLOW);
        int arr[]=new int[4];
        arr[0]=indices[0];
        arr[1]=indices[1];
        arr[2]=score[0];
        arr[3]=score[1];
        return arr;
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