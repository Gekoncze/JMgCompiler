package cz.mg.compiler.tasks.mg.resolver.command.utilities;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.annotations.storage.Value;
import cz.mg.collections.list.List;
import cz.mg.language.entities.mg.runtime.parts.MgDatatype;


public class ExpectedParentInput {
    @Mandatory @Part
    private final List<@Mandatory @Value MgDatatype> datatypes;

    public ExpectedParentInput() {
        this.datatypes = new List<>();
    }

    public ExpectedParentInput(MgDatatype datatype) {
        this.datatypes = new List<>(datatype);
    }

    public ExpectedParentInput(List<MgDatatype> datatypes) {
        this.datatypes = datatypes;
    }

    public List<MgDatatype> getDatatypes() {
        return datatypes;
    }
}
