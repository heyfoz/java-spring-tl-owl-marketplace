package org.springframework.samples.owlmarketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * OWl Marketplace Application
 *
 * @author Forrest Moulin
 *
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Ist440WOwlMarketplace {

	public static void main(String[] args) {
		SpringApplication.run(Ist440WOwlMarketplace.class, args);

		// INSERT YOUR BING MAPS API KEY BELOW
		String bingMapsKey = "InsertTheBingMapsAPIKeyHere";
		// Streetside base URL string
		String baseURL1 = "https://dev.virtualearth.net/REST/v1/Imagery/Map/Streetside/";
		String address = "15010 NE 36th St, Redmond, WA 98052";
		address = address.replace(" ", "%20"); // Replace spaces with %20 for encoding
		String streetsideImageFinalURL = baseURL1 + address + "?zoomlevel=0&key=" + bingMapsKey;

		String baseURL2 = "https://dev.virtualearth.net/REST/v1/LocalSearch/?";
		/* type={type_string_id_list}&userLocation={point}&key={BingMapsKey} */
		String type = "type=" + "";
		String userLocation = "userLocation=" + "15010 NE 36th St, Redmond, WA 98052";
		String localSearchFinalURL = baseURL2 + "&" + userLocation + "&key=" + bingMapsKey;

		// Get location json response using address
		// http://dev.virtualearth.net/REST/v1/Locations/US/{adminDistrict}/{postalCode}/{locality}/{addressLine}?
			// includeNeighborhood={includeNeighborhood}&include={includeValue}&maxResults={maxResults}&key={BingMapsKey}
		String baseURL3 = "http://dev.virtualearth.net/REST/v1/Locations/US/";
		String adminDistrict = "Washington";
		String postalCode = "98052";
		String locality = "Redmond";
		String addressLine = "15010 NE 36th St".replace(" ", "%20");
		String locationResultFinalURL = baseURL3 + adminDistrict + "/" + postalCode + "/" + locality + "/" + addressLine
			+ "?includeNeighborhood=1&key=" + bingMapsKey;

		// Static Map
		// https://docs.microsoft.com/en-us/bingmaps/rest-services/imagery/get-a-static-map
		// Location and area types info
		// https://docs.microsoft.com/en-us/bingmaps/rest-services/common-parameters-and-types/location-and-area-types
		// Pushpin syntax and icon styles
		// https://docs.microsoft.com/en-us/bingmaps/rest-services/common-parameters-and-types/pushpin-syntax-and-icon-styles
		String baseURL4 = "https://dev.virtualearth.net/REST/v1/Imagery/Map/";
		Double latitude = 40.807213;
		Double longitude = -77.85887;
		String centerPoint = latitude + "," + longitude;
		String imagerySet = "AerialWithLabels";
		String imagerySet2 = "AerialWithLabelsOnDemand";
		String imagerySet3 = "CanvasDark";
		String imagerySet4 = "Streetside";
		// Only required if center points is not supplied
		String mapArea = "40.806,-77.857,40.808,-77.859";
		String iconStyle = "48"; // 33, 36, or 48 are nice options
		String pushpinLabel = "";
		String pushpin = centerPoint + ";" + iconStyle + ";" + pushpinLabel;
		int zoomLevel = 17;
		// String staticMapFinalURL = staticMapBaseURL + imagerySet + "/" + centerPoint +
		// "/" + zoomLevel + "?" + "key=" + bingMapsKey;
		String staticMapFinalURL = baseURL4 + imagerySet2 + "/" + centerPoint + "/" + zoomLevel + "?"
				+ "pushpin=" + pushpin + "&key=" + bingMapsKey;
		System.out.println("Static Map" + staticMapFinalURL);
		System.out.println("Streetside Image:" + streetsideImageFinalURL);

		// https://dev.virtualearth.net/REST/v1/Imagery/Map/imagerySet/centerPoint/zoomLevel?mapSize={mapSize}&pushpin={pushpin}&mapLayer={mapLayer}&format={format}&mapMetadata={mapMetadata}&key={BingMapsKey}
		// https://dev.virtualearth.net/REST/v1/Imagery/Map/imagerySet?mapArea={mapArea}&mapSize={mapSize}&pushpin={pushpin}&mapLayer={mapLayer}&format={format}&mapMetadata=mapMetadata}&key={BingMapsKey}
		restfulConnection(streetsideImageFinalURL, bingMapsKey);
		restfulConnection(staticMapFinalURL, bingMapsKey);
		restfulConnection(locationResultFinalURL, bingMapsKey);
		restfulConnection(localSearchFinalURL, bingMapsKey);
	}

	public static void restfulConnection(String uRL, String bingMapsKey) {
		System.out.println("RESTFUL CONNECTION METHOD EXECUTED USING THE FOLLOWING URL:");
		System.out.println(uRL);
		String response;
		HttpURLConnection connection = null;
		try { // Define the final URL that will be used for the connection
			URL url = new URL(uRL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("token", bingMapsKey);
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			response = br.readLine();
			System.out.println("JSON RESPONSE:" + "\n" + response + "\n");
		}
		catch (IOException ex) {
			System.out.println(ex);
		}
		finally {
			connection.disconnect();
		}
	}
}