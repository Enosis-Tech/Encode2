package com.github.enosisTech.themes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;

/**
 * @brief Singleton que gestiona el tema activo de la aplicación.
 *
 *        Responsabilidades: - Mantener la lista de temas disponibles. - Aplicar
 *        el CSS del tema a la \c Scene. - Exponer \c currentThemeProperty()
 *        para que los componentes "theme-aware" (iconos del árbol, fuente,
 *        etc.) se actualicen al cambiar.
 *
 * @note Actualmente los temas están hardcodeados; en el futuro podrían cargarse
 *       desde un JSON.
 */
public class ThemeManager {

	/// Instancia única (patrón singleton).
	private static final ThemeManager INSTANCE = new ThemeManager();
	/// Tema actualmente seleccionado (observable).
	private final ObjectProperty<Theme> currentTheme = new SimpleObjectProperty<>();
	/// Lista de temas registrados.
	private final List<Theme> availableThemes = new ArrayList<>();

	@SuppressWarnings("unused")
	/// Escena a la que se le aplicará el CSS del tema.
	private Scene scene;

	/**
	 * @brief Constructor privado del singleton.
	 *
	 *        Registra los temas integrados y establece el primero como activo.
	 */
	private ThemeManager() {
		registerBuiltinThemes();
		currentTheme.set(availableThemes.get(0));
	}

	/**
	 * @brief Registra los temas integrados (claro y oscuro).
	 *
	 * @todo Migrar a carga desde JSON.
	 */
	// Primero serán hardcordeados, después le creó un JSON
	private void registerBuiltinThemes() {
		// Tema claro
		availableThemes.add(new Theme("light", "Claro", "/themes/light/theme.css",
				Map.of("folder", "/themes/light/icons/folder.png", "folder-open", "/themes/light/icons/folder-open.png",
						"file", "/themes/light/icons/file.png")));

		// Tema oscuro
		availableThemes.add(
				new Theme("dark", "Oscuro", "/themes/dark/theme.css", Map.of("folder", "/themes/dark/icons/folder.png",
						"folder-open", "/themes/dark/icons/folder-open.png", "file", "/themes/dark/icons/file.png")));
	}

	/**
	 * @brief Aplica un tema a la escena actual
	 * @param theme Tema a aplicar
	 */
	public void appyTheme(Theme theme) {
		if (theme == null || scene == null)
			return;

		String base = getClass().getResource("/themes/base.css").toExternalForm();
		String themeCss = getClass().getResource(theme.cssPath()).toExternalForm();
		scene.getStylesheets().setAll(base, themeCss);
		currentTheme.set(theme);
	}

	/*
	 * @brief Enganch a la escena sobre la que se aplicará el tema.
	 * 
	 * @param scene Escena principal
	 */
	public void attach(Scene scene) {
		this.scene = scene;
		appyTheme(currentTheme.get());
	}

	/**
	 * @brief Busca un tema por su id
	 * @param id Identificador del tema
	 * @return Tema encontrado o \c null
	 */
	public Theme findById(String id) {
		return availableThemes.stream().filter(t -> t.id().equals(id)).findFirst().orElse(null);
	}

	/**
	 * @brief Lista de temas disponibles (solo para lectura)
	 * @return Copia de la lista
	 */
	public List<Theme> getAviableThemes() {
		return List.copyOf(availableThemes);
	}

	/**
	 * @brief Propiedad observable del tema actual
	 * @return Propiedad del tama activo
	 */
	public ObjectProperty<Theme> currentThemeProperty() {
		return currentTheme;
	}

	/**
	 * @brief Obtiene la instancia única del \c ThemeManager.
	 * @return La instancia singleton.
	 */
	// Obtener la instancia
	public static ThemeManager getInstance() {
		return INSTANCE;
	}

}