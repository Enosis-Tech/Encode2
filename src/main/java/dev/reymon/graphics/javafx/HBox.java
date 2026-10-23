package dev.reymon.graphics.javafx;

import java.util.List;
import java.util.function.Supplier;

import javafx.geometry.Pos;
import javafx.scene.Node;

public class HBox extends javafx.scene.layout.HBox {

	public HBox() {
		super();
	}

	public HBox(double spacing, Node... children) {
		super(spacing, children);
	}

	public HBox(double spacing) {
		super(spacing);
	}

	public HBox(Node... children) {
		super(children);
	}

	// The general methods for add nodes

	private void add(Node node) {
		this.getChildren().add(node);
	}

	private void add(Node... arr) {
		this.getChildren().addAll(arr);
	}

	private void add(List<Node> list) {
		this.getChildren().addAll(list);
	}

	/**
	 * Methods for add on top the nodes
	 */

	public void addTop(Node node) {
		this.setAlignment(Pos.TOP_CENTER);
		this.add(node);
	}

	public void addTopLeft(Node node) {
		this.setAlignment(Pos.TOP_LEFT);
		this.add(node);
	}

	public void addTopRight(Node node) {
		this.setAlignment(Pos.TOP_RIGHT);
		this.add(node);
	}

	public void addTop(Node... node) {
		this.setAlignment(Pos.TOP_CENTER);
		this.add(node);
	}

	public void addTopLeft(Node... node) {
		this.setAlignment(Pos.TOP_LEFT);
		this.add(node);
	}

	public void addTopRight(Node... node) {
		this.setAlignment(Pos.TOP_RIGHT);
		this.add(node);
	}

	public void addTop(Supplier<List<Node>> list) {
		this.setAlignment(Pos.TOP_CENTER);
		this.add(list.get());
	}

	public void addTopLeft(Supplier<List<Node>> list) {
		this.setAlignment(Pos.TOP_LEFT);
		this.add(list.get());
	}

	public void addTopRight(Supplier<List<Node>> list) {
		this.setAlignment(Pos.TOP_RIGHT);
		this.add(list.get());
	}

	/**
	 * Methods for add on center the nodes with the lambda expression
	 */

	public void addCenter(Node node) {
		this.setAlignment(Pos.CENTER);
		this.add(node);
	}

	public void addCenterLeft(Node node) {
		this.setAlignment(Pos.CENTER_LEFT);
		this.add(node);
	}

	public void addCenterRight(Node node) {
		this.setAlignment(Pos.CENTER_RIGHT);
		this.add(node);
	}

	public void addCenter(Node... node) {
		this.setAlignment(Pos.CENTER);
		this.add(node);
	}

	public void addCenterLeft(Node... node) {
		this.setAlignment(Pos.CENTER_LEFT);
		this.add(node);
	}

	public void addCenterRight(Node... node) {
		this.setAlignment(Pos.CENTER_RIGHT);
		this.add(node);
	}

	public void addCenter(Supplier<List<Node>> list) {
		this.setAlignment(Pos.CENTER);
		this.add(list.get());
	}

	public void addCenterLeft(Supplier<List<Node>> list) {
		this.setAlignment(Pos.CENTER_LEFT);
		this.add(list.get());
	}

	public void addCenterRight(Supplier<List<Node>> list) {
		this.setAlignment(Pos.CENTER_RIGHT);
		this.add(list.get());
	}

	/**
	 * Methods for add on bottom the nodes with the lambda expression
	 */

	public void addBottom(Node node) {
		this.setAlignment(Pos.BOTTOM_CENTER);
		this.add(node);
	}

	public void addBottomLeft(Node node) {
		this.setAlignment(Pos.BOTTOM_LEFT);
		this.add(node);
	}

	public void addBottomRight(Node node) {
		this.setAlignment(Pos.BOTTOM_RIGHT);
		this.add(node);
	}

	public void addBottom(Node... node) {
		this.setAlignment(Pos.BOTTOM_CENTER);
		this.add(node);
	}

	public void addBottomLeft(Node... node) {
		this.setAlignment(Pos.BOTTOM_LEFT);
		this.add(node);
	}

	public void addBottomRight(Node... node) {
		this.setAlignment(Pos.BOTTOM_RIGHT);
		this.add(node);
	}

	public void addBottom(Supplier<List<Node>> list) {
		this.setAlignment(Pos.BOTTOM_CENTER);
		this.add(list.get());
	}

	public void addBottomLeft(Supplier<List<Node>> list) {
		this.setAlignment(Pos.BOTTOM_LEFT);
		this.add(list.get());
	}

	public void addBottomRight(Supplier<List<Node>> list) {
		this.setAlignment(Pos.BOTTOM_RIGHT);
		this.add(list.get());
	}

}
