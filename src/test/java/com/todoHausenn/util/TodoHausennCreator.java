package com.todoHausenn.util;

import com.todoHausenn.model.TodoHausenn;

public class TodoHausennCreator {
	
	public static TodoHausenn createTodoHausennTobeSave() {
		TodoHausenn t =  new TodoHausenn();
		t.setId(1);
		t.setName("Taks1");
		return t;
	}
	
	public static TodoHausenn createValidTodoHausenn() {
		TodoHausenn t =  new TodoHausenn();
		t.setId(1);
		t.setName("Taks1");	
		return t;
	}
	
	public static TodoHausenn createValidUpdateTodoHausenn() {
		TodoHausenn t =  new TodoHausenn();
		t.setId(1);
		t.setName("Taks2");
	
		return t;
	}
	
	

}
