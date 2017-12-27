package LocalPropertyConnect;
import java.sql.*;

import java.util.Properties;

import java.util.Vector;



public class DBConnectionMgr {
	private Vector connections = new Vector(10);
	
		private String driver = "com.mysql.jdbc.Driver";
		private String url = "jdbc:mysql://172.25.235.34:3306/localProperty";
	
		private String user = "root";
		private String password = "1234";
	
		private boolean _traceOn = false;
		private boolean initalized = false;
		private int _openConnections = 10;
		private static DBConnectionMgr instance = null;
		public DBConnectionMgr(){
			
		}
		/**Use this method to set the maximum number open connections before 
		 * unused connections are cloased;
		 */
		public static DBConnectionMgr getInstance(){
			if(instance == null){
				synchronized(DBConnectionMgr.class){
						if(instance == null){
							instance = new DBConnectionMgr();
				}
			}
		}
		//System.out.println("getInstance():"+instance.hashCode() );
		return instance;
	}
	public void setopenConnectionCount(int count){
		_openConnections = count;
	}
	public void setEnableTrace(boolean enable){
		_traceOn=enable;
	}
	/**Return a Vector of java.sql.Connection objects*/
	public Vector getConnectionList(){
		return connections;
	}
	/**Opens specified "count" of connections and adds them to the existing pool*/
	public synchronized void setInitOpenConnections(int count)throws SQLException{
		Connection c = null;
		ConnectionObject co = null;
		
		for(int i=0; i<count; i++){
			c = createConnection();
			co = new ConnectionObject(c,false);
			connections.addElement(co);
			trace("ConnectionPoolManager:Adding new DB connection to pool("+connections.size()+")");
		}
	}
	/**Returns a count of open connections*/
	public int getConnectionCount(){
		return connections.size();
	}
	
	
	/**Returns an unused existing or new connection.*/
	public synchronized Connection getConnection(String url, String user, String password) throws Exception{
		if(!initalized){
			Class c = Class.forName(driver);
			DriverManager.registerDriver((Driver)c.newInstance());
			initalized = true;
		}
		
		Connection c = null;
		ConnectionObject co = null;
		boolean badConnection = false;
		
		for(int i=0; i<connections.size(); i++){
			co = (ConnectionObject) connections.elementAt(i);
			
			if(!co.inUse){
				try{
					badConnection = (co.connection.getWarnings()!=null);
				}catch(Exception e){
					badConnection = true;
					e.printStackTrace();
				}
				if(badConnection){
					connections.removeElementAt(i);
					trace("ConnectionPoolManager:Remove disconnected DB connection #"+i);
					continue;
				}
				c = co.connection;
				co.inUse = true;
				trace("ConnectionPoolManager:Using existing DB connection #"+(i+1));
				break;
			}
		}
		//처음 접속자는 무조건 null, Vector로부터 Connection을 받지 못한 경우도 null
		if(c==null){
			c= createConnection(url,user,password);
			co = new ConnectionObject(c,true);
			
			connections.addElement(co);
			trace("ConnectionPoolManager:Creating new DB connection #"+connections.size());
		}
		return c;
	}
	
	
	public synchronized void freeConnection(Connection c){
		if(c==null){
			return;
		}
		ConnectionObject co = null;
		
		//Client가 사용한 Connection 객체를 찾아냅니다.
		for(int i=0; i<connections.size(); i++){
			co = (ConnectionObject)connections.elementAt(i);
			
			//Vector에 등록된 Connection이면
			if(c==co.connection){
				//System.out.println("c.hashCode():"+c.hashCode());
				//System.out.println("co.connection:"+co.connection);
				
				co.inUse= false;
				break;
			}
		}
		for(int i=0; i<connections.size(); i++){
			co = (ConnectionObject)connections.elementAt(i);
			if((i+1)>_openConnections && !co.inUse){
				//10+1=(11>10)&&!false
				removeConnection(co.connection);
			}
		}
	}
	public void freeConnection(Connection c, PreparedStatement p, ResultSet r){
		try{
			if (r!=null)r.close();
			if(r!=null)p.close();
			freeConnection(c);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void freeConnection(Connection c, Statement s, ResultSet r){
		try{
			if(r!=null)r.close();
			if(s!=null)s.close();
			freeConnection(c);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void freeConnection(Connection c, PreparedStatement p){
		try{
			if(p!=null)p.close();
			freeConnection(c);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void freeConnection(Connection c, Statement s){
		try{
			if(s!=null)s.close();
			freeConnection(c);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public synchronized void removeConnection(Connection c){
		if(c == null)
			return;
		ConnectionObject co = null;
		for(int i=0; i<connections.size(); i++){
			co = (ConnectionObject) connections.elementAt(i);
			//삭제하려는 Connection 객체를 찾습니다.
			if(c == co.connection){
				try{
					c.close();
					connections.removeElementAt(i);
					trace("Removed"+c.toString());
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			}
		}
	}
	/*
	 * 실제의 커넥션을 만들어 돌려줌
	 * @return 
	 * @throws SQLException
	 */
	
	
	private Connection createConnection() throws SQLException{

		Connection con =null;
		try{
			if(user == null)user = "root";
			if(password == null)password = "1234";
			Properties props = new Properties();
			props.put("root", user);
			props.put("1234", password);
			con = DriverManager.getConnection(url,user,password);
		}catch(Throwable t){
			throw new SQLException(t.getMessage());
		}
		return con;
	}
	private Connection createConnection(String url, String user, String password) throws SQLException{
		Connection con =null;
		try{
			if(user == null)this.user = user;
			if(password == null)this.password = password;
			Properties props = new Properties();
			props.put(user, this.user);
			props.put(password, this.password);
			con = DriverManager.getConnection(url,user,password);
		}catch(Throwable t){
			throw new SQLException(t.getMessage());
		}
		return con;
	}
	/**Closes all connections and clears out the connection pool*/
	public void releaseFreeConnections(){
		trace("ConnectionPoolManager.releaseFreeConnections()");
		Connection c = null;
		ConnectionObject co = null;
		for(int i=0; i<connections.size(); i++){
			co = (ConnectionObject) connections.elementAt(i);
			if(!co.inUse)
				removeConnection(co.connection);
		}
	}
	/**Closes all connections and clears out the connection pool*/
	public void finalize(){
		trace("ConnectionPoolManager.finalize()");
		Connection c = null;
		ConnectionObject co = null;
		for(int i=0; i<connections.size();i++){
			co = (ConnectionObject) connections.elementAt(i);
			try{
				co.connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			co = null;
		}
		connections.removeAllElements();
	}
	private void trace(String s){
		if(_traceOn){
			System.err.println(s);
		}
	}
}

class ConnectionObject{
	public java.sql.Connection connection = null;
	public boolean inUse =false;
	public ConnectionObject(Connection c, boolean useFlag){
		connection = c;
		inUse = useFlag;
	}
}
