package com.github.aites.globaltest.application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;


public class AppUIController implements Initializable{
	/*------------Orchestration component----------------*/
	@FXML private AnchorPane orchestrationPane;
	@FXML private ScrollPane orchestrationScrollPane;
	
	@FXML private TableView  orchestrationMemberTable;
	@FXML private TableColumn<String, String> memberNameColumn;
	@FXML private TableColumn<String, String> affilienceColumn;
	
	@FXML private TextArea orchestrationLogTextArea;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}
