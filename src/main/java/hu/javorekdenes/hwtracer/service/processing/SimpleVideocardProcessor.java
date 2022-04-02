package hu.javorekdenes.hwtracer.service.processing;

import hu.javorekdenes.hwtracer.model.GpuDesigner;
import hu.javorekdenes.hwtracer.model.HardwareManufacturer;
import hu.javorekdenes.hwtracer.model.VideocardModelRegistry;
import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import hu.javorekdenes.hwtracer.service.HardwareProcessor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SimpleVideocardProcessor implements HardwareProcessor<Videocard, ProcessedVideocard> {

    private final VideocardModelRegistry modelRegistry;
    private final Map<String, String[]> modelNamePartCache = new HashMap<>();

    @Autowired
    public SimpleVideocardProcessor(VideocardModelRegistry modelRegistry) {
        this.modelRegistry = modelRegistry;
    }

    @Override
    public ProcessedVideocard process(Videocard toProcess) {
        String title = toProcess.getTitle();

        String modelName = findModelName(title);
        HardwareManufacturer manufacturer = findManufacturer(title);
        GpuDesigner designer = findGpuDesigner(modelName);
        int memorySize = findMemorySize(title);
        Boolean warranty = findWarranty(title);

        return new ProcessedVideocard(toProcess, manufacturer, designer, modelName, warranty, memorySize);
    }

    private String findModelName(String title) {
        String UNKNOWN_MODEL = "Unknown model";

        Map<String, Integer> matchCountMap = getMatchScoreForModels(title, true);

        if (matchCountMap.values().stream().noneMatch((v) -> v > 0)) {
            matchCountMap = getMatchScoreForModels(title, false);

            if (matchCountMap.values().stream().noneMatch((v) -> v > 0)) {
                return UNKNOWN_MODEL;
            }
        }

        Integer maxMatchCount = Collections.max(matchCountMap.entrySet(), Map.Entry.comparingByValue()).getValue();
        List<String> modelsWithMaxHitCount = matchCountMap.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), maxMatchCount))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

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
        List<String> keywords = List.of("gb", "giga", "gib", "g");
        String[] titleParts = title.toLowerCase().split("\\s+");

        for (int i = 0; i < titleParts.length ; i++) {
            for (String keyword : keywords) {
                String titlePart = titleParts[i];
                if (titlePart.endsWith(keyword)) {
                    if (titlePart.length() > keyword.length()) {
                        String partWithoutKeyword = titlePart.replace(keyword, "");

                        if (NumberUtils.isParsable(partWithoutKeyword)) {
                            return Integer.parseInt(partWithoutKeyword);
                        } else if (i > 0 && NumberUtils.isParsable(titleParts[i-1])) {
                            return Integer.parseInt(titleParts[i-1]);
                        } else {
                            return 0;
                        }
                    }
                }
            }
        }

        return 0;
    }

    private Boolean findWarranty(String title) {
        List<String> keywords = List.of("warr", "gar", "új", "bontatlan");
        List<String> antiKeyword = List.of("no", "lejárt", "nincs");

        String[] titleParts = title.toLowerCase().split("\\s+");

        for (int i = 0; i < titleParts.length ; i++) {
            int nthPart = i;

            boolean containsWarrantyKeyword = keywords.stream().anyMatch((keyword) -> titleParts[nthPart].contains(keyword));

            if (containsWarrantyKeyword) {
                if ((nthPart > 0 && antiKeyword.stream().anyMatch((keyword) -> titleParts[nthPart - 1].contains(keyword))) ||
                        (nthPart < titleParts.length - 1 && antiKeyword.stream().anyMatch((keyword) -> titleParts[nthPart + 1].contains(keyword)))) {
                    return false;
                }
                return true;
            }
        }

        return false;
    }

    // TODO: Move out
    private Map<String, Integer> getMatchScoreForModels(String title, boolean strict) {
        Map<String, Integer> matchCountMap = new HashMap<>();
        title = title.toUpperCase();

        for (String model : modelRegistry.getAllModels()) {
            int countForModel = 0;

            modelNamePartCache.putIfAbsent(model, model.toUpperCase().split("\\s+"));
            String[] modelNameParts = modelNamePartCache.get(model);

            for (String modelNamePart : modelNameParts) {
                if (strict) {
                    if (title.contains(" " + modelNamePart + " ") || title.endsWith(" " + modelNamePart) || title.startsWith(modelNamePart + " ")) {
                        countForModel++;
                    }
                } else {
                    if (title.contains(modelNamePart)) {
                        countForModel++;
                    }
                }

            }
            matchCountMap.put(model, countForModel);
        }
        return matchCountMap;
    }
}
