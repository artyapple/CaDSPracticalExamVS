//package cads.impl.mom;
//
//import java.net.InetAddress;
//import java.net.SocketException;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//
//import cads.impl.communication.Client;
//import cads.impl.communication.Server;
//import cads.impl.communication.UDPClient;
//import cads.impl.communication.UDPServer;
//
//public class DummyServer {
//
//	public static void main(String[] args) throws SocketException, InterruptedException, JsonProcessingException {
//
//		IBuffer<Message> buffer = new MessageBuffer<>();
//		MessageHandler handler = new MessageHandler(buffer, new MarshallingService());
//		System.out.println("Has messages: "+buffer.hasMessages());
//		Server<String> udp = new UDPServer(8011, handler);
//		Thread t = new Thread(udp);
//		t.start();
//		System.out.println("entry");
//		
//		
//		System.out.println("wait");
//		
//		IMessage msg = buffer.getLastMessage();
//		MarshallingService ms = new MarshallingService();
//		String msgstr = ms.serialize(msg);
//		
//		
//		System.out.println("Has messages: "+buffer.hasMessages());
//		System.out.println(msgstr);
//		t.join();
//		udp.stop();
//		
//		
//	}
//
//}
