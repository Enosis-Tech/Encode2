package dev.reymon.graphics.javafx;

import java.util.List;
import java.util.function.Supplier;

public class MenuBar extends javafx.scene.control.MenuBar {

	/**
	 * Constructors
	 */

	public MenuBar() {
		super();
	}

	public MenuBar(Menu... menus) {
		super(menus);
	}

	public MenuBar(List<Menu> menus) {

		for (int i = 0; i < menus.size(); i++)
			this.getMenus().add(menus.get(i));

	}

	public MenuBar(Supplier<List<Menu>> list) {

		for (Menu menu : list.get())
			this.getMenus().add(menu);

	}

	/**
	 * Methods
	 */

	public void add(Menu... menus) {
		this.getMenus().addAll(menus);
	}

	public void add(Supplier<List<Menu>> list) {
		this.getMenus().addAll(list.get());
	}

}
