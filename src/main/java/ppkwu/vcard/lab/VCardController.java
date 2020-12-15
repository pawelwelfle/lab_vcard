package ppkwu.vcard.lab;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VCardController {

    @GetMapping("/vcard")
    @ResponseBody
    public ResponseEntity getVCardURL(@RequestParam("name") String name, @RequestParam("location") String location) {
        String panoramaURL = "https://panoramafirm.pl/szukaj?k=" + name + "&l=" + location;
        return null;
    }
}
