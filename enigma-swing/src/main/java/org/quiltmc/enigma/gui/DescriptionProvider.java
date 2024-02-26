package org.quiltmc.enigma.gui;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

public interface DescriptionProvider {
	default void setMappings(Path path) throws IOException {}

	default Map<String, String> getClassProperties(String obfClass) {
		return Collections.emptyMap();
	}

	default Map<String, String> getFieldProperties(String obfClass, String obfField, String obfDesc) {
		return Collections.emptyMap();
	}

	default Map<String, String> getMethodProperties(String obfClass, String obfMethod, String obfDesc) {
		return Collections.emptyMap();
	}

	default Map<String, String> getArgumentProperties(String obfClass, String obfMethod, String obfDesc, String obfArgument) {
		return Collections.emptyMap();
	}
}
