package cz.mg.compiler.tasks.mg.resolver.search;

import cz.mg.collections.Clump;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.Usage;


public interface Source {
    public Clump<Usage> getComponents();
}
