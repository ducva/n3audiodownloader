package my.projects.n3audiodownloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import my.projects.n3audiodownloader.exception.DownloadException;
import my.projects.n3audiodownloader.exception.ExtractingException;
import my.projects.n3audiodownloader.helper.DownloadHelper;
import my.projects.n3audiodownloader.model.AudioLink;

public class Main {
    public static void main(String[] args){
        String homePageUrl = "http://www.vnjpclub.com/luyen-tap/luyen-nghe/luyen-nghe-n3.html";
        List<String> subPageLinks = new ArrayList<>();
        try {
            subPageLinks = DownloadHelper.extractSubPageLinks(homePageUrl);
        }
        catch(ExtractingException e){
            throw new RuntimeException(e);
        }
        if(subPageLinks.isEmpty()){
           System.out.println("No Subpages Found");
        }

        List<AudioLink> audioLinks = new ArrayList<>();

        for(String subpage : subPageLinks){
            try {
                audioLinks.addAll(DownloadHelper.extractAudioLinks(subpage));
            }
            catch(ExtractingException e){
                throw new RuntimeException(e);
            }

        }

        if(audioLinks.isEmpty()){
            System.out.println("No Links found");
        }

        String outputDir = "/tmp/N3/";
        for(AudioLink audioLink : audioLinks){
            try{
                DownloadHelper.downloadLink(audioLink, outputDir);
            }
            catch(DownloadException e){
                throw new RuntimeException(e);
            }
        }

    }
}
