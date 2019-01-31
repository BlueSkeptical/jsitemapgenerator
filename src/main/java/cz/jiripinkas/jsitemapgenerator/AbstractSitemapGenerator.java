package cz.jiripinkas.jsitemapgenerator;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;

import cz.jiripinkas.jsitemapgenerator.exception.GWTException;

public abstract class AbstractSitemapGenerator <T extends AbstractGenerator> extends AbstractGenerator <T> {

	protected W3CDateFormat dateFormat = new W3CDateFormat();

	public AbstractSitemapGenerator(String baseUrl) {
		super(baseUrl);
	}

	public abstract String[] toStringArray();

	/**
	 * Construct sitemap into single String
	 * 
	 * @return sitemap
	 * @deprecated Use {@link #toString()} instead
	 */
	@Deprecated
	public String constructSitemapString() {
		return toString();
	}

	/**
	 * Construct sitemap into single String
	 *
	 * @return sitemap
	 */
	public String toString() {
		String[] sitemapArray = toStringArray();
		StringBuilder result = new StringBuilder();
		for (String line : sitemapArray) {
			result.append(line);
		}
		return result.toString();
	}

	private ByteArrayOutputStream gzipIt(InputStream inputStream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		try {
			try(GZIPOutputStream gzos = new GZIPOutputStream(outputStream);
				InputStream in = inputStream) {
				int len;
				while ((len = in.read(buffer)) > 0) {
					gzos.write(buffer, 0, len);
				}
			}
		} catch (IOException ex) {
			throw new RuntimeException("Cannot perform gzip", ex);
		}
		return outputStream;
	}

	/**
	 * Construct sitemap into gzipped file
	 *
	 * @return byte array
	 * @deprecated Use {@link #toGzipByteArray()} instead
	 */
	@Deprecated
	public byte[] constructSitemapGzip() {
		return toGzipByteArray();
	}

	/**
	 * Construct sitemap into gzipped file
	 *
	 * @return byte array
	 */
	public byte[] toGzipByteArray() {
		String sitemap = this.toString();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(sitemap.getBytes(StandardCharsets.UTF_8));
		ByteArrayOutputStream outputStream = gzipIt(inputStream);
		return outputStream.toByteArray();
	}

	/**
	 * Save sitemap to output file
	 * 
	 * @param file
	 *            Output file
	 * @param sitemap
	 *            Sitemap as array of Strings (created by constructSitemap()
	 *            method)
	 * @throws IOException
	 *             when error
	 * @deprecated Use {@link #toFile(Path)} instead
	 */
	@Deprecated
	public void saveSitemap(File file, String[] sitemap) throws IOException {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (String string : sitemap) {
				writer.write(string);
			}
		}
	}

	/**
	 * Construct and save sitemap to output file
	 *
	 * @param file
	 *            Output file
	 * @throws IOException
	 *             when error
	 */
	public void toFile(File file) throws IOException {
		String[] sitemap = toStringArray();
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (String string : sitemap) {
				writer.write(string);
			}
		}
	}

	/**
	 * Construct and save sitemap to output file
	 *
	 * @param path
	 *            Output file
	 * @throws IOException
	 *             when error
	 */
	public void toFile(Path path) throws IOException {
		toFile(path.toFile());
	}

	/**
	 * Construct and save sitemap to output file
	 *
	 * @param file
	 *            Output file
	 * @throws IOException
	 *             when error
	 * @deprecated Use {@link #toFile(File)} instead
	 */
	@Deprecated
	public void constructAndSaveSitemap(File file) throws IOException {
		toFile(file);
	}

	/**
	 * Construct and save sitemap to output file
	 *
	 * @param path
	 *            Output file
	 * @throws IOException
	 *             when error
	 * @deprecated Use {@link #toFile(Path)} instead
	 */
	@Deprecated
	public void constructAndSaveSitemap(Path path) throws IOException {
		toFile(path);
	}

	/**
	 * Ping Google that sitemap has changed. Will call this URL:
	 * https://www.google.com/ping?sitemap=URL_Encoded_sitemapUrl
	 * 
	 * @param sitemapUrl
	 *            sitemap url
	 */
	public void pingGoogle(String sitemapUrl) {
		ping("https://www.google.com/ping?sitemap=", sitemapUrl, "Google");
	}

	/**
	 * Ping Bing that sitemap has changed. Will call this URL:
	 * http://www.bing.com/ping?sitemap=URL_Encoded_sitemapUrl
	 * 
	 * @param sitemapUrl
	 *            sitemap url
	 * 
	 */
	public void pingBing(String sitemapUrl) {
		ping("http://www.bing.com/ping?sitemap=", sitemapUrl, "Bing");
	}

	private void ping(String resourceUrl, String sitemapUrl, String serviceName) {
		try {
			String pingUrl = resourceUrl + URLEncoder.encode(sitemapUrl, "UTF-8");
			// ping Bing
			int returnCode = HttpClientUtil.get(pingUrl);
			if (returnCode != 200) {
				throw new GWTException(serviceName + " could not be informed about new sitemap!");
			}
		} catch (Exception ex) {
			throw new GWTException(serviceName + " could not be informed about new sitemap!");
		}
	}

	/**
	 * Ping Google that sitemap has changed. Sitemap must be on this location:
	 * baseUrl/sitemap.xml (for example http://www.javavids.com/sitemap.xml)
	 */
	public void pingGoogle() {
		pingGoogle(baseUrl + "sitemap.xml");
	}

	/**
	 * Ping Google that sitemap has changed. Sitemap must be on this location:
	 * baseUrl/sitemap.xml (for example http://www.javavids.com/sitemap.xml)
	 */
	public void pingBing() {
		pingBing(baseUrl + "sitemap.xml");
	}

	/**
	 * Escape special characters in XML
	 * @param url Url to be escaped
	 * @return Escaped url
	 */
	protected String escapeXmlSpecialCharacters(String url) {
		// https://stackoverflow.com/questions/1091945/what-characters-do-i-need-to-escape-in-xml-documents
		return url
				.replace("&", "&amp;") // must be escaped first!!!
				.replace("\"", "&quot;")
				.replace("'", "&apos;")
				.replace("<", "&lt;")
				.replace(">", "&gt;");
	}

}
