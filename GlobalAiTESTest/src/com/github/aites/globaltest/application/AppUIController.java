package com.github.aites.globaltest.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;


import com.github.aites.uicomponent.Attributes;
import com.github.aites.uicomponent.EnvBoundery;
import com.github.aites.uicomponent.ForeignKey;
import com.github.aites.uicomponent.MemberList;
import com.github.aites.uicomponent.MemberNode;
import com.github.aites.uicomponent.RuleList;
import com.github.aites.uicomponent.SmartModuleList;
import com.github.aites.uicomponent.Tables;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;


public class AppUIController implements Initializable{
	/*------------Orchestration component----------------*/
	@FXML private AnchorPane orchestrationPane;
	@FXML private ScrollPane orchestrationScrollPane;
	
	@FXML private TableView  orchestrationMemberTable;
	@FXML private TableColumn<MemberList, String> memberNameColumn;
	@FXML private TableColumn<MemberList, String> affilienceColumn;
	
	@FXML private TextArea orchestrationLogTextArea;
	/*------------Rule configuration component----------------*/
	@FXML private TableView gkSchemaTableTableView;
	@FXML private TableColumn<Tables, String> tableNameColumn;
	@FXML private TableView gkSchemaPropertyTableView;
	@FXML private TableColumn<Attributes, String> propertyNameColumn;
	@FXML private TableColumn<Attributes, String> propertyTypeColumn;
	@FXML private TableView gkSchemaForeignKeyTableView;
	@FXML private TableColumn<ForeignKey, String> foreignKeyAttribteColumn;
	@FXML private TableColumn<ForeignKey, String> foreignKeyRTColumn;
	
	@FXML private Label owlURLLabel;
	@FXML private TextArea ruleSetTextArea;
	
	@FXML private TableView swrlRuleSetTableView;
	@FXML private TableColumn<RuleList, String> ruleNameColumn;
	@FXML private TableColumn<RuleList, String> classAtomColumn;
	@FXML private TableColumn<RuleList, String> propertyAtomColumn;
	@FXML private TableColumn<RuleList, String> builtInAtomColumn;
	
	@FXML private TextArea rulConfigLogTextArea;
	
	/*------------Smart Module component----------------*/
	@FXML private ImageView mlVisualizationImageView;
	@FXML private TextArea smartModuleLogTextArea;
	@FXML private TableView smartModuleListTableView;
	@FXML private TableColumn<SmartModuleList, String> moduleNameColumn;
	@FXML private TableColumn<SmartModuleList, String> variableColumn;
	@FXML private TableColumn<SmartModuleList, String> outFileColumn;
	
	/*------------Class component----------------*/
	private ObservableList<MemberList> memberListObservableList;
	private ObservableList<Tables> tablenameObservableList;
	private ObservableList<Attributes> attributesObservableList;
	private ObservableList<RuleList> ruleListObservableList;
	private ObservableList<ForeignKey> fkObservableList;
	private ObservableList<SmartModuleList> smartModuleList;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		MemberNode globalNode = new MemberNode(550,10,"Global/Global1");
		globalNode.addToPane(orchestrationPane);
		
		MemberNode localNode1 = new MemberNode(150,110,"Global1/HomeLocal");
		localNode1.hasRelation(globalNode);
		MemberNode localNode2 = new MemberNode(950,110,"Global1/SolarLocal");
		localNode2.hasRelation(globalNode);
		
		localNode1.addToPane(orchestrationPane);
		localNode2.addToPane(orchestrationPane);
		MemberNode IoTgateway1 = new MemberNode(150,210,"HomeLocal/IoTgateway1");
		IoTgateway1.hasRelation(localNode1);
		MemberNode IoTgateway2 = new MemberNode(950,210,"SolarLocal/IoTgateway2");
		IoTgateway2.hasRelation(localNode2);
		IoTgateway1.addToPane(orchestrationPane);
		IoTgateway2.addToPane(orchestrationPane);
		
		
		
		EnvBoundery env1 = new EnvBoundery(10,310);
		env1.hasRelation(IoTgateway1);
		env1.addToPane(orchestrationPane);
		MemberNode tempMember = env1.addDeviceMember("IoTgateway1/Aircon1");
		tempMember.addToPane(orchestrationPane);
		
		tempMember = env1.addDeviceMember("IoTgateway1/TV1");
		tempMember.addToPane(orchestrationPane);
		tempMember = env1.addDeviceMember("IoTgateway1/LivingLamp");
		tempMember.addToPane(orchestrationPane);
		tempMember = env1.addDeviceMember("IoTgateway1/RVC1");
		tempMember.addToPane(orchestrationPane);
		tempMember = env1.addDeviceMember("IoTgateway1/flowerPot1");
		tempMember.addToPane(orchestrationPane);
		tempMember = env1.addDeviceMember("IoTgateway1/WM1");
		tempMember.addToPane(orchestrationPane);
		tempMember = env1.addDeviceMember("IoTgateway1/BedLamp2");
		tempMember.addToPane(orchestrationPane);
		
