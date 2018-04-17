package com.github.aites.framework.globalknowledge;

import java.sql.SQLException;
/**
 * Class for connect database fool
 * just using connected inital connection, get junj data.
 * @author JungHyun An
 * @version 3.0.1
 * 
 */
public class ConnectionStarter extends DBConnector{
	public ConnectionStarter(String url, String user, String password){
		super.url = url;
		super.user = user;
		super.password =  password;
	};
	@Override
	public void executeSetting() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setQuery() {
		// TODO Auto-generated method stub
		return "show tables";
	}

}
