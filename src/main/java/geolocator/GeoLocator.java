package geolocator;

import com.google.common.net.UrlEscapers;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Class for obtaining geolocation information about an IP address or host
 * name. The class uses the <a href="http://ip-api.com/">IP-API.com</a>
 * service.
 */
public class GeoLocator {

    private static final Logger logger = LoggerFactory.getLogger(GeoLocator.class);

    /**
     * URI of the geolocation service.
     */
    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Gson GSON = new Gson();

    /**
     * Creates a <code>GeoLocator</code> object.
     */
    public GeoLocator() {}
    /**
     * Returns geolocation information about the JVM running the application.
     *
     * @return an object wrapping the geolocation information returned
     * @throws IOException if any I/O error occurs
     */
    public GeoLocation getGeoLocation() throws IOException {
        return getGeoLocation(null);
    }

    /**
     * Returns geolocation information about the IP address or host name
     * specified. If the argument is <code>null</code>, the method returns
     * geolocation information about the JVM running the application.
     *
     * @param ipAddrOrHost the IP address or host name, may be {@code null}
     * @return an object wrapping the geolocation information returned
     * @throws IOException if any I/O error occurs
     */
    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
        if (ipAddrOrHost != null) {
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            logger.debug(ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
            logger.info(GEOLOCATOR_SERVICE_URI);
        } else {
            logger.warn(ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI);
            logger.info(GEOLOCATOR_SERVICE_URI);
        }
        String s = IOUtils.toString(url, "UTF-8");
        logger.debug(s);
        return GSON.fromJson(s, GeoLocation.class);
    }
    //CHECKSTYLE:OFF
    public static void main(String[] args) throws IOException {
        try {
            String arg = args.length > 0 ? args[0] : null;
            logger.debug(arg);
            System.out.println(new GeoLocator().getGeoLocation(arg));
            logger.info(arg);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            logger.error(e.getMessage());
        }
    }

}
