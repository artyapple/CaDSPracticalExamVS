package cads.impl.app.server.listener;

import java.io.IOException;
import java.util.Map;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

import cads.impl.app.server.listener.ObservableValue.ValueType;
import cads.impl.mom.MarshallingService;
import cads.impl.os.UDPClient;

public class RobotStatusListener implements ICaDSEV3RobotStatusListener {

	private MarshallingService ms = new MarshallingService();

	private ObservableValue<Integer> vertikalValue;
	private ObservableValue<Integer> horizontalValue;
	private ObservableValue<Boolean> gripperOpen;

	public enum ValueToObserve {
		VERTIKAL, HORIZONTAL, GRIPPER
	}

	public RobotStatusListener() {
		this(new ObservableValue<Integer>(0), new ObservableValue<Integer>(0), new ObservableValue<Boolean>(false));
	}

	public RobotStatusListener(ObservableValue<Integer> vertikalValue, ObservableValue<Integer> horizontalValue,
			ObservableValue<Boolean> gripperOpen) {
		super();
		this.vertikalValue = vertikalValue;
		this.vertikalValue.setValueType(ValueType.VERTIKAL);
		this.horizontalValue = horizontalValue;
		this.gripperOpen = gripperOpen;
	}

	@Override
	public void onStatusMessage(JSONObject json) {
		try {
			Status status = ms.deSerialize(json.toJSONString(), Status.class);
			if (status.getStatusType().equals(Status.StatusType.VERTICAL)) {
				vertikalValue.setValue(status.getPercent());
			} else if (status.getStatusType().equals(Status.StatusType.HORIZONTAL)) {
				horizontalValue.setValue(status.getPercent());
			} else if (status.getStatusType().equals(Status.StatusType.GRAB)) {
				gripperOpen.setValue(status.isGrapOpen());
			} else {

			}
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
