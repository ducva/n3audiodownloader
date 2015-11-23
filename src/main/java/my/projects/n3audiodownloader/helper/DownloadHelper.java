package my.projects.n3audiodownloader.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import my.projects.n3audiodownloader.exception.DownloadException;
import my.projects.n3audiodownloader.exception.ExtractingException;
import my.projects.n3audiodownloader.model.AudioLink;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 */
public class DownloadHelper {


    public static String extractBaseUrl(String homePageUrl) throws ExtractingException{
        try {
            URL url = new URL(homePageUrl);
            return url.getProtocol() + "://" + url.getAuthority();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new ExtractingException("invalid URL format", homePageUrl);
        }
    }
    public static List<String> extractSubPageLinks(String homePageUrl) throws ExtractingException{
        String baseUrl = DownloadHelper.extractBaseUrl(homePageUrl);

        List<String> results = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(homePageUrl).get();
            Elements newsHeadlines = doc.select("td[headers=categorylist_header_title] a");
            if(CollectionUtils.isEmpty(newsHeadlines)){
                throw new ExtractingException("Subpages links not found. Check the homePage Url", homePageUrl);
            }

            for(Element el : newsHeadlines){
                results.add(baseUrl + el.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExtractingException(e.getMessage(), homePageUrl);
        }
        return results;
    }

    public static List<AudioLink> extractAudioLinks(String subpage) throws ExtractingException{
        String baseUrl = DownloadHelper.extractBaseUrl(subpage);
        List<AudioLink> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(subpage).get();
            Elements audioLinks = doc.select("audio source");

            if(CollectionUtils.isEmpty(audioLinks)){
                throw new ExtractingException("No Audio links found!", subpage);
            }

            for(Element el : audioLinks){
                AudioLink link = new AudioLink(baseUrl + el.attr("src"), el.attr("type"));
                result.add(link);
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExtractingException("Cannot read subpage. Check the subpage URL", subpage);
        }
    }

    public static boolean downloadLink(AudioLink audioLink, String outputDir) throws DownloadException{

        try {
            URLConnection conn = new URL(audioLink.getUrl()).openConnection();

            InputStream is = conn.getInputStream();
            OutputStream outstream = new FileOutputStream(new File(outputDir + audioLink.getFileName()));
            byte[] buffer = new byte[4096];
            int len;
            while ((len = is.read(buffer)) > 0) {
                outstream.write(buffer, 0, len);
            }
            outstream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String extractResourceName(String audioLink) throws ExtractingException{
            File file = new File(audioLink);
            String fileName = file.getName();
            return fileName;
    }
}
