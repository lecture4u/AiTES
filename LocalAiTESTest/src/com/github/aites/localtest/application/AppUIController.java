package com.github.aites.localtest.application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class AppUIController implements Initializable{
	/*------------Config_MQTTBrokerServer&Affilience component----------------*/
	@FXML Label brokerServerURLLabel;
	@FXML Label clintIDLabel;
	@FXML Label connectionStatusLabel;
	
	@FXML Label affilienceGlobalLabel;
	@FXML Label affilienceLocalLabel;
	@FXML Label affilienceIoTgatewayLabel;
	
	@FXML TableView subTopicListTableView;
	@FXML TableView pubTopicListTableView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}
