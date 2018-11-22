package project.com.newsikdang;

public class Review {
    //이메일, 이름, 사진url으로 이루어짐
    private String revKey;
    private String resKey;
    private String userUid;
    private String name;
    private String text;
    private String photo;
    private String date;
    private float star;
    private long heart;

    public Review() {
        //기본 생성자
    }
    public Review(String revKey, String resKey, String userUid, String name, String text, String date) {
        //생성자2
        this.revKey = revKey;
        this.resKey = resKey;
        this.userUid = userUid;
        this.name = name;
        this.text = text;
        this.photo = "";
        this.date = date;
        this.star = 0;
        this.heart = 0;
    }
    public Review(String revKey, String resKey, String userUid, String name, String text, String photo, String date, float star, long heart) {
        //생성자3
        this.revKey = revKey;
        this.resKey = resKey;
        this.userUid = userUid;
        this.name = name;
        this.text = text;
        this.photo = photo;
        this.date = date;
        this.star = star;
        this.heart = heart;
    }

    public String getRevKey() { return revKey; }
    public String getResKey() { return resKey; }
    public String getUserUid() { return userUid; }
    public String getName() { return name; }
    public String getText() { return text; }
    public String getPhoto() { return photo; }
    public String getDate() { return date; }
    public float getStar() { return star; }
    public long getHeart() { return heart; }

    public void setRevKey(String revKey) { this.revKey = revKey; }
    public void setResKey(String resKey) { this.resKey = resKey; }
    public void setUserUid(String userUid) { this.userUid = userUid; }
    public void setName(String name) { this.name = name; }
    public void setText(String text) { this.text = text; }
    public void setPhoto(String photo) { this.photo = photo; }
    public void setDate(String date) { this.date = date; }
    public void setStar(float star) { this.star = star; }
    public void setHeart(long heart) { this.heart = heart; }

}
