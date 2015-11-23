package my.projects.n3audiodownloader.model;

import java.io.Serializable;

import my.projects.n3audiodownloader.exception.ExtractingException;
import my.projects.n3audiodownloader.helper.DownloadHelper;

/**
 *
 */
public class AudioLink implements Serializable{
    private String url;
    private String mimeType = "audio/mpeg";

    public AudioLink(String url){
        this.url = url;
    }

    public AudioLink(String url, String mimeType){
        this(url);
        this.mimeType = mimeType;
    }


    public String getUrl() {
        return url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFileName() {
        try {
            return DownloadHelper.extractResourceName(this.url);
        } catch (ExtractingException e) {
            throw new IllegalArgumentException("Invalid resource");
        }
    }
}
