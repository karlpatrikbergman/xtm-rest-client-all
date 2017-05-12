package dockercompose;

import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;

final class YamlToJavaParser {

    public static DockerCompose parse(String resourceOnClassPath) throws IOException {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(new Constructor(DockerCompose.class),representer);
        return yaml.loadAs(new ResourceInputStream(resourceOnClassPath), DockerCompose.class);
    }
}
