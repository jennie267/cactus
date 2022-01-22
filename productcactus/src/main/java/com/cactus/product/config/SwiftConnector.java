package com.cactus.product.config;

import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.model.Account;
import org.springframework.context.annotation.Configuration;

import net.sf.json.JSONObject;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import net.sf.json.JSONArray;

@Configuration
@EnableSwagger2
public class SwiftConnector {

	private static String provider;
	private static String tenantname;
	private static String username;
	private static String password;
	private static String authurl;
	
	public SwiftConnector() {
		// TODO Auto-generated constructor stub
		setUserProvidedVcapServices();		
	}
	
	private void setUserProvidedVcapServices() {
		/* test */
        String vcap_services = System.getenv("VCAP_SERVICES");
		/*
		 * String vcap_services = "{\r\n" + "      \"glusterfs\": [\r\n" +
		 * "        {\r\n" + "          \"label\": \"glusterfs\",\r\n" +
		 * "          \"provider\": null,\r\n" +
		 * "          \"plan\": \"glusterfs-5Mb\",\r\n" +
		 * "          \"name\": \"productstorage\",\r\n" + "          \"tags\": [\r\n" +
		 * "            \"glusterfs\",\r\n" + "            \"document\"\r\n" +
		 * "          ],\r\n" + "          \"instance_name\": \"productstorage\",\r\n" +
		 * "          \"binding_name\": null,\r\n" + "          \"credentials\": {\r\n"
		 * + "            \"password\": \"88c19125750a020c\",\r\n" +
		 * "            \"provider\": \"swift-keystone\",\r\n" +
		 * "            \"tenantname\": \"op_b17f19b7afdefc99\",\r\n" +
		 * "            \"auth_url\": \"http://10.31.0.190:82/v2.0\",\r\n" +
		 * "            \"username\": \"8dc58ea9b2224919\"\r\n" + "          },\r\n" +
		 * "          \"syslog_drain_url\": null,\r\n" +
		 * "          \"volume_mounts\": []\r\n" + "        }\r\n" + "      ]\r\n" +
		 * "}";
		 */
        System.out.println("VCAP_SERVICES = " + vcap_services);
        if(vcap_services == null)return;
        JSONObject jsonObj = JSONObject.fromObject(vcap_services);
        System.out.println("vcap_service = " + jsonObj);

        JSONArray userPro = jsonObj.getJSONArray("glusterfs");

        jsonObj = JSONObject.fromObject(userPro.get(0));
        jsonObj = jsonObj.getJSONObject("credentials");
        SwiftConnector.authurl = jsonObj.getString("auth_url");
        SwiftConnector.password = jsonObj.getString("password");
        SwiftConnector.provider = jsonObj.getString("provider");
        SwiftConnector.tenantname = jsonObj.getString("tenantname");
        SwiftConnector.username = jsonObj.getString("username");
        
        System.out.println("auth_url : "+ jsonObj.getString("auth_url"));
        System.out.println("password : "+ jsonObj.getString("password"));
        System.out.println("provider : "+ jsonObj.getString("provider"));
        System.out.println("tenantname : "+ jsonObj.getString("tenantname"));
        System.out.println("username : "+ jsonObj.getString("username"));
	}

	public static String getProvider() {
		return provider;
	}

	public static String getTenantname() {
		return tenantname;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static String getAuthurl() {
		return authurl;
	}

	public Account getAccount(){
		AccountConfig config = new AccountConfig();
		config.setUsername(username);
		config.setTenantName(tenantname);
		config.setPassword(password);
        config.setAuthUrl(authurl + "/tokens");
        config.setAuthenticationMethod(AuthenticationMethod.KEYSTONE);
    	Account account = new AccountFactory(config).createAccount();
    	return account;
	}
	
}
