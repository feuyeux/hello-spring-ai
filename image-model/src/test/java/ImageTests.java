import lombok.extern.slf4j.Slf4j;
import org.feuyeux.ai.hello.ImageService;
import org.feuyeux.ai.hello.repository.ModelType;
import org.junit.jupiter.api.Test;

@Slf4j
public class ImageTests {
    @Test
    public void test() {
        String prompt = """
                    A cartoon depicting a gangster donkey wearing 
                    sunglasses and eating grapes in a city street.
                """;
        var imageService = new ImageService();
        String result = imageService.generateImage(ModelType.qianfan,prompt);
        log.info("RESULT: {}", result);
    }
}
