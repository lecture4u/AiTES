
package LocalPropertyConnect;
import java.sql.*;

public abstract class DBConnector {
	Connection cn = null;   
	PreparedStatement ps = null;
	ResultSet rs = null;
	Object object; // 데이터베이스에 저장하거나 받아올 변수.
	
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
		  /*Template Method Pattern, 데이터풀이 등록하고 해제하는 공통 상위 메소드입니다.*/		    	
		  DBConnectionMgr db = DBConnectionMgr.getInstance();
		  try{ 
				
				cn = db.getConnection(url,user,password);
				
			
				
				String query = setQuery(); // 데이터베이스에 전달할 쿼리 셋팅 부분
				 
			    ps = cn.prepareStatement(query);
				executeSetting();  //쿼리 실행 후 데이터 처리 부분입니다.
			      			      
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
