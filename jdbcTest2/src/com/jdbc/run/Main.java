package com.jdbc.run;

import com.jdbc.controller.MemberControllerImpl;

public class Main {

	public static void main(String[] args) {
		new MemberControllerImpl().mainMenu();
		int age = 10;
		System.out.println(age);
		System.out.println("난 반장이다 말 잘들어라!!!");
	}
}


