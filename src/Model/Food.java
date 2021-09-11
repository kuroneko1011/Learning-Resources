package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Food {
    private int id;
    private String name;
    private int weight;
    private String type;
    private String place;
    private Date expiredDate;

    public Food(int id, String name, int weight, String type, String place, Date expiredDate) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.type = type;
        this.place = place;
        this.expiredDate = expiredDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
    
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public String toString() {
        return String.format("%-10d%-20s%-20d%-15s%-15s%-15s", id, name, weight, type, place, df.format(expiredDate));
    }

    public int comparedTo(Food f) {
        return this.getExpiredDate().compareTo(f.getExpiredDate());

    }
}
