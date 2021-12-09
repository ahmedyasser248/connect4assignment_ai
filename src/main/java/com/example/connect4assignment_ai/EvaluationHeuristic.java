package com.example.connect4assignment_ai;

import java.util.BitSet;

//TODO Weight of 2 connected Discs will be low and that of 1 disc will be very low
//TODO WEIGHT OF 2 BY DIAGONAL IS HIGHER THAN TWO BY ROW OR COLUMN
public class EvaluationHeuristic {
    BitSet EMPTY=new BitSet(2);
    BitSet RED=new BitSet(2);
    BitSet YELLOW=new BitSet(2);
    int numOfBoardRows;
    int numOfBoardColumns;
    int boardCellSize;
    int numOfBitsPerRow;
    double weightOfFourConnectedDiscs=30;
    double weightOfThreeConnectedDiscs=11.2;
    double weightOfTwoConnectedDiscs=5.7;
    double weightOfFourConnectedDiscsPlayer=31;
    double weightOfThreeConnectedDiscsPlayer=12.2;
    double weightOfTwoConnectedDiscsPlayer=6.7;
    double weightOfThreeClosed =15 ;
    double weightOfTwoClosed =10 ;
    public EvaluationHeuristic(){
        this.RED.set(1);
        this.YELLOW.set(0);
        numOfBoardRows=6;
        numOfBoardColumns=7;
        boardCellSize=2;//Each cell on the board is represented by 2 bits;
        numOfBitsPerRow=numOfBoardColumns*boardCellSize;
    }


    double evaluateConnectedDiscs(BitSet boardState,BitSet computerColor){
        return evaluateConnectedDiscsByColumn(boardState,computerColor)+evaluateBoardStateByRow(boardState,computerColor)+evaluateBoardStateByRightDiagonal(boardState,computerColor)+evaluateBoardStateByLeftDiagonal(boardState,computerColor);
    }
    double evaluateConnectedDiscsByColumn(BitSet boardState,BitSet computerColor){
        double evaluationScore=0;
        for(int col=0;col<numOfBoardColumns;col++){

            BitSet currentDiscColor=boardState.get((5*numOfBitsPerRow+boardCellSize*col),(5*numOfBitsPerRow+boardCellSize*col)+boardCellSize);//5 is the index of the last row
            if(currentDiscColor.equals(EMPTY)){
                continue;
            }
            int numOfConnectedDiscs=1;
            for(int row=4;row>=0;row--){
                BitSet colorOfDiscAboveCurrent=boardState.get((row*numOfBitsPerRow+boardCellSize*col),(row*numOfBitsPerRow+boardCellSize*col)+boardCellSize);
                if(colorOfDiscAboveCurrent.equals(currentDiscColor)){
                    numOfConnectedDiscs++;
                }
                else if (colorOfDiscAboveCurrent.equals(EMPTY)){
                    double weight=0;
                    switch(numOfConnectedDiscs){
                        case 3:
                            //There is an empty cell above these 3 consecutive discs and hence it is possible to place a fourth disc above to connect 4 discs
                            if(currentDiscColor.equals(computerColor)){
                                weight = weightOfThreeConnectedDiscs;
                            }else{
                                weight =weightOfThreeConnectedDiscsPlayer;
                            }
                            break;
                        case 2:
                            if((row+1)>=2) {//There are 2 empty cells above these 2 consecutive discs and hence it is possible to place 2 discs above to connect 4 discs
                                if(currentDiscColor.equals(computerColor)){
                                    weight = weightOfTwoConnectedDiscs;
                                }else{
                                    weight = weightOfTwoConnectedDiscsPlayer;
                                }

                            }
                            break;
                    }
                    if(currentDiscColor.equals(computerColor)) {
                        evaluationScore += weight;
                    }
                    else {
                        evaluationScore -= weight;
                    }
                    break;
                }
                else {
                    double weight=0;
                    switch(numOfConnectedDiscs){
                        case 3 :
                            weight=weightOfThreeClosed;
                            break;
                        case 2:
                            weight=weightOfTwoClosed;
                            break;
                    }

                    if(currentDiscColor.equals(computerColor)){
                        evaluationScore+=weight;
                    }
                    else {
                        evaluationScore-=weight;
                    }
                    currentDiscColor=colorOfDiscAboveCurrent;
                    numOfConnectedDiscs=1;
                }

                if(numOfConnectedDiscs>=4){
                    if(currentDiscColor.equals(computerColor)) {
                        evaluationScore += weightOfFourConnectedDiscs;
                    }
                    else {
                        evaluationScore -= weightOfFourConnectedDiscsPlayer;
                    }
                }
            }
        }
        return evaluationScore;
    }






