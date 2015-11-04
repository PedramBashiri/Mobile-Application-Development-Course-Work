package com.example.homework05;
/*Arun Sai Sangawar Vijay*/
public class AppInfo {

//	 id, app title, developer name, url, small photo url, large photo url, and
//	 app price
	
	String id,appTitle,devName,appUrl,smallPhotoUrl,price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppTitle() {
		return appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getSmallPhotoUrl() {
		return smallPhotoUrl;
	}

	public void setSmallPhotoUrl(String smallPhotoUrl) {
		this.smallPhotoUrl = smallPhotoUrl;
	}

	

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "AppInfo [id=" + id + ", appTitle=" + appTitle + ", devName="
				+ devName + ", appUrl=" + appUrl + ", smallPhotoUrl="
				+ smallPhotoUrl + ", price=" + price + "]";
	}
	
	
}
