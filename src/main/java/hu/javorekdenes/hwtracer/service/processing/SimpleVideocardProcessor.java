package hu.javorekdenes.hwtracer.service.processing;

import hu.javorekdenes.hwtracer.model.GpuDesigner;
import hu.javorekdenes.hwtracer.model.HardwareManufacturer;
import hu.javorekdenes.hwtracer.model.VideocardModelRegistry;
import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import hu.javorekdenes.hwtracer.service.VideoCardProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SimpleVideocardProcessor implements VideoCardProcessor {

    private final VideocardModelRegistry modelRegistry;

    @Autowired
    public SimpleVideocardProcessor(VideocardModelRegistry modelRegistry) {
        this.modelRegistry = modelRegistry;
    }

    @Override
    public ProcessedVideocard process(Videocard toProcess) {
        String title = toProcess.getName();

        String modelName = findModelName(title);
        HardwareManufacturer manufacturer = findManufacturer(title);
        GpuDesigner designer = findGpuDesigner(modelName);
        int memorySize = findMemorySize(title);
        Boolean warranty = findWarranty(title);

        return new ProcessedVideocard(toProcess, manufacturer, designer, modelName, warranty, memorySize);
    }

    private String findModelName(String title) {
        Map<String, Integer> matchCountMap = new HashMap<>();
        title = title.toUpperCase();

        for (String model : modelRegistry.getAllModels()) {
            int countForModel = 0;
            // TODO: Cache this
            String[] modelNameParts = model.toUpperCase().split("\\s+");

            for (String modelNamePart : modelNameParts) {
                if (title.contains(modelNamePart)) {
                    countForModel++;
                }
            }
            matchCountMap.put(model, countForModel);
        }

        Integer maxMatchCount = Collections.max(matchCountMap.entrySet(), Map.Entry.comparingByValue()).getValue();
        List<String> modelsWithMaxHitCount = matchCountMap.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), maxMatchCount))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        if (modelsWithMaxHitCount.isEmpty()) {
            return "Unknown model";
        }

        if (modelsWithMaxHitCount.size() > 1) {
            return modelsWithMaxHitCount.stream().min(Comparator.comparingInt(String::length)).get();
        }
        return modelsWithMaxHitCount.get(0);
    }

    private HardwareManufacturer findManufacturer(String title) {
        List<String> manufacturerNamesInTitle = Arrays.stream(HardwareManufacturer.values())
                .map(HardwareManufacturer::getName)
                .filter(title::contains)
                .collect(Collectors.toList());

        // Fallback on multiple matches too
        if (manufacturerNamesInTitle.size() != 1) {
            return HardwareManufacturer.UNKNOWN;
        }
        return HardwareManufacturer.fromName(manufacturerNamesInTitle.get(0));
    }

    private GpuDesigner findGpuDesigner(String model) {
        return modelRegistry.designerFromModel(model);
    }

    private int findMemorySize(String title) {
        // TODO
        return 2;
    }

    private Boolean findWarranty(String title) {
        return false;
    }
}
