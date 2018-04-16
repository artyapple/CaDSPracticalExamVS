///*
// *  Java OTR library 
// *  Copyright (C) 2008-2009  Ian Goldberg, Muhaimeen Ashraf, Andrew Chung, 
// *                           Can Tang 
// * 
// *  This library is free software; you can redistribute it and/or 
// *  modify it under the terms of version 2.1 of the GNU Lesser General 
// *  Public License as published by the Free Software Foundation. 
// * 
// *  This library is distributed in the hope that it will be useful, 
// *  but WITHOUT ANY WARRANTY; without even the implied warranty of 
// *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
// *  Lesser General Public License for more details. 
// * 
// *  You should have received a copy of the GNU Lesser General Public 
// *  License along with this library; if not, write to the Free Software 
// *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
// */
//
//package cads.impl.mom;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//
//import cads.impl.communication.Client;
//import cads.impl.communication.UDPClient;
//import cads.impl.mom.Message.MsgType;
//
//public class DummyClient {
//
//	public static void main(String[] args) throws InterruptedException, IOException {
//		// MarshallingService ms = new MarshallingService();
//		// IMessage msg = new Message(MsgType.HORIZONTAL, 3, 100, 0);
//		// String str = ms.serialize(msg);
//		// System.out.println(str);
//		// IMessage ob = ms.deSerialize(str, Message.class);
//		//
//		// String str2 = ms.serialize(ob);
//		// System.out.println(str2);
//		IMessage msg = new Message(MsgType.HORIZONTAL, 3, 100, 0);
//		IBuffer<Message> buffer = new MessageBuffer<>();
//
//		MessageHandler handler = new MessageHandler(buffer, new MarshallingService());
//		Client<String> udp = new UDPClient(InetAddress.getByName("localhost"), 8011, handler);
//		buffer.addMessage((Message) msg);
//		Thread t = new Thread(udp);
//		t.start();
//		System.out.println("entry");
//		t.join();
//		udp.stop();
//
//	}
//
//}