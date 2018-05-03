package cads.impl.mom;

public class Message {
	
	public enum MsgType {
	    VERTIKAL, HORIZONTAL, GRIPPER, PING
	}
	
	private int seqid;
	private MsgType type;
	private int lenght;
	private int value;
	
	public Message(){
		super();
	}
	
	public Message(MsgType type, int lenght, int value, int seqid) {
		this.type = type;
		this.lenght = lenght;
		this.value = value;
		this.seqid = seqid;
	}
	
	public MsgType getType() {
		return type;
	}
	public void setType(MsgType type) {
		this.type = type;
	}
	public int getLenght() {
		return lenght;
	}
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getSeqId(){
		return seqid;
	}
	public void setSeqId(int seqid){
		this.seqid = seqid;
	}
}
