package com.github.enosisTech.editor;

// Javafx
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

// RichTextFX
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

// Java
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @brief Panel de edición para un archivo concreto.
 *
 *        Contiene un \c CodeArea (RichTextFX) con números de línea y resaltado
 *        de sintaxis básico para Java. Expone una propiedad \c dirty que indica
 *        si el contenido difiere del original en disco.
 *
 * @see EditorManager
 */
public class CodeEditorPane extends javafx.scene.layout.BorderPane {

	/// Archivo asociado al editor.
	private final File file;
	/// Área de código con números de línea.
	private final CodeArea codeArea = new CodeArea();
	/// Indica si el contenido ha sido modificado respecto al original.
	private final BooleanProperty dirty = new SimpleBooleanProperty(false);
	/// Contenido original del archivo (para detectar cambios).
	private String originalContent;

	/// Palabras clave de Java usadas para el resaltado de sintaxis.
	// Resaltado de sintaxis, por ahora resaltaremos Java
	private static final String[] KEYWORDS = { "abstract", "assert", "boolean", "break", "byte", "case", "catch",
			"char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
			"finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long",
			"native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
			"super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile",
			"while", "true", "false", "null", "var", "record", "sealed", "permits", "yield" };

	/**
	 * @brief Patrón de resaltado de sintaxis para Java.
	 *
	 *        Grupos con nombre: - \c KEYWORD: palabras clave. - \c STRING: cadenas
	 *        entre comillas dobles (con escapes). - \c COMMENT: comentarios de
	 *        línea (\c //) y de bloque (\c /\* ... *\/). - \c NUMBER: números
	 *        enteros o decimales. - \c ANNOTATION: anotaciones (\c @Nombre).
	 */
	private static final Pattern SYNTAX = Pattern.compile("(?<KEYWORD>\\b(" + String.join("|", KEYWORDS) + ")\\b)"
			+ "|(?<STRING>\"([^\"\\\\]|\\\\.)*\")" + "|(?<COMMENT>//[^\n]*|/\\*(?:[^*]|\\*[^/])*\\*/)"
			+ "|(?<NUMBER>\\b\\d+(\\.\\d+)?\\b)" + "|(?<ANNOTATION>@\\w+)");

	/**
	 * @brief Calcula los \c StyleSpans de resaltado para un texto dado.
	 * @param text Texto completo del editor.
	 * @return Colección de estilos por rango.
	 */
	private static StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = SYNTAX.matcher(text);
		StyleSpansBuilder<Collection<String>> spans = new StyleSpansBuilder<>();
		int lastEnd = 0;
		while (matcher.find()) {
			String style;
			if (matcher.group("KEYWORD") != null)
				style = "keyword";
			else if (matcher.group("STRING") != null)
				style = "string";
			else if (matcher.group("COMMENT") != null)
				style = "comment";
			else if (matcher.group("NUMBER") != null)
				style = "number";
			else if (matcher.group("ANNOTATION") != null)
				style = "annotation";
			else
				continue;

			spans.add(Collections.emptyList(), matcher.start() - lastEnd);
			spans.add(Collections.singleton(style), matcher.end() - matcher.start());

			lastEnd = matcher.end();
		}
		spans.add(Collections.emptyList(), text.length() - lastEnd);
		return spans.create();
	}

	/**
	 * @brief Guarda el archivo en disco (UTF-8) y limpia el flag \c dirty.
	 * @throws IOException Si falla la escritura.
	 */
	// Guardar el archivo y liampa el flag dirty
	public void save() throws IOException {
		String text = codeArea.getText();
		Files.writeString(file.toPath(), text, StandardCharsets.UTF_8);
		originalContent = text;
		dirty.set(false);
	}

	/**
	 * @brief Construye el panel de edición para un archivo concreto.
	 *
	 *        Configura: - El contenido inicial del \c CodeArea. - La fábrica de
	 *        números de línea. - La clase CSS \c code-area. - Un listener reactivo
	 *        que recalcula el resaltado y actualiza \c dirty. - Una primera pasada
	 *        de resaltado.
	 *
	 * @param file    Archivo asociado.
	 * @param content Contenido inicial del archivo.
	 */
	public CodeEditorPane(File file, String content) {
		this.file = file;
		this.originalContent = content;

		codeArea.replaceText(content);
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		codeArea.getStyleClass().add("code-area");

		// Resaltado reactivo
		codeArea.textProperty().addListener((obs, oldText, newText) -> {
			codeArea.setStyleSpans(0, computeHighlighting(newText));
			dirty.set(!newText.equals(originalContent));
		});
		// Primera pasada
		codeArea.setStyleSpans(0, computeHighlighting(content));

		setCenter(codeArea);
	}

	/**
	 * @brief Devuelve la propiedad observable \c dirty.
	 * @return Propiedad que indica si hay cambios sin guardar.
	 */
	public BooleanProperty dirtyProperty() {
		return dirty;
	}

	/**
	 * @brief Devuelve el \c CodeArea subyacente.
	 * @return Área de código.
	 */
	public CodeArea getCodeArea() {
		return codeArea;
	}

	/**
	 * @brief Devuelve el archivo asociado al editor.
	 * @return Archivo.
	 */
	public File getFile() {
		return file;
	}

}