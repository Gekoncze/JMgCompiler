package cz.mg.compiler.tasks.mg.compiler;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.file.Document;
import cz.mg.language.entities.file.File;
import cz.mg.language.entities.file.Folder;
import cz.mg.language.entities.mg.logical.components.MgLogicalLocation;


public class MgCompileFolderLogicTask extends MgCompileFileLogicTask {
    @Input
    private final Folder folder;

    @Output
    private MgLogicalLocation location;

    public MgCompileFolderLogicTask(Folder folder) {
        this.folder = folder;
    }

    public MgLogicalLocation getLocation() {
        return location;
    }

    @Override
    protected void onRun() {
        location = new MgLogicalLocation(folder.getName());
        for(File file : folder.getFiles()){
            if(file instanceof Folder){
                MgCompileFolderLogicTask compileFolderTask = new MgCompileFolderLogicTask((Folder) file);
                compileFolderTask.run();
                location.getComponents().addLast(compileFolderTask.getLocation());
            } else if(file instanceof Document){
                MgCompileDocumentLogicTask compileDocumentTask = new MgCompileDocumentLogicTask((Document) file);
                compileDocumentTask.run();
                location.getComponents().addLast(compileDocumentTask.getComponent());
            } else {
                throw new LanguageException("Unsupported file type " + file.getClass().getSimpleName() + " for file " + file.getName() + ".");
            }
        }
    }
}
