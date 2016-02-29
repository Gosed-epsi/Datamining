package epsi.i5.datamining.repustate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RepustateClient {

    public static String DOMAIN = "http://api.repustate.com/v3/";
    public static String API_KEY = "a16f016fcfdbc012644c7fe02166ff6195683dee";
    public static String BASE_URL = DOMAIN + API_KEY;

    public static String FORMAT_JSON = ".json";

    public static String SENTIMENT_PATH = "/score.json";
    public static String SENTIMENT_BULK_PATH = "/bulk-score.json";
    public static String CHUNK_PATH = "/chunk.json";
    public static String CLEAN_HTML_PATH = "/clean-html.json";
    public static String SEMANTIC_PATH = "/entities.json";
    public static String TOPIC_PATH = "/topic.json";
    public static String USAGE_PATH = "/usage.json";

    public static String CUSTOM_SENTIMENT_RULES_PATH = "/sentiment-rules.json";

    /**
     * Generic method for calling any other future methods that Repustate may
     * add
     *
     * @param path
     * @param format
     * @param httpRequestType
     * @param data
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws RepustateException
     */
    public static String getRepustateData(String path, String httpRequestType, Map<String, String> data) throws MalformedURLException, IOException, RepustateException {
        String output = sendRequest(path, httpRequestType, getContentFromMap(data));
        return output;
    }

    /**
     * This method takes a map of strings as argument. Each key value pair will
     * represent a block of text. Each argument starting with 'text' will be the
     * only ones scored. To help you reconcile scores with blocks of text,
     * Repustate requires that you append some sort of ID to the POST argument.
     * For example, if you had 50 blocks of text, you could enumerate them from
     * 1 to 50 as text1, text2, ..., text50.
     *
     * @param format
     * @param data
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws RepustateException
     */
    public static String getSentimentBulk(Map<String, String> data) throws MalformedURLException, IOException, RepustateException {
        String output = sendRequest(SENTIMENT_BULK_PATH, "POST", getContentFromMap(data));
        return output;
    }

    public static String getSentiment(Map<String, String> data) throws MalformedURLException, IOException, RepustateException {
        String output = sendRequest(SENTIMENT_PATH, "POST", getContentFromMap(data));
        return output;
    }

    public static String getChunk(Map<String, String> data) throws MalformedURLException, IOException, RepustateException {
        String output = sendRequest(CHUNK_PATH, "POST", getContentFromMap(data));
        return output;
    }

    /**
     * API notes from Repustate Note: all of the above API calls use this
     * implicitly so you don't have to clean your HTML before passing it to
     * Repustate. Only use this API call if you intend to do your own processing
     * after the fact with the cleaned up HTML.
     *
     * Note 2: This call doesn't work on home pages e.g. cnn.com, ebay.com,
     * nytimes.com. Its intended use is for single article pages.
     *
     * @param url
     * @param data
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws RepustateException
     */
    public static String getCleanHTML(Map<String, String> data, String url) throws MalformedURLException, IOException, RepustateException {
        //http://api.repustate.com/v3/demokey/clean-html.json?url=http://tcrn.ch/aav9Ty
        String urlStr = CLEAN_HTML_PATH;
        if (url != null) {
            urlStr = urlStr + "?url=" + url;
        }
        String output = sendRequest(urlStr, "GET", getContentFromMap(data));
        return output;
    }

    public static String getUsage() throws MalformedURLException, IOException, RepustateException {
        String output = sendRequest(USAGE_PATH, "GET", "");
        return output;
    }

    /**
     * If the url argument is specified, we will use the HTTP GET method, or if
     * the text is specified, then we will use the POST method according to the
     * repustate APIs. if you are using HTTP GET, any optional arguments need to
     * be URL-encoded. If you are using HTTP POST, any optional arguments should
     * be part of your POST'ed data.
     *
     * @param format
     * @param data
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws RepustateException
     */
    public static String getEntities(Map<String, String> data) throws MalformedURLException, IOException, RepustateException {
        // curl -d "text=Obama is the president of the United States" http://api.repustate.com/v3/YOUR_API_KEY/entities.json
        String output = sendRequest(SEMANTIC_PATH, "POST", getContentFromMap(data));
        return output;
    }

    public static String addSentimentRule(Map<String, String> data) throws MalformedURLException, IOException, RepustateException {
        // curl -d "text=love&sentiment=neg&lang=en" https://api.repustate.com/v3/API_KEY/sentiment-rules.json
        String output = sendRequest(CUSTOM_SENTIMENT_RULES_PATH, "POST", getContentFromMap(data));
        return output;
    }

    public static String listSentimentRules(String lang) throws MalformedURLException, IOException, RepustateException {
        // curl https://api.repustate.com/v3/API_KEY/sentiment-rules.json
        String output = sendRequest(CUSTOM_SENTIMENT_RULES_PATH, "GET", "");
        return output;
    }

    public static String deleteSentimentRule(Map<String, String> data) throws MalformedURLException, IOException, RepustateException {
        // curl -X DELETE https://api.repustate.com/v3/API_KEY/sentiment-rules.json?rule_id=$ruleid
        String output = sendRequest(CUSTOM_SENTIMENT_RULES_PATH + "?" + getContentFromMap(data), "DELETE", "");
        return output;
    }

    public static String commitSentimentRules() throws MalformedURLException, IOException, RepustateException {
        // curl -X PUT https://api.repustate.com/v3/API_KEY/sentiment-rules.json
        String output = sendRequest(CUSTOM_SENTIMENT_RULES_PATH, "PUT", "");
        return output;
    }

    //***************Utility Methods***********************//
    public static String getContentFromMap(Map<String, String> data) throws UnsupportedEncodingException {
        String content = "";
        if (data != null && data.size() > 0) {
            Set<String> keys = data.keySet();
            Iterator<String> keyIter = keys.iterator();
            for (int i = 0; keyIter.hasNext(); i++) {
                Object key = keyIter.next();
                if (i != 0) {
                    content += "&";
                }
                content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
            }
        } else {
            content = "";
        }
        return content;
    }

    public static String sendRequest(String path, String method, String data)
            throws IOException, RepustateException {
        URL url = new URL(BASE_URL + path);

        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod(method.toUpperCase());

        httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpCon.setRequestProperty("Accept", "application/json");

        if (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) {
            httpCon.setRequestProperty("Content-Length", String.valueOf(data.length()));
            OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            out.write(data);
            out.close();
        } else {
            httpCon.connect();
        }

        char[] responseData = readResponseData(httpCon.getInputStream(), "UTF-8");

        int responseCode = httpCon.getResponseCode();
        String redirectURL = null;
        if ((responseCode == HttpURLConnection.HTTP_MOVED_PERM
                || responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_CREATED)
                && (redirectURL = httpCon.getHeaderField("Location")) != null) {
            return sendRequest(redirectURL);
        }
        if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RepustateException("HTTP Error: " + responseCode + "; Data: " + new String(responseData));
        }
        return new String(responseData);
    }

    public static String sendRequest(String path) throws IOException, RepustateException {
        return sendRequest(path, "GET", "");
    }

    private static char[] readResponseData(InputStream stream, String encoding) {
        BufferedReader in = null;
        char[] data = null;
        try {
            StringBuffer buf = new StringBuffer();
            data = new char[1024];

            in = new BufferedReader(new InputStreamReader(stream, encoding));
            int charsRead;
            while ((charsRead = in.read(data)) != -1) {
                buf.append(data, 0, charsRead);
            }
            data = new char[buf.length()];
            buf.getChars(0, data.length, data, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data != null ? data : null;
    }
}
