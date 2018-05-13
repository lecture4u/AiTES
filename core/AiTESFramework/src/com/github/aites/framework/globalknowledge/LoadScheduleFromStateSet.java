package com.github.aites.framework.globalknowledge;

import java.sql.SQLException;
import java.util.ArrayList;

import com.github.aites.framework.framework.Timer;
import com.github.aites.framework.orchestration.Participants;
import com.github.aites.framework.planner.Plan;

public class LoadScheduleFromStateSet extends DBConnector{
    String occurState;
    String time;
    String target;
    String action;
    String schemName;
    Timer timer;
    ArrayList<Plan> schedule = new ArrayList<Plan>();
    Participants participants = Participants.getInstance();
    public LoadScheduleFromStateSet(String occurState, Timer timer, String schemName){
    	this.occurState = occurState;
    	this.timer = timer;
    	this.schemName = schemName;
	}
	@Override
	public String setQuery() {
		String query = "SELECT "+schemName+"localschedule_time,"+schemName+"localschedule_target,"+schemName+"localschedule_action from "+schemName+"localschedule where "+schemName+"localschedule_occurstate ='"+occurState+"'";
		return query;
	}

	@Override
	public void executeSetting() throws SQLException {
        
		
		rs = ps.executeQuery();
		
		while(rs.next()){		
			time = rs.getString("shlocalschedule_time");
			target= rs.getString("shlocalschedule_target");
			action= rs.getString("shlocalschedule_action");
			convertTime();
			convertAction();
			System.out.println("Load matching plan about "+occurState+":"+time+","+target+","+action);
	          
		 }	
		
	}
    private void convertTime(){
    	time = timer.makeCalendarAddTime(time);
   
    }
    private void convertAction(){
    	String[] actionControl = action.split(","); // 0:control , 1:level
    	int afterActionLevel = 0;
    	String currentActionLevel = participants.getDeviceAction(target);
    	System.out.println("DeviceTarget: "+target + ", currentAction:"+participants.getDeviceAction(target));
    	if(actionControl[0].equals("down")){
    		afterActionLevel = participants.getDeviceActionLevel(target) - Integer.parseInt(actionControl[1]);
    		
    	}
    	else{
    	     afterActionLevel = participants.getDeviceActionLevel(target) + Integer.parseInt(actionControl[1]);
    	}
    	participants.setDeviceActionLevel(target, afterActionLevel);
    	System.out.println("Change");
    }
}
