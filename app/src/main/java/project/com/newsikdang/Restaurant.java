package project.com.newsikdang;

public class Restaurant {
    //이메일, 이름, 사진url으로 이루어짐
    public String resKey;
    public String name;
    public String address;
    public String photo;
    public String date;
    public float star;
    public int like;
    public int review;

    public Restaurant() {
        //기본 생성자
    }
    public Restaurant(String resKey, String name, String address, String date) {
        //생성자2
        this.resKey = resKey;
        this.name = name;
        this.address = address;
        this.photo = "";
        this.date = date;
        this.star = 0;
        this.like = 0;
        this.review = 0;
    }
    public Restaurant(String resKey, String name, String address, String photo, String date, float star, int like, int review) {
        //생성자3
        this.resKey = resKey;
        this.name = name;
        this.address = address;
        this.photo = photo;
        this.date = date;
        this.star = star;
        this.like = like;
        this.review = review;
    }

    public String getResKey() { return resKey; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhoto() { return photo; }
    public String getDate() { return date; }
    public float getStar() { return star; }
    public int getLike() { return like; }
    public int getReview() { return review; }

    public void setResKey(String resKey) { this.resKey = resKey; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoto(String photo) { this.photo = photo; }
    public void setDate(String date) { this.date = date; }
    public void setStar(float star) { this.star = star; }
    public void setLike(int like) { this.like = like; }
    public void setReview(int review) { this.review = review; }
}
