package api.endpoints;

import java.util.ResourceBundle;

import api.payload.Login;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class AccountEndpoints {
		
	    //method to get the url from properties file
		static ResourceBundle getURL(){
			ResourceBundle routes=ResourceBundle.getBundle("logindetails");
			return routes;
		}
		
		// Authorization method
		public static Response authorized(Login payload) {
			// storing the authorization url in variable(auth_url) from .properties file
			String auth_url=getURL().getString("authorized_url"); 
			
			Response response=	given()
									.headers("Accept","application/json")
									.headers("Content-Type","application/json")
									.body(payload)
								.when()
									.post(auth_url);			
			return response;
		}
		
		//Generate Token Method
		public static Response generateToken(Login payload) {
			// storing the generate token url in variable(generate_token_url) from .properties file
			String generateToken_url=getURL().getString("generate_token_url");
			
			Response response=	given()
									.headers("Accept","application/json")
									.headers("Content-Type","application/json")
									.body(payload)
								.when()
									.post(generateToken_url);			
			return response;
		}
		
		//User Creation Method
		public static Response user(Login payload) {
			// storing the user url in variable(user_url) from .properties file
			String user_url=getURL().getString("user_url");
			
			Response response=	given()
									.headers("Accept","application/json")
									.headers("Content-Type","application/json")
									.body(payload)
								.when()
									.post(user_url);			
			return response;
		}
		
		//Delete user by id
		public static Response deleteUserbyID(String userId) {
			// storing the delete user by id url in variable(delete_url) from .properties file
			String delete_url=getURL().getString("delete_userByID_url");
			
			Response response= given()
									.pathParam("UUID", userId)  // passing the id in path parameter
							   .when()
									.delete(delete_url);
			return response;
		}
}

