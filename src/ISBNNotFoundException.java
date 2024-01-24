public class ISBNNotFoundException extends RuntimeException{
    public ISBNNotFoundException(String isbn) {
        super(isbn+": 번호의 책이 존재하지 않습니다.");
    }
}
