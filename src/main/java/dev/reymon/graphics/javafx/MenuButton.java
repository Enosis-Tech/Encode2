package dev.reymon.graphics.javafx;

import java.util.List;
import java.util.function.Supplier;

import dev.reymon.lang.TRJavafx;
import dev.reymon.lang.TRJavafxOption;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

public class MenuButton extends javafx.scene.control.MenuButton {

	public MenuButton() {
	}

	public MenuButton(String text) {
		super(text);
	}
	
	public MenuButton(String text, TRJavafxOption option) {
		
		switch (option) {
		
		case BIND:
			
			this.textProperty().bind(TRJavafx.bind(text));
			
			break;
		
		}
		
	}

	public MenuButton(String text, Node graphic) {
		super(text, graphic);
	}

	public MenuButton(String text, Node graphic, MenuItem... items) {
		super(text, graphic, items);
	}
	
	public void addCenter(MenuItem... list) {
		this.getItems().addAll(list);
	}
	
	public void addCenter(Supplier<List<MenuItem>> list) {
		this.getItems().addAll(list.get());
	}

}
