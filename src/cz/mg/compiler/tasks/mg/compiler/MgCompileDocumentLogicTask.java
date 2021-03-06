package cz.mg.compiler.tasks.mg.compiler;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.parser.MgParsePageTask;
import cz.mg.language.entities.file.Document;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedComponent;
import cz.mg.compiler.tasks.input.file.MgLoadTextFileTask;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildRootTask;
import cz.mg.compiler.tasks.mg.composer.MgComposeBlocksTask;


public class MgCompileDocumentLogicTask extends MgCompileFileLogicTask {
    @Input
    private final Document document;

    @Output
    private MgUnresolvedComponent component;

    public MgCompileDocumentLogicTask(Document document) {
        this.document = document;
    }

    public MgUnresolvedComponent getComponent() {
        return component;
    }

    @Override
    protected void onRun() {
        MgLoadTextFileTask loadTextFileTask = new MgLoadTextFileTask(document.getFile());
        loadTextFileTask.run();

        MgParsePageTask parsePageTask = new MgParsePageTask(loadTextFileTask.getContent());
        parsePageTask.run();

        MgComposeBlocksTask parseBlocksTask = new MgComposeBlocksTask(parsePageTask.getPage());
        parseBlocksTask.run();

        MgBuildRootTask buildRootTask = new MgBuildRootTask(parseBlocksTask.getRoot());
        buildRootTask.run();

        component = buildRootTask.getComponent();
    }
}
