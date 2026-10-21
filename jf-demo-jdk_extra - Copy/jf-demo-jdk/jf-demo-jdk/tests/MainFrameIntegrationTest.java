import java.awt.Window;
import java.util.Arrays;
import java.util.List;
import javax.swing.SwingUtilities;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainFrameIntegrationTest {
    @Test
    void loadButtonFillsName() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            MainFrame frame = new MainFrame();
            try {
                frame.clickLoadButton();

                assertEquals("Dragan", frame.getEnteredName());
            } finally {
                // 3. Освобождаваме прозореца дори при неуспешен тест.
                frame.dispose();
            }
        });
    }

    
    
}
