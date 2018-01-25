package com.github.aites.localtest.application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.github.aites.localtest.uicomponent.BlockNode;
import com.github.aites.localtest.uicomponent.DeviceList;
import com.github.aites.localtest.uicomponent.IoTEnvData;
import com.github.aites.localtest.uicomponent.Module;
import com.github.aites.localtest.uicomponent.PlanActionList;
import com.github.aites.localtest.uicomponent.PlanList;
import com.github.aites.localtest.uicomponent.PubTopic;
import com.github.aites.localtest.uicomponent.RuleList;
import com.github.aites.localtest.uicomponent.StateCombine;
import com.github.aites.localtest.uicomponent.StateReason;
import com.github.aites.localtest.uicomponent.SubTopic;
import com.github.aites.localtest.utility.PropReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
	@FXML TableColumn<SubTopic,String> subTopicNameColumn;
	@FXML TableView pubTopicListTableView;
	@FXML TableColumn<PubTopic,String> pubTopicNameColumn;
	/*------------Config_GlobalKnowledge database connection component----------------*/
	@FXML Label globalKnowledgeURLLabel;
	@FXML Label connectionIDLable;
	@FXML Label gkconnectionStatusLabel;
	
	@FXML TextArea gkConnectionLogTextArea;
	
	/*------------Config_External file information component----------------*/
	@FXML Label ruleSetURLLabel;
	@FXML Label moduleURLLabel;
	@FXML Label logURLLabel;
	@FXML TableView moduleListTableView;
	@FXML TableColumn<Module,String> moduleNameColumn;
	
	/*------------Monitor component----------------*/
	@FXML TableView iotEnvTableView;
	@FXML TableColumn<IoTEnvData,String> arrivalTimeColumn;
	@FXML TableColumn<IoTEnvData,Double> airconditionerColumn;
	@FXML TableColumn<IoTEnvData,Double> tvColumn;
	@FXML TableColumn<IoTEnvData,Double> lampColumn;
	@FXML TableColumn<IoTEnvData,Double> rvcColumn;
	@FXML TableColumn<IoTEnvData,Double> flowerpotColumn;
	@FXML TableColumn<IoTEnvData,Double> wmColumn;
	@FXML TableColumn<IoTEnvData,Double> lamp2Column;
	@FXML TableColumn<IoTEnvData,Double> smartcookColumn;
	@FXML TableColumn<IoTEnvData,Double> cctvColumn;
	@FXML TableColumn<IoTEnvData,Double> mixerColumn;
	@FXML TableColumn<IoTEnvData, Double> aispeakerColumn;
	@FXML TableColumn<IoTEnvData, Double> ovenColumn;
	
	@FXML LineChart<String,Number> shpcLineChart;
	
	@FXML TextArea monitorLogTextArea;
	/*------------Analyzer_StateReasoner component----------------*/
	@FXML TableView stateReasonerTableView;
	@FXML TableColumn<StateReason,String> reasonerTimeColumn;
	@FXML TableColumn<StateReason,String> individualolumn;
	@FXML TableColumn<StateReason,String> classColumn;
	@FXML TableColumn<StateReason,String> propertyDataColumn;
	@FXML TableColumn<StateReason,String> ruleColumn;
	@FXML TableColumn<StateReason,String> resultColumn;
	/*------------Analyzer_StateCombiner component----------------*/
	@FXML TableView stateCombinerTableView;
	@FXML TableColumn<StateCombine,String> combinerTimeColumn;
	@FXML TableColumn<StateCombine,String> combinerStateColumn;
	@FXML TableColumn<StateCombine,String> combinerState2Column;
	@FXML TableColumn<StateCombine,String> combinerState3Column;
	
	@FXML Label combineResultLabel;
	/*------------Analyzer_Other component----------------*/
	@FXML TableView ruleListTableView;
	@FXML TableColumn<RuleList,String> ruleNameColumn;
	@FXML TableColumn<RuleList,String> classAtomColumn;
	@FXML TableColumn<RuleList,String> propertyAtomColumn;
	@FXML TableColumn<RuleList,String> builtInAtomColumn;
	
	@FXML Label ruleSetURILabel;
	@FXML Label analyzerCurrentTime;
	
	/*------------Planner_Scheduler component----------------*/
	@FXML AnchorPane schedulerAnchorPane;
	/*------------Planner_Schedule Plan component----------------*/
	@FXML Label totalspendTimeLabel;
	@FXML Label plannameLabel;
	@FXML Label planResultLabel;
	
	@FXML TableView actionListTableView;
	@FXML TableColumn<PlanActionList,String> actionNameColumn;
	@FXML TableColumn<PlanActionList,String> moduleColumn;
	@FXML TableColumn<PlanActionList,String> targetColumn;
	@FXML TableColumn<PlanActionList,String> spendTimeColumn;
	@FXML TableColumn<PlanActionList,String> processStateColumn;
	/*------------Planner_Other component----------------*/
	@FXML TextArea plannerLogTextArea;
	@FXML TableView planListTableView;
	@FXML TableColumn<PlanList,String> planNameColumn;
	@FXML TableColumn<PlanList,String> planTotalSpendColumn;
	@FXML TableColumn<PlanList,String> planPerposeColumn;
	/*------------Executor component----------------*/
	@FXML TableView executorTableView;
	@FXML TextArea executorLogTextArea;
	@FXML TableColumn<DeviceList,String> deviceNameColumn;
	@FXML TableColumn<DeviceList,String> powerConsumtionColumn;
	@FXML TableColumn<DeviceList,String> adressColumn;
	@FXML TableColumn<DeviceList,String> currentModeColumn;
	@FXML TableColumn<DeviceList,String> typeColumn;
	@FXML TableColumn<DeviceList,String> affilienceColumn;
	/*------------Class Variables--------------*/
	private ObservableList<PubTopic> pubTopicObservableList;
	private ObservableList<SubTopic> subTopicObservbleList;
	private ObservableList<Module> moduleObservableList;
	private ObservableList<IoTEnvData> iotEnvDataObservableList;
	
	private ObservableList<StateReason> stateReasonObservableList;
	private ObservableList<StateCombine> stateCombineObservableList;
	private ObservableList<RuleList> ruleListObservableList;
	private ObservableList<PlanActionList> planActionListobservableList;
	private ObservableList<PlanList> planListobservableList;
	private ObservableList<DeviceList> deviceListObservableList;
	XYChart.Series<String,Number> seriesIoTEnvData = new XYChart.Series();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initTables();
		setIoTEnvGraph();
		setAnalyzerInfo();
		setPlannerInfo();
		setExecutorInfo();
	}
	
	public void setConfigSetting(PropReader propReader){
		
		System.out.println(propReader.getCilentID());
		brokerServerURLLabel.setText(propReader.getBrokerIP());
		clintIDLabel.setText(propReader.getCilentID());
		
		globalKnowledgeURLLabel.setText(propReader.getGlobalKnowledgeURL());
		connectionIDLable.setText(propReader.getConnectionID());
	
		ruleSetURLLabel.setText(propReader.getRuleSetURL());
		moduleURLLabel.setText(propReader.getLogURL());
		logURLLabel.setText(propReader.getModuleURL());
		
	}
	/*------------UI initialize methods-------------*/
	private void initTables(){
		subTopicObservbleList = FXCollections.observableArrayList();
		pubTopicObservableList = FXCollections.observableArrayList();
		moduleObservableList = FXCollections.observableArrayList();
		iotEnvDataObservableList= FXCollections.observableArrayList();
		stateReasonObservableList = FXCollections.observableArrayList();
		stateCombineObservableList = FXCollections.observableArrayList();
		ruleListObservableList = FXCollections.observableArrayList();
		planActionListobservableList = FXCollections.observableArrayList();
		planListobservableList =FXCollections.observableArrayList();
		deviceListObservableList = FXCollections.observableArrayList();
		
		subTopicObservbleList.add(new SubTopic("Local/Global1Local1/#"));
		
		pubTopicObservableList.add(new PubTopic("IoTgateway/Local1/Gate1/check"));
		pubTopicObservableList.add(new PubTopic("IoTgateway/Local1/Gate1/module"));
		pubTopicObservableList.add(new PubTopic("IoTgateway/Local1/Gate1/disconnect"));
		moduleObservableList.add(new Module("CCTVSavemode"));
		moduleObservableList.add(new Module("AirconSavemode"));
		moduleObservableList.add(new Module("AirconRunmode"));
		moduleObservableList.add(new Module("TVactivatemode"));
		moduleObservableList.add(new Module("flowerpotActmode"));
		addEnvData();
		
		arrivalTimeColumn.setCellValueFactory(cellData->cellData.getValue().arrivalTimeProperty());
		airconditionerColumn.setCellValueFactory(cellData->cellData.getValue().airconditionerProperty().asObject());
		tvColumn.setCellValueFactory(cellData->cellData.getValue().tvProperty().asObject());
	    lampColumn.setCellValueFactory(cellData->cellData.getValue().lampProperty().asObject());
	    rvcColumn.setCellValueFactory(cellData->cellData.getValue().rvcProperty().asObject());
		flowerpotColumn.setCellValueFactory(cellData->cellData.getValue().flowerpotProperty().asObject());
		wmColumn.setCellValueFactory(cellData->cellData.getValue().wmProperty().asObject());
		lamp2Column.setCellValueFactory(cellData->cellData.getValue().lamp2Property().asObject());
		smartcookColumn.setCellValueFactory(cellData->cellData.getValue().smartcookProperty().asObject());
		cctvColumn.setCellValueFactory(cellData->cellData.getValue().cctvProperty().asObject());
	    mixerColumn.setCellValueFactory(cellData->cellData.getValue().mixcerProperty().asObject());
		aispeakerColumn.setCellValueFactory(cellData->cellData.getValue().aiSpeakerProperty().asObject());
		ovenColumn.setCellValueFactory(cellData->cellData.getValue().ovenProperty().asObject());
		
		subTopicNameColumn.setCellValueFactory(cellData->cellData.getValue().topicNameProperty());
		pubTopicNameColumn.setCellValueFactory(cellData->cellData.getValue().topicNameProperty());
		moduleNameColumn.setCellValueFactory(cellData->cellData.getValue().moduleNameProperty());
		
		reasonerTimeColumn.setCellValueFactory(cellData->cellData.getValue().currentTimeProperty());
		individualolumn.setCellValueFactory(cellData->cellData.getValue().individualProperty());
		classColumn.setCellValueFactory(cellData->cellData.getValue().classAtomProperty());
		propertyDataColumn.setCellValueFactory(cellData->cellData.getValue().propertyDataProperty());
		ruleColumn.setCellValueFactory(cellData->cellData.getValue().ruleProperty());
		resultColumn.setCellValueFactory(cellData->cellData.getValue().resultProperty());
		
		combinerTimeColumn.setCellValueFactory(cellData->cellData.getValue().currentTimeProperty());
		combinerStateColumn.setCellValueFactory(cellData->cellData.getValue().stateProperty());
		combinerState2Column.setCellValueFactory(cellData->cellData.getValue().state2Property());
		combinerState3Column.setCellValueFactory(cellData->cellData.getValue().state3Property());
		
		ruleNameColumn.setCellValueFactory(cellData->cellData.getValue().ruleNameProperty());
		classAtomColumn.setCellValueFactory(cellData->cellData.getValue().classAtomProperty());
		propertyAtomColumn.setCellValueFactory(cellData->cellData.getValue().propertyAtomProperty());
		builtInAtomColumn.setCellValueFactory(cellData->cellData.getValue().builtInAtomProperty());
		
	    actionNameColumn.setCellValueFactory(cellData->cellData.getValue().actionNameProperty());
		moduleColumn.setCellValueFactory(cellData->cellData.getValue().moduleProperty());
		targetColumn.setCellValueFactory(cellData->cellData.getValue().targetProperty());
		spendTimeColumn.setCellValueFactory(cellData->cellData.getValue().spendTimeProperty());
		processStateColumn.setCellValueFactory(cellData->cellData.getValue().processStateProperty());
		
		
		planNameColumn.setCellValueFactory(cellData->cellData.getValue().planNameProperty());
		planTotalSpendColumn.setCellValueFactory(cellData->cellData.getValue().totalSpendTimeProperty());
		planPerposeColumn.setCellValueFactory(cellData->cellData.getValue().perposeProperty());
		
		deviceNameColumn.setCellValueFactory(cellData->cellData.getValue().deviceNameProperty());
		powerConsumtionColumn.setCellValueFactory(cellData->cellData.getValue().powerConsumtionTimeProperty());
		adressColumn.setCellValueFactory(cellData->cellData.getValue().addressTimeProperty());
		currentModeColumn.setCellValueFactory(cellData->cellData.getValue().currentModeTimeProperty());
		typeColumn.setCellValueFactory(cellData->cellData.getValue().typeProperty());
		affilienceColumn.setCellValueFactory(cellData->cellData.getValue().affilienceProperty());
		
		iotEnvTableView.setItems(iotEnvDataObservableList);
		subTopicListTableView.setItems(subTopicObservbleList);
		pubTopicListTableView.setItems(pubTopicObservableList);
		moduleListTableView.setItems(moduleObservableList);
		stateReasonerTableView.setItems(stateReasonObservableList);
		stateCombinerTableView.setItems(stateCombineObservableList);
		ruleListTableView.setItems(ruleListObservableList);
		actionListTableView.setItems(planActionListobservableList);
		planListTableView.setItems(planListobservableList);
		executorTableView.setItems(deviceListObservableList);
	}
	private void addEnvData(){
		ArrayList<Double> envDataList1 = new ArrayList<Double>();
		envDataList1.add(665.0);
		envDataList1.add(38.25);
		envDataList1.add(2.5);
		
		envDataList1.add(2.5);
		envDataList1.add(2.2);
		envDataList1.add(220.0);
		
		envDataList1.add(11.25);
		envDataList1.add(6.7);
		envDataList1.add(1.5);
		
		envDataList1.add(1.4);
		envDataList1.add(8.75);
		envDataList1.add(180.0);
		iotEnvDataObservableList.add(new IoTEnvData("2017.7.17 0:00",envDataList1));
		
		ArrayList<Double> envDataList2 = new ArrayList<Double>();
		envDataList2.add(498.75);
		envDataList2.add(15.3);
		envDataList2.add(1.0);
		
		envDataList2.add(2.5);
		envDataList2.add(2.2);
		envDataList2.add(220.0);
		
		envDataList2.add(3.75);
		envDataList2.add(6.7);
		envDataList2.add(1.5);
		
		envDataList2.add(1.4);
		envDataList2.add(3.5);
		envDataList2.add(180.0);
		iotEnvDataObservableList.add(new IoTEnvData("2017.7.17 1:00",envDataList2));
		
		ArrayList<Double> envDataList3 = new ArrayList<Double>();
		envDataList3.add(166.25);
		envDataList3.add(15.3);
		envDataList3.add(1.0);
		
		envDataList3.add(2.5);
		envDataList3.add(2.2);
		envDataList3.add(220.0);
		
		envDataList3.add(1.5);
		envDataList3.add(6.7);
		envDataList3.add(1.5);
		
		envDataList3.add(1.4);
		envDataList3.add(3.5);
		envDataList3.add(180.0);
		iotEnvDataObservableList.add(new IoTEnvData("2017.7.17 2:00",envDataList3));
		
		ArrayList<Double> envDataList4 = new ArrayList<Double>();
		envDataList4.add(665.0);
		envDataList4.add(38.25);
		envDataList4.add(2.5);
		
		envDataList4.add(2.5);
		envDataList4.add(2.2);
		envDataList4.add(220.0);
		
		envDataList4.add(11.25);
		envDataList4.add(6.7);
		envDataList4.add(1.5);
		
		envDataList4.add(1.4);
		envDataList4.add(8.75);
		envDataList4.add(180.0);
		iotEnvDataObservableList.add(new IoTEnvData("2017.7.17 3:00",envDataList4));
		
		ArrayList<Double> envDataList5 = new ArrayList<Double>();
		envDataList5.add(665.0);
		envDataList5.add(38.25);
		envDataList5.add(2.5);
		
		envDataList5.add(2.5);
		envDataList5.add(2.2);
		envDataList5.add(220.0);
		
		envDataList5.add(11.25);
		envDataList5.add(6.7);
		envDataList5.add(1.5);
		
		envDataList5.add(1.4);
		envDataList5.add(8.75);
		envDataList5.add(180.0);
		iotEnvDataObservableList.add(new IoTEnvData("2017.7.17 4:00",envDataList1));
		
		ArrayList<Double> envDataList6 = new ArrayList<Double>();
		envDataList6.add(665.0);
		envDataList6.add(38.25);
		envDataList6.add(2.5);
		
		envDataList6.add(2.5);
		envDataList6.add(2.2);
		envDataList6.add(220.0);
		
		envDataList6.add(11.25);
		envDataList6.add(6.7);
		envDataList6.add(1.5);
		
		envDataList6.add(1.4);
		envDataList6.add(8.75);
		envDataList6.add(180.0);
		iotEnvDataObservableList.add(new IoTEnvData("2017.7.17 5:00",envDataList6));
		
		ArrayList<Double> envDataList7 = new ArrayList<Double>();
		envDataList7.add(665.0);
		envDataList7.add(38.25);
		envDataList7.add(2.5);
		
		envDataList7.add(2.5);
		envDataList7.add(2.2);
		envDataList7.add(220.0);
		
		envDataList7.add(11.25);
		envDataList7.add(6.7);
		envDataList7.add(1.5);
		
		envDataList7.add(1.4);
		envDataList7.add(8.75);
		envDataList7.add(180.0);
		iotEnvDataObservableList.add(new IoTEnvData("2017.7.17 6:00",envDataList7));
		
		ArrayList<Double> envDataList8 = new ArrayList<Double>();
		envDataList8.add(665.0);
		envDataList8.add(38.25);
		envDataList8.add(2.5);
		
		envDataList8.add(2.5);
		envDataList8.add(2.2);
		envDataList8.add(220.0);
		
		envDataList8.add(11.25);
		envDataList8.add(6.7);
		envDataList8.add(1.5);
		
		envDataList8.add(1.4);
		envDataList8.add(8.75);
		envDataList8.add(180.0);
		iotEnvDataObservableList.add(new IoTEnvData("2017.7.17 7:00",envDataList1));
		
		ArrayList<Double> envDataList9 = new ArrayList<Double>();
		envDataList9.add(665.0);
		envDataList9.add(38.25);
		envDataList9.add(2.5);
		
		envDataList9.add(2.5);
		envDataList9.add(2.2);
		envDataList9.add(220.0);
		
		envDataList9.add(11.25);
		envDataList9.add(6.7);
		envDataList9.add(1.5);
		
		envDataList9.add(1.4);
		envDataList9.add(8.75);
		envDataList9.add(180.0);
		iotEnvDataObservableList.add(new IoTEnvData("2017.7.17 9:00",envDataList1));
	}
	
	private void setIoTEnvGraph(){
		shpcLineChart.setTitle("IoT Env total power consumtion");
		seriesIoTEnvData.setName("power");
		
		seriesIoTEnvData.getData().add(new XYChart.Data<>("1",1140.05));
		seriesIoTEnvData.getData().add(new XYChart.Data<>("2",936.6));
		seriesIoTEnvData.getData().add(new XYChart.Data<>("3",601.85));
		seriesIoTEnvData.getData().add(new XYChart.Data<>("4",502.1));
		seriesIoTEnvData.getData().add(new XYChart.Data<>("5",525.05));
		seriesIoTEnvData.getData().add(new XYChart.Data<>("6",708.05));
		seriesIoTEnvData.getData().add(new XYChart.Data<>("7",1106.96));
		seriesIoTEnvData.getData().add(new XYChart.Data<>("8",1311.2));
		seriesIoTEnvData.getData().add(new XYChart.Data<>("9",1347.95));
		seriesIoTEnvData.getData().add(new XYChart.Data<>("10",1317.2));
		
		shpcLineChart.getData().add(seriesIoTEnvData);
	}
	private void setAnalyzerInfo(){
		ruleSetURILabel.setText("http://com.github.aites/ontologies/smarthome.owl");
	    analyzerCurrentTime.setText("2017.7.17. 17:00");
	    ArrayList<String> reason1 = new ArrayList<String>();
	    reason1.add("2017.7.17. 3:00");
	    reason1.add(":EnvTotalPower1");
	    reason1.add(":PowerConsumtion");
	    reason1.add("601.85");
	    reason1.add(":isUnderPowrRule");
	    reason1.add("true");
	    stateReasonObservableList.add(new StateReason(reason1));
	    ArrayList<String> reason2 = new ArrayList<String>();
	    reason2.add("2017.7.17. 3:00");
	    reason2.add(":EnvTotalPower1");
	    reason2.add(":PowerConsumtion");
	    reason2.add("601.85");
	    reason2.add(":isAcceldecreaseRule");
	    reason2.add("flase");
	    stateReasonObservableList.add(new StateReason(reason2));
	    ArrayList<String> reason3 = new ArrayList<String>();
	    reason3.add("2017.7.17. 3:00");
	    reason3.add(":Userlocate1");
	    reason3.add(":UserLocate");
	    reason3.add("livingroom");
	    reason3.add(":isManoutsideRule");
	    reason3.add("flase");
	    stateReasonObservableList.add(new StateReason(reason3));
	    ArrayList<String> reason4 = new ArrayList<String>();
	    reason4.add("2017.7.17. 13:00");
	    reason4.add(":EnvTotalPower11");
	    reason4.add(":PowerConsumtion");
	    reason4.add("1242.8");
	    reason4.add(":isOverPowrRule");
	    reason4.add("true");
	    stateReasonObservableList.add(new StateReason(reason4));
	    ArrayList<String> reason5 = new ArrayList<String>();
	    reason5.add("2017.7.17. 13:00");
	    reason5.add(":EnvTotalPower11");
	    reason5.add(":PowerConsumtion");
	    reason5.add("1242.8");
	    reason5.add(":isAcceldecreaseRule");
	    reason5.add("true");
	    stateReasonObservableList.add(new StateReason(reason5));
	    ArrayList<String> reason6 = new ArrayList<String>();
	    reason6.add("2017.7.17. 13:00");
	    reason6.add(":Userlocate11");
	    reason6.add(":UserLocate");
	    reason6.add("outside");
	    reason6.add(":isManoutsideRule");
	    reason6.add("true");
	    stateReasonObservableList.add(new StateReason(reason6));
	    stateCombineObservableList.add(new StateCombine("2017.7.17. 03:00","underpowre","stable","inside"));
	    stateCombineObservableList.add(new StateCombine("2017.7.17. 13:00","overpower","accel","outside"));
	    combineResultLabel.setText("= overpower, accel, outside");
	    
	    ruleListObservableList.add(new RuleList(":isOverPowerRule",":OverPower",":power","swrlb:graterThan"));
	    ruleListObservableList.add(new RuleList(":isUnderPowerRule",":UnderPower",":power","swrlb:lessThan"));
	    ruleListObservableList.add(new RuleList(":isOutSideRule",":OutSide",":locate","swrlb:equals"));
	    ruleListObservableList.add(new RuleList(":isAcceldecreaseRule",":Accel",":speed","swrlb:graterThan"));
	    ruleListObservableList.add(new RuleList("isStableRule",":Stable",":speed","swrlb:lessThan"));
	}
	private void setPlannerInfo(){
		BlockNode bnode1 = new BlockNode("Plan0",10,80);
		bnode1.addToPane(schedulerAnchorPane);
		
		BlockNode bnode2 = new BlockNode("Plan1",180,80);
		bnode2.hasRelation(bnode1);
		bnode2.addToPane(schedulerAnchorPane);
		
		BlockNode bnode3 = new BlockNode("Plan2",350,80);
		bnode3.hasRelation(bnode2);
		bnode3.addToPane(schedulerAnchorPane);
		
		BlockNode bnode4 = new BlockNode("Plan3",520,80);
		bnode4.hasRelation(bnode3);
		bnode4.addToPane(schedulerAnchorPane);
		
		planActionListobservableList.add(new PlanActionList("plan5action1","CCTVSavemode","cctv1","10","Processing"));
		planActionListobservableList.add(new PlanActionList("plan5action2","AirconSavemode","aircon1","10","Processing"));
		planActionListobservableList.add(new PlanActionList("plan5action3","flowerPotActmode","flowerpot1","10","Processing"));
		
		planListobservableList.add(new PlanList("EnergySavePlan","30","Plan for save Energy"));
		planListobservableList.add(new PlanList("EnergySpendPlan","50","Plan for spend Energy"));
		planListobservableList.add(new PlanList("EnergySellingPlan","40","Plan for selling Energy"));
		planListobservableList.add(new PlanList("EnergyPurchasePlan","20","Plan for purchase Energy"));
		planListobservableList.add(new PlanList("ShoutoutPlan","120","Plan for shutout"));
		
	}
	private void setExecutorInfo(){
		deviceListObservableList.add(new DeviceList("Aircon1","665.0","172.192.0.8","Act","Air_conditioner","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("TV1","38.25","172.192.0.9","Ready","Tv","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("LivingLamp","2.5","172.192.0.10","Stnaby","Lamp1","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("RVC1","2.5","172.192.0.11","Sleep","Rvc","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("flowerPot1","2.2","172.192.0.12","Act","flowerPot","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("WM1","220","172.192.0.13","Sleep","WM","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("BedLamp2","11.25","172.192.0.14","Sleep","Lamp2","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("CCTV1","6.7","172.192.0.15","Act","Cctv","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("Mixer1","1.5","172.192.0.16","Stanby","Mixer","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("AiSpeaker1","1.4","172.192.0.17","Stanby","Ai_speaker","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("SmartCook1","8.75","172.192.0.18","Sleep","Smartcook","IoTgateway1"));
		deviceListObservableList.add(new DeviceList("Oven1","180","172.192.0.19","Sleep","Oven",""));
	}
}
