package com.github.enosisTech;

import dev.reymon.graphics.javafx.Menu;
import dev.reymon.graphics.javafx.MenuItem;
import dev.reymon.lang.TRJavafx;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class MenuBarController {

	private HostServices hostServices; // Services for the function support

	// Menu File and items of this menu
	@FXML
	private Menu file;
	@FXML
	private MenuItem openFile;
	@FXML
	private MenuItem closeFile;

	// Menu Setting and menus or items for this menu
	@FXML
	private Menu setting;
	@FXML
	private Menu languageSetting;
	@FXML
	private MenuItem es;
	@FXML
	private MenuItem en;
	@FXML
	private MenuItem el;
	@FXML
	private MenuItem de;

	// Menu help and items for this Menu
	@FXML
	private Menu help;
	@FXML
	private MenuItem supportHelp;
	@FXML
	private MenuItem exitHelp;

	public void initialize() {

		/**
		 * Create translate of the menu and menus in the menu File
		 */
		file.textProperty().bind(TRJavafx.bind("MenuBar.File"));
		openFile.textProperty().bind(TRJavafx.bind("MenuBar.File.Open"));
		closeFile.textProperty().bind(TRJavafx.bind("MenuBar.File.Close"));

		/**
		 * Create translate of the menu and menus in the menu Setting
		 */
		setting.textProperty().bind(TRJavafx.bind("MenuBar.Setting"));
		languageSetting.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language"));

		es.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.es"));
		en.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.en"));
		el.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.el"));
		de.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.de"));

		/**
		 * Create function for change language on click button
		 */
		es.setOnAction(_ -> TRJavafx.setLocale("es"));
		en.setOnAction(_ -> TRJavafx.setLocale("en"));
		el.setOnAction(_ -> TRJavafx.setLocale("el"));
		de.setOnAction(_ -> TRJavafx.setLocale("de"));

		/**
		 * Create translate of the menu and menus in the menu Help
		 */
		help.textProperty().bind(TRJavafx.bind("MenuBar.Help"));
		supportHelp.textProperty().bind(TRJavafx.bind("MenuBar.Help.Support"));
		exitHelp.textProperty().bind(TRJavafx.bind("MenuBar.Help.Exit"));
		
		// Create function for send tickets
		supportHelp.setOnAction(_ -> hostServices.showDocument("mailto:contact@reymon.dev"));
		
		// Create exit function on click the button
		exitHelp.setOnAction(_ -> Platform.exit());

	}

	// ###############
	// ### Setters ###
	// ###############

	public void setHostServices(HostServices hostServices) {
		this.hostServices = hostServices;
	}

	// ###############
	// ### Methods ###
	// ###############

	public void openAction() {

	}

	public void closeAction() {

	}

}
