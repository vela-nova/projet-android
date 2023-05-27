package easy.tuto.bottomnavigationfragmentdemo;

public class model{
        private int imageResId;
        private String text;

        public model(int imageResId, String text) {
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