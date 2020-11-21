package cz.mg.compiler.tasks.mg.resolver.link;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.search.TypeSearch;
import cz.mg.language.entities.mg.unresolved.parts.MgUnresolvedDatatype;
import cz.mg.language.entities.mg.runtime.parts.MgDatatype;
import cz.mg.compiler.tasks.mg.resolver.MgPostponeResolveTask;


public class MgResolveVariableDatatypeTask extends MgPostponeResolveTask {
    @Input
    private final MgUnresolvedDatatype logicalDatatype;

    @Output
    private MgDatatype datatype;

    public MgResolveVariableDatatypeTask(Context context, MgUnresolvedDatatype logicalDatatype) {
        super(context);
        this.logicalDatatype = logicalDatatype;
    }

    public MgDatatype getDatatype() {
        return datatype;
    }

    @Override
    protected void onRun() {
        cz.mg.compiler.tasks.mg.resolver.search.TypeSearch search = new TypeSearch(getContext().getGlobalSource(), logicalDatatype.getName());
        this.datatype = new MgDatatype(
            search.find(),
            MgDatatype.Storage.valueOf(logicalDatatype.getStorage().name()),
            MgDatatype.Requirement.valueOf(logicalDatatype.getRequirement().name())
        );
    }
}
