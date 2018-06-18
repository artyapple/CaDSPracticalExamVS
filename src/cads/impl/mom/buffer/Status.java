//package cads.impl.mom.buffer;
//
//
//public class Status {
//
//	public enum StatusType {VERTICAL, HORIZONTAL, GRAB, NONE}
//	
//	private String state;
//	private String type;
//	private String value;
//	private int percent;
//	
//	public StatusType getStatusType() {
//		switch (state) {
//			case "vertical":
//				return StatusType.VERTICAL;
//			case "horizontal":
//				return StatusType.HORIZONTAL;
//			case "gripper":
//				return StatusType.GRAB;
//			case "ultraSonic":
//			default:
//				return StatusType.NONE;
//		}
//	}
//	
//	public boolean isGrapOpen() {
//		return "open".equals(value);
//	}
//	
//	public String getState() {
//		return state;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public String getValue() {
//		return value;
//	}
//
//	public int getPercent() {
//		return percent;
//	}
//
//	public void setState(String state) {
//		this.state = state;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public void setValue(String value) {
//		this.value = value;
//	}
//
//	public void setPercent(int percent) {
//		this.percent = percent;
//	}
//}
