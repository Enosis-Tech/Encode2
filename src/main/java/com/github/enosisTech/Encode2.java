package com.github.enosisTech;

import dev.reymon.graphics.javafx.Scene;
import dev.reymon.lang.TRJavafx;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Encode2 extends Application {
	
	private HostServices hostServices;
	
	private Module module = Encode2.class.getModule();
	
	@Override
	public void init() {
		
		TRJavafx.setModule(module);
		
		hostServices = getHostServices();
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Window.fxml"));
		
		// Get the root of the scene
		Parent root = loader.load();
		
		// Get the controller of the Menu Bar
		MenuBarController menuBarController = (MenuBarController) loader.getNamespace().get("MenuBarController");
		
		menuBarController.setHostServices(hostServices); // Get the hostServices for support function on
														 // Menu Help
		
		// Create Scene
		Scene scene = new Scene(root, 640, 480);
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
