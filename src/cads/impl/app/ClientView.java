package cads.impl.app;

import javax.swing.JFrame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ClientView extends JFrame {
	
	private static final long serialVersionUID = -5616712684518095604L;
	
	private ClientController controller;
	
	private JButton gripperOpenButton;
	private JButton gripperCloseButton;
	
	public ClientView() {
		super("Fernbedienung");
		setLayout(new FlowLayout());
		
		gripperOpenButton = new JButton("Open");
		add(gripperOpenButton);
		gripperCloseButton = new JButton("Close");
		add(gripperCloseButton);
		
		ClientHandler clientHandler = new ClientHandler();
		gripperOpenButton.addActionListener(clientHandler);
		gripperCloseButton.addActionListener(clientHandler);
		
	}
	
	void setController(ClientController controller) {
		this.controller = controller;
	}
	
	private class ClientHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ClientEvent) {
			if (ClientEvent.getSource() == gripperOpenButton) {
				controller.open();
			} else if (ClientEvent.getSource() == gripperCloseButton) {
				controller.close();
			}
		}
		
	}
}
