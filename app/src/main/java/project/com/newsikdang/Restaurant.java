package project.com.newsikdang;

public class Restaurant {
    //이메일, 이름, 사진url으로 이루어짐
    public String resKey;
    public String name;
    public String address;
    public String photo;
    public String date;
    public float star;
    public long heart;
    public long review;
    boolean event;

    public Restaurant() {
        //기본 생성자
    }
    public Restaurant(String resKey, String name, String address, String photo, String date, float star, long heart, long review, boolean event) {
        //생성자3
        this.resKey = resKey;
        this.name = name;
        this.address = address;
        this.photo = photo;
        this.date = date;
        this.star = star;
        this.heart = heart;
        this.review = review;
        this.event = event;
    }

    public String getResKey() { return resKey; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhoto() { return photo; }
    public String getDate() { return date; }
    public float getStar() { return star; }
    public long getHeart() { return heart; }
    public long getReview() { return review; }
    public boolean getEvent() { return event; }

    public void setResKey(String resKey) { this.resKey = resKey; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoto(String photo) { this.photo = photo; }
    public void setDate(String date) { this.date = date; }
    public void setStar(float star) { this.star = star; }
    public void setHeart(long heart) { this.heart = heart; }
    public void setReview(long review) { this.review = review; }
    public void setEvent(boolean event) { this.event = event; }
}
