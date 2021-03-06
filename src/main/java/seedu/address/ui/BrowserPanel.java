package seedu.address.ui;

import static seedu.address.model.DateTimeUtil.PAGE_DATE_FORMAT;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.EventSelectionChangedEvent;
import seedu.address.model.event.Event;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    private static final String EVENT_PAGE_URL = "https://cs2113-ay1819s1-t12-1.github.io/main/EventSearchPage.html";


    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(javafx.event.Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Gets the URL without parameters
     */
    public static String getEventPageUrl() {
        return EVENT_PAGE_URL;
    }

    /**
     * Translates a string into {@code application/x-www-form-urlencoded}
     * format using UTF_8 encoding scheme. Spaces are replaced with '%20' instead of '+'.
     * Returns an empty string if UnsupportedEncodingException is encountered.
     */
    public String encodeString(String arg) {
        try {
            String encodedString = URLEncoder.encode(arg, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
            return encodedString;
        } catch (UnsupportedEncodingException uee) {
            logger.warning("Encoding not supported.");
            return "";
        }
    }

    /**
     * Formats HTML file path into string
     */
    private String formatEventPageUrl(Event event) {
        String queryString = "?name=" + encodeString(event.getName().toString())
                + "&contact=" + encodeString(event.getContact().toString())
                + "&phone=" + encodeString(event.getPhone().toString())
                + "&email=" + encodeString(event.getEmail().toString())
                + "&venue=" + encodeString(event.getVenue().value)
                + "&dateTime=" + encodeString(PAGE_DATE_FORMAT.format(event.getDateTime().dateTime))
                + "&status=" + encodeString(event.getStatus().toString())
                + "&tags=" + encodeString(event.getTagsString())
                + "&attendance=" + encodeString(event.getAttendanceString())
                + "&comment=" + encodeString(event.getComment().toString().replace("{", "<").replace("}", ">"));

        return EVENT_PAGE_URL + queryString;
    }

    /**
     * Loads a HTML file with data from event passed into it
     */
    private void loadEventPage(Event event) {
        try {
            URL searchPage = new URL(formatEventPageUrl(event));
            loadPage(searchPage.toExternalForm());
        } catch (MalformedURLException mue) {
            logger.warning("Event page has invalid parameters. Default page will be loaded.");
            loadDefaultPage();
        }
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleEventSelectionChangedEvent(EventSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadEventPage(event.getNewSelection());
    }
}
