
package com.github.aites.aitesframework.globalknowledge;
import java.sql.*;

public abstract class DBConnector {
	protected Connection cn = null;   
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;
	protected Object object; // �����ͺ��̽��� �����ϰų� �޾ƿ� ����.
	
    static String url;
	static String user;
	static String password;
	public DBConnector(Object object){ 
		this.object = object;
	}
	public DBConnector(){
		
	}
	public DBConnector(String url, String user, String password){
		this.url = url;
		this.user = user;
		this.password = password;
	}
	public void dbConnect(){
		  /*Template Method Pattern, ������Ǯ�� ����ϰ� �����ϴ� ���� ���� �޼ҵ��Դϴ�.*/		    	
		  DBConnectionMgr db = DBConnectionMgr.getInstance();
		  try{ 
				
				cn = db.getConnection(url,user,password);
				
			
				
				String query = setQuery(); // �����ͺ��̽��� ������ ���� ���� �κ�
				 
			    ps = cn.prepareStatement(query);
				executeSetting();  //���� ���� �� ������ ó�� �κ��Դϴ�.
			      			      
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				db.freeConnection(cn,ps,rs);
			}
	}
	public abstract String setQuery();
	public abstract void executeSetting() throws SQLException;
	public Connection getConnection(){
		return cn;
	}
}
