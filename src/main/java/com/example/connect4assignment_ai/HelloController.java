package com.example.connect4assignment_ai;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.BitSet;
import java.util.HashMap;
import java.util.ResourceBundle;


public class HelloController{
    //which technique
    static int select;
    //store height of each column;
    int[] columnsHeight =new int[7];
    //depth chosen by player
    static int depth = 3;
    GameRunner gameRunner = new GameRunner();
    @FXML
    GridPane grid;

    @FXML
    Label computer;

    @FXML
    Label player;


    @FXML
    public void clickedOnCircle(MouseEvent e){
        Circle c =(Circle) e.getSource();
        Integer colIndex= GridPane.getColumnIndex(c);
        Integer rowIndex = GridPane.getRowIndex(c);
        int row = rowIndex;
        int col = colIndex;
        int realr;
        int realc;
        if(5-columnsHeight[col]==row){
            Circle circle = getDesiredCircleByRowAndColumn(5-columnsHeight[col], col);
                circle.setFill(Color.RED);
                realr=5-columnsHeight[col];
                columnsHeight[col]++;
        }else if(5-columnsHeight[col]>row){
            Circle circle = getDesiredCircleByRowAndColumn(5-columnsHeight[col], col);
            circle.setFill(Color.RED);
            realr=5-columnsHeight[col];
            columnsHeight[col]++;
        }else{
            return;
        }
        realc=col;

        PauseTransition pauseTransition = new PauseTransition(Duration.millis(200));
        pauseTransition.setOnFinished(actionEvent -> {
            int results[]= gameRunner.computerMove(realr,realc, depth, select!=1);
            Circle r = getDesiredCircleByRowAndColumn(results[0],results[1]);
            columnsHeight[results[1]]++;
            r.setFill(Color.BLUE);
            computer.setText(results[2]+"");
            player.setText(results[3]+"");
        });
        pauseTransition.play();

    }
    public Circle getDesiredCircleByRowAndColumn(int row, int column){
        Node result =null;
        ObservableList<Node> childrens = grid.getChildren();
        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return (Circle) result;
    }



}