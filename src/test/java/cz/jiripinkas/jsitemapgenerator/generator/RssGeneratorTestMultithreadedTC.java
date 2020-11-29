package cz.jiripinkas.jsitemapgenerator.generator;

import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.util.TestUtil;
import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RssGeneratorTestMultithreadedTC extends MultithreadedTestCase {

    private RssGenerator rssGenerator;

    void thread1() {
    	//System.out.println(Thread.currentThread().getId());
    	rssGenerator = RssGenerator.of("http://www.topjavablogs.com", "Top Java Blogs", "News from Java community");
        try {
            String rss = rssGenerator.toString();
            ByteArrayInputStream xml = new ByteArrayInputStream(rss.getBytes(StandardCharsets.UTF_8));
            TestUtil.testSitemapXsd(xml, new File("src/test/resources/rss20.xsd"));
        } catch (Exception e) {
        	Assertions.assertEquals("cvc-complex-type.2.4.b: The content of element 'channel' is not complete. One of '{image, textInput, skipHours, skipDays, item}' is expected.", e.getMessage());
        }
    }
    
    void thread2() {
    	rssGenerator = RssGenerator.of("http://www.topjavablogs.com", "Top Java Blogs", "News from Java community");
        try {
            String rss = rssGenerator.toString();
            ByteArrayInputStream xml = new ByteArrayInputStream(rss.getBytes(StandardCharsets.UTF_8));
            TestUtil.testSitemapXsd(xml, new File("src/test/resources/rss20.xsd"));
        } catch (Exception e) {
        	Assertions.assertEquals("cvc-complex-type.2.4.b: The content of element 'channel' is not complete. One of '{image, textInput, skipHours, skipDays, item}' is expected.", e.getMessage());
        }
    }
    
    @Test
    public void testConstructRssEmptyItemsShouldThrowException() throws Throwable {
    	TestFramework.runManyTimes(new RssGeneratorTestMultithreadedTC(), 100);
    }

}
