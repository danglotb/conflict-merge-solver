package fr.inria.dagstuhl;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 30/01/18
 */
public class Cloner {

    private static void resetHard(String sha, File output) {
        try {
            Git.open(output)
                    .reset()
                    .setMode(ResetCommand.ResetType.HARD)
                    .setRef(sha)
                    .call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void clone(String uri, String branch, File output) {
        try {
            Git.cloneRepository()
                    .setURI(uri)
                    .setDirectory(output)
                    .setBranch(branch)
                    .call();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    public static void get(String projectName,
                           int idPr1,
                           int idPr2,
                           String baseOutput) throws IOException {
        final GitHub gitHub = GitHub.connectAnonymously();
        final GHRepository repository = gitHub.getRepository(projectName);
        final List<GHPullRequest> openedPullRequests =
                repository.getPullRequests(GHIssueState.OPEN)
                        .stream()
                        .filter(ghPullRequest ->
                                ghPullRequest.getNumber() == idPr1 ||
                                        ghPullRequest.getNumber() == idPr2
                        ).collect(Collectors.toList());

        if (! openedPullRequests.get(0).getBase().getRef().equals(
                openedPullRequests.get(1).getBase().getRef())) {
            throw new RuntimeException("The base of the two prs must be the same");
        }
        clone(openedPullRequests.get(0).getBase().getRepository().getGitTransportUrl(),
                openedPullRequests.get(0).getBase().getRef(),
                new File(baseOutput + "_" + openedPullRequests.get(0).getBase().getRef()));
        openedPullRequests.forEach(ghPullRequest -> {
                    clone(ghPullRequest.getHead().getRepository().getGitTransportUrl(),
                            ghPullRequest.getHead().getRef(),
                            new File(baseOutput + "_" + ghPullRequest.getHead().getRef()));
                    resetHard(ghPullRequest.getHead().getSha(),
                            new File(baseOutput + "_" + ghPullRequest.getHead().getRef()));
                }
        );
    }

}
