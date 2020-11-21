package cz.mg.compiler.tasks.mg.compiler;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.parser.MgParsePageTask;
import cz.mg.language.entities.file.Document;
import cz.mg.language.entities.mg.logical.components.MgLogicalComponent;
import cz.mg.compiler.tasks.input.file.MgLoadTextFileTask;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildRootTask;
import cz.mg.compiler.tasks.mg.composer.MgComposeBlocksTask;


public class MgCompileDocumentLogicTask extends MgCompileFileLogicTask {
    @Input
    private final Document document;

    @Output
    private MgLogicalComponent component;

    public MgCompileDocumentLogicTask(Document document) {
        this.document = document;
    }

    public MgLogicalComponent getComponent() {
        return component;
    }

    @Override
    protected void onRun() {
        MgLoadTextFileTask loadTextFileTask = new MgLoadTextFileTask(document.getFile());
        loadTextFileTask.run();

        cz.mg.compiler.tasks.mg.parser.MgParsePageTask parsePageTask = new MgParsePageTask(loadTextFileTask.getContent());
        parsePageTask.run();

        MgComposeBlocksTask parseBlocksTask = new MgComposeBlocksTask(parsePageTask.getPage());
        parseBlocksTask.run();

        MgBuildRootTask buildRootTask = new MgBuildRootTask(parseBlocksTask.getRoot());
        buildRootTask.run();

        component = buildRootTask.getComponent();
    }
}
