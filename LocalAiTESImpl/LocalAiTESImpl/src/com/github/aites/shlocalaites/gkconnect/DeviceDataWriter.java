package com.github.aites.shlocalaites.gkconnect;

import java.sql.SQLException;

import com.github.aites.shlocalaites.device.Device;

import LocalPropertyConnect.DBConnector;

public class DeviceDataWriter extends DBConnector{
	private Device device;
	
	public DeviceDataWriter(Device device){
		this.device = device;
	}
	@Override
	public void executeSetting() throws SQLException {
		// TODO Auto-generated method stub
		super.ps.setString(1, device.getDeviceName());
		super.ps.setString(2, device.getAddress());
		super.ps.setString(3, device.getModelCode());
		
		super.ps.execute();
	
	}

	@Override
	public String setQuery() {
		String query = "insert into shlocaldevice(shlocaldevice_devicename,shlocaldevice_address,shlocaldevice_modelcode) values(?,?,?)";
		return query;
	}

}
