package cz.mg.compiler.tasks.mg.builder.part;

import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.logical.components.MgLogicalVariable;
import cz.mg.language.entities.mg.logical.parts.MgLogicalDatatype;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.leaves.Operator;
import cz.mg.language.entities.text.structured.parts.leaves.names.ObjectName;
import cz.mg.language.entities.text.structured.parts.leaves.names.TypeName;


public class MgBuildDeclarationTask extends MgBuildPartTask {
    @Output
    private MgLogicalVariable variable;

    public MgBuildDeclarationTask(List<Part> parts) {
        super(parts);
    }

    public MgLogicalVariable getVariable() {
        return variable;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        checkCount(3);
        TypeName typeName = get(TypeName.class, 0);
        Operator operator = get(Operator.class, 1);
        ObjectName objectName = get(ObjectName.class, 2);
        MgLogicalDatatype datatype = createDatatype(typeName.getText(), operator.getText());

        if(datatype == null){
            throw new LanguageException("Expected '&', '&?', '$', '$?'. Got '" + operator.getText() + "'.");
        }

        variable = new MgLogicalVariable(objectName.getText(), datatype);
    }

    public static MgLogicalDatatype createDatatype(ReadableText typeName, ReadableText operators){
        MgLogicalDatatype.Storage storage;
        MgLogicalDatatype.Requirement requirement;
        switch (operators.toString()){
            case "&":
                storage = MgLogicalDatatype.Storage.INDIRECT;
                requirement = MgLogicalDatatype.Requirement.MANDATORY;
                break;
            case "&?":
                storage = MgLogicalDatatype.Storage.INDIRECT;
                requirement = MgLogicalDatatype.Requirement.OPTIONAL;
                break;
            case "$":
                storage = MgLogicalDatatype.Storage.DIRECT;
                requirement = MgLogicalDatatype.Requirement.MANDATORY;
                break;
            case "$?":
                storage = MgLogicalDatatype.Storage.DIRECT;
                requirement = MgLogicalDatatype.Requirement.OPTIONAL;
                break;
            default:
                return null;
        }
        return new MgLogicalDatatype(typeName, storage, requirement);
    }
}
