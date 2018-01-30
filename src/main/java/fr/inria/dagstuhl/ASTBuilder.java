package fr.inria.dagstuhl;

import gumtree.spoon.AstComparator;
import gumtree.spoon.diff.Diff;

import java.io.File;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 30/01/18
 */
public class ASTBuilder {

        public static Diff[] build(String pathToMaster,
                                   String pathToPr1,
                                   String pathToPr2) {
            try {
                final AstComparator astComparator = new AstComparator();
                final Diff diffPr1 = astComparator.compare(new File(pathToMaster), new File(pathToPr1));
                final Diff diffPr2 = astComparator.compare(new File(pathToMaster), new File(pathToPr2));
                return new Diff[]{diffPr1, diffPr2};
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }



}
