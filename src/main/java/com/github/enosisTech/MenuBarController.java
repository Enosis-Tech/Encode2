package com.github.enosisTech;

import com.github.enosisTech.editor.CodeEditorPane;
import com.github.enosisTech.editor.EditorManager;
import dev.reymon.graphics.javafx.Menu;
import dev.reymon.graphics.javafx.MenuItem;
import dev.reymon.lang.TRJavafx;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;

/**
 * @brief Controlador de la barra de menú de la aplicación.
 *
 *        Gestiona los menús de Archivo, Configuración (idioma) y Ayuda. Delega
 *        las operaciones de archivo al \c EditorManager y las de
 *        apertura/cierre de carpeta al \c MainController. Los textos de los
 *        menús se enlazan (bind) a claves de i18n vía TRJavafx.
 *
 * @see TRJavafx
 * @see EditorManager
 * @see MainController
 */
public class MenuBarController {
	
	/// Servicios del host (mail, urls, etc.).
	private HostServices hostServices;
	
	/// Ventana propietaria para diálogos.
	private Window ownerWindow;
	
	/// Gestor del editor (pestañas y guardado).
	private EditorManager editorManager;
	
	/// Controlador principal para operaciones de carpeta.
	private MainController mainController;

	@FXML private Menu file;
	@FXML private MenuItem openFile, closeFile;
	@FXML private MenuItem saveFile, saveAsFile, closeTab;

	@FXML private Menu setting;
	@FXML private Menu languageSetting;
	@FXML private MenuItem es, en, el, de;

	@FXML private Menu help;
	@FXML private MenuItem supportHelp;
	@FXML private MenuItem exitHelp;

	/**
	 * @brief Inicialización del controlador (invocada por FXML).
	 *
	 *        - Enlaza los textos de los menús a las claves i18n de TRJavafx. -
	 *        Registra los manejadores de acción de cada \c MenuItem.
	 */
	
	public void initialize() {

		file.textProperty().bind(TRJavafx.bind("MenuBar.File"));
		openFile.textProperty().bind(TRJavafx.bind("MenuBar.File.Open"));
		closeFile.textProperty().bind(TRJavafx.bind("MenuBar.File.Close"));
		saveFile.textProperty().bind(TRJavafx.bind("MenuBar.File.Save")); // No se genera
		saveAsFile.textProperty().bind(TRJavafx.bind("MenuBar.File.SaveAs")); // No se genera
		closeTab.textProperty().bind(TRJavafx.bind("MenuBar.File.CloseTab")); // No se genera

		setting.textProperty().bind(TRJavafx.bind("MenuBar.Setting"));
		languageSetting.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language"));
		es.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.es"));
		en.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.en"));
		el.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.el"));
		de.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.de"));

		help.textProperty().bind(TRJavafx.bind("MenuBar.Help"));
		supportHelp.textProperty().bind(TRJavafx.bind("MenuBar.Help.Support"));
		exitHelp.textProperty().bind(TRJavafx.bind("MenuBar.Help.Exit"));

		// Acciones
		openFile.setOnAction(_ -> openAction());
		closeFile.setOnAction(_ -> closeAction());
		saveFile.setOnAction(_ -> saveCurrent());
		saveAsFile.setOnAction(_ -> saveAsCurrent());
		closeTab.setOnAction(_ -> closeCurrentTab());

		es.setOnAction(_ -> TRJavafx.setLocale("es"));
		en.setOnAction(_ -> TRJavafx.setLocale("en"));
		el.setOnAction(_ -> TRJavafx.setLocale("el"));
		de.setOnAction(_ -> TRJavafx.setLocale("de"));

		supportHelp.setOnAction(_ -> {
			if (hostServices != null)
				hostServices.showDocument("mailto:contact@reymon.dev");
		});
		exitHelp.setOnAction(_ -> Platform.exit());

	}

	/**
	 * @brief Guarda la pestaña actualmente seleccionada.
	 * @note Si no hay pestaña o \c editorManager es nulo, no hace nada.
	 */
	// Acciones
	private void saveCurrent() {
		if (editorManager == null)
			return;
		Tab tab = editorManager.getTabPane().getSelectionModel().getSelectedItem();
		if (tab == null)
			return;
		try {
			((CodeEditorPane) tab.getContent()).save();
		} catch (Exception ex) {
			ex.printStackTrace();
			// TODO: No olvidar implementar un sistema de diálogs cawn
		}
	}

	/**
	 * @brief Guarda como... (aún no implementado).
	 * TODO Fase 3: implementar \c FileChooser + reescritura.
	 */
	private void saveAsCurrent() {
		// TODO: fase 3: fileChooser + reescribir
	}

	/**
	 * @brief Cierra la pestaña actualmente seleccionada.
	 */
	private void closeCurrentTab() {
		if (editorManager == null)
			return;
		Tab tab = editorManager.getTabPane().getSelectionModel().getSelectedItem();
		if (tab != null)
			editorManager.getTabPane().getTabs().remove(tab);
	}

	/**
	 * @brief Abre un diálogo para seleccionar una carpeta y la carga en el árbol.
	 *
	 *        Usa el home del usuario como directorio inicial si es válido. Delega
	 *        la apertura al \c MainController.
	 */
	public void openAction() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Selecciona una carpeta");

		File home = new File(System.getProperty("user.home")); // Tomamos la ruta principal del sisetma del usuario
		if (home.isDirectory())
			chooser.setInitialDirectory(home); // La ponemos como predeterminada

		File carpeta = chooser.showDialog(ownerWindow); // Mostramos un diálogo para que el usuario pueda seleccionar la
														// carpeta
		if (carpeta == null || mainController == null)
			return;
		mainController.onFolserOpened(carpeta); // Abrimos la carpeta seleccionada por el usuario
	}

	/**
	 * @brief Cierra la carpeta abierta en el \c MainController.
	 */
	public void closeAction() {
		if (mainController != null)
			mainController.onFolderClosed();
	}

	/**
	 * @brief Inyecta los \c HostServices.
	 * @param hostServices Servicios del host.
	 */
	// Setters
	public void setHostServices(HostServices hostServices) {
		this.hostServices = hostServices;
	}

	/**
	 * @brief Inyecta la ventana propietaria para los diálogos.
	 * @param window Ventana propietaria.
	 */
	public void setOwnerWindow(Window window) {
		this.ownerWindow = window;
	}

	/**
	 * @brief Inyecta el \c EditorManager.
	 * @param editorManager Gestor del editor.
	 */
	public void setEditorManager(EditorManager editorManager) {
		this.editorManager = editorManager;
	}

	/**
	 * @brief Inyecta el \c MainController.
	 * @param mainController Controlador principal.
	 */
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}