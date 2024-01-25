public class AlreadyExsitExcption extends RuntimeException {
    public AlreadyExsitExcption(String isbn) {
        super(isbn+": 번호의 책이 이미 존재합니다.");
    }
}
