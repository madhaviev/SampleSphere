package controllers;

import play.*;
import play.mvc.*;
import sphere.ShopController;
import views.html.*;

/**
 * Home page controller to display message on running the application
 *
 */
public class HomePageController extends ShopController {

	public static Result home() {
		return ok(index.render("Welcome to Sample Sphere DEMO."));
	}
}