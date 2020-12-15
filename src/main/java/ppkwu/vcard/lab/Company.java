package ppkwu.vcard.lab;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {
    String name;
    String location;
    String email;
    String phoneNumber;

    public Company(String name, String location, String email, String phoneNumber) {
        this.name = name;
        this.location = location;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
