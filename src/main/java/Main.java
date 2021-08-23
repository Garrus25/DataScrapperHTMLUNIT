import java.io.IOException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class Main {

    public static void main(String[] args){

        WebClient client = new WebClient(BrowserVersion.BEST_SUPPORTED);

        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setRedirectEnabled(true);
        client.getCache().setMaxSize(0);
        client.waitForBackgroundJavaScript(10000);
        client.setJavaScriptTimeout(10000);
        client.waitForBackgroundJavaScriptStartingBefore(10000);

        try {

            String url = "https://forum.valium.pl/index.php?/topic/81855-akademia-przerwa-techniczna-2008-0900/";

            HtmlPage page = client.getPage(url);

            synchronized(page) {
                page.wait(7000);
            }
            //Print cookies for test purposes. Comment out in production.
            URL _url = new URL(url);
            for(Cookie c : client.getCookies(_url)) {
                System.out.println(c.getName() +"="+c.getValue());
            }

            //This prints the content after bypassing Cloudflare.
            System.out.println(client.getPage(url).getWebResponse().getContentAsString());
        } catch (FailingHttpStatusCodeException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
