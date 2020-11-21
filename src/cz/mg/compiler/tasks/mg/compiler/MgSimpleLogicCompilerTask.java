package cz.mg.compiler.tasks.mg.compiler;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedLocation;
import cz.mg.compiler.tasks.input.file.MgLoadFolderTreeTask;


public class MgSimpleLogicCompilerTask extends MgCompilerTask {
    @Input
    private final ReadableText sourceRootPath;

    @Output
    private MgUnresolvedLocation root;

    public MgSimpleLogicCompilerTask(ReadableText sourceRootPath) {
        this.sourceRootPath = sourceRootPath;
    }

    public MgUnresolvedLocation getRoot() {
        return root;
    }

    @Override
    protected void onRun() {
        MgLoadFolderTreeTask loadFolderTreeTask = new MgLoadFolderTreeTask(sourceRootPath);
        loadFolderTreeTask.run();

        MgCompileFolderLogicTask compileFolderTask = new MgCompileFolderLogicTask(loadFolderTreeTask.getFolder());
        compileFolderTask.run();

        root = compileFolderTask.getLocation();
    }
}
