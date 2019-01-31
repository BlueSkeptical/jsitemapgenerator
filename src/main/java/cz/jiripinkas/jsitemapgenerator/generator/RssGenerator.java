package cz.jiripinkas.jsitemapgenerator.generator;

import cz.jiripinkas.jsitemapgenerator.AbstractGenerator;
import cz.jiripinkas.jsitemapgenerator.WebPage;

import java.text.SimpleDateFormat;
import java.util.*;

public class RssGenerator extends AbstractGenerator<RssGenerator> {

    private static final String DATE_PATTERN = "EEE, d MMM yyyy HH:mm:ss";

    private String webTitle;

    private String webDescription;

    /**
     * Create RssGenerator
     *
     * @deprecated use {@link #of(String, boolean, String, String)}
     * @param baseUrl        Base URL
     * @param root           If Base URL is root (for example http://www.javavids.com or if
     *                       it's some path like http://www.javalibs.com/blog)
     * @param webTitle       Web title
     * @param webDescription Web description
     */
    @Deprecated
    public RssGenerator(String baseUrl, boolean root, String webTitle, String webDescription) {
        super(baseUrl, root);
        this.webTitle = webTitle;
        this.webDescription = webDescription;
    }

    /**
     * Create RssGenerator. Root = true.
     *
     * @deprecated use {@link #of(String, String, String)}
     * @param baseUrl        Base URL
     * @param webTitle       Web title
     * @param webDescription Web description
     */
    @Deprecated
    public RssGenerator(String baseUrl, String webTitle, String webDescription) {
        super(baseUrl);
        this.webTitle = webTitle;
        this.webDescription = webDescription;
    }

    /**
     * Helper method to create an instance of SitemapGenerator
     * @param baseUrl Base URL
     * @param root If Base URL is root (for example http://www.javavids.com or if
     *             it's some path like http://www.javalibs.com/blog)
     * @param webTitle Web title
     * @param webDescription Web description
     * @return Instance of RssGenerator
     */
    public static RssGenerator of(String baseUrl, boolean root, String webTitle, String webDescription) {
        return new RssGenerator(baseUrl, root, webTitle, webDescription);
    }

    /**
     * Helper method to create an instance of SitemapGenerator
     * @param baseUrl Base URL
     * @param root If Base URL is root (for example http://www.javavids.com or if
     *             it's some path like http://www.javalibs.com/blog)
     * @return Instance of RssGenerator
     */
    public static RssGenerator of(String baseUrl, boolean root) {
        return new RssGenerator(baseUrl, root, null, null);
    }

    /**
     * Helper method to create an instance of SitemapGenerator
     * @param baseUrl Base URL
     * @param webTitle Web title
     * @param webDescription Web description
     * @return Instance of RssGenerator
     */
    public static RssGenerator of(String baseUrl, String webTitle, String webDescription) {
        return new RssGenerator(baseUrl, webTitle, webDescription);
    }

    /**
     * Helper method to create an instance of SitemapGenerator
     * @param baseUrl Base URL
     * @return Instance of RssGenerator
     */
    public static RssGenerator of(String baseUrl) {
        return new RssGenerator(baseUrl, null, null);
    }

    /**
     * Set Web title
     * @param webTitle Web title
     * @return this
     */
    public RssGenerator webTitle(String webTitle) {
        this.webTitle = webTitle;
        return this;
    }

    /**
     * Set Web description
     * @param webDescription Web description
     * @return this
     */
    public RssGenerator webDescription(String webDescription) {
        this.webDescription = webDescription;
        return this;
    }

    /**
     * This will construct RSS from web pages. Web pages are sorted using
     * lastMod in descending order (latest is first)
     *
     * @return Constructed RSS
     * @deprecated Use {@link #toString()} instead
     */
    @Deprecated
    public String constructRss() {
        return this.toString();
    }

    /**
     * This will construct RSS from web pages. Web pages are sorted using
     * lastMod in descending order (latest is first)
     *
     * @return Constructed RSS
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "\n")
                .append("<rss version=\"2.0\">" + "\n")
                .append("<channel>" + "\n")
                .append("<title>")
                .append(webTitle)
                .append("</title>" + "\n")
                .append("<link>")
                .append(baseUrl)
                .append("</link>" + "\n")
                .append("<description>")
                .append(webDescription)
                .append("</description>" + "\n");

        List<WebPage> webPages = new ArrayList<>(urls.values());

        webPages.sort(Comparator.comparing(WebPage::getLastMod).reversed());

        Date latestDate = new Date();
        if (!webPages.isEmpty()) {
            latestDate = webPages.get(0).getLastMod();
        }

        builder.append("<pubDate>")
                .append(new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH).format(latestDate))
                .append(" +0000</pubDate>" + "\n")
                .append("<lastBuildDate>")
                .append(new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH).format(latestDate))
                .append(" +0000</lastBuildDate>" + "\n")
                .append("<ttl>1800</ttl>" + "\n");

        for (WebPage webPage : webPages) {
            builder.append("<item>" + "\n")

                    .append("<title>")
                    .append(webPage.constructName())
                    .append("</title>" + "\n")

                    .append("<description>")
                    .append(webPage.getShortDescription())
                    .append("</description>" + "\n")

                    .append("<link>")
                    .append(baseUrl)
                    .append(webPage.getShortName())
                    .append("</link>" + "\n")

                    .append("<pubDate>")
                    .append(new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH).format(webPage.getLastMod()))
                    .append(" +0000")
                    .append("</pubDate>" + "\n")

                    .append("</item>" + "\n");
        }
        builder.append("</channel>" + "\n")
                .append("</rss>" + "\n");
        return builder.toString();
    }

}
