package dev.reymon.graphics.javafx;

import dev.reymon.lang.TRJavafx;
import dev.reymon.lang.TRJavafxOption;
import javafx.scene.Node;

public class MenuItem extends javafx.scene.control.MenuItem {

	public MenuItem() {
	}

	public MenuItem(String text, TRJavafxOption option) {

		switch (option) {

		case BIND:
			this.textProperty().bind(TRJavafx.bind(text));
			break;

		}

	}

	public MenuItem(String text) {
		super(text);
	}

	public MenuItem(String text, Node graphic) {
		super(text, graphic);
	}

}
