package com.sysbank;

import com.sysbank.controller.ContaController;
import com.sysbank.service.ContaService;
import com.sysbank.view.MenuView;

public class Main {

	public static void main(String[] args) {
		ContaService service = new ContaService();
		ContaController controller = new ContaController(service);
		MenuView view = new MenuView(controller);
		view.iniciar();
	}
}