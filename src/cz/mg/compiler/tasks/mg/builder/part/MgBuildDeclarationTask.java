package cz.mg.compiler.tasks.mg.builder.part;

import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedVariable;
import cz.mg.language.entities.mg.unresolved.parts.MgUnresolvedDatatype;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.leaves.Operator;
import cz.mg.language.entities.text.structured.parts.leaves.names.ObjectName;
import cz.mg.language.entities.text.structured.parts.leaves.names.TypeName;


public class MgBuildDeclarationTask extends MgBuildPartTask {
    @Output
    private MgUnresolvedVariable variable;

    public MgBuildDeclarationTask(List<Part> parts) {
        super(parts);
    }

    public MgUnresolvedVariable getVariable() {
        return variable;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        checkCount(3);
        TypeName typeName = get(TypeName.class, 0);
        Operator operator = get(Operator.class, 1);
        ObjectName objectName = get(ObjectName.class, 2);
        MgUnresolvedDatatype datatype = createDatatype(typeName.getText(), operator.getText());

        if(datatype == null){
            throw new LanguageException("Expected '&', '&?', '$', '$?'. Got '" + operator.getText() + "'.");
        }

        variable = new MgUnresolvedVariable(objectName.getText(), datatype);
    }

    public static MgUnresolvedDatatype createDatatype(ReadableText typeName, ReadableText operators){
        MgUnresolvedDatatype.Storage storage;
        MgUnresolvedDatatype.Requirement requirement;
        switch (operators.toString()){
            case "&":
                storage = MgUnresolvedDatatype.Storage.INDIRECT;
                requirement = MgUnresolvedDatatype.Requirement.MANDATORY;
                break;
            case "&?":
                storage = MgUnresolvedDatatype.Storage.INDIRECT;
                requirement = MgUnresolvedDatatype.Requirement.OPTIONAL;
                break;
            case "$":
                storage = MgUnresolvedDatatype.Storage.DIRECT;
                requirement = MgUnresolvedDatatype.Requirement.MANDATORY;
                break;
            case "$?":
                storage = MgUnresolvedDatatype.Storage.DIRECT;
                requirement = MgUnresolvedDatatype.Requirement.OPTIONAL;
                break;
            default:
                return null;
        }
        return new MgUnresolvedDatatype(typeName, storage, requirement);
    }
}
