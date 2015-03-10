package controllers;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import com.neovisionaries.i18n.CountryCode;

import io.sphere.client.CommandRequest;
import io.sphere.client.shop.CreateOrderBuilder;
import io.sphere.client.shop.SignInResult;
import io.sphere.client.shop.SphereClient;
import io.sphere.client.shop.model.Address;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Cart.InventoryMode;
import io.sphere.client.shop.model.CartUpdate;
import io.sphere.client.shop.model.LineItem;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.OrderUpdate;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.client.shop.model.Product;
import play.*;
import play.mvc.*;
import sphere.CurrentCart;
import sphere.OrderService;
import sphere.QueryRequest;
import sphere.ShopController;
import sphere.Sphere;
import views.html.*;

/**
 * Create a test data for the sphere application. 
 * Involves methods for doing customer logins, creating carts , 
 * adding products and place an order
 *
 */
public class TestDataController extends ShopController {

	public static Result createOrderCustomer1() {
		// Logout any existing customer logins
		sphere().logout();

		// Perform a login
		if (!sphere().isLoggedIn()) {
			boolean loginStatus = sphere().login("c1.sphere@gmail.com",
					"password123");
		}

		// Create / Get an existing cart
		CurrentCart currentCart = sphere().currentCart();

		// Add Shipping address details to the cart
		Address address = new Address(CountryCode.DE);
		address.setBuilding("SchwanthalerStr.143");
		address.setCity("Munich");
		address.setPostalCode("80339");

		currentCart.setShippingAddress(address);

		// Retrieve existing products and add to the cart
		List<Product> productsList = sphere().products().all().fetch()
				.getResults();
		currentCart.addLineItem(productsList.get(0).getId(), 2);
		currentCart.addLineItem(productsList.get(1).getId(), 3);

		// Place an order
		Order order = currentCart.createOrder(PaymentState.Paid);

		// Perform logout for the customer
		sphere().logout();

		return ok(index.render("Your order - " + order.getTotalQuantity()
				+ " Total Price - " + order.getTotalPrice()));

	}

	public static Result createOrderCustomer2() {

		// Logout any existing customer logins
		sphere().logout();

		// Perform a login
		if (!sphere().isLoggedIn()) {
			boolean loginStatus = sphere().login("c2.sphere@gmail.com",
					"password123");
		}

		// Create / Get an existing cart
		CurrentCart currentCart = sphere().currentCart();

		// Add Shipping address details to the cart
		Address address = new Address(CountryCode.DE);
		address.setBuilding("Theresienhohe 13");
		address.setCity("Munich");
		address.setPostalCode("80339");

		currentCart.setShippingAddress(address);

		// Retrieve existing products and add to the cart
		List<Product> productsList = sphere().products().all().fetch()
				.getResults();
		currentCart.addLineItem(productsList.get(2).getId(), 3);
		// currentCart.addLineItem(productsList.get(1).getId(), 1);

		// Place an order
		Order order = currentCart.createOrder(PaymentState.Paid);

		// Perform logout for the customer
		sphere().logout();

		return ok(index.render("Your order - " + order.getTotalQuantity()
				+ " Total Price - " + order.getTotalPrice()));
	}
}