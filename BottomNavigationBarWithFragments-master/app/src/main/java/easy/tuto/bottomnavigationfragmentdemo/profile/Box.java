package easy.tuto.bottomnavigationfragmentdemo.profile;

public class Box {
    private String imageResId;
    private String text;

    public Box(String imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public String getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }
}
