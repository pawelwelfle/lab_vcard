package ppkwu.vcard.lab;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.StructuredName;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@RestController
public class VCardController {

    @GetMapping("/vcard")
    @ResponseBody
    public String getVCardURL(@RequestParam("name") String name, @RequestParam("location") String location) throws IOException {
        String panoramaURL = "https://panoramafirm.pl/szukaj?k=" + name + "&l=" + location;


        return panoramaURL;
    }

    private VCard createVCard() {
        VCard vcard = new VCard();
        StructuredName n = new StructuredName();
        n.setFamily(name);
        n.setGroup(location);
        vcard.setStructuredName(n);
        String str = Ezvcard.write(vcard).version(VCardVersion.V4_0).go();
        return vcard;
    }

    //got from VCalendar
    private static Document getDocument(String url) throws IOException {
        URL urlConn = new URL(url);
        URLConnection conn = urlConn.openConnection();
        BufferedReader b = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder s = new StringBuilder();
        String loadedLines;
        while ((loadedLines = b.readLine()) != null) {
            s.append(loadedLines);
        }
        String lines = s.toString();
        Document document = Jsoup.parse(lines);
        return document;
    }

    private static List<Company> getCompanyInfo(String url){
    }
}