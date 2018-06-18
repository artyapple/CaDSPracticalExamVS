package cads.impl.mom.kafka;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import cads.impl.mom.IBuffer;

public class CaDSKafkaProducer implements Runnable {

	private final static String BOOTSTRAP_SERVERS_DEF = "localhost:9093,localhost:9094";
	private final String TOPIC;
	private final String BOOTSTRAP_SERVERS;
	private final Producer<Long, String> producer;
	private IBuffer<String> buffer;

	public CaDSKafkaProducer(String topic, String kafkaServers, IBuffer<String> buffer) {
		this.buffer = buffer;
		this.TOPIC = topic;
		this.BOOTSTRAP_SERVERS = kafkaServers;
		System.out.println(TOPIC);
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "Kafka" + TOPIC + "Producer");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		this.producer = new KafkaProducer<>(props);
	}

	public CaDSKafkaProducer(String topic, IBuffer<String> buffer) {
		this(topic, BOOTSTRAP_SERVERS_DEF, buffer);
	}
	
	public Producer<Long, String> getProducer(){
		return producer;
	}

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {
			if(buffer.hasElements()){
				String msg = buffer.getLast();
				long time = System.currentTimeMillis();
				ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, time, msg);
				producer.send(record, (metadata, exception) -> {
					//long elapsedTime = System.currentTimeMillis() - time;
					if (metadata != null) {
						//System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n",
								//record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
					} else {
						exception.printStackTrace();
					}
				});
			}
			
		}
		producer.flush();
		producer.close();

	}
}
