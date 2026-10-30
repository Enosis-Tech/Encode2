package com.github.enosisTech;

import java.io.File;

import com.github.enosisTech.editor.EditorManager;
import com.github.enosisTech.themes.ThemeManager;
import com.github.enosisTech.themes.Theme;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Window;

/**
 * @brief Controlador principal de la ventana del editor.
 *
 *        Coordina el árbol de archivos (\c TreeView) y el panel de pestañas del
 *        editor (\c TabPane), así como el \c EditorManager y el \c
 *        MenuBarController. Actúa como fachada para las operaciones de
 *        abrir/cerrar carpeta y como puente entre el menu y el editor.
 *
 * @see EditorManager
 * @see MenuBarController
 * @see FileTreeItem
 */
public class MainController {
	/// Referencia al controlador de la barra de menú (inyectada por FXML).
	@FXML
	private MenuBarController MenuBarController;
	/// Árbol de archivos del proyecto abierto.
	@FXML
	private TreeView<File> fileTreeView;
	/// Panel de pestañas donde se muestran los editores abiertos.
	@FXML
	private TabPane editorTabpane;

	/// Gestor de pestañas/edición.
	private EditorManager editorManager;

	@SuppressWarnings("unused")
	/// Servicios del host (para acciones externas).
	private HostServices hostServices;

	/**
	 * @brief Configura el árbol de archivos: tooltip, estilos, cell factory y
	 *        manejador de click.
	 *
	 *        - Define una \c TreeCell personalizada que muestra el nombre del
	 *        archivo (o la ruta absoluta si el nombre está vacío) y un tooltip con
	 *        la ruta. - Al hacer click simple sobre un archivo, lo abre en el
	 *        editor.
	 */
	private void configurateTree() {
		fileTreeView.getStyleClass().add("file-tree");

		fileTreeView.setCellFactory(_ -> new javafx.scene.control.TreeCell<>() {
			@Override
			protected void updateItem(File file, boolean empty) {
				super.updateItem(file, empty);
				if (empty || file == null) {
					setText(null);
					setGraphic(null);
					setTooltip(null);
					return;
				}
				String name = file.getName().isEmpty() ? file.getAbsolutePath() : file.getName();
				setText(name);
				setTooltip(new javafx.scene.control.Tooltip(file.getAbsolutePath()));
				TreeItem<File> item = getTreeItem();
				boolean expanded = item != null && item.isExpanded();
				setGraphic(iconFor(file, expanded));
			}
		});

		// Actualizar icono al expandir/colapsar
		fileTreeView.setCellFactory(tv -> new javafx.scene.control.TreeCell<>() {
			@Override
			protected void updateItem(File file, boolean empty) {
				super.updateItem(file, empty);
				if (empty || file == null) {
					setText(null);
					setGraphic(null);
					setTooltip(null);
					return;
				}
				String name = file.getName().isEmpty() ? file.getAbsolutePath() : file.getName();
				setText(name);
				setTooltip(new javafx.scene.control.Tooltip(file.getAbsolutePath()));
				TreeItem<File> item = getTreeItem();
				boolean expanded = item != null && item.isExpanded();
				setGraphic(iconFor(file, expanded));
			}

			@Override
			public void updateSelected(boolean selected) {
				super.updateSelected(selected);
				File f = getItem();
			}

			// Listener para cambios de expansión
			{
				treeViewProperty().addListener((_, _, tv) -> {
					/* no-op */ });
			}
		});

		// Escuchar cambios de expansión para refrescar iconos
		fileTreeView.expandedItemCountProperty().addListener((_, _, _) -> {
			fileTreeView.refresh();
		});

		fileTreeView.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
				TreeItem<File> item = fileTreeView.getSelectionModel().getSelectedItem();
				if (item != null && item.getValue() != null && item.getValue().isFile())
					editorManager.open(item.getValue());
			}
		});
	}

	// Devuelve el ImageView del icono según el tema actual y si es carpeta
	// abierta/cerrada.
	private ImageView iconFor(File file, boolean expanded) {
		Theme theme = ThemeManager.getInstance().currentThemeProperty().get();
		if (theme == null)
			return null;
		String key;
		if (file.isDirectory())
			key = expanded ? "folder-open" : "folder";
		else
			key = "file";
		String path = theme.icon(key);
		if (path == null)
			return null;
		try {
			var url = getClass().getResource(path);
			if (url == null)
				return null;
			ImageView iv = new ImageView(new Image(url.toExternalForm()));
			iv.setFitWidth(16);
			iv.setFitHeight(16);
			return iv;
		} catch (Exception ex) {
			return null;
		}
	}

	// Fuerza el refresco visual del árbol (se llama al cambiar de tema).
	public void refreshTreeIcons() {
		if (fileTreeView != null)
			fileTreeView.refresh();
	}

	/**
	 * @brief Inicialización del controlador (invocada por FXML).
	 *
	 *        Crea el \c EditorManager, configura el árbol y conecta el menu con el
	 *        editor.
	 */
	@FXML
	public void initialize() {
		editorManager = new EditorManager(editorTabpane);
		// Conectar con el arbol cawn
		configurateTree();
		// Conectar el menu con el editor cawn
		if (MenuBarController != null)
			MenuBarController.setEditorManager(editorManager);
	}

	/**
	 * @brief Abre una carpeta como raíz del árbol de archivos.
	 * @param folder Carpeta a mostrar.
	 */
	// Abrir/cerrar carpeta
	public void onFolserOpened(File folder) {
		FileTreeItem root = new FileTreeItem(folder);
		root.setExpanded(true);
		fileTreeView.setRoot(root);
	}

	/**
	 * @brief Cierra la carpeta actual: limpia el árbol y cierra todas las pestañas.
	 */
	public void onFolderClosed() {
		fileTreeView.setRoot(null);
		editorManager.closeAll();
	}

	/**
	 * @brief Inyecta los \c HostServices y los propaga al menu.
	 * @param hostServices Servicios del host.
	 */
	// Setters
	public void setHostService(HostServices hostServices) {
		this.hostServices = hostServices;
		if (MenuBarController != null)
			MenuBarController.setHostServices(hostServices);
	}

	/**
	 * @brief Inyecta la ventana propietaria y la propaga al menu.
	 * @param wn Ventana propietaria.
	 */
	public void setOwnerWindow(Window wn) {
		if (MenuBarController != null)
			MenuBarController.setOwnerWindow(wn);
	}

	/**
	 * @brief Obtiene la ventana propietaria (a partir de la escena del árbol).
	 * @return Ventana o \c null si aún no está enganchada a una escena.
	 */
	// Getters
	public Window getOwnerWindow() {
		return fileTreeView.getScene() != null ? fileTreeView.getScene().getWindow() : null;
	}
}