package com.github.aites.framework.monitor;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface PreProcessor {
	/*Stretegy Pattern���� ��� ��ó�� �޼ҵ�� ���� 3���� �޼ҵ带 �Ϲ�ȭ�ϴ� �������� �����˴ϴ�.*/
	public void processData(Object mqttMessage); //�ϳ��� �޼����� �� �������� �뵵�� �°� �и��ϴ� �޼ҵ�
	public void dataPreprocess(); //�и��� �����͸� �ϳ��� �ڷ� ������ ���� �޼ҵ�
	public Object getProcessedData(); //ó���� �����͸� ��ȯ�ϴ� �޼ҵ�
}

