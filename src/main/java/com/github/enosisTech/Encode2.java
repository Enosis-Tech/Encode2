package com.github.enosisTech;

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

	}
	
	@Override
	public void stop() {
		
	}
	
	public static void main(String[] args) {
		launch();
	}

}
