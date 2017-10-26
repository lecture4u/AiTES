package LocalPropertyConnect;

import java.sql.SQLException;

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
