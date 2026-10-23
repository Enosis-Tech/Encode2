package dev.reymon.graphics.javafx;

import javafx.scene.Parent;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;

public class Scene extends javafx.scene.Scene {

	public Scene(Parent root) {
		super(root);
	}

	public Scene(Parent root, Paint fill) {
		super(root, fill);
	}

	public Scene(Parent root, double width, double height) {
		super(root, width, height);
	}

	public Scene(Parent root, double width, double height, Paint fill) {
		super(root, width, height, fill);
	}

	public Scene(Parent root, double width, double height, boolean depthBuffer) {
		super(root, width, height, depthBuffer);
	}

	public Scene(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) {
		super(root, width, height, depthBuffer, antiAliasing);
	}

}
