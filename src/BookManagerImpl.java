import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
	public void add(Book book) throws AlreadyExsitExcption { // 책 등록
		Book exsitBook = books.stream()
				.filter(b -> b.getIsbn().equals(book.getIsbn()))
				.findFirst().orElse(null);

		if (exsitBook == null) {
			books.add(book);
			return;
		}
		exsitBook.setQuantity(exsitBook.getQuantity() + book.getQuantity());

	}

	@Override
	public List<Book> getList() { // 서점에 있는 모든 책 조회
		return books;
	}

	@Override
	public Book searchByIsbn(String isbn) throws ISBNNotFoundException {
		return books.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst()
				.orElseThrow(() -> new ISBNNotFoundException(isbn));
	}

	@Override
	public List<Book> searchByTitle(String title) throws TitleNotFoundException {
		List<Book> findBooks = books.stream().filter(b -> b.getTitle().equals(title)).toList();
		if (findBooks.isEmpty()) {
			throw new TitleNotFoundException(title);
		}
		return findBooks;
	}

	@Override
	public List<Magazine> getMagazines() {
		return books.stream().filter(b -> b instanceof Magazine).map(b -> (Magazine) b).toList();
	}

	@Override
	public List<Book> getBooks() {
		return books.stream().filter(b -> !(b instanceof Magazine)).toList();
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
		Book book = books.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst()
				.orElseThrow(() -> new ISBNNotFoundException(isbn));

		if (book.getQuantity() - quantity < 0) {
			throw new QuantityException();
		}
		book.setQuantity(book.getQuantity() - quantity);
	}

	@Override
	public void buy(String isbn, int quantity) throws ISBNNotFoundException { // 책 구매 (해당 수량만큼 +)
		Book book = books.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst()
				.orElseThrow(() -> new ISBNNotFoundException(isbn));
		book.setQuantity(book.getQuantity() + quantity);
	}

	@Override
	public void removeByIsbn(String isbn) throws ISBNNotFoundException { // isbn이 같은 책 삭제
		boolean removed = books.removeIf(b -> b.getIsbn().equals(isbn));
		if (!removed) {
			throw new ISBNNotFoundException(isbn);
		}
	}

	@Override
	public void removeByTitle(String title) throws TitleNotFoundException { // title이 같은 책 전부 삭제, 인덱스 뒤에서 부터
		boolean removed = books.removeIf(b -> b.getTitle().equals(title));
		if (!removed) {
			throw new TitleNotFoundException(title);
		}
	}

	@Override
	public void removeAll() {
		books.clear();
	}

	@Override
	public void save(String filePath) throws IOException {
		ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(filePath));
		oss.writeObject(books);
		oss.close();
	}

	@SuppressWarnings("unchecked")
	public void load(String filePath) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
		List<Book> loadBooks = (ArrayList<Book>) ois.readObject();
		ois.close();
		books = loadBooks;
	}
}
