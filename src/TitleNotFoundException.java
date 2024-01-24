public class TitleNotFoundException extends RuntimeException{
    public TitleNotFoundException(String title) {
        super(title+": 제목의 책은 존재하지 않습니다.");
    }
}
