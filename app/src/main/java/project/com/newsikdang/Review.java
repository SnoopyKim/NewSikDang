package project.com.newsikdang;

public class Review {
    //이메일, 이름, 사진url으로 이루어짐
    public String revKey;
    public String resKey;
    public String email;
    public String name;
    public String text;
    public String photo;
    public String date;

    public Review() {
        //기본 생성자
    }
    public Review(String revKey, String resKey, String email, String name, String text, String date) {
        //생성자2
        this.revKey = revKey;
        this.resKey = resKey;
        this.email = email;
        this.name = name;
        this.text = text;
        this.photo = "";
        this.date = date;
    }
    public Review(String revKey, String resKey, String email, String name, String text, String photo, String date) {
        //생성자3
        this.revKey = revKey;
        this.resKey = resKey;
        this.email = email;
        this.name = name;
        this.text = text;
        this.photo = photo;
        this.date = date;
    }

    public String getRevKey() { return revKey; }
    public String getResKey() { return resKey; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getText() { return text; }
    public String getPhoto() { return photo; }
    public String getDate() { return date; }

    public void setRevKey(String revKey) { this.revKey = revKey; }
    public void setResKey(String resKey) { this.resKey = resKey; }
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }
    public void setText(String text) { this.text = text; }
    public void setPhoto(String photo) { this.photo = photo; }
    public void setDate(String date) { this.date = date; }
}
