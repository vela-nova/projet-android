package easy.tuto.bottomnavigationfragmentdemo.profile;

public class modelProfile {
    private int imageResId;
    private String text;

    public modelProfile(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }
}
