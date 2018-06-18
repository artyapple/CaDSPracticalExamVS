package cads.impl.app.client.gui;

import java.io.IOException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;

import cads.impl.factory.Factory;
import cads.impl.mom.IBuffer;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.buffer.Buffer;
import cads.impl.mom.buffer.State;
import cads.impl.mom.kafka.CaDSKafkaConsumer;
import lejos.hardware.Sound;

public class StatusGUIController implements Runnable {

	private CaDSRobotGUISwing gui;
	private MarshallingService marshallingService;
	private IBuffer<String> messageBuffer;
	private Consumer<Long, String> consumer;
	private RobotManager manager;
	private final String currentRobot;

	public StatusGUIController(String topic, String currentRobot) throws InstantiationException, IllegalAccessException, IOException {
		Factory fac = Factory.current();
		// messageBuffer = new Buffer<>(10);
		gui = fac.getInstance(CaDSRobotGUISwing.class);
		this.currentRobot = currentRobot;
		marshallingService = new MarshallingService();
		manager = Factory.current().getInstance(RobotManager.class);
		CaDSKafkaConsumer consumerFactory = new CaDSKafkaConsumer(topic, messageBuffer);
		consumer = consumerFactory.getKafkaConsumer();
		//Thread th = new Thread(consumer);
		//th.start();
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			int p;
			ConsumerRecords<Long, String> consumerRecords = consumer.poll(3);
			if (consumerRecords.count() != 0) {
				for (ConsumerRecord<Long, String> record : consumerRecords) {
					String serializedMessage = record.value();
					State status = null;
					try {
						status = marshallingService.deSerialize(serializedMessage, State.class);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if (status != null) {
						//System.out.println(serializedMessage);
						if(manager.getCurrent().equals(currentRobot)){
							switch (status.getStatusType()) {
							case VERTICAL:
								p = status.getPercent();
								gui.setVerticalProgressbar(p);
								manager.setVertPos(p);
								break;
							case HORIZONTAL:
								p = status.getPercent();
								gui.setHorizontalProgressbar(p);
								manager.setHorPos(p);
								break;
							case GRAB:
								if (status.isGrapOpen()) {
									gui.setGripperOpen();
								} else {
									gui.setGripperClosed();
								}
								break;
							default:
								break;
							}
						}
						
					}
				}
			}
			consumer.commitAsync();
		}
	}
}
