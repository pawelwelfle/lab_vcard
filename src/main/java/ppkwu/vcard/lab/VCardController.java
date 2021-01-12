package ppkwu.vcard.lab;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.Encoding;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VCardController {

    @GetMapping("/vcard")
    @ResponseBody
    public String getVCardURL(@RequestParam("name") String name, @RequestParam("location") String location) throws IOException {
        String panoramaURL = "https://panoramafirm.pl/szukaj?k=" + name + "&l=" + location;
        List<Company> companyInfo = getCompanyInfo(panoramaURL);
//        VCard vcard = createVCard(companyInfo);
//        System.out.println(vcard);

        String vcardInfo = createVCardString(companyInfo);
        String filename = "vcard1.vcf";
        File file = new File(filename);
        FileOutputStream outputStream = new FileOutputStream(file);
        if (file.exists()) {
            outputStream.write(vcardInfo.getBytes());
            outputStream.flush();
            outputStream.close();
        }
        Path path = Paths.get(filename);
        Resource resource = new UrlResource(path.toUri());

        System.out.println(resource);

        return panoramaURL;
    }

//    private VCard createVCard(List<Company> companyInfo) {
//        Company firstCompany = companyInfo.get(0);
//        VCard vcard = new VCard(VCardVersion.V4_0);
//        StructuredName n = new StructuredName();
//        n.getParameters().setEncoding(Encoding.QUOTED_PRINTABLE);
//        n.getParameters().setCharset("utf-8");
//        n.setLanguage("pl");
//        n.setFamily(firstCompany.name);
//        n.setGroup(firstCompany.location);
//
//        vcard.setStructuredName(n);
//        vcard.addTelephoneNumber(firstCompany.phoneNumber);
//        vcard.addEmail(firstCompany.email);
//        System.out.println(vcard);
//        return vcard;
//    }

    private String createVCardString(List<Company> companyInfo) {
        Company firstCompany = companyInfo.get(0);

        return "BEGIN:VCARD\n" +
                "VERSION:4.0\n" +
                "FN;CHARSET=utf-8:" + firstCompany.getName() + "\n" +
                "TEL;WORK;VOICE:" + firstCompany.getPhoneNumber() + "\n" +
                "ADR;CHARSET=utf-8;TYPE=WORK,PREF:;;" + firstCompany.getLocation() + "\n" +
                "EMAIL:" + firstCompany.getEmail() + "\n" +
                "END:VCARD";
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