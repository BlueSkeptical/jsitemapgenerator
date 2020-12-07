package utcec;

import cz.jiripinkas.jsitemapgenerator.generator.RssGenerator;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class RssGeneratorTest {
    private Boolean ifEqualActor1, ifEqualActor2;
    private RssGenerator rssGenerator;

    public static void testSitemapXsd(InputStream sitemapXml, File xsd) throws SAXException, IOException {
		Source xmlFile = new StreamSource(sitemapXml);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsd);
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}

    public RssGeneratorTest() {
        rssGenerator = RssGenerator.of("http://www.topjavablogs.com", "Top Java Blogs", "News from Java community");
        ifEqualActor1 = true;
		ifEqualActor2 = true;
    }

    @Actor
	public void actor1() {
    	try {
            String rss = rssGenerator.toString();
            ByteArrayInputStream xml = new ByteArrayInputStream(rss.getBytes(StandardCharsets.UTF_8));
            testSitemapXsd(xml, new File("src/test/resources/rss20.xsd"));
        } catch (Exception e) {
            if ("cvc-complex-type.2.4.b: The content of element 'channel' is not complete. One of '{image, textInput, skipHours, skipDays, item}' is expected.".equals(e.getMessage())) {
                ifEqualActor1 = false;
            }
        }
	}

	@Actor
	public void actor2() {
        try {
            String rss = rssGenerator.toString();
            ByteArrayInputStream xml = new ByteArrayInputStream(rss.getBytes(StandardCharsets.UTF_8));
            testSitemapXsd(xml, new File("src/test/resources/rss20.xsd"));
        } catch (Exception e) {
            if ("cvc-complex-type.2.4.b: The content of element 'channel' is not complete. One of '{image, textInput, skipHours, skipDays, item}' is expected.".equals(e.getMessage())) {
                ifEqualActor2 = false;
            }
        }
	}

	@Arbiter
	public void arbiter(ZZ_Result r) {
		r.r1 = ifEqualActor1;
		r.r2 = ifEqualActor2;
	}
}

