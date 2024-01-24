import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookManagerImpl implements IBookManager {
    private List<Book> books;

    private static IBookManager instance;

    private BookManagerImpl() {
        books = new ArrayList<>();
    }

    public static IBookManager getInstance() {
        if (instance == null) {
            instance = new BookManagerImpl();
        }
        return instance;
    }

    @Override
    public void add(Book book) { // 책 등록
        books.add(book);
    }

    @Override
    public void addAll(List<Book> newBooks) {
        books.addAll(newBooks);
    }

    @Override
    public List<Book> getList() { // 서점에 있는 모든 책 조회
        return books;
    }

    @Override
    public Book searchByIsbn(String isbn) throws ISBNNotFoundException { // isbn 으로 한 권 조회
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        throw new ISBNNotFoundException(isbn);
    }

    @Override
    public List<Book> searchByTitle(String title) throws TitleNotFoundException { // title 로 책 조회 (같은 제목이어도 버전이나 형태가 다르면
        // Isbn이 다름)
        List<Book> findBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                findBooks.add(book);
            }
        }
        if (!findBooks.isEmpty()) {
            return findBooks;
        }
        throw new TitleNotFoundException(title);
    }

    @Override
    public List<Magazine> getMagazines() { // 잡지 목록 조회, null 일수도 있음.
        List<Magazine> magazines = new ArrayList<>();
        for (Book book : books) {
            if (book instanceof Magazine) {
                magazines.add((Magazine) book);
            }
        }
        return magazines;
    }

    @Override
    public List<Book> getBooks() { // 잡지를 제외한 책들 조회
        List<Book> pureBooks = new ArrayList<>();
        for (Book book : books) {
            if (!(book instanceof Magazine)) {
                pureBooks.add(book);
            }
        }
        return pureBooks;
    }

    @Override
    public int getTotalPrice() { // 전체 가격 조회
        return books.stream().mapToInt(Book::getPrice).sum();
    }

    @Override
    public double getPriceAvg() { // 전체 가격 평균 조회
        return (double) getTotalPrice() / books.size();
    }

    @Override
    public void sell(String isbn, int quantity) throws QuantityException, ISBNNotFoundException { // 책 판매(해당 수량만큼 -)
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.getQuantity() - quantity < 0) {
                    throw new QuantityException();
                }
                book.setQuantity(book.getQuantity() - quantity);
                return;
            }
        }
        throw new ISBNNotFoundException(isbn);
    }

    @Override
    public void buy(String isbn, int quantity) throws ISBNNotFoundException { // 책 구매 (해당 수량만큼 +)
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.setQuantity(book.getQuantity() + quantity);
                return;
            }
        }
        throw new ISBNNotFoundException(isbn);
    }

    @Override
    public void removeByIsbn(String isbn) throws ISBNNotFoundException { // isbn이 같은 책 삭제
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                books.remove(i);
                return;
            }
        }
        throw new ISBNNotFoundException(isbn);
    }

    @Override
    public void removeByTitle(String title) throws TitleNotFoundException { // title이 같은 책 여러개 삭제, 인덱스 뒤에서 부터
        int originSize = books.size();
        for (int i = books.size() - 1; i >= 0; i--) {
            if (books.get(i).getTitle().equals(title)) {
                books.remove(i);
            }
        }
        if (originSize != books.size()) {
            return;
        }
        throw new TitleNotFoundException(title);
    }

    @Override
    public void removeAll() { // 전체 삭제
        books.clear();
    }

    @Override
    public void save(String filePath) throws IOException {
        ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(filePath));
        oss.writeObject(books);
        oss.close();
    }

    @SuppressWarnings("unchecked")
    public List<Book> load(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
        List<Book> loadBooks = (ArrayList<Book>) ois.readObject();
        ois.close();
        books = loadBooks;
        return books;
    }
}
