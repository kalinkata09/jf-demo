import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void findsUserByUsernameAndPassword() throws Exception {
        // 1. Подготвяме временни данни в истински PostgreSQL.
        Repository repository = new TestRepository();

        // 2. Извикваме истинския метод от Repository.
        User user = repository.getUserByUsernameAndPassword("Petko", "1234");

        // 3. Проверяваме дали получаваме обект и всички негови стойности.
        assertNotNull(user);
        assertEquals(101, user.getId());
        assertEquals("Petko", user.getUsername());
        assertEquals("petko@example.com", user.getEmail());
        assertEquals("1234", user.getPassword());
        assertEquals("student", user.getRole());

        // 4. Грешна парола или име не трябва да връщат потребител. Ще върне null.
        assertNull(repository.getUserByUsernameAndPassword("Petko", "0000"));
        assertNull(repository.getUserByUsernameAndPassword("ivan", "1234"));    
    }
}
