package main;

import java.io.Serializable;

abstract class ClickableSpace extends Space implements Serializable{
	private static final long serialVersionUID = 15513411;
	
	abstract void performAction();
	
	public ClickableSpace(int x, int y){
		super(x, y);
	}
	
	//either a blank space or a mineable space
}
