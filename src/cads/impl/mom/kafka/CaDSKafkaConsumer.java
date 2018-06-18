package cads.impl.mom.kafka;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import cads.impl.mom.IBuffer;

public class CaDSKafkaConsumer implements Runnable {

	private final static String BOOTSTRAP_SERVERS_DEF = "localhost:9093,localhost:9094";
	private final String TOPIC;
	private final String BOOTSTRAP_SERVERS;
	private IBuffer<String> buffer;

	private final Consumer<Long, String> consumer;

	public CaDSKafkaConsumer(String topic, IBuffer<String> buffer) {
		this(topic, BOOTSTRAP_SERVERS_DEF, buffer);
	}

	public CaDSKafkaConsumer(String topic, String kafkaServers, IBuffer<String> buffer) {
		this.buffer = buffer;
		this.TOPIC = topic;
		this.BOOTSTRAP_SERVERS = kafkaServers;
		System.out.println(TOPIC);
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "Kafka" + this.TOPIC + "Consumer");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
		// Create the consumer using props.
		consumer = new KafkaConsumer<>(props);
		// Subscribe to the topic.
		consumer.subscribe(Collections.singletonList(this.TOPIC));
	}
	
	public Consumer<Long, String> getKafkaConsumer(){
		return consumer;
	}
	
	@Override
	public void run() {
		System.out.println("Kafka Consumer on topic: " +TOPIC);

		while (!Thread.currentThread().isInterrupted()) {
			ConsumerRecords<Long, String> consumerRecords = consumer.poll(10);
			if (consumerRecords.count() != 0) {
				for(ConsumerRecord<Long, String> record : consumerRecords){
					//buffer.add(r.value());
					System.out.printf("Consumer Record:(%d, %s, %d, %d)\n", record.key(), record.value(),
							record.partition(), record.offset());
				}
				//consumer.commitAsync();
			}
		}
		consumer.close();
	}
}
