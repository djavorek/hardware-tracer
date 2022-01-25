package hu.javorekdenes.hwtracer.model.config;

import hu.javorekdenes.hwtracer.model.GpuDesigner;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class GpuDesignerConfigBinding implements Converter<String, GpuDesigner> {
    @Override
    public GpuDesigner convert(@NonNull String source) {
        return GpuDesigner.valueOf(source);
    }
}
