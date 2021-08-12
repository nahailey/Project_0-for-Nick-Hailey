package com.revature.controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;
//purpose of this method is to be able take our javalin object abd to map to some end points 
public class TestController implements Controller {

	private Handler hello = (ctx) -> {
		ctx.html("<h1>Hello World!</h1>");
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/hello", hello);
	}

//	// This is NOT a method. This is a lambda expression that is basically implementing the Handler functional interface.
//	// Therefore this is a Handler object definition
//	private Handler hello = (ctx) -> {
//		ctx.html("<h1>Hello World!</h1>");
//	};
//	
//	@Override
//	public void mapEndpoints(Javalin app) {
//		app.get("/hello", hello); // for the 'GET /hello' endpoint, we will make use of the hello Handler object behavior
//	}

}