package easy.tuto.bottomnavigationfragmentdemo.profile.listViewOnclick;

public class modelProfileDetail {
    private int imageResId;
    private String text;
    private String textViewText;

    public modelProfileDetail(int imageResId, String text, String textViewText) {
        this.imageResId = imageResId;
        this.text = text;
        this.textViewText = textViewText;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }

    public String getTextViewText() { // Ajouter cette méthode
        return textViewText;
    }

    public void setTextViewText(String textViewText) { // Ajouter cette méthode
        this.textViewText = textViewText;
    }
}