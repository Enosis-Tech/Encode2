package dev.reymon.graphics.javafx;

import dev.reymon.lang.TRJavafx;
import dev.reymon.lang.TRJavafxOption;
import javafx.scene.Node;

public class Button extends javafx.scene.control.Button {

	public Button() {
		super();
	}
	
	public Button(String text, TRJavafxOption option) {
		
		switch (option) {
		
		case BIND:
			
			
			this.textProperty().bind(TRJavafx.bind(text));
			break;
		
		}
		
	}

	public Button(String text, Node graphic) {
		super(text, graphic);
	}

	public Button(String text) {
		super(text);
	}

}
