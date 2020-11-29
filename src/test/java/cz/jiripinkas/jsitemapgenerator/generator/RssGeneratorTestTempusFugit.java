package cz.jiripinkas.jsitemapgenerator.generator;

import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Rule;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RssGeneratorTestTempusFugit {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

    private RssGenerator rssGenerator;

    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void testConstructRssEmptyItemsShouldThrowException() {
    	//System.out.println(Thread.currentThread().getId());
    	rssGenerator = RssGenerator.of("http://www.topjavablogs.com", "Top Java Blogs", "News from Java community");
        try {
            String rss = rssGenerator.toString();
            ByteArrayInputStream xml = new ByteArrayInputStream(rss.getBytes(StandardCharsets.UTF_8));
            TestUtil.testSitemapXsd(xml, new File("src/test/resources/rss20.xsd"));
        } catch (Exception e) {
            assertEquals("cvc-complex-type.2.4.b: The content of element 'channel' is not complete. One of '{image, textInput, skipHours, skipDays, item}' is expected.", e.getMessage());
        }
    }

}
