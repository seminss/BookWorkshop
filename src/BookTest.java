import java.io.IOException;
import java.util.List;

public class BookTest {
    private IBookManager bm;

    private BookTest() {
        bm = BookManagerImpl.getInstance();
    }

    public static void main(String[] args) {

        BookTest test = new BookTest();

        test.addAllTest(List.of(new Book("21423", "Java Pro", "김하나", "jaen.kr",
                15000, "Java 기본 문법 v0", 10), new Book("21424", "Java Pro", "김하나", "jaen.kr",
                15000, "Java 기본 문법 v1", 10), new Book("21425", "Java Pro", "김하나", "jaen.kr",
                15000, "Java 기본 문법 v2", 5), new Book("35355", "분석설계", "소나무", "jaen.kr",
                30000, "SW 모델링", 30), new Magazine("45678", "월간 알고리즘", "홍길동", "jaen.kr",
                10000, "1월 알고리즘", 40, 2021, 1)));

        //test.loadBook("books.txt");

        test.getListTest();
        test.getBookTest();
        test.getMagazinesTest();
        test.getValueTest();

        test.sellTest(20, "21424");
        test.sellTest(5, "21424");
        test.searchByIsbnTest("21424");

        test.buyTest(10, "11111");
        test.buyTest(10, "21424");
        test.searchByIsbnTest("21424");

        test.removeByIsbnTest("35355");
        test.getListTest();

        test.removeByTitleTest("Java Pro");
        test.searchByTitleTest("Java pro");

        test.getListTest();

//        test.bm.removeAll();
        //test.saveTest("books.txt");
    }

    private void saveTest(String filePath) {
        try {
            bm.save(filePath);
            System.out
                    .println("-------------------- [ " + filePath + "에 현재까지 등록된 책들이 저장 되었습니다. ] --------------------");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadBook(String filePath) {
        try {
            bm.load(filePath);
            System.out.println("-------------------- [ " + filePath + "에서 로드되었습니다. ] --------------------");
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addAllTest(List<Book> books) {
        System.out.println("-------------------- [ 책이 manager에게 전달되었습니다. ] --------------------");
        bm.addAll(books);
    }

    private void removeByTitleTest(String title) {
        System.out.println("-------------------- [ removeByTitle:" + title + "] --------------------");
        try {
            bm.removeByTitle(title);
            System.out.println("성공");
        } catch (TitleNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeByIsbnTest(String isbn) {
        System.out.println("-------------------- [ removeByIsbn:" + isbn + "] --------------------");
        try {
            bm.removeByIsbn(isbn);
            System.out.println("성공");
        } catch (ISBNNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buyTest(int quantity, String isbn) {
        System.out.println("-------------------- [ buy(" + quantity + "):" + isbn + " ] --------------------");
        try {
            bm.buy(isbn, quantity);
            System.out.println("성공");
        } catch (ISBNNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sellTest(int quantity, String isbn) {
        System.out.println("-------------------- [ sell(" + quantity + "):" + isbn + "] --------------------");
        try {
            bm.sell(isbn, quantity);
            System.out.println("성공");
        } catch (ISBNNotFoundException | QuantityException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getValueTest() {
        System.out.println("--------------------------------------------------------");
        System.out.println("도서 가격 총합 : " + bm.getTotalPrice());
        System.out.println("도서 가격 평균 : " + bm.getPriceAvg());
    }

    private void getMagazinesTest() {
        System.out.println("-------------------- [ 잡지만 조회 ] --------------------");
        bm.getMagazines().forEach(this::print);
    }

    private void getBookTest() {
        System.out.println("-------------------- [ 책만 조회 ] --------------------");
        bm.getBooks().forEach(this::print);
    }

    private void getListTest() {
        System.out.println("-------------------- [ 전체 조회 ] --------------------");
        bm.getList().forEach(this::print);
    }

    private void searchByIsbnTest(String isbn) {
        System.out.println("-------------------- [ searchByIsbn:" + isbn + "] --------------------");
        try {
            print(bm.searchByIsbn(isbn));
        } catch (ISBNNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchByTitleTest(String title) {
        System.out.println("-------------------- [ searchByTitle:" + title + "] --------------------");
        try {
            bm.searchByTitle(title).forEach(this::print);
        } catch (TitleNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void print(Book b) {
        System.out.println(b);
    }
}