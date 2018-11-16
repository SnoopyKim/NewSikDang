package project.com.newsikdang;

public class Review {
    //이메일, 이름, 사진url으로 이루어짐
    public String key;
    public String email;
    public String name;
    public String text;
    public String photo;

    public Review() {
        //기본 생성자
    }
    public Review(String uid, String email, String name, String text) {
        //생성자2
        this.key = key;
        this.email = email;
        this.name = name;
        this.text = text;
        this.photo = "";
    }
    public Review(String uid, String email, String name, String text, String photo) {
        //생성자2
        this.key = key;
        this.email = email;
        this.name = name;
        this.text = text;
        this.photo = photo;
    }

    public String getKey() { return key; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getText() { return text; }
    public String getPhoto() { return photo; }

    public void setUid(String key) { this.key = key; }
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }
    public void setPhoto(String photo) { this.photo = photo; }

}
