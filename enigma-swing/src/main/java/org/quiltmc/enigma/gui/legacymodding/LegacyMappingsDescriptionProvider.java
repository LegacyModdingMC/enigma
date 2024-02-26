package org.quiltmc.enigma.gui.legacymodding;

import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.tree.MappingTree;
import net.fabricmc.mappingio.tree.MemoryMappingTree;
import org.quiltmc.enigma.gui.DescriptionProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Shows comments (declared as names in the 'comment' namespace). */
public class LegacyMappingsDescriptionProvider implements DescriptionProvider {
	private MemoryMappingTree comments = null;

	/** Needed to find the actual method reference given an overriding implementation. */
	private final Map<String, MappingTree.MemberMapping> srgToMember = new HashMap<>();

	public void setMappings(Path path) throws IOException {
		this.comments = new MemoryMappingTree();
		MappingReader.read(path, this.comments);
		this.comments.setDstNamespaces(List.of("comment"));

		this.srgToMember.clear();
		for (MappingTree.ClassMapping cls : this.comments.getClasses()) {
			for (MappingTree.MethodMapping method : cls.getMethods()) {
				if (method.getSrcName().startsWith("func_")) {
					this.srgToMember.put(method.getSrcName(), method);
				}
			}

			for (MappingTree.FieldMapping field : cls.getFields()) {
				if (field.getSrcName().startsWith("field_")) {
					this.srgToMember.put(field.getSrcName(), field);
				}
			}
		}
	}

	public Map<String, String> getFieldProperties(String obfClass, String obfField, String obfDesc) {
		if (this.comments != null) {
			MappingTree.FieldMapping field = this.comments.getField(obfClass, obfField, null);
			if (field == null) {
				field = (MappingTree.FieldMapping) this.srgToMember.get(obfField);
			}

			if (field != null) {
				return this.getMemberProperties(field.getDstName(0));
			}
		}

		return Collections.emptyMap();
	}

	public Map<String, String> getMethodProperties(String obfClass, String obfMethod, String obfDesc) {
		if (this.comments != null) {
			MappingTree.MethodMapping method = this.comments.getMethod(obfClass, obfMethod, obfDesc);
			if (method == null) {
				method = (MappingTree.MethodMapping) this.srgToMember.get(obfMethod);
			}

			if (method != null) {
				return this.getMemberProperties(method.getDstName(0));
			}
		}

		return Collections.emptyMap();
	}

	public Map<String, String> getArgumentProperties(String obfClass, String obfMethod, String obfDesc, String obfArgument) {
		if (this.comments != null) {
			MappingTree.MethodMapping method = this.comments.getMethod(obfClass, obfMethod, obfDesc);
			if (method == null) {
				method = (MappingTree.MethodMapping) this.srgToMember.get(obfMethod);
			}

			if (method != null) {
				MappingTree.MethodArgMapping arg = method.getArg(-1, -1, obfArgument);
				if (arg != null) {
					return this.getMemberProperties(arg.getDstName(0));
				}
			}
		}

		return Collections.emptyMap();
	}

	private Map<String, String> getMemberProperties(String comment) {
		Map<String, String> map = new HashMap<>();
		map.put("Comment", comment);
		return map;
	}
}
