package com.example.connect4assignment_ai;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    TextField tf;

    @FXML
    public void selectNormalAndOpenNewWindow(){
        openNewStage("connect4 without pruning",1);

    }
    @FXML
    public void selectPruningAndOpenNewWindow(){
        openNewStage("Connect4 with pruning",2);
    }
    public void openNewStage(String title ,int buttonClicked ){
        FXMLLoader fxmlLoader =  new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        try{
            if(!tf.getText().isEmpty()){
                HelloController.depth=Integer.parseInt(tf.getText());
            }
            System.out.println(HelloController.depth);
            HelloController.select=buttonClicked;
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
