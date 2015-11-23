package my.projects.n3audiodownloader.exception;

/**
 *
 */
public class ExtractingException extends Exception{
    private String homePageUrl;
    public ExtractingException(String message, String homePageUrl) {
        super(message);
        this.homePageUrl = homePageUrl;
    }

    public String getHomePageUrl(){
        return this.homePageUrl;
    }
}
