package project.com.newsikdang;

import java.io.Serializable;

public class Menu implements Serializable {
    private String name;
    private String cost;

    public Menu() {
        //기본 생성자
    }
    public Menu(String name, String cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() { return name; }
    public String getCost() { return cost; }

    public void setName(String name) { this.name = name; }
    public void setCost(String cost) { this.cost = cost; }

}
