package cads.impl.app.server.listener;

import java.io.IOException;
import java.util.Map;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

import cads.impl.app.server.listener.ObservableValue.ValueType;
import cads.impl.mom.IBuffer;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.buffer.State;
import cads.impl.mom.kafka.CaDSKafkaProducer;
import cads.impl.os.UDPClient;

public class RobotStatusListener implements ICaDSEV3RobotStatusListener {

	private MarshallingService ms = new MarshallingService();
	private IBuffer<String> buffer;
	private Producer<Long, String> producer;
	private final String topic;

	private ObservableValue<Integer> vertikalValue;
	private ObservableValue<Integer> horizontalValue;
	private ObservableValue<Boolean> gripperOpen;

	public enum ValueToObserve {
		VERTIKAL, HORIZONTAL, GRIPPER
	}

	public RobotStatusListener(IBuffer<String> buffer, String topic) {
		this(new ObservableValue<Integer>(0), new ObservableValue<Integer>(0), new ObservableValue<Boolean>(false), buffer, topic);
	}

	public RobotStatusListener(ObservableValue<Integer> vertikalValue, ObservableValue<Integer> horizontalValue,
			ObservableValue<Boolean> gripperOpen, IBuffer<String> buffer, String topic) {
		super();
		this.buffer = buffer;
		this.vertikalValue = vertikalValue;
		this.vertikalValue.setValueType(ValueType.VERTIKAL);
		this.horizontalValue = horizontalValue;
		this.horizontalValue.setValueType(ValueType.HORIZONTAL);
		this.gripperOpen = gripperOpen;
		this.gripperOpen.setValueType(ValueType.GRIPPER);
		this.topic = topic;
		this.producer = new CaDSKafkaProducer(topic, buffer).getProducer();
	}

	@Override
	public void onStatusMessage(JSONObject json) {
		try {
			String str = json.toJSONString();
			State status = ms.deSerialize(str, State.class);
			if (status.getStatusType().equals(State.StatusType.VERTICAL)) {
				vertikalValue.setValue(status.getPercent());
				producer.send(new ProducerRecord<>(topic, System.currentTimeMillis(), str));
			} else if (status.getStatusType().equals(State.StatusType.HORIZONTAL)) {
				horizontalValue.setValue(status.getPercent());
				producer.send(new ProducerRecord<>(topic, System.currentTimeMillis(), str));
			} else if (status.getStatusType().equals(State.StatusType.GRAB)){
				producer.send(new ProducerRecord<>(topic, System.currentTimeMillis(), str));
			}
			
			//System.out.println(str);
			//buffer.add(str);
		} catch (IOException e) {
			Logger.getLogger(UDPClient.class.getName()).log(Level.WARNING, "Status failed: " + json.toJSONString(), e);
		}

	}

	public <T extends Observer> void subscribe(ValueToObserve ovalue, T observer) {
		if (ovalue.equals(ValueToObserve.HORIZONTAL)) {
			horizontalValue.addObserver(observer);
		} else if (ovalue.equals(ValueToObserve.VERTIKAL)) {
			vertikalValue.addObserver(observer);
		} else if (ovalue.equals(ValueToObserve.GRIPPER)) {
			gripperOpen.addObserver(observer);
		} else {
		}
	}

	public <T extends Observer> void subscribe(Map<ValueToObserve, T> map) {
		for (Map.Entry<ValueToObserve, T> entry : map.entrySet()) {
			subscribe(entry.getKey(), entry.getValue());
		}
	}
}
