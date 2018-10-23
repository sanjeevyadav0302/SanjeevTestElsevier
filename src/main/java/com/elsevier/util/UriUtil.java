package com.elsevier.util;

public class UriUtil {

    private static final String USERS = "users";
    private static final String REPOS = "repos";
    private static final String COLLABORATORS = "collaborators";
    private static final String CONTRIBUTORS = "contributors";
    private static final String SLASH = "/";


    /**
     * Get public repository Uri
     *
     * @param userName
     * @return String
     */
    public static String getRepositoryUri(String userName) {
        String repositoryUri = SLASH + USERS + SLASH + userName + SLASH + REPOS;
        return repositoryUri;
    }

    /**
     * Return collaborators Uri
     *
     * @param owner
     * @param repoName
     * @return String
     */
    public static String getCollaboratorUri(String owner, String repoName) {
        String collaboratorsUri = SLASH + REPOS + SLASH + owner + SLASH + repoName + SLASH + COLLABORATORS;
        return collaboratorsUri;
    }

    /**
     * Return Contributors Uri
     *
     * @param owner
     * @param repoName
     * @return String
     */
    public static String getContributorsUri(String owner, String repoName) {
        String contributorsUri = SLASH + REPOS + SLASH + owner + SLASH + repoName + SLASH + CONTRIBUTORS;
        return contributorsUri;
    }
}
