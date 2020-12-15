package ppkwu.vcard.lab;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.StructuredName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VCardController {

    @GetMapping("/vcard")
    @ResponseBody
    public String getVCardURL(@RequestParam("name") String name, @RequestParam("location") String location) {
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


}
