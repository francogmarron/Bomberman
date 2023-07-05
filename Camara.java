 public class Camara {
	private double x,y;
	private double resX, resY;

    public Camara(double x,double y) {
    	this.x=x;
    	this.y=y;
    }

	public void seguimiento(Heroe obj){
		Nivel e = Nivel.getInstance();
		//this.x = -b.getX()+(m.getWidth()/8);
		this.x = -obj.getX()+resX/2;
		if (this.x>0){
				this.x=0;
		}

		if(this.x < -(e.getWidth()-resX)){
			this.x = -(e.getWidth()-resX);
		}

	}

	public void setViewPort(double x,double y){
		setVisible(x,y);
	}

	public void setVisible(double x,double y){
		resX=x;
		resY=y;
	}

    public void setX(double x){
    	this.x=x;
	}
	
     public void setY(double y){
    	this.y=y;
	}
	
    public double getX(){
    	return this.x;
	}
	
     public double getY(){
    	return this.y;
    }
 
}