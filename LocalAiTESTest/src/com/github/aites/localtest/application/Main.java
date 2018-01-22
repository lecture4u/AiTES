package com.github.aites.localtest.application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	FXMLLoader loader;
	@Override
	public void start(Stage primaryStage) {
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("AppUI.fxml"));
			
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setTitle("Local Adaptive Internet Things Ecosystem");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
