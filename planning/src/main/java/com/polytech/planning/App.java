package com.polytech.planning;

import com.ploytech.planning.view.MainView;

public class App {
	public static void main(String[] args) {
//		for(int i = 0;i<args.length;i++){
//			System.out.println(args[i]);
//		}
		
		
		try {
			MainView mainView= new MainView(args);
			mainView.createMainView();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