    private double evaluateSlidingWindow(BitSet []slidingWindow,BitSet computerColor){
        int noComputerDiscs=0;
        int noOpponentDiscs=0;
        double evaluationScore=0;
        for(int i=0;i<slidingWindow.length;i++){
            if(slidingWindow[i].equals(computerColor)){
                noComputerDiscs++;
            }
            else if (!slidingWindow[i].equals(EMPTY)){
                noOpponentDiscs++;
            }
        }
        if(noComputerDiscs>0 && noOpponentDiscs==0){
            double weight=0;
            switch (noComputerDiscs){
                case 4 :
                    weight=weightOfFourConnectedDiscs;
                    break;
                case 3:
                    weight=weightOfThreeConnectedDiscs;
                    break;
                case 2 :
                    weight=weightOfTwoConnectedDiscs;
                    break;
            }
            evaluationScore+=weight;
        }
        else if (noComputerDiscs==0 && noOpponentDiscs>0){
            double weight=0;
            switch (noOpponentDiscs){
                case 4 :
                    weight=weightOfFourConnectedDiscsPlayer;
                    break;
                case 3:
                    weight=weightOfThreeConnectedDiscsPlayer;
                    break;
                case 2 :
                    weight=weightOfTwoConnectedDiscsPlayer;
                    break;
            }
            evaluationScore-=weight;
        }
        else if (noComputerDiscs>0 && noOpponentDiscs>0){
            double weight=0;
            if(noComputerDiscs>noOpponentDiscs){
                switch(noComputerDiscs){
                    case 3:
                        weight=weightOfThreeClosed;
                        break;
                    case 2:
                        weight=weightOfTwoClosed;
                        break;
                }
                evaluationScore+=weight;
            }
            else if (noOpponentDiscs>noComputerDiscs){
                switch(noOpponentDiscs){
                    case 3 :
                        weight=weightOfThreeClosed;
                        break;
                    case 2:
                        weight=weightOfTwoClosed;
                        break;
                }
                evaluationScore-=weight;
            }

        }
        return evaluationScore;
    }



    private BitSet [] createSlidingWindowByRow(BitSet boardState,int rowIndex,int startingColumnIndex){
        BitSet [] slidingWindow=new BitSet[4];
        //Initialize The Bitsets
        for(int i=0;i<slidingWindow.length;i++){
            slidingWindow[i]=new BitSet(2);
        }
        for(int col=startingColumnIndex;col<startingColumnIndex+4;col++){
            slidingWindow[col-startingColumnIndex]=boardState.get(rowIndex*numOfBitsPerRow+boardCellSize*col,rowIndex*numOfBitsPerRow+boardCellSize*col+boardCellSize);
        }
        return slidingWindow;
    }
    double evaluateBoardStateByRow(BitSet boardState,BitSet computerColor){
        double evaluationScore=0;
        for(int row =numOfBoardRows-1;row>=0;row--){
            for(int startingColumnIndex=0;startingColumnIndex<4;startingColumnIndex++){
                evaluationScore+=evaluateSlidingWindow(createSlidingWindowByRow(boardState,row,startingColumnIndex),computerColor);
            }
        }
        return evaluationScore;
    }



    private BitSet [] createSlidingWindowByRightDiagonal(BitSet boardState,int startingRowIndex,int startingColumnIndex){
        BitSet [] slidingWindow=new BitSet[4];
        //Initialize The Bitsets
        for(int i=0;i<slidingWindow.length;i++){
            slidingWindow[i]=new BitSet(2);
        }
        int rowIndex=startingRowIndex;
        int colIndex=startingColumnIndex;
        for(int i=0;i<slidingWindow.length;i++){
            slidingWindow[i]=boardState.get(rowIndex*numOfBitsPerRow+boardCellSize*colIndex,rowIndex*numOfBitsPerRow+boardCellSize*colIndex+boardCellSize);
            rowIndex--;
            colIndex++;
        }
        return slidingWindow;
    }
    private double evaluateBoardStateByRightDiagonal(BitSet boardState,BitSet computerColor){
        double evaluationScore=0;
        for(int row=3;row<numOfBoardRows;row++){
            int numberOfIterations=(row+1)-4+1; //In each iteration 4 rows are visited ,then the number of iterations = current row NUMBER (row index +1) - 4 +1
            for(int i=0;i<numberOfIterations;i++){
                evaluationScore+=evaluateSlidingWindow(createSlidingWindowByRightDiagonal(boardState,row-i,i),computerColor);
            }
        }
        for(int col=1;col<=3;col++){
            int numberOfIterations=4-(col+1)+1;
            for(int i=0;i<numberOfIterations;i++){
                evaluationScore+=evaluateSlidingWindow(createSlidingWindowByRightDiagonal(boardState,numOfBoardRows-1-i,col+i),computerColor);
            }
        }
        return evaluationScore;
    }



    private BitSet [] createSlidingWindowByLeftDiagonal(BitSet boardState,int startingRowIndex,int startingColumnIndex){
        BitSet [] slidingWindow=new BitSet[4];
        //Initialize The Bitsets
        for(int i=0;i<slidingWindow.length;i++){
            slidingWindow[i]=new BitSet(2);
        }
        int rowIndex=startingRowIndex;
        int colIndex=startingColumnIndex;
        for(int i=0;i<slidingWindow.length;i++){
            slidingWindow[i]=boardState.get(rowIndex*numOfBitsPerRow+boardCellSize*colIndex,rowIndex*numOfBitsPerRow+boardCellSize*colIndex+boardCellSize);
            rowIndex--;
            colIndex--;
        }
        return slidingWindow;
    }

    double evaluateBoardStateByLeftDiagonal(BitSet boardState, BitSet computerColor){
        double evaluationScore=0;
        for(int row=3;row<numOfBoardRows;row++){
            int numberOfIterations=(row+1)-4+1; //In each iteration 4 rows are visited ,then the number of iterations = current row NUMBER (row index +1) - 4 +1
            for(int i=0;i<numberOfIterations;i++){
                evaluationScore+=evaluateSlidingWindow(createSlidingWindowByLeftDiagonal(boardState,row-i,numOfBoardColumns-1-i),computerColor);
            }
        }
        for(int col=numOfBoardColumns-2;col>=3;col--){
            int numberOfIterations=(col+1)-4+1;
            for(int i=0;i<numberOfIterations;i++){
                evaluationScore+=evaluateSlidingWindow(createSlidingWindowByLeftDiagonal(boardState,numOfBoardRows-1-i,col-i),computerColor);
            }
        }
        return evaluationScore;
    }
}