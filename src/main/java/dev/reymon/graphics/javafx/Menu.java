package dev.reymon.graphics.javafx;

import java.util.List;
import java.util.function.Supplier;

import dev.reymon.lang.TRJavafx;
import dev.reymon.lang.TRJavafxOption;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

public class Menu extends javafx.scene.control.Menu {

	public Menu() {
		super();
	}
	
	public Menu(String translate, TRJavafxOption option) {
		
		switch(option) {
		
		case BIND:
			this.textProperty().bind(TRJavafx.bind(translate));
			break;
		
		}
		
	}

	public Menu(String text, Node graphic, MenuItem... items) {
		super(text, graphic, items);
	}

	public Menu(String text, Node graphic) {
		super(text, graphic);
	}

	public Menu(String text) {
		super(text);
	}

	public void add(Supplier<List<MenuItem>> list) {

		for (var item : list.get())
			this.getItems().add(item);

	}

}
