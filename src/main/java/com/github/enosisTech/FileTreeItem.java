package com.github.enosisTech;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @brief Item de árbol (\c TreeItem<File>) con carga perezosa (lazy) de hijos.
 *
 *        Extiende \c TreeItem para representar archivos y carpetas del sistema
 *        de archivos. Los hijos de una carpeta no se cargan hasta que se
 *        solicitan (por ejemplo al expandir el nodo), lo que mejora el
 *        rendimiento con directorios grandes.
 *
 * @note Los filtros aplicados excluyen: archivos ocultos, nombres que empiezan
 *       por '.', la carpeta \c node_modules y la carpeta \c target.
 */
public class FileTreeItem extends TreeItem<File> {

	/**
	 * @brief Orden de los hijos: primero carpetas, luego archivos, y en ambos casos
	 *        por nombre (case-insensitive).
	 */
	private static final Comparator<File> ORDER = Comparator.comparing(File::isFile) // carpetas primero
			.thenComparing(f -> f.getName().toLowerCase());

	/// Indica si los hijos ya fueron cargados alguna vez.
	private boolean childrenLoaded = false;

	/**
	 * @brief Constructor.
	 * @param file Archivo o carpeta que representa este nodo.
	 */
	public FileTreeItem(File file) {
		super(file);
	}

	/**
	 * @brief Fuerza la recarga de los hijos en la próxima llamada a \c
	 *        getChildren().
	 *
	 *        Limpia los hijos actuales y marca el estado como "no cargado".
	 */
	public void refresh() {
		childrenLoaded = false;
		super.getChildren().clear();
	}

	/**
	 * @brief Construye la lista de hijos a partir del sistema de archivos.
	 *
	 *        Aplica los filtros de exclusión (ocultos, '.', \c node_modules, \c
	 *        target) y ordena según \c ORDER.
	 *
	 * @return Lista observable de \c TreeItem<File> (posiblemente vacía).
	 */
	private ObservableList<TreeItem<File>> buildChildren() {
		File dir = getValue();
		if (dir == null || !dir.isDirectory())
			return FXCollections.emptyObservableList();

		File[] files = dir.listFiles(f -> !f.isHidden() && !f.getName().startsWith(".")
				&& !f.getName().equals("node_modules") && !f.getName().equals("target"));

		if (files == null || files.length == 0)
			return FXCollections.emptyObservableList();

		Arrays.sort(files, ORDER);

		ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();
		for (File f : files) {
			children.add(new FileTreeItem(f));
		}
		return children;
	}

	/**
	 * @brief Devuelve los hijos del nodo, cargándolos de forma perezosa la primera
	 *        vez.
	 * @return Lista observable de hijos.
	 */
	@Override
	public ObservableList<TreeItem<File>> getChildren() {
		if (!childrenLoaded) {
			childrenLoaded = true;
			super.getChildren().setAll(buildChildren());
		}
		return super.getChildren();
	}

	/**
	 * @brief Determina si el nodo es hoja (archivo o nulo).
	 * @return \c true si es un archivo o valor nulo; \c false si es carpeta.
	 */
	@Override
	public boolean isLeaf() {
		File f = getValue();
		if (f == null)
			return true;
		if (f.isFile())
			return true;
		return false;
	}
}