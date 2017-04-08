package enigma.engine.apphack;

public class FlashLightLogic {
	protected long start;
	protected boolean toggle = true;

	long count = 0;

	public boolean toggle() {
		toggle = !toggle;
		return toggle;

	}

	public boolean logic(){
		long first = 300;
		long second = 1000;
		long third = 1550;
		long fourth = 1600;
		long fifth = 1650;
		long shutoff = 1700;
		long toggleOff = 2000;
		
		if(toggle){
			count += 2;
			//System.out.println(count);
			//period 1
			if(count > first && count < first + 50){
				return false;
			}
			
			//period 2
			if(count > second && count < second + 10){
				return false;
			}
			
			//period 3
			if(count > third && count < third + 10){
				return false;
			}
			
			//period 4
			if(count > fourth && count < fourth + 10){
				return false;
			}
			
			//period 5
			if(count > fifth && count < fifth + 10){
				return false;
			}
			
			if(count > shutoff && count < toggleOff){
				return false;
			}
			
			if(count > toggleOff){
				count--;
				return false;
			}
			//shutoff
			return true; 
		} else {
			if(count <= 0){
				return false;
			}
			count -= 2;
			return false;
		}
	}
}
