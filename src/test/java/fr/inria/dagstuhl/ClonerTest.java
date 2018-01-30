package fr.inria.dagstuhl;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 30/01/18
 */
public class ClonerTest {

    @Before
    public void setUp() throws Exception {
        try {
            FileUtils.forceDelete(new File("target/trash"));
        } catch (Exception ignored) {

        }
    }

    @Test
    public void test() throws Exception {

        /*
            The clone should clone the 3 versions of the project from GH.
            The project is designed by its name: user/name
                example danglotb/tavern : user is danglotb, the project name is tavern
            The base branch, in which we want to merge must be the same for both prs
            The 2 prs that must be merge and has conflict are designed by their Number (see the int after '#')

            TODO /!\ this test required to be connected to the internet.
         */

        Cloner.get("danglotb/tavern/", 4, 5, "target/trash/");

        // TODO a little bit weak assumptions
        assertTrue(new File("target/trash/_master").exists());
        assertTrue(new File("target/trash/_pr-1").exists());
        assertTrue(new File("target/trash/_pr-2").exists());

    }

    @Test
    public void testShouldThrowException() throws Exception {

        /*
            The clone throw an exception in case of the two prs have not the same base
         */

        try {
            Cloner.get("danglotb/tavern/", 1, 5, "target/trash/");
            fail("Should have throw an exception because the prs have not the same base.");
        } catch (Exception e) {
            // expected
        }
    }

    @After
    public void tearDown() throws Exception {
       try {
            FileUtils.forceDelete(new File("target/trash"));
        } catch (Exception ignored) {

        }
    }
}
