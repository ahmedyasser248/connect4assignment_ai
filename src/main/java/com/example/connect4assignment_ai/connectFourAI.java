package com.example.connect4assignment_ai;

import com.example.connect4assignment_ai.EvaluationHeuristic;
import com.example.connect4assignment_ai.treeNode;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Queue;
import java.util.function.Function;

public class connectFourAI {
    treeNode root = new treeNode();
    int k = 0;
    BitSet bestMoveState;
    EvaluationHeuristic evalClass = new EvaluationHeuristic();


    double minMax(BitSet state,int color,int depth,boolean pruning){
        root = new treeNode();
        k = depth;
        if(pruning){
            return maxPruning(state,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,color,root);
        }else{
            return max(state,depth,color,root);
        }
    }

    double min(BitSet state,int depth,int color,treeNode node){
        if(depth == 0 || fullBoard(state)){
            node.stateValue = evaluation(state);
            node.state = state;
            return node.stateValue;
        }
        ArrayList<BitSet> children = children( state, color);
        double minEval = Double.MAX_VALUE;
        for (BitSet child: children){
            double temp;
            // adding children to tree
            treeNode newChild = new treeNode();
            node.children.add(newChild);
            temp = max(child, depth - 1,color==0?1:0,newChild);
            if(temp < minEval){
                minEval = temp;
            }
        }
        node.stateValue = minEval;
        node.state = state;
        return minEval;
    }

    double max(BitSet state,int depth,int color,treeNode node){
        if(depth == 0 || fullBoard(state)){
            node.stateValue = evaluation(state);
            node.state = state;
            return node.stateValue;
        }
        ArrayList<BitSet> children = children( state, color);
        // highest evaluation amongst children and the corresponding child
        double maxEval = Double.MAX_VALUE*-1;
        BitSet maxChild = null;
        for (BitSet child: children){
            double temp;
            treeNode newChild = new treeNode();
            node.children.add(newChild);
            temp = min(child, depth - 1,color==0?1:0,newChild);
            if(temp > maxEval){
                maxEval = temp;
                maxChild = child;
            }
        }
        node.stateValue = maxEval;
        // if this is the starting state, set 'bestMoveState' with the state of the child
        // having the highest evaluation value
        if(depth == k){
            bestMoveState = maxChild;
        }
        node.state = state;
        return maxEval;
    }

    double minPruning(BitSet state,int depth,double alpha, double beta,int color,treeNode node){
        if(depth == 0 || fullBoard(state)){
            node.stateValue = evaluation(state);
            node.state = state;
            return node.stateValue;
        }
        ArrayList<BitSet> children = children( state, color);
        double minEval = Double.MAX_VALUE;
        for (BitSet child: children){
            double temp;
            treeNode newChild = new treeNode();
            node.children.add(newChild);
            temp = maxPruning(child, depth - 1,alpha,beta,color==0?1:0,newChild);
            if(temp < minEval){
                minEval = temp;
            }
            if(minEval <= alpha) break;
            if(minEval < beta){
                beta = minEval;
            }
        }
        node.stateValue = minEval;
        node.state = state;
        return minEval;
    }

    double maxPruning(BitSet state,int depth,double alpha, double beta,int color,treeNode node){
        if(depth == 0 || fullBoard(state)){
            node.stateValue = evaluation(state);
            node.state = state;
            return node.stateValue;
        }
        ArrayList<BitSet> children = children( state, color);
        // highest evaluation amongst children and the corresponding child
        double maxEval = Double.MAX_VALUE*-1;
        BitSet maxChild = null;
        for (BitSet child: children){
            double temp;
            treeNode newChild = new treeNode();
            node.children.add(newChild);
            temp = minPruning(child, depth - 1,alpha,beta,color==0?1:0,newChild);
            if(temp > maxEval){
                maxChild = child;
                maxEval = temp;
            }
            if(maxEval >= beta) break;
            if(maxEval > alpha){
                alpha = maxEval;
            }
        }
        node.stateValue = maxEval;
        // if this is the starting state, set 'bestMoveState' with the state of the child
        // having the highest evaluation value
        if(depth == k){
            bestMoveState = maxChild;
        }
        node.state = state;
        return maxEval;
    }

    double evaluation(BitSet state){
        return evalClass.evaluateConnectedDiscs(state,evalClass.YELLOW);
    }

    // finds all possible next moves
    ArrayList<BitSet> children(BitSet state, int color){
        BitSet emptySpace = new BitSet(2);
        ArrayList<BitSet> children = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            // find the deepest empty space in the current column
            int j = 0;
            while( j < 6 && state.get(j*14 + i*2,j*14 + i*2+2).isEmpty() ){
                j++;
            }
            if(j != 0){ // column isn't full
                BitSet newChild = (BitSet) state.clone();
                newChild.set((j-1)*14 + i*2 + color);
                children.add(newChild);
            }
        }
        return children;
    }

    // checks if there are any empty spaces
    boolean fullBoard(BitSet state){
        BitSet emptySpace = new BitSet(2);
        ArrayList<BitSet> children = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if(state.get(i*14+j*2,i*14+j*2+2).equals(emptySpace)){
                    return false;
                }
            }
        }
        return true;
    }

    // calculate the indices of the best move
    int[] moveIndices(BitSet nextState, BitSet currentState){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if(!currentState.get(i*14+j*2,i*14+j*2+2)
                        .equals(nextState.get(i*14+j*2,i*14+j*2+2))){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    // Function<BitSet,Integer> evaluation = bitSet -> evaluation(bitSet);
    // TODO return the best move
}