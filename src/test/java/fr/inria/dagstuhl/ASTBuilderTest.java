package fr.inria.dagstuhl;

import gumtree.spoon.diff.Diff;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 30/01/18
 */
public class ASTBuilderTest {

    @Test
    public void test() throws Exception {

        /*
            The ASTBuilder should build two diff one for each PRs.
         */

        final Diff[] build = ASTBuilder.build("src/test/resources/_master",
                "src/test/resources/_pr-1",
                "src/test/resources/_pr-2");

        assertEquals(2, build.length);
    }
}
