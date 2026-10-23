package com.github.enosisTech;

import dev.reymon.graphics.javafx.BorderPane;
import dev.reymon.graphics.javafx.Scene;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

public class Encode2 extends Application {
	
	@SuppressWarnings("unused")
	private HostServices hostServices;
	
	@Override
	public void init() {
		
		hostServices = getHostServices();
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		Scene scene = new Scene(new BorderPane(), 640, 480);
		
		primaryStage.setTitle("Encode2");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void stop() {
		
	}
	
	public static void main(String[] args) {
		launch();
	}

}
