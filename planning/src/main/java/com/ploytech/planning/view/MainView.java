package com.ploytech.planning.view;

import com.ploytech.planning.view.handler.MainViewHandler;
import com.polytech.planning.controller.GeneratePlanning;
import com.polytech.planning.controller.WritePlanning;

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
