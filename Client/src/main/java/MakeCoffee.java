interface Coffee{
		public double makeCoffee();

        public String getReceipt();
}

class BasicCoffee implements Coffee{

	private double cost = 4.50;

    @Override
    public String getReceipt(){
        return "Black Coffee: $4.50";
    }

    @Override
	public double makeCoffee() {
		return cost;
	}	
}

abstract class CoffeeDecorator implements Coffee{
	protected Coffee specialCoffee;
	
	public CoffeeDecorator(Coffee specialCoffee) {
		this.specialCoffee = specialCoffee;
	}

    public String getReceipt(){
        return specialCoffee.getReceipt();
    }
	
	public double makeCoffee() {
		return specialCoffee.makeCoffee();
	}
}

class ColdFoam extends CoffeeDecorator{
    private double cost = 0.75;
    ColdFoam(Coffee specialCoffee) {super(specialCoffee);}
    public double makeCoffee() {return specialCoffee.makeCoffee() + addColdFoam();}
    public String getReceipt(){return specialCoffee.getReceipt() + addOn();}
    private String addOn(){return "\n + cold foam: $0.75";}
    private double addColdFoam(){return cost;}
}

class WhippedCream extends CoffeeDecorator{
    private double cost = 0.50;
    WhippedCream(Coffee specialCoffee) {super(specialCoffee);}
    public double makeCoffee() {return specialCoffee.makeCoffee() + addWhippedCream();}
    public String getReceipt(){return specialCoffee.getReceipt() + addOn();}
    private String addOn(){return "\n + whipped cream: $0.50";}
    private double addWhippedCream(){return cost;}
}

class ExtraShot extends CoffeeDecorator{
	
	private double cost = 1.20;
	
	ExtraShot(Coffee specialCoffee){
		super(specialCoffee);
	}
	
	public double makeCoffee() {
		return specialCoffee.makeCoffee() + addShot();
	}

    public String getReceipt(){return specialCoffee.getReceipt() + addOn();}

    private String addOn() { return "\n + extra shot: $1.20";}

	private double addShot() {
//		System.out.println(" + extra shot: $1.20");
		
		return cost;
	}
}

class Cream extends CoffeeDecorator{

	private double cost = .50;
	Cream(Coffee specialCoffee){
		super(specialCoffee);
	}

	public double makeCoffee() {
		return specialCoffee.makeCoffee() + addCream();
	}

    public String getReceipt(){return specialCoffee.getReceipt() + addOn();}

    private String addOn() { return "\n + cream: $.50";}

	private double addCream() {

//		System.out.println(" + cream: $.50");

		return cost;
	}
}

class Sugar extends CoffeeDecorator{
	
	private double cost = .50;
	
	Sugar(Coffee specialCoffee){
		super(specialCoffee);
	}
	
	public double makeCoffee() {
		return specialCoffee.makeCoffee()+ addSugar();
	}

    public String getReceipt(){return specialCoffee.getReceipt() + addOn();}

    private String addOn() { return "\n + sugar: $.50";}
	
	private double addSugar() {
		return cost;
	}
}





