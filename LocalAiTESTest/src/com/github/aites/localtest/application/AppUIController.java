package com.github.aites.localtest.application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class AppUIController implements Initializable{
	/*------------Config_MQTTBrokerServer&Affilience component----------------*/
	@FXML Label brokerServerURLLabel;
	@FXML Label clintIDLabel;
	@FXML Label connectionStatusLabel;
	
	@FXML Label affilienceGlobalLabel;
	@FXML Label affilienceLocalLabel;
	@FXML Label affilienceIoTgatewayLabel;
	
	@FXML TableView subTopicListTableView;
	@FXML TableColumn<String,String> subTopicNameColumn;
	@FXML TableView pubTopicListTableView;
	@FXML TableColumn<String,String> pubTopicNameColumn;
	/*------------Config_GlobalKnowledge database connection component----------------*/
	@FXML Label globalKnowledgeURLLabel;
	@FXML Label connectionIDLable;
	@FXML Label gkconnectionStatusLabel;
	
	@FXML TextArea gkConnectionLogTextArea;
	
	/*------------Config_External file information component----------------*/
	@FXML TableView moduleListTableView;
	@FXML TableColumn<String,String> moduleNameColumn;
	
	/*------------Monitor component----------------*/
	@FXML TableView iotEnvTableView;
	@FXML TableColumn<String,String> arrivalTimeColumn;
	@FXML TableColumn<String,String> airconditionerColumn;
	@FXML TableColumn<String,String> tvColumn;
	@FXML TableColumn<String,String> lampColumn;
	@FXML TableColumn<String,String> rvcColumn;
	@FXML TableColumn<String,String> flowerpotColumn;
	@FXML TableColumn<String,String> wmColumn;
	@FXML TableColumn<String,String> lamp2Column;
	@FXML TableColumn<String,String> smartcookColumn;
	@FXML TableColumn<String,String> cctvColumn;
	@FXML TableColumn<String,String> mixerColumn;
	@FXML TableColumn<String, String> aispeakerColumn;
	@FXML TableColumn<String, String> ovenColumn;
	
	@FXML LineChart shpcLineChart;
	@FXML TextArea monitorLogTextArea;
	/*------------Analyzer_StateReasoner component----------------*/
	@FXML TableView stateReasonerTableView;
	@FXML TableColumn<String,String> reasonerTimeColumn;
	@FXML TableColumn<String,String> individualolumn;
	@FXML TableColumn<String,String> classColumn;
	@FXML TableColumn<String,String> propertyDataTimeColumn;
	@FXML TableColumn<String,String> ruleColumn;
	@FXML TableColumn<String,String> resultColumn;
	/*------------Analyzer_StateCombiner component----------------*/
	@FXML TableView stateCombinerTableView;
	@FXML TableColumn<String,String> combinerTimeColumn;
	@FXML TableColumn<String,String> combinerStateColumn;
	@FXML TableColumn<String,String> combinerState2Column;
	@FXML TableColumn<String,String> combinerState3Column;
	
	@FXML Label combineResultLabel;
	/*------------Analyzer_Other component----------------*/
	@FXML TableView ruleListTableView;
	@FXML TableColumn<String,String> ruleNameColumn;
	@FXML TableColumn<String,String> classAtomColumn;
	@FXML TableColumn<String,String> propertyAtomColumn;
	@FXML TableColumn<String,String> builtInAtomColumn;
	
	@FXML Label ruleSetURILabel;
	@FXML Label analyzerCurrentTime;
	
	/*------------Planner_Scheduler component----------------*/
	@FXML AnchorPane schedulerAnchorPane;
	/*------------Planner_Schedule Plan component----------------*/
	@FXML Label totalspendTimeLabel;
	@FXML Label plannameLabel;
	@FXML Label planResultLabel;
	
	@FXML TableView actionListTableView;
	@FXML TableColumn<String,String> actionNameColumn;
	@FXML TableColumn<String,String> moduleColumn;
	@FXML TableColumn<String,String> targetColumn;
	@FXML TableColumn<String,String> spendTimeColumn;
	@FXML TableColumn<String,String> processStateColumn;
	/*------------Planner_Other component----------------*/
	@FXML TextArea plannerLogTextArea;
	@FXML TableView planListTableView;
	@FXML TableColumn<String,String> planNameColumn;
	@FXML TableColumn<String,String> planTotalSpendColumn;
	@FXML TableColumn<String,String> planPerposeColumn;
	/*------------Executor component----------------*/
	@FXML TableView executorTableView;
	@FXML TextArea executorLogTextArea;
	@FXML TableColumn<String,String> deviceNameColumn;
	@FXML TableColumn<String,String> powerConsumtionColumn;
	@FXML TableColumn<String,String> adressColumn;
	@FXML TableColumn<String,String> currentModeColumn;
	@FXML TableColumn<String,String> typeColumn;
	@FXML TableColumn<String,String> affilienceColumn;
	/*------------Class Variables--------------*/
	private ObservableList<String> pubTopicObservableList;
	private ObservableList<String> subTopicObservbleList;
	private ObservableList<String> moduleObservableList;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	/*------------UI initialize methods-------------*/
	private void initTable(){
		subTopicListTableView.setItems(subTopicObservbleList);
		pubTopicListTableView.setItems(pubTopicObservableList);
	}
}
