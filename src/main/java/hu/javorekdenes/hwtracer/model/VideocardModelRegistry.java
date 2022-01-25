package hu.javorekdenes.hwtracer.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public final class VideocardModelRegistry {
    private static final Gson gson = new Gson();

    private final String dataFileName;
    private Map<GpuDesigner, List<String>> models;

    @Autowired
    public VideocardModelRegistry(@Value("${hu.javorekdenes.gpunames.file}") String dataFileName) {
        this.dataFileName = dataFileName;
    }

    @PostConstruct
    public void readModelData() {
        Type modelsMapType = new TypeToken<Map<GpuDesigner, List<String>>>() {}.getType();
        String jsonString;

        try {
            InputStream inputStream = new ClassPathResource(dataFileName).getInputStream();
            jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        models = gson.fromJson(jsonString, modelsMapType);
    }

    public List<String> getAllModels() {
        Collection<List<String>> modelsForDesigners = models.values();

        return modelsForDesigners.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<String> getAllModelsForDesigner(GpuDesigner designer) {
        return new ArrayList<>(models.get(designer));
    }

    public GpuDesigner designerFromModel(String model) {
        for(Map.Entry<GpuDesigner, List<String>> entry : models.entrySet()) {
            if (entry.getValue().contains(model)) {
                return entry.getKey();
            }
        }
        return GpuDesigner.UNKNOWN;
    }

    private List<String> getListOfAllModels() {
        return models.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
