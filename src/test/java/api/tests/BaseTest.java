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

	/*
	since all the methods(expect testDeleteUser) in the class needs username and password , 
	using a beforeClass annotation from testng to set the data before the methods in the class starts its execution
	*/
	
	@BeforeClass
	public void setUpData() {
		data=new Login();
		
		data.setUserName("SampleUser10");
		data.setPassword("$ampleUser@10");
	}
	
	// [Epic, Feature, Story, Description are the annotations supported by allure for detailed reporting.
	@Epic("EP001")
	@Feature("User Feature")
	@Story("User Creation")
	
	@Description("Creating a new user")
	
	@Test(priority=1)
	public void testUser(ITestContext context) {
		Response response=AccountEndpoints.user(data);
		response.then().log().all();
		String user_id=response.jsonPath().getString("userID");   
		context.setAttribute("user_id", user_id);            // globalising the user_id value 
		
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
				context.setAttribute("user_token", token);       // Globalising the token value
				
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
	
	/*We can't delete the user( by id or not) if the user is not created in first place
	   since  the method "testDeleteUser" is dependent on the method "testUser",
	   so used a testng annotation 'dependsOnMethods' testng annotation - if the testUser fails this method will not run
	*/
	@Test(priority=4, dependsOnMethods={"testUser"})
	public void testDeleteUser(ITestContext context) {
		String user_id=(String) context.getAttribute("user_id");      //fetching the value of user_id from 'testUser' method and storing in a variable
		Response response=AccountEndpoints.deleteUserbyID(user_id);    // passing user_id as path parameter.
		response.then().log().all();
	}
	
	
	

}

