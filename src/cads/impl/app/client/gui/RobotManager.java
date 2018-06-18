package cads.impl.app.client.gui;

public class RobotManager {

	private String current;
	private int vertPos;
	private int horPos;

	public int getVertPos() {
		return vertPos;
	}

	public void setVertPos(int vertPos) {
		this.vertPos = vertPos;
	}

	public int getHorPos() {
		return horPos;
	}

	public void setHorPos(int horPos) {
		this.horPos = horPos;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String value) {
		current = value;
	}
}
