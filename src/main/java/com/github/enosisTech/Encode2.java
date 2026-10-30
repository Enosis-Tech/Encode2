package com.github.enosisTech;

import com.github.enosisTech.themes.ThemeManager;

import dev.reymon.graphics.javafx.Scene;
import dev.reymon.lang.TRJavafx;

import javafx.application.HostServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * @brief Clase principal (Application) del editor Encode.
 *
 *        Punto de entrada de la aplicación JavaFX. Se encarga de: - Inicializar
 *        el modulo de la aplicación para el sistema de i18n (TRJavafx). -
 *        Cargar la vista FXML principal (\c /fxml/Window.fxml). - Inyectar
 *        dependencias (HostServices, owner window) en los controladores. -
 *        Enganchar el \c MenuBarController con el \c MainController. - Aplicar
 *        la hoja de estilos del tema y mostrar la ventana principal.
 *
 * @note Esta clase extiende \c javafx.application.Application, por lo que su
 *       ciclo de vida lo gestiona el runtime de JavaFX.
 * @see MainController
 * @see MenuBarController
 */
public class Encode2 extends Application {

	/// Servicios del host (para abrir URIs externas, correos, etc.).
	private HostServices hostServices;

	/// Modulo Java 9+ al que pertenece esta clase, usado por TRJavafx para i18n.
	private final Module module = Encode2.class.getModule();

	/**
	 * @brief Inicialización previa al arranque de JavaFX.
	 *
	 *        Registra el modulo en TRJavafx (para resolución de recursos de idioma)
	 *        y obtiene la instancia de \c HostServices que se pasará a los
	 *        controladores.
	 */
	@Override
	public void init() {
		TRJavafx.setModule(module);
		hostServices = getHostServices();
	}

	/**
	 * @brief Arranca la interfaz gráfica principal.
	 *
	 *        Carga el FXML, obtiene los controladores, conecta el menu con el
	 *        controlador principal, construye la escena, aplica estilos y muestra
	 *        la ventana.
	 *
	 * @param primaryStage Escenario principal provisto por JavaFX.
	 * @throws Exception Si falla la carga del FXML o algún recurso.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Window.fxml"));
		Parent root = loader.load();
		MainController main = loader.getController();
		main.setHostService(hostServices);
		main.setOwnerWindow(primaryStage);

		// Enganchar el menu al maincontroller
		MenuBarController menu = (MenuBarController) loader.getNamespace().get("MenuBarController");
		if (menu != null)
			menu.setMainController(main);

		Scene scene = new Scene(root, 1000, 650);
		ThemeManager.getInstance().attach(scene);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Encode");
		primaryStage.setMinWidth(720);
		primaryStage.setMinHeight(480);
		primaryStage.show();

	}

	/**
	 * @brief Punto de entrada estándar de la JVM.
	 * @param args Argumentos de línea de comandos (no usados).
	 */
	public static void main(String[] args) {
		launch();
	}
}