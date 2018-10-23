package com.elsevier.util;

public class UriUtil {

    /** Get public repository Uri
     * @param userName
     * @return String
     */
    public static String getRepositoryUri(String userName) {
        String url = "/users/" + userName + "/repos";
        return url;
    }

    /**
     * Return collaborators Uri
     * @param owner
     * @param repoName
     * @return String
     */
    public static String getCollaboratorUri(String owner, String repoName) {
        String url = "/repos/" + owner + "/" + repoName + "/collaborators";
        return url;
    }

    /**
     * Return Contributors Uri
     * @param owner
     * @param repoName
     * @return String
     */
    public static String getContributorsUri(String owner, String repoName) {
        String url = "/repos/" + owner + "/" + repoName + "/contributors";
        return url;
    }
}
