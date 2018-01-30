# Tavern

This a a toy project for tutorial on usage of **DSpot**.

There are two branches: `master` which is stable and `refactor` which contains regression bugs made during the refactoring of the method **sellItem** of **fr.inria.stamp.tavern.Seller**.

Ensure that on both version the existing test suite is passing:

```sh
mvn clean test
git checkout refactor
mvn clean test
git checkout master
```

First, run `dspot` using the **TestDataMutator**, and the default selector **PitMutantScoreSelector** with the following command:

```
java -jar dspot-1.0.2-jar-with-dependencies.jar --path-to-properties src/main/resources/tavern.properties --iteration 2 --test fr.inria.stamp.MainTest --cases test --amplifiers TestDataMutator --test-criterion PitMutantScoreSelector --verbose --path-pit-result mutations_results/original.csv
```

At the end of the execution, you should obtain something like:
```
======= REPORT =======
PitMutantScoreSelector: 
The original test suite kills 66 mutants
The amplification results with 7 new tests
it kills 48 more mutants
```

You can now copy paste the amplified from output to src/test/java using:

```
cp output/fr/inria/stamp/AmplMainTest.java src/test/java/fr/inria/stamp/AmplMainTest.java
```

then run tests:

```
mvn clean test
```

Eventually, checkout the refactor-branch and re-run maven test:

```
git checkout refactor
mvn clean test
```

You see a failing test. This test encodes the regression introduces during the refactoring.

### Details on the command and the customization of DSpot's execution:

* `--path-to-properties ../tavern/src/main/resources/tavern.properties` &#10137; Specifies the path the property file.
* `--iteration 2` &#10137; Number of time that the DSpot's main loop will be applied.
* `--test fr.inria.stamp.MainTest` &#10137; The test class **fr.inria.stamp.MainTest** (full qualified name) to be amplified.
* `--cases test` &#10137; The test method (test case) named **test** of the specified test class to be amplified.
* `--amplifiers TestDataMutator` &#10137; Class name of the amplifier used to modify input of the test.
* `--test-criterion PitMutantScoreSelector` &#10137; Class name of the selector used to filter which amplified test DSpot must keep.
* `--verbose` &#10137; enable the verbose mode of **DSpot**.
* `--path-pit-result mutations_results/original.csv` &#10137; use pre-computed mutation analysis of the original test suite.

You can find the property file to give as input to DSpot in `src/main/resources/`.
```
#relative path to the project root from dspot project
project=../tavern
#relative path to the source project from the project properties
src=src/main/java/
#relative path to the test source project from the project properties
testSrc=src/test/java
#java version used
javaVersion=8
#filter used to amplify specific test cases
filter=fr.inria.stamp.*
#path to the output folder
outputDirectory=output
#following properties are used by the ChangeDetectorSelector
configPath=src/main/resources/tavern.properties
folderPath=../tavern-refactor
```

### Mutation analysis results

You can find results of the mutations analysis in `mutations_results`:

* original.csv is the mutations analysis obtained used only the existing test.
* amplified.csv is the mutations analysis obtained used both the original and the amplified version of test.
* diff.csv is the list of new mutants killed by the amplified version.
* remain_alive.csv is the list of mutants that remains alive after the amplification.
