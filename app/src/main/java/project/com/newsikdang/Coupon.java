package project.com.newsikdang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Coupon{
    String crestaurant;
    String cprice;
    String cdate;
    public String getCrestaurant() {return crestaurant; }
    public String getCprice() {return cprice;}
    public String getCdate() {return  cdate;}
    public Coupon(String crestaurant, String cprice, String cdate){
        this.crestaurant = crestaurant;
        this.cprice = cprice;
        this.cdate = cdate;
    }
}
