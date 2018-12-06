package project.com.newsikdang;

import java.util.List;

public class Review {
    private boolean detail;
    private String revKey;
    private String resKey;
    private String userUid;
    private String userProfile;
    private String name;
    private String text;
    private List<String> photo;
    private String date;
    private float star_main, star_taste, star_cost, star_service, star_ambiance;
    private long heart;

    public Review() {
        //기본 생성자
    }
    public Review(String revKey, String resKey, String userUid, String name, String profile, String text, String date, float star, long heart) {
        //한줄리뷰 생성자
        this.detail = false;
        this.revKey = revKey;
        this.resKey = resKey;
        this.userUid = userUid;
        this.name = name;
        this.userProfile = profile;
        this.text = text;
        this.date = date;
        this.star_main = star;
        this.heart = heart;
    }
    public Review(String revKey, String resKey, String userUid, String name, String profile, String text, List<String> photo, String date,
                  float star, float star_t, float star_c, float star_s, float star_a, long heart) {
        //상세리뷰 생성자
        this.detail = true;
        this.revKey = revKey;
        this.resKey = resKey;
        this.userUid = userUid;
        this.name = name;
        this.userProfile = profile;
        this.text = text;
        this.photo = photo;
        this.date = date;
        this.star_main = star;
        this.star_taste = star_t;
        this.star_cost = star_c;
        this.star_service = star_s;
        this.star_ambiance = star_a;
        this.heart = heart;
    }

    public boolean isDetail() { return detail; }
    public String getRevKey() { return revKey; }
    public String getResKey() { return resKey; }
    public String getUserUid() { return userUid; }
    public String getName() { return name; }
    public String getUserProfile() { return userProfile; }
    public String getText() { return text; }
    public List<String> getPhoto() { return photo; }
    public String getDate() { return date; }
    public float getStar() { return star_main; }
    public float getStartaste() { return star_taste; }
    public float getStarcost() { return star_cost; }
    public float getStarservice() { return star_service; }
    public float getStarambiance() { return star_ambiance; }
    public long getHeart() { return heart; }

    public void setRevKey(String revKey) { this.revKey = revKey; }
    public void setResKey(String resKey) { this.resKey = resKey; }
    public void setUserUid(String userUid) { this.userUid = userUid; }
    public void setName(String name) { this.name = name; }
    public void setText(String text) { this.text = text; }
    public void setPhoto(List<String> photo) { this.photo = photo; }
    public void setDate(String date) { this.date = date; }
    public void setStar(float star) { this.star_main = star; }
    public void setStartaste(float star) { this.star_taste = star; }
    public void setStarcost(float star) { this.star_cost = star; }
    public void setStarservice(float star) { this.star_service = star; }
    public void setStarambiance(float star) { this.star_ambiance = star; }
    public void setHeart(long heart) { this.heart = heart; }

}
