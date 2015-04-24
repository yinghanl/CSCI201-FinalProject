package main;

public class Bullet extends Moveable{
	int direction;
	int damage;
	
	public Bullet(Space loc, int direction, int val){
		super(loc);
		this.direction = direction;
		damage = val;
	}
	
	public void hit(Creep c){
		c.hit(damage);
	}
	
	public void run(){
		while(true){
			try {
				sleep(200);
				move(direction);
				//if there is a creep in that location, hit it
				if(currentLocation instanceof PathSpace){
					PathSpace space = (PathSpace)currentLocation;
//					for(Moveable m: space.getMoveable()){
//						if(m instanceof Creep){
//							System.out.println("H");
//						}
//					}
					if(space.getCreep() != null){
						space.getCreep().hit(damage);
						System.out.println(space.getCreep().isDead());
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BoundaryException be){
				break;
			}
			
		}
	}
	
	

}
