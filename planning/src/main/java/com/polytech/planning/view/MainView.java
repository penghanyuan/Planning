package com.polytech.planning.view;

import com.polytech.planning.controller.GeneratePlanning;
import com.polytech.planning.controller.WritePlanning;
import com.polytech.planning.view.handler.MainViewHandler;

public class MainView {

	private String[] command;

	/**
	 * @param command
	 */
	public MainView(String[] command) {
		this.command = command;
	}

	public void createMainView() throws Exception {
		System.out.println("**************** Planning Generator ****************");
		MainViewHandler mainViewHandler = new MainViewHandler();
		System.out.println("Excuting command...");
		mainViewHandler.excuteCommands(this.command);
	}
}
