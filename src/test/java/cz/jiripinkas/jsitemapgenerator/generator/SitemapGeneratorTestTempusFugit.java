package cz.jiripinkas.jsitemapgenerator.generator;

import cz.jiripinkas.jsitemapgenerator.*;
import cz.jiripinkas.jsitemapgenerator.exception.WebmasterToolsException;
import cz.jiripinkas.jsitemapgenerator.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class SitemapGeneratorTestTempusFugit {

	private SitemapGenerator sitemapGenerator;
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

	@Test
	@Concurrent(count = 2)
	@Repeating(repetition = 100)
	public void testConstructImage() {
		sitemapGenerator = SitemapGenerator.of("http://www.javavids.com");
		sitemapGenerator.addPage(WebPage.builder().name("index.php").priority(1.0).changeFreqNever().lastMod(LocalDateTime.of(2019, 1, 1, 0, 0)).build());
		sitemapGenerator.addPage(WebPage.builder().name("latest.php").build());
		sitemapGenerator.addPage(WebPage.builder().name("contact.php").build());
		Image image = new Image();
		image.setLoc("http://example.com/image");
		String actualImageString = sitemapGenerator.constructImage(image);
		String expectedImageString = "<image:image>\n<image:loc>http://example.com/image</image:loc>\n</image:image>\n";
		assertEquals(expectedImageString, actualImageString);
	}

}
