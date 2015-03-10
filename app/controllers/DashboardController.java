package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Optional;

import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.SphereClient;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.Product;
import play.libs.Json;
import play.mvc.Result;
import scala.Array;
import sphere.CustomerService;
import sphere.SearchRequest;
import sphere.ShopController;
import sphere.Sphere;
import play.*;
import play.mvc.*;
import views.html.*;

/**
 * Dashboard controller that gets invoked on clicking the dashboard link on the
 * sidenav of the application. 
 * 
 */
public class DashboardController extends ShopController {

	/**
	 * Method to get the products counts, customers count, orders count 
	 * and number of orders per customers details.
	 * Returns the json data of all the above details on ajax request. 
	 * 
	 */
	public static Result dashboard() {
		
		//Fetch the product details 
		SearchRequest<Product> productsSearchRequest = sphere().products().all();
		SearchResult<Product> productsResult = productsSearchRequest.fetch();

		//Fetch all the customer details
		SphereClient client = Sphere.getInstance().client();
		// TODO: How to get the count without fetching all the customers. Did
		// not find in the documentation.
		QueryRequest<Customer> all = client.customers().query();
		QueryResult<Customer> customerFetch = all.fetch();

		//Fetch all the order details
		sphere.QueryRequest<Order> ordersSearchReq = sphere().orders().query();
		List<Order> ordersList = ordersSearchReq.fetch().getResults();

		//Create a json object to fill all the required details for dashboard
		ObjectNode json = Json.newObject();

		json.put("productsCount", productsResult.getCount());
		json.put("customersCount", customerFetch.getCount());
		json.put("ordersCount", ordersList.size());

		//Set the response type to application/json
		response().setContentType("application/json");

		Map<String, Integer> map = groupByCustomerCount(ordersList);

		Set<Entry<String, Integer>> entrySet = map.entrySet();

		//Add the array node to json object to fill the orders per customer detail
		ArrayNode ordersPerCustomerDetails = json.putArray("ordersPerCustomer");

		for (Entry<String, Integer> entry : entrySet) {
			ObjectNode jsonArr = Json.newObject();
			jsonArr.put(entry.getKey(), entry.getValue());
			ordersPerCustomerDetails.add(jsonArr);
		}

		return ok(json);
	}

	/**
	 * Method to return a map indicating the orders count per customer detail.
	 * @param ordersList
	 * @return
	 */
	private static Map<String, Integer> groupByCustomerCount(
			List<Order> ordersList) {

		Map<String, Integer> map = new HashMap<String, Integer>();

		// Assert.assertNotNull(ordersList);

		for (Order o : ordersList) {
			Customer customer = Sphere.getInstance().client().customers()
					.byId(o.getCustomerId()).fetch().get();

			if (!map.containsKey(customer.getEmail())) {
				map.put(customer.getEmail(), new Integer(1));
			} else {
				Integer count = map.get(customer.getEmail());
				map.put(customer.getEmail(), count + 1);
			}
		}
		return map;

	}

}
