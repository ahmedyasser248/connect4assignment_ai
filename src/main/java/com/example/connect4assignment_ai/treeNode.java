package com.example.connect4assignment_ai;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;

import java.util.Queue;

public class treeNode {
   public double stateValue = 0;
    public ArrayList<treeNode> children = new ArrayList<>();
    BitSet state;


    // print tree using BFS
    static void printTree(treeNode root){
        Queue<treeNode> prQ = new LinkedList<>();
        prQ.add(root);
        int parentNo = 1;
        int childrenNo = 1;
        while (!prQ.isEmpty()){
            treeNode current = prQ.poll();
            parentNo--;
            childrenNo--;
            System.out.printf("%.1f \n",current.stateValue);
            printStateTree(current.state);
            for(treeNode child : current.children){
                prQ.add(child);
                childrenNo++;
            }
            if(parentNo == 0){
                System.out.println("new tree level");
                parentNo = childrenNo;
                System.out.println();
            }
        }
    }


    static void printStateTree(BitSet state){
        BitSet EMPTY=new BitSet(2);
        BitSet RED=new BitSet(2);
        BitSet YELLOW=new BitSet(2);
        RED.set(1);
        YELLOW.set(0);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                BitSet currentSpace = state.get(i * 14 + j * 2, i * 14 + j * 2 + 2);
                if (currentSpace.equals(RED)) {
                    System.out.print("R ");
                }
                if (currentSpace.equals(YELLOW)) {
                    System.out.print("Y ");
                }
                if (currentSpace.equals(EMPTY)) {
                    System.out.print("E ");
                }
            }
            System.out.println();
        }
    }
}