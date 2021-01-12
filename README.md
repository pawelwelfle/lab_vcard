# lab_vcard


Documentation
Method **getVCardURL** will check for us information from this site.

`/GET @GetMapping("/vcard")'

`/GET @GetMapping("/vcardName")'

https://panoramafirm.pl/szukaj?k=" + name + "&l=" + location
main url which is tested

Method **getCompanyInfo** 
gets information about companies by getting info by specified elements

### **To test it:**
### The main method to see info about Events:

### Search by NAME and Location:
`hit to the endpoint to get VCard about some company` --->
http://localhost:8080/vcard?name=piekarnia&location=lodz+ruda

### Search by NAME:
`hit to the endpoint to get VCard about some company` --->
http://localhost:8080/vcardName?name=piekarnia


### Example of:
http://localhost:8080/vcardName?name=piekarnia

BEGIN:VCARD
VERSION:4.0
FN;CHARSET=utf-8:Piekarnia Oskroba S.A.
TEL;WORK;VOICE:(25) 756 29 00
ADR;CHARSET=utf-8;TYPE=WORK,PREF:;;05-340 Człekówka 9
EMAIL:biuro@oskroba.pl
END:VCARD

### Example of:
http://localhost:8080/vcard?name=piekarnia&location=lodz+ruda

BEGIN:VCARD
VERSION:4.0
FN;CHARSET=utf-8:Piekarnia Mikołaj Stanisław Sikorski Sp. z o.o.
TEL;WORK;VOICE:(42) 672 45 81
ADR;CHARSET=utf-8;TYPE=WORK,PREF:;;ul. Tatrzańska 9, 93-115 Łódź
EMAIL:biuro@piekarnia-mikolaj.pl
END:VCARD
