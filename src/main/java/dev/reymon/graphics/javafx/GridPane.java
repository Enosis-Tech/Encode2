package dev.reymon.graphics.javafx;

import java.util.List;
import java.util.function.Supplier;

import javafx.scene.Node;

public class GridPane extends javafx.scene.layout.GridPane {

	public GridPane() {
	}

	public GridPane(double hgap, double vgap) {
		super(hgap, vgap);
	}
	
	public void add(Supplier<List<Node>> list) {
		this.getChildren().addAll(list.get());
	}

}
