package com.github.enosisTech.themes;

import java.util.Map;

/**
 * @brief Representa un tema visual de la aplicación.
 *
 *        Record inmutable que agrupa el identificador, el nombre visible, la
 *        ruta al CSS del tema y un mapa de rutas de iconos (por clave lógica,
 *        p.ej. \c "folder", \c "folder-open", \c "file").
 *
 * @note Un record es una clase inmutable, diseñada para representar datos de
 *       manera concisa. Con esto se evita escribir código repetitivo.
 *
 * @param id          Identificador único del tema (p.ej. "light", "dark").
 * @param displayName Nombre mostrado al usuario.
 * @param cssPath     Ruta al archivo CSS del tema.
 * @param iconPath    Mapa de tipo de icono -> ruta del recurso.
 */
public record Theme(String id, String displayName, String cssPath, Map<String, String> iconPath /* Tipo y ruta */) {
	/**
	 * @brief Obtiene la ruta del icono asociado a una clave.
	 * @param key Clave lógica del icono (p.ej. "folder", "file").
	 * @return Ruta del recurso o \c null si no existe.
	 */
	public String icon(String key) {
		return iconPath.get(key);
	}
}