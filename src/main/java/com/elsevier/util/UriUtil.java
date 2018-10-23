package com.elsevier.util;

public class UriUtil {

    /**
     * @param userName
     * @return
     */
    public static String getRepositoryUri(String userName) {
        String url = "/users/" + userName + "/repos";
        return url;
    }

    /**
     * @param owner
     * @param repoName
     * @return
     */
    public static String getCollaboratorUri(String owner, String repoName) {
        String url = "/repos/" + owner + "/" + repoName + "/collaborators";
        return url;
    }

    /**
     * @param owner
     * @param repoName
     * @return
     */
    public static String getContributors(String owner, String repoName) {
        String url = "/repos/" + owner + "/" + repoName + "/contributors";
        return url;
    }
}
