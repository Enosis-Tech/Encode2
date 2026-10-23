package dev.reymon.graphics.javafx;

import javafx.scene.Node;

public class BorderPane extends javafx.scene.layout.BorderPane {

	public BorderPane() {
		super();
	}

	public BorderPane(Node center, Node top, Node right, Node bottom, Node left) {
		super(center, top, right, bottom, left);
	}

	public BorderPane(Node center) {
		super(center);
	}

}
