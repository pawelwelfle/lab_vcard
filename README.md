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
