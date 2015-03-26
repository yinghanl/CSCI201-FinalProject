package main;

abstract class ClickableSpace extends Space{
	abstract void performAction();
	
	public ClickableSpace(int x, int y){
		super(x, y);
	}
	
	//either a blank space or a mineable space
}
