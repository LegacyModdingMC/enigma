package org.quiltmc.enigma.api.translation.mapping.serde;

import org.quiltmc.enigma.api.ProgressListener;
import org.quiltmc.enigma.api.translation.mapping.EntryMapping;
import org.quiltmc.enigma.api.translation.mapping.tree.EntryTree;

import java.io.IOException;
import java.nio.file.Path;

public interface MappingsReader {
	EntryTree<EntryMapping> read(Path path, ProgressListener progress) throws MappingParseException, IOException;

	default EntryTree<EntryMapping> read(Path path) throws MappingParseException, IOException {
		return this.read(path, ProgressListener.none());
	}
}
