package org.feuyeux.ai.hello;

import lombok.extern.slf4j.Slf4j;
import org.feuyeux.ai.hello.repository.ModelType;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import static org.feuyeux.ai.hello.repository.ModelClient.buildQianFanImageModel;
import static org.feuyeux.ai.hello.repository.ModelClient.buildZhipuAiImageModel;
import static org.feuyeux.ai.hello.utils.DotEnv.loadEnv;

@Slf4j
public class ImageService {
    static {
        loadEnv();
    }

    ImageModel imageModel;

    public String generateImage(ModelType type,String prompt) {
        ImagePrompt imagePrompt = new ImagePrompt(prompt);
        ImageResponse imageResponse;
        if (Objects.requireNonNull(type) == ModelType.qianfan) {
            imageModel = buildQianFanImageModel();
            log.info("使用千帆AI模型");
        } else {
            imageModel = buildZhipuAiImageModel();
            log.info("使用智谱AI模型");
        }
        imageResponse= imageModel.call(imagePrompt);
        return resolveImage(imageResponse);
    }

    private String resolveImage(ImageResponse imageResponse) {
        Image image = imageResponse.getResult().getOutput();
        return Optional
                .ofNullable(image.getUrl())
                .orElseGet(() -> {
                    String b64Json = image.getB64Json();
                    return base64ToImage(b64Json);
                });
    }

    private String base64ToImage(String b64Json) {
        byte[] imageBytes = Base64.getDecoder().decode(b64Json);
        Path outputPath = Paths.get("/tmp/" + UUID.randomUUID() + ".jpg");;
        try (FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
            fos.write(imageBytes);
        } catch (IOException e) {
            log.error("Failed to save image to local file", e);
            throw new RuntimeException("Failed to save image to local file", e);
        }
        return outputPath.toAbsolutePath().toString();
    }
}

