package ppkwu.vcard.lab;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VCardController {

    @GetMapping("/vcard")
    @ResponseBody
    public String getVCardURL(@RequestParam("name") String name, @RequestParam("location") String location) throws IOException {
        String panoramaURL = "https://panoramafirm.pl/szukaj?k=" + name + "&l=" + location;
        List<Company> companyInfo = getCompanyInfo(panoramaURL);
        createVCard(companyInfo);


        return panoramaURL;
    }

    private VCard createVCard(List<Company> companyInfo) {
        Company firstCompany = companyInfo.get(0);

        VCard vcard = new VCard();
        StructuredName n = new StructuredName();
        n.setFamily(firstCompany.name);
        n.setGroup(firstCompany.location);
        vcard.addTelephoneNumber(firstCompany.phoneNumber);
        vcard.addEmail(firstCompany.email);
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

    private static List<Company> getCompanyInfo(String url) throws IOException {
        List<Company> companyInfoList = new ArrayList<>();
        Document document = getDocument(url);

        Elements element = document.select("li");
        Elements name = element.select("h2");
        Elements location = element.select("div.address");
        Elements contact = element.select("div.item");
        Elements email = contact.select("a.ajax-modal-link");
        Elements phoneNumber = contact.select("a.icon-telephone");

        for (int i = 0; i < name.size(); i++) {
            companyInfoList.add(new Company(
                    name.get(i).text(),
                    location.get(i).text(),
                    email.get(i).attr("data-company-email"),
                    phoneNumber.get(i).attr("title")));
        }
        return companyInfoList;
    }
}