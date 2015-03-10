package models;

import java.util.Map;

public class DashBoard {
	
	private int productsCount;
	private int customersCount;
	private int ordersCount;
	private Map<String,Integer> ordersPerCustomer;
	
	public int getProductsCount() {
		return productsCount;
	}
	public void setProductsCount(int productsCount) {
		this.productsCount = productsCount;
	}
	public int getCustomersCount() {
		return customersCount;
	}
	public void setCustomersCount(int customersCount) {
		this.customersCount = customersCount;
	}
	public int getOrdersCount() {
		return ordersCount;
	}
	public void setOrdersCount(int ordersCount) {
		this.ordersCount = ordersCount;
	}
	public Map<String, Integer> getOrdersPerCustomer() {
		return ordersPerCustomer;
	}
	public void setOrdersPerCustomer(Map<String, Integer> ordersPerCustomer) {
		this.ordersPerCustomer = ordersPerCustomer;
	}
	
	@Override
	public String toString() {
		return "DashBoard [productsCount=" + productsCount
				+ ", customersCount=" + customersCount + ", ordersCount="
				+ ordersCount + ", ordersPerCustomer=" + ordersPerCustomer
				+ "]";
	}
	
	
}