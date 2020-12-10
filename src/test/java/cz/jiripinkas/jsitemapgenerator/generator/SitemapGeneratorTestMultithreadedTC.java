package cz.jiripinkas.jsitemapgenerator.generator;

import cz.jiripinkas.jsitemapgenerator.*;
import cz.jiripinkas.jsitemapgenerator.exception.WebmasterToolsException;
import cz.jiripinkas.jsitemapgenerator.util.TestUtil;
import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class SitemapGeneratorTestMultithreadedTC extends MultithreadedTestCase {

	private SitemapGenerator sitemapGenerator;

	void thread1() {
		sitemapGenerator = SitemapGenerator.of("http://www.javavids.com");
		sitemapGenerator.addPage(WebPage.builder().name("index.php").priority(1.0).changeFreqNever().lastMod(LocalDateTime.of(2019, 1, 1, 0, 0)).build());
		sitemapGenerator.addPage(WebPage.builder().name("latest.php").build());
		sitemapGenerator.addPage(WebPage.builder().name("contact.php").build());
		Image image = new Image();
		image.setLoc("http://example.com/image");
		String actualImageString = sitemapGenerator.constructImage(image);
		String expectedImageString = "<image:image>\n<image:loc>http://example.com/image</image:loc>\n</image:image>\n";
		Assert.assertEquals(expectedImageString, actualImageString);
	}

	void thread2() {
		sitemapGenerator = SitemapGenerator.of("http://www.javavids.com");
		sitemapGenerator.addPage(WebPage.builder().name("index.php").priority(1.0).changeFreqNever().lastMod(LocalDateTime.of(2019, 1, 1, 0, 0)).build());
		sitemapGenerator.addPage(WebPage.builder().name("latest.php").build());
		sitemapGenerator.addPage(WebPage.builder().name("contact.php").build());
		Image image = new Image();
		image.setLoc("http://example.com/image");
		String actualImageString = sitemapGenerator.constructImage(image);
		String expectedImageString = "<image:image>\n<image:loc>http://example.com/image</image:loc>\n</image:image>\n";
		Assert.assertEquals(expectedImageString, actualImageString);
	}
	
	@Test
    public void testConstructImage() throws Throwable {
    	TestFramework.runManyTimes(new SitemapGeneratorTestMultithreadedTC(), 100);
    }
}
