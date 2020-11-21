package cz.mg.compiler.tasks.writers;

import cz.mg.collections.list.List;
import cz.mg.language.entities.text.plain.Page;


public interface PageWriterTask {
    List<Page> getPages();
}
