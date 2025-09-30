package api.tests;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.AccountEndpoints;
import api.payload.Login;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;

public class BaseTest {
	
	Login data;
	
	@BeforeClass
	public void setUpData() {
		data=new Login();
		
		data.setUserName("SampleUser8");
		data.setPassword("$ampleUser@8");
	}
	
	
	@Epic("EP001")
	@Feature("User Feature")
	@Story("User Creation")
	
	@Description("Creating a new user")
	
	@Test(priority=1)
	public void testUser(ITestContext context) {
		Response response=AccountEndpoints.user(data);
		response.then().log().all();
		String user_id=response.jsonPath().getString("userID");
		context.setAttribute("user_id", user_id);
		
	}
	
	@Epic("EP001")
	@Feature("Authentication Feature")
	@Story("Token Generation")
	
	@Description("Generating a token for the created user")
	
	
	@Test(priority=2)
	public void testGenerateToken(ITestContext context) {
		Response response=AccountEndpoints.generateToken(data);
		response.then().log().all();
		String token=response.jsonPath().getString("token");
				context.setAttribute("user_token", token);
				
			System.out.println("Generated Token: "+ context.getAttribute("user_token"));		
	}
	
	@Epic("EP001")
	@Feature("Authentication Feature")
	@Story("User authorizatoin")
	
	@Description("Once the token is generated , authorizing the user")
	
	
	@Test(priority=3)
	public void testAuthorized() {
		Response response=AccountEndpoints.authorized(data);
		response.then().log().all();
		
	}
	
	
	
	@Epic("EP001")
	@Feature("User Feature")
	@Story("Deleting user")
	
	@Description("Deleting the created user by user id")
	
	
	@Test(priority=4, dependsOnMethods={"testUser"})
	public void testDeleteUser(ITestContext context) {
		String user_id=(String) context.getAttribute("user_id");
		Response response=AccountEndpoints.deleteUserbyID(user_id);
		response.then().log().all();
	}
	
	
	

}
