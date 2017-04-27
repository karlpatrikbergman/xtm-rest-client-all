package basic;

import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

final class YamlToConfigurationParser {

    public static Configuration parse(String resourceOnClassPath) throws IOException {
        Yaml yaml = new Yaml();
        InputStream in = new ResourceInputStream(resourceOnClassPath);
        return yaml.loadAs(in, Configuration.class);
    }
}
