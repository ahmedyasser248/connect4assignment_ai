package com.example.connect4assignment_ai;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;

import java.util.Queue;

public class treeNode {
    double stateValue = 0;
    ArrayList<treeNode> children = new ArrayList<>();
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

//    static void numberOfNodes(treeNode root){
//        Queue<treeNode> prQ = new LinkedList<>();
//        prQ.add(root);
//        int number = 1;
//        int parentNo = 1;
//        int childrenNo = 1;
//        while (!prQ.isEmpty()){
//            treeNode current = prQ.poll();
//            parentNo--;
//            childrenNo--;
//            System.out.printf("%.1f \n",current.stateValue);
//            printStateTree(current.state);
//            for(treeNode child : current.children){
//                prQ.add(child);
//                childrenNo++;
//            }
//            if(parentNo == 0){
//                System.out.println("new tree level");
//                parentNo = childrenNo;
//                System.out.println();
//            }
//        }
//    }


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

    static public void traversePreOrder(treeNode root){
        if(root!=null) {
            System.out.println(root.stateValue);
            String pointerForRight = " └──";
            String pointerForLeft = (root.children.size()>1)? " ├──" : " └──";
            int first=1;
            int size=root.children.size();
            for(treeNode child:root.children){
                if(first==1) {
                    size--;
                    traverseNodes(child,"",pointerForLeft,size!=0);
                    first=0;
                }
                else {
                    size--;
                    traverseNodes(child,"", pointerForRight,size!=0);
                }

            }
        }
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n\n\n\n");
    }
    static public void traverseNodes(treeNode child,String padding, String pointer, boolean hasRightSibling) {
        if (child != null) {

            System.out.print("\n"+padding+pointer+child.stateValue);
            String newPadding=padding;
            if (hasRightSibling) {
                newPadding+=" │  ";
            } else {
                newPadding+="    ";
            }
            String pointerRight = " └──";
            String pointerLeft = (child.children.size()>1) ? " ├──" : " └──";
            int first=1;
            int size=child.children.size();
            for(treeNode childNode: child.children) {
                if(first==1) {
                    size--;
                    traverseNodes(childNode,newPadding, pointerLeft,size!=0 );
                    first=0;
                }
                else {
                    size--;
                    traverseNodes(childNode,newPadding, pointerRight, size!=0);
                }
            }
        }
    }

    static void printStateTreeWithPadding(BitSet state,String padding, String pointer,double stateValue){
        BitSet EMPTY=new BitSet(2);
        BitSet RED=new BitSet(2);
        BitSet YELLOW=new BitSet(2);
        RED.set(1);
        YELLOW.set(0);
        System.out.print("\n"+padding+pointer);
        System.out.printf(" %.2f\n",stateValue);
        for (int i = 0; i < 6; i++) {
            System.out.print(padding+"     ");
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