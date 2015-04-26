package main;

public class MineableSpace extends ClickableSpace{
	private int coinValue;
	private int mineRate;
	
	public MineableSpace(int x, int y, int coinValue, int mineRate){
		super(x, y);
		this.coinValue = coinValue;
		this.mineRate = mineRate;
	}
	
	public void performAction(){
		//mine for gold
	}
	
	public int mine()
	{		
		if(coinValue >= mineRate)
		{
			coinValue = coinValue - mineRate;
			return mineRate;
		}
		else
		{
			return 0;
		}
		
	}
	
	public int getAvailable()
	{
		return coinValue;
	}
}
