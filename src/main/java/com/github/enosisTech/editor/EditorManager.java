package com.github.enosisTech.editor;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @brief Gestiona las pestañas del editor.
 *
 *        Funciones principales: - Evitar abrir el mismo archivo dos veces (foco
 *        en la pestaña existente). - Marcar pestañas "sucias" cuando el
 *        contenido cambia. - Centralizar las operaciones de
 *        abrir/cerrar/guardar.
 *
 * @see CodeEditorPane
 */
public class EditorManager {
	/// Panel de pestañas gestionado.
	private final TabPane tabPane;
	/// Mapa: ruta absoluta -> pestaña abierta.
	// Ruta absoluta -> pestaña
	private final Map<String, Tab> openTabs = new HashMap<>();

	/**
	 * @brief Constructor.
	 * @param tabPane Panel de pestañas que se gestionará.
	 */
	public EditorManager(TabPane tabPane) {
		this.tabPane = tabPane;
	}

	/**
	 * @brief Abre un archivo en una nueva pestaña (o enfoca la existente).
	 *
	 *        Si el archivo ya está abierto, selecciona su pestaña. En caso
	 *        contrario: - Lee el contenido en UTF-8. - Crea un \c CodeEditorPane. -
	 *        Crea una \c Tab y la registra en \c openTabs. - Escucha la propiedad
	 *        \c dirty para añadir/quitar el asterisco en el título.
	 *
	 * @param file Archivo a abrir.
	 */
	public void open(File file) {
		if (file == null || !file.isFile())
			return;
		String key = file.getAbsolutePath();

		// Ya está abierto? -> enfocar
		Tab existing = openTabs.get(key);
		if (existing != null) {
			tabPane.getSelectionModel().select(existing);
			return;
		}

		// Crear pestaña nueva
		try {
			String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
			CodeEditorPane editor = new CodeEditorPane(file, content);
			Tab tab = new Tab(file.getName(), editor);

			// Cerrar pestaña
			tab.setOnClosed(_ -> openTabs.remove(key));
			// Si el editor está "sucio", marcar pestaña
			editor.dirtyProperty().addListener((obs, oldV, newV) -> {
				String title = file.getName() + (newV ? " *" : "");
				tab.setText(title);
			});

			openTabs.put(key, tab);
			tabPane.getTabs().add(tab);
			tabPane.getSelectionModel().select(tab);

		} catch (IOException ex) {
			// TODO: Fse 3: Diálogos
			ex.printStackTrace();
		}
	}

	/**
	 * @brief Cierra todas las pestañas y limpia el registro interno.
	 */
	public void closeAll() {
		tabPane.getTabs().clear();
		openTabs.clear();
	}

	/**
	 * @brief Obtiene el panel de pestañas gestionado.
	 * @return El \c TabPane.
	 */
	public TabPane getTabPane() {
		return tabPane;
	}

}