package cz.mg.compiler.tasks.input.file;

import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.file.Document;
import cz.mg.language.entities.file.Folder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class MgLoadFolderTreeTask extends MgFileInputTask {
    @Input
    private final ReadableText sourceRootDirectoryPath;

    @Output
    private Folder folder;

    public MgLoadFolderTreeTask(ReadableText sourceRootDirectoryPath) {
        this.sourceRootDirectoryPath = sourceRootDirectoryPath;
    }

    public Folder getFolder() {
        return folder;
    }

    @Override
    protected void onRun() {
        File file = new File(sourceRootDirectoryPath.toString());
        if(!file.exists()){
            throw new LanguageException("File " + generateFileMessage(file) + " does not exist.");
        }
        if(!file.isDirectory()){
            throw new LanguageException("File " + generateFileMessage(file) + " is not a directory.");
        }
        List<File> path = new List<>();
        folder = createFolder(file, path);
    }

    private Folder createFolder(File directory, List<File> path){
        checkCycles(directory, path);
        Folder folder = new Folder(directory);
        path.addLast(directory);
        for(File file : getAllFiles(directory)){
            if(file.isDirectory()){
                folder.getFiles().addLast(createFolder(file, path));
            } else {
                folder.getFiles().addLast(createDocument(file));
            }
        }
        path.removeLast();
        return folder;
    }

    private Document createDocument(File file){
        return new Document(file);
    }

    private void checkCycles(File directory, List<File> path){
        try {
            for(File pathDirectory : path){
                if(Files.isSameFile(directory.toPath(), pathDirectory.toPath())){
                    throw new LanguageException("Symlink cycle detected. Directories " + generateFileMessage(directory) + " and " + generateFileMessage(pathDirectory) + " are the same.");
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static File[] getAllFiles(File directory){
        File[] files = directory.listFiles();
        if(files != null){
            return files;
        } else {
            return new File[0];
        }
    }

    private String generateFileMessage(File file){
        return "[" + sourceRootDirectoryPath + "] aka [" + file.getAbsolutePath() + "]";
    }
}
