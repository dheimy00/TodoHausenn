package com.todoHausenn.controler.generic;

import java.io.Serializable;

import com.todoHausenn.model.generic.BaseEntity;
import com.todoHausenn.service.interfaces.Service;

public abstract class Controller<E extends BaseEntity, S extends Service<E>> implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract S getService();

}