		tempMember = env1.addDeviceMember("IoTgateway1/CCTV1");
		tempMember.addToPane(orchestrationPane);
		
		tempMember = env1.addDeviceMember("IoTgateway1/Mixer1");
		tempMember.addToPane(orchestrationPane);
		tempMember = env1.addDeviceMember("IoTgateway1/AiSpeaker1");
		tempMember.addToPane(orchestrationPane);
		
		tempMember = env1.addDeviceMember("IoTgateway1/Oven1");
		tempMember.addToPane(orchestrationPane);
		
		
		
		
		
		EnvBoundery env2 = new EnvBoundery(855,310);
		env2.hasRelation(IoTgateway2);
		env2.addToPane(orchestrationPane);
		tempMember = env2.addDeviceMember("IoTgateway2/Pannel1");
		tempMember.addToPane(orchestrationPane);
		tempMember = env2.addDeviceMember("IoTgateway2/Pannel2");
		tempMember.addToPane(orchestrationPane);
		tempMember = env2.addDeviceMember("IoTgateway2/Pannel3");
		tempMember.addToPane(orchestrationPane);
		tempMember = env2.addDeviceMember("IoTgateway2/Pannel4");
		tempMember.addToPane(orchestrationPane);
		tempMember = env2.addDeviceMember("IoTgateway2/Pannel5");
		tempMember.addToPane(orchestrationPane);
		
		orchestrationScrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                	orchestrationPane.setScaleX(orchestrationPane.getScaleX()* 1.1);
                	orchestrationPane.setScaleY(orchestrationPane.getScaleY() * 1.1);
                } else if (event.getDeltaY() < 0) {
                	orchestrationPane.setScaleX(orchestrationPane.getScaleX() / 1.1);
                	orchestrationPane.setScaleY(orchestrationPane.getScaleY() / 1.1);
                }
            }
        });
		initTalbe();
		FileInputStream input;
		try {
			input = new FileInputStream("/Users/srsok/AiTES/GlobalAiTESTest/culuster.jpg");
			Image image = new Image(input);
			mlVisualizationImageView.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void initTalbe(){
		memberListObservableList= FXCollections.observableArrayList();
		tablenameObservableList = FXCollections.observableArrayList();
		attributesObservableList = FXCollections.observableArrayList();
		ruleListObservableList = FXCollections.observableArrayList();
		fkObservableList = FXCollections.observableArrayList();
		smartModuleList = FXCollections.observableArrayList();
		
		
		
		memberNameColumn.setCellValueFactory(cellData->cellData.getValue().memberNameProperty());
		affilienceColumn.setCellValueFactory(cellData->cellData.getValue().affilienceProperty());
		tableNameColumn.setCellValueFactory(cellData->cellData.getValue().tableNameProperty());
		propertyNameColumn.setCellValueFactory(cellData->cellData.getValue().attributeNameProperty());
		propertyTypeColumn.setCellValueFactory(cellData->cellData.getValue().typeNameProperty());
		
		ruleNameColumn.setCellValueFactory(cellData->cellData.getValue().ruleNameProperty());
		classAtomColumn.setCellValueFactory(cellData->cellData.getValue().classAtomProperty());
		propertyAtomColumn.setCellValueFactory(cellData->cellData.getValue().propertyAtomProperty());
		builtInAtomColumn.setCellValueFactory(cellData->cellData.getValue().builtInAtomProperty());
		
		foreignKeyAttribteColumn.setCellValueFactory(cellData->cellData.getValue().attributeNameProperty());
		foreignKeyRTColumn.setCellValueFactory(cellData->cellData.getValue().rtProperty());
		
	    moduleNameColumn.setCellValueFactory(cellData->cellData.getValue().moduleNameProperty());
		variableColumn.setCellValueFactory(cellData->cellData.getValue().variableProperty());
		outFileColumn.setCellValueFactory(cellData->cellData.getValue().outputFileProperty());
		memberListObservableList.add(new MemberList("Global/Global1","Global"));
		memberListObservableList.add(new MemberList("Global1/HomeLocal","Global1"));
		memberListObservableList.add(new MemberList("Global1/SolarLocl","Global1"));
		memberListObservableList.add(new MemberList("HomeLocal/IoTgateway1","HomeLocal"));
		memberListObservableList.add(new MemberList("SolarLocl/IoTgateway2","SolarLocl"));
		
		memberListObservableList.add(new MemberList("IoTgateway1/Aircon1","IoTgateway1"));
		memberListObservableList.add(new MemberList("IoTgateway1/TV1","IoTgateway1"));
		memberListObservableList.add(new MemberList("IoTgateway1/LiningLamp","IoTgateway1"));
		memberListObservableList.add(new MemberList("IoTgateway1/RVC1","IoTgateway1"));
		memberListObservableList.add(new MemberList("IoTgateway1/flowerPot1","IoTgateway1"));
		memberListObservableList.add(new MemberList("IoTgateway1/WM1","IoTgateway1"));
		memberListObservableList.add(new MemberList("IoTgateway1/BedLamp2","IoTgateway1"));
		memberListObservableList.add(new MemberList("IoTgateway1/CCTV1","IoTgateway1"));
		memberListObservableList.add(new MemberList("IoTgateway1/Mixer1","IoTgateway1"));
		memberListObservableList.add(new MemberList("IoTgateway1/AiSperker1","IoTgateway1"));
		memberListObservableList.add(new MemberList("IoTgateway1/Oven1","IoTgateway1"));
		
		
		memberListObservableList.add(new MemberList("IoTgateway2/Panne1","IoTgateway2"));
		memberListObservableList.add(new MemberList("IoTgateway2/Panne2","IoTgateway2"));
		memberListObservableList.add(new MemberList("IoTgateway2/Panne3","IoTgateway2"));
		memberListObservableList.add(new MemberList("IoTgateway2/Panne4","IoTgateway2"));
		memberListObservableList.add(new MemberList("IoTgateway2/Panne5","IoTgateway2"));
	
		tablenameObservableList.add(new Tables("Aactivity"));
		tablenameObservableList.add(new Tables("Cctv"));
		tablenameObservableList.add(new Tables("Device"));
		tablenameObservableList.add(new Tables("Env"));
		tablenameObservableList.add(new Tables("LivingRoom"));
		
		attributesObservableList.add(new Attributes("collect_time","string"));
		attributesObservableList.add(new Attributes("devie_id","string"));
		attributesObservableList.add(new Attributes("env_id","int"));
		attributesObservableList.add(new Attributes("activity_type","string"));
		attributesObservableList.add(new Attributes("cctv_product","string"));
		attributesObservableList.add(new Attributes("cctv_place","string"));
		attributesObservableList.add(new Attributes("cctv_capacity","double"));
		attributesObservableList.add(new Attributes("cctv_lastrecoard","string"));
		attributesObservableList.add(new Attributes("device_name","string"));
		attributesObservableList.add(new Attributes("device_type","string"));
		attributesObservableList.add(new Attributes("device_address","string"));
		attributesObservableList.add(new Attributes("device_affilience","string"));
		attributesObservableList.add(new Attributes("device_powerconsumtion","string"));
		attributesObservableList.add(new Attributes("device_status","string"));
		attributesObservableList.add(new Attributes("env_name","string"));
		attributesObservableList.add(new Attributes("env_type","string"));
		attributesObservableList.add(new Attributes("env_affilience","string"));
		attributesObservableList.add(new Attributes("env_status","string"));
		attributesObservableList.add(new Attributes("power_consumtion","double"));
		attributesObservableList.add(new Attributes("temperture","double"));
		attributesObservableList.add(new Attributes("act_time","string"));
		attributesObservableList.add(new Attributes("space","string"));
		
		ruleListObservableList.add(new RuleList(":isOverPowerRule",":OverPower",":power","swrlb:graterThan"));
		ruleListObservableList.add(new RuleList(":isUnderPowerRule",":UnderPower",":power","swrlb:lessThan"));
		ruleListObservableList.add(new RuleList(":isOutSideRule",":OutSide",":locate","swrlb:equals"));
		ruleListObservableList.add(new RuleList(":isAcceldecreaseRule",":Accel",":speed","swrlb:graterThan"));
		ruleListObservableList.add(new RuleList("isStableRule",":Stable",":speed","swrlb:lessThan"));
		
		fkObservableList.add(new ForeignKey("hasdevice_id","device"));
		fkObservableList.add(new ForeignKey("hasenv_id","env"));
		
		smartModuleList.add(new SmartModuleList("HirarchyDay","k-means","HirarchyDay.zip"));
		smartModuleList.add(new SmartModuleList("HirarchyYear","v-means","HirarchyYear.zip"));
		smartModuleList.add(new SmartModuleList("HirarchyMonth","ibory","HirarchyMonth.zip"));
		
		orchestrationMemberTable.setItems(memberListObservableList);
		gkSchemaTableTableView.setItems(tablenameObservableList);
		gkSchemaPropertyTableView.setItems(attributesObservableList);
		swrlRuleSetTableView.setItems(ruleListObservableList);
		gkSchemaForeignKeyTableView.setItems(fkObservableList);
		smartModuleListTableView.setItems(smartModuleList);
		
	}
}
