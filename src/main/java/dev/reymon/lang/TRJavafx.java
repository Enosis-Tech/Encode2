package dev.reymon.lang;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public final class TRJavafx {
	
	private static Module module;
	private static final ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());

	public static void setModule(Module module) {
		TRJavafx.module = module;
	}
	
	public static void setLocale(Locale newLocale) {
		locale.set(newLocale);
	}
	
	public static void setLocale(String langCode) {
		locale.set(Locale.of(langCode));
	}

	public static ObjectProperty<Locale> localeProperty() {
		return locale;
	}

	public static String get(String key, Object... args) {
		ResourceBundle bundle = ResourceBundle.getBundle("i18n.translate", locale.get(), module);
		String text = bundle.getString(key);
		return args.length == 0 ? text : MessageFormat.format(text, args);
	}

	// Binding that updates automatically when the locale changes
	public static StringBinding bind(String key, Object... args) {
		return Bindings.createStringBinding(() -> get(key, args), locale);
	}

}
