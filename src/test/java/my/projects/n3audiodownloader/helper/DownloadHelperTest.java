package my.projects.n3audiodownloader.helper;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import my.projects.n3audiodownloader.model.AudioLink;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class DownloadHelperTest {
    String homePageUrl = "http://www.vnjpclub.com/luyen-tap/luyen-nghe/luyen-nghe-n3.html";

    @Test
    public void testExtractBaseUrl() throws Exception{
        String actual = DownloadHelper.extractBaseUrl(this.homePageUrl);
        Assert.assertEquals("http://www.vnjpclub.com", actual);
    }
    @Test
    public void testExtractSubPageLinks() throws Exception {
        List<String> actuals = DownloadHelper.extractSubPageLinks(this.homePageUrl);
        Assert.assertEquals(6, actuals.size());
        Assert.assertEquals("http://www.vnjpclub.com/luyen-tap/luyen-nghe/luyen-nghe-n3/bai-1.html", actuals.get(0));
    }

    @Test
    public void testExtractAudioLinks() throws Exception {
        String subpage = "http://www.vnjpclub.com/luyen-tap/luyen-nghe/luyen-nghe-n3/bai-1.html";
        List<AudioLink> actuals = DownloadHelper.extractAudioLinks(subpage);

        Assert.assertEquals(37, actuals.size());
        Assert.assertEquals("http://www.vnjpclub.com/Audio/FD6/N3_KIKU_A_01.mp3", actuals.get(0).getUrl());
        Assert.assertEquals("audio/mpeg", actuals.get(0).getMimeType());
    }

    @Test
    public void testDownloadLink() throws Exception {
        String audioLink = "http://www.vnjpclub.com/Audio/FD6/N3_KIKU_A_01.mp3";
        String outputFile = "/tmp/";
        AudioLink link = new AudioLink(audioLink, "audio/mpeg");
        DownloadHelper.downloadLink(link, outputFile);
        String outputFilePath = "/tmp/" + link.getFileName();
        Assert.assertTrue(new File(outputFilePath).exists());
    }

    @Test
    public void testExtractFilename() throws Exception{
        String audioLink = "http://www.vnjpclub.com/Audio/FD6/N3_KIKU_A_01.mp3";
        String actual = DownloadHelper.extractResourceName(audioLink);
        Assert.assertEquals("N3_KIKU_A_01.mp3", actual);
    }
}