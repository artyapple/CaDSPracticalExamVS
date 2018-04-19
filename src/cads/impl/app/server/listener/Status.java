package cads.impl.app.server.listener;

public class StatusMove {

	enum State{
		vertical, horizontal
	}
	
	enum Type{
		GRIPPER_INFO
	}
	
	private State status;
	private Type type;
	private int percent;
	
	public StatusMove(){
		super();
	}
	public StatusMove(State status, Type type, int percent) {
		super();
		this.status = status;
		this.type = type;
		this.percent = percent;
	}
	public State getStatus() {
		return status;
	}
	public void setStatus(State status) {
		this.status = status;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
}
