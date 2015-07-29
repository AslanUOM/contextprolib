package com.aslan.contextprolib;

import com.aslan.contextprolib.model.SensorData;
import com.aslan.contextprolib.model.SensorResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Main {

	public static void main(String[] args) {
		postRequest();
	}

	public static void getRequest() {
		try {
			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:8080/contextpro/rest/sensordatareceiver/gobi");
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			String output = response.getEntity(String.class);
			System.out.println("Output from Server .... \n");
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void postRequest() {
		SensorData d1 = new SensorData();
		d1.setType("location");
		d1.setAccuracy(99.00);
		d1.setSource("gps");
		d1.setTime(System.currentTimeMillis());
		d1.setData(new String[] { "6.97233152", "79.87017127" });

		SensorData d2 = new SensorData();
		d2.setType("networks");
		d2.setAccuracy(99.00);
		d2.setSource("wifi");
		d2.setTime(System.currentTimeMillis());
		d2.setData(new String[] { "UOM_Wireless", "CSE-Research-1", "CSE-Research-2", "Final Research Temp" });

		SensorResponse sensorResponse = new SensorResponse();
		sensorResponse.setUserID("U0001");
		sensorResponse.setDeviceID("D0001");
		sensorResponse.addSensorData(d1);
		sensorResponse.addSensorData(d2);

		try {

			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:8080/contextpro/rest/sensordatareceiver/save");
			String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, sensorResponse);
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
