package parking;

public class Rate {
	private String days;
	private String times;
	private int price;
	
    public String getDays() {
        return days;
    }
    public void setDays(String days) {
        this.days = days;
    }
    
    public String getTimes() {
        return times;
    }
    public void setTimes(String times) {
        this.times = times;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}