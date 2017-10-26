package Monitor;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface PreProcessor {
	/*Stretegy Pattern으로 모든 전처리 메소드는 다음 3가지 메소드를 일반화하는 형식으로 구현됩니다.*/
	public void processData(Object mqttMessage); //하나의 메세지를 각 데이터의 용도에 맞게 분리하는 메소드
	public void dataPreprocess(); //분리된 데이터를 하나의 자료 구조로 묶는 메소드
	public Object getProcessedData(); //처리된 데이터를 반환하는 메소드
}

