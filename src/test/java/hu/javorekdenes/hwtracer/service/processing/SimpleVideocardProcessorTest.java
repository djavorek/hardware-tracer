package hu.javorekdenes.hwtracer.service.processing;

import hu.javorekdenes.hwtracer.model.*;
import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimpleVideocardProcessorTest {

    private static final Price DUMMY_PRICE = new Price(99999);
    private static final LocalDate DUMMY_UPLOAD_DATE = LocalDate.of(2022, 1, 30);
    private static final String DUMMY_URL = "https://nonexisting.test";

    @InjectMocks
    private SimpleVideocardProcessor subject;

    @Mock
    private VideocardModelRegistry modelRegistry;

    @Test
    void processShouldSetPropertiesFromRawCard() {
        // Given
        String title = "XFX Radeon RX 580 8GB";
        Videocard rawVideocard = new Videocard(title, DUMMY_UPLOAD_DATE, DUMMY_PRICE, DUMMY_URL);

        List<String> modelsInRegistry = Collections.emptyList();
        when(modelRegistry.getAllModels()).thenReturn(modelsInRegistry); // Using empty registry
        when(modelRegistry.designerFromModel(Mockito.anyString())).thenReturn(GpuDesigner.UNKNOWN);

        // When
        ProcessedVideocard processedVideocard = subject.process(rawVideocard);

        // Then
        assertAll("Properties from raw card",
                () -> assertEquals(title, processedVideocard.getTitle()),
                () -> assertEquals(DUMMY_UPLOAD_DATE, processedVideocard.getUploadedDate()),
                () -> assertEquals(DUMMY_PRICE, processedVideocard.getPrice()),
                () -> assertEquals(DUMMY_URL, processedVideocard.getUrl()),
                () -> assertEquals(HardwareType.VIDEOCARD, processedVideocard.getType()));
    }

    @Test
    void processShouldFindVideocardSpecificProperties() {
        // Given
        String title = "XFX Radeon RX 580 8GB";

        Videocard rawVideocard = new Videocard(title, DUMMY_UPLOAD_DATE, DUMMY_PRICE, DUMMY_URL);

        List<String> modelsInRegistry = List.of("Radeon RX 580", "Radeon RX 580 2048SP", "Radeon RX 580G", "Radeon RX 580X");
        when(modelRegistry.getAllModels()).thenReturn(modelsInRegistry);
        when(modelRegistry.designerFromModel(Mockito.anyString())).thenReturn(GpuDesigner.AMD);

        // When
        ProcessedVideocard processedVideocard = subject.process(rawVideocard);

        // Then
        assertAll("Processed properties",
                () -> assertEquals("Radeon RX 580", processedVideocard.getModelName()),
                () -> assertEquals(HardwareManufacturer.XFX, processedVideocard.getManufacturer()),
                () -> assertEquals(GpuDesigner.AMD, processedVideocard.getGpuDesigner()),
                () -> assertEquals(8, processedVideocard.getMemorySize()),
                () -> assertFalse(processedVideocard.isWarranty()));
    }

    @TestFactory
    Stream<DynamicTest> modelNameIdentificationTests() {
        Map<String, String> titleToModelMap = Map.of(
                "SAPPHIRE Radeon RX 570 Pulse 4GB GDDR5 256bit Eladó", "Radeon RX 570"
        );

        List<String> mockModelRegistryContent = List.of(
                "Radeon RX 5700", "Radeon RX 5700 XT", "Radeon RX 570X", "Radeon RX 570", "Radeon RX 570 X2",
                "Radeon HD 8570 OEM", "Radeon HD 7570");

        return titleToModelMap.entrySet().stream()
                .map(entry -> DynamicTest.dynamicTest("Model processing test: " + entry.getKey() , () -> {
                    // Given
                    var title = entry.getKey();
                    var expectedModelName = entry.getValue();

                    when(modelRegistry.getAllModels()).thenReturn(mockModelRegistryContent);
                    when(modelRegistry.designerFromModel(Mockito.anyString())).thenReturn(GpuDesigner.UNKNOWN);

                    var toProcess = new Videocard(title, DUMMY_UPLOAD_DATE, DUMMY_PRICE, DUMMY_URL);

                    // When
                    var processedVideocard = subject.process(toProcess);

                    // Then
                    assertEquals(expectedModelName, processedVideocard.getModelName());
                }
            ));
    }
}
