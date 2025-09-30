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
			String delete_url=getURL().getString("delete_userByID_url");
			
			Response response=given()
					.pathParam("UUID", userId)
					.when()
					.delete(delete_url);
			return response;
		}
}
