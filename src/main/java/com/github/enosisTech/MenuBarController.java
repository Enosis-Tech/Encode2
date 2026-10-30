package com.github.enosisTech;

import java.io.File;

import com.github.enosisTech.editor.CodeEditorPane;
import com.github.enosisTech.editor.EditorManager;
import com.github.enosisTech.themes.ThemeManager;

import dev.reymon.graphics.javafx.Menu;
import dev.reymon.graphics.javafx.MenuItem;
import dev.reymon.lang.TRJavafx;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

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
	
	private final ThemeManager themeManager = ThemeManager.getInstance();
	
	/// Servicios del host (mail, urls, etc.).
	private HostServices hostServices;
	
	/// Ventana propietaria para diálogos.
	private Window ownerWindow;
	
	/// Gestor del editor (pestañas y guardado).
	private EditorManager editorManager;
	
	/// Controlador principal para operaciones de carpeta.
	private MainController mainController;

	// Archivo
	@FXML private Menu file;
	@FXML private MenuItem newFile, newProject;
	@FXML private MenuItem openFile, openProject, closeFile, closeTab;
	@FXML private MenuItem saveFile, saveAsFile, saveAll;
	@FXML private MenuItem recentFiles, exitApp;

	// Editar
	@FXML private Menu edit;
	@FXML private MenuItem undo, redo;
	@FXML private MenuItem cut, copy, paste;
	@FXML private MenuItem find, replace, goToLine, formatCode;

	// Compilar
	@FXML private Menu build;
	@FXML private MenuItem buildProject, rebuildProject, cleanProject;
	@FXML private MenuItem buildConfigCOBOL, buildConfigAda, buildConfigFortran, buildConfigPLI;
	@FXML private MenuItem runProject, stopProject;

	// Depurar
	@FXML private Menu debug;
	@FXML private MenuItem startDebug, stopDebug;
	@FXML private MenuItem stepOver, stepInto, stepOut, continueExecution;
	@FXML private MenuItem toggleBreakpoint, breakpointManager, watchVariables, callStack;
	@FXML private MenuItem debugConsole;

	// Herramientas
	@FXML private Menu tools;
	@FXML private MenuItem compilerSettings, runtimeSettings, projectStructure;
	@FXML private MenuItem lineEndingCRLF, lineEndingLF, columnRuler, encodingSettings;
	@FXML private MenuItem pluginManager, terminal, taskRunner;

	// Ventana
	@FXML private Menu window;
	@FXML private MenuItem toggleFileTree, toggleEditorPane, toggleConsole;
	@FXML private MenuItem resetLayout, fullScreen, newEditorSplit;

	// Configuración
	@FXML private Menu setting;
	@FXML private Menu languageSetting;
	@FXML private MenuItem es, en, el, de;
	@FXML private Menu themeSetting;
	@FXML private MenuItem themeLight, themeDark;
	@FXML private MenuItem themeSolarizedLight, themeSolarizedDark;
	@FXML private MenuItem themeMonokai, themeDracula, themeHighContrast;
	@FXML private Menu editorSetting;
	@FXML private MenuItem fontSize, fontFamily, tabSize, wordWrap, syntaxHighlighting, autoIndent;
	@FXML private MenuItem keymaps, preferences;

	// Ayuda
	@FXML private Menu help;
	@FXML private MenuItem documentation, shortcutsReference, releaseNotes;
	@FXML private MenuItem supportHelp, checkUpdates, about, exitHelp;

	/**
	 * @brief Inicialización del controlador (invocada por FXML).
	 *
	 *        - Enlaza los textos de los menús a las claves i18n de TRJavafx. -
	 *        Registra los manejadores de acción de cada \c MenuItem.
	 */
	
	public void initialize() {

		// Archivo
		file.textProperty().bind(TRJavafx.bind("MenuBar.File"));
		newFile.textProperty().bind(TRJavafx.bind("MenuBar.File.New"));
		newProject.textProperty().bind(TRJavafx.bind("MenuBar.File.NewProject"));
		openFile.textProperty().bind(TRJavafx.bind("MenuBar.File.Open"));
		openProject.textProperty().bind(TRJavafx.bind("MenuBar.File.OpenProject"));
		closeFile.textProperty().bind(TRJavafx.bind("MenuBar.File.Close"));
		closeTab.textProperty().bind(TRJavafx.bind("MenuBar.File.CloseTab"));
		saveFile.textProperty().bind(TRJavafx.bind("MenuBar.File.Save"));
		saveAsFile.textProperty().bind(TRJavafx.bind("MenuBar.File.SaveAs"));
		saveAll.textProperty().bind(TRJavafx.bind("MenuBar.File.SaveAll"));
		recentFiles.textProperty().bind(TRJavafx.bind("MenuBar.File.RecentFiles"));
		exitApp.textProperty().bind(TRJavafx.bind("MenuBar.File.Exit"));

		// Editar
		edit.textProperty().bind(TRJavafx.bind("MenuBar.Edit"));
		undo.textProperty().bind(TRJavafx.bind("MenuBar.Edit.Undo"));
		redo.textProperty().bind(TRJavafx.bind("MenuBar.Edit.Redo"));
		cut.textProperty().bind(TRJavafx.bind("MenuBar.Edit.Cut"));
		copy.textProperty().bind(TRJavafx.bind("MenuBar.Edit.Copy"));
		paste.textProperty().bind(TRJavafx.bind("MenuBar.Edit.Paste"));
		find.textProperty().bind(TRJavafx.bind("MenuBar.Edit.Find"));
		replace.textProperty().bind(TRJavafx.bind("MenuBar.Edit.Replace"));
		goToLine.textProperty().bind(TRJavafx.bind("MenuBar.Edit.GoToLine"));
		formatCode.textProperty().bind(TRJavafx.bind("MenuBar.Edit.FormatCode"));

		// Compilar
		build.textProperty().bind(TRJavafx.bind("MenuBar.Build"));
		buildProject.textProperty().bind(TRJavafx.bind("MenuBar.Build.BuildProject"));
		rebuildProject.textProperty().bind(TRJavafx.bind("MenuBar.Build.RebuildProject"));
		cleanProject.textProperty().bind(TRJavafx.bind("MenuBar.Build.CleanProject"));
		buildConfigCOBOL.textProperty().bind(TRJavafx.bind("MenuBar.Build.ConfigCOBOL"));
		buildConfigAda.textProperty().bind(TRJavafx.bind("MenuBar.Build.ConfigAda"));
		buildConfigFortran.textProperty().bind(TRJavafx.bind("MenuBar.Build.ConfigFortran"));
		buildConfigPLI.textProperty().bind(TRJavafx.bind("MenuBar.Build.ConfigPLI"));
		runProject.textProperty().bind(TRJavafx.bind("MenuBar.Build.Run"));
		stopProject.textProperty().bind(TRJavafx.bind("MenuBar.Build.Stop"));

		// Depurar
		debug.textProperty().bind(TRJavafx.bind("MenuBar.Debug"));
		startDebug.textProperty().bind(TRJavafx.bind("MenuBar.Debug.Start"));
		stopDebug.textProperty().bind(TRJavafx.bind("MenuBar.Debug.Stop"));
		stepOver.textProperty().bind(TRJavafx.bind("MenuBar.Debug.StepOver"));
		stepInto.textProperty().bind(TRJavafx.bind("MenuBar.Debug.StepInto"));
		stepOut.textProperty().bind(TRJavafx.bind("MenuBar.Debug.StepOut"));
		continueExecution.textProperty().bind(TRJavafx.bind("MenuBar.Debug.Continue"));
		toggleBreakpoint.textProperty().bind(TRJavafx.bind("MenuBar.Debug.ToggleBreakpoint"));
		breakpointManager.textProperty().bind(TRJavafx.bind("MenuBar.Debug.BreakpointManager"));
		watchVariables.textProperty().bind(TRJavafx.bind("MenuBar.Debug.WatchVariables"));
		callStack.textProperty().bind(TRJavafx.bind("MenuBar.Debug.CallStack"));
		debugConsole.textProperty().bind(TRJavafx.bind("MenuBar.Debug.Console"));

		// Herramientas
		tools.textProperty().bind(TRJavafx.bind("MenuBar.Tools"));
		compilerSettings.textProperty().bind(TRJavafx.bind("MenuBar.Tools.CompilerSettings"));
		runtimeSettings.textProperty().bind(TRJavafx.bind("MenuBar.Tools.RuntimeSettings"));
		projectStructure.textProperty().bind(TRJavafx.bind("MenuBar.Tools.ProjectStructure"));
		lineEndingCRLF.textProperty().bind(TRJavafx.bind("MenuBar.Tools.LineEndingCRLF"));
		lineEndingLF.textProperty().bind(TRJavafx.bind("MenuBar.Tools.LineEndingLF"));
		columnRuler.textProperty().bind(TRJavafx.bind("MenuBar.Tools.ColumnRuler"));
		encodingSettings.textProperty().bind(TRJavafx.bind("MenuBar.Tools.EncodingSettings"));
		pluginManager.textProperty().bind(TRJavafx.bind("MenuBar.Tools.PluginManager"));
		terminal.textProperty().bind(TRJavafx.bind("MenuBar.Tools.Terminal"));
		taskRunner.textProperty().bind(TRJavafx.bind("MenuBar.Tools.TaskRunner"));

		// Ventana
		window.textProperty().bind(TRJavafx.bind("MenuBar.Window"));
		toggleFileTree.textProperty().bind(TRJavafx.bind("MenuBar.Window.ToggleFileTree"));
		toggleEditorPane.textProperty().bind(TRJavafx.bind("MenuBar.Window.ToggleEditorPane"));
		toggleConsole.textProperty().bind(TRJavafx.bind("MenuBar.Window.ToggleConsole"));
		resetLayout.textProperty().bind(TRJavafx.bind("MenuBar.Window.ResetLayout"));
		fullScreen.textProperty().bind(TRJavafx.bind("MenuBar.Window.FullScreen"));
		newEditorSplit.textProperty().bind(TRJavafx.bind("MenuBar.Window.NewEditorSplit"));

		// Configuración
		setting.textProperty().bind(TRJavafx.bind("MenuBar.Setting"));
		languageSetting.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language"));
		es.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.es"));
		en.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.en"));
		el.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.el"));
		de.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Language.de"));
		themeSetting.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Theme"));
		themeLight.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Theme.Light"));
		themeDark.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Theme.Dark"));
		themeSolarizedLight.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Theme.SolarizedLight"));
		themeSolarizedDark.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Theme.SolarizedDark"));
		themeMonokai.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Theme.Monokai"));
		themeDracula.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Theme.Dracula"));
		themeHighContrast.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Theme.HighContrast"));
		editorSetting.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Editor"));
		fontSize.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Editor.FontSize"));
		fontFamily.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Editor.FontFamily"));
		tabSize.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Editor.TabSize"));
		wordWrap.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Editor.WordWrap"));
		syntaxHighlighting.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Editor.SyntaxHighlighting"));
		autoIndent.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Editor.AutoIndent"));
		keymaps.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Keymaps"));
		preferences.textProperty().bind(TRJavafx.bind("MenuBar.Setting.Preferences"));

		// Ayuda
		help.textProperty().bind(TRJavafx.bind("MenuBar.Help"));
		documentation.textProperty().bind(TRJavafx.bind("MenuBar.Help.Documentation"));
		shortcutsReference.textProperty().bind(TRJavafx.bind("MenuBar.Help.Shortcuts"));
		releaseNotes.textProperty().bind(TRJavafx.bind("MenuBar.Help.ReleaseNotes"));
		supportHelp.textProperty().bind(TRJavafx.bind("MenuBar.Help.Support"));
		checkUpdates.textProperty().bind(TRJavafx.bind("MenuBar.Help.CheckUpdates"));
		about.textProperty().bind(TRJavafx.bind("MenuBar.Help.About"));
		exitHelp.textProperty().bind(TRJavafx.bind("MenuBar.Help.Exit"));

		
		
		// Acciones

		// Archivo
		openFile.setOnAction(_ -> openAction());
		closeFile.setOnAction(_ -> closeAction());
		saveFile.setOnAction(_ -> saveCurrent());
		saveAsFile.setOnAction(_ -> saveAsCurrent());
		closeTab.setOnAction(_ -> closeCurrentTab());

		// Configuración
		es.setOnAction(_ -> TRJavafx.setLocale("es"));
		en.setOnAction(_ -> TRJavafx.setLocale("en"));
		el.setOnAction(_ -> TRJavafx.setLocale("el"));
		de.setOnAction(_ -> TRJavafx.setLocale("de"));

		themeLight.setOnAction(_ -> themeManager.appyTheme(themeManager.findById("light")));
		themeDark.setOnAction(_ -> themeManager.appyTheme(themeManager.findById("dark")));

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