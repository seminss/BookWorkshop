import java.io.IOException;
import java.util.List;

public interface IBookManager {
	void add(Book book) throws AlreadyExsitExcption;

	void removeByIsbn(String isbn) throws ISBNNotFoundException;

	void removeByTitle(String title) throws TitleNotFoundException;

	List<Book> getList();

	Book searchByIsbn(String isbn) throws ISBNNotFoundException;

	List<Book> searchByTitle(String title);

	List<Magazine> getMagazines();

	List<Book> getBooks();

	int getTotalPrice();

	double getPriceAvg();

	void removeAll();

	void sell(String isbn, int quantity) throws QuantityException, ISBNNotFoundException;

	void buy(String isbn, int quantity) throws ISBNNotFoundException;

	void load(String filePath) throws IOException, ClassNotFoundException;

	void save(String filePath) throws IOException;
}
