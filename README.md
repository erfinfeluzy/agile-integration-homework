# agile-integration-homework

Fuse on springboot standalone. Design to run both on standalone machine and openshift

Erfin github repository

## Prerequisite:
```
1. openjdk8
1. maven 3.5.x+
```

## Run the Code

### 1. Clone from My Github (user = erfinfeluzy)
```
git clone https://github.com/erfinfeluzy/agile-integration-homework
```

### 2. Build Artifact Folder
this folder will be used as common library for whole application. Java object will be generated from wsdl 

```
cd $BASE_DIR/artifact
```

```
mvn clean install
```

### 3. Run target SOAP Service and embedded ActiveMQ Server

```
cd $BASE_DIR/integration-test-server-erf
```

clean, install, and run spring boot. This module will run on port 9000
```
mvn clean install spring-boot:run
```

soap service can be access on `http://localhost:9000/soap`
activeMQ can be access from hawtio on `tcp:localhost:61616`

### 4. Run Inbound Application

```
cd $BASE_DIR/inbound-erf
```

clean, install, and run spring boot. 
```
mvn clean install spring-boot:run
```

inbound webservice can be accessed on `http://localhost:9098/cxf/demos/match`

This module expose jolokia on port 10000

### 5. Run Xlate Application

```
cd $BASE_DIR/xlate-erf
```

clean, install, and run spring boot. This module expose jolokia on port 12000
```
mvn clean install spring-boot:run
```

### 6. Run Outbound Application

```
cd $BASE_DIR/outbound-erf
```

clean, install, and run spring boot. This module expose jolokia on port 11000
```
mvn clean install spring-boot:run
```

## TEST IT

*Request* to `http://localhost:9098/cxf/demos/match` with payload:
```
<?xml version="1.0" encoding="UTF-8"?>
<p:Person xmlns:p="http://www.app.customer.com"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.app.customer.com PatientDemographics.xsd ">

  <p:age>30</p:age>
  <p:legalname>
    <p:given>First</p:given>
    <p:family>Last</p:family>
  </p:legalname>
  <p:fathername>Dad</p:fathername>
  <p:mothername>Mom</p:mothername>
  <p:gender xsi:type="p:Code">
    <p:code>Male</p:code>
  </p:gender>
</p:Person>
```
*Direct Response*
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ESBResponse xmlns="http://www.response.app.customer.com">
    <BusinessKey>ac6d243b-333c-4a70-8661-5c66c16cd571</BusinessKey>
    <Published>true</Published>
    <Comment>DONE</Comment>
</ESBResponse>
```
check logs on `integration-test-server-erf`
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns2:Envelope xmlns:ns2="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns3="http://webservice.index.mdm.sun.com/">
    <ns2:Body>
        <ns3:executeMatchUpdate>
            <callerInfo>
                <application>App</application>
                <applicationFunction>Function</applicationFunction>
                <authUser>Xlate</authUser>
            </callerInfo>
            <sysObjBean>
                <person>
                    <fatherName>Dad</fatherName>
                    <firstName>First</firstName>
                    <gender>Male</gender>
                </person>
            </sysObjBean>
        </ns3:executeMatchUpdate>
    </ns2:Body>
</ns2:Envelope>
```
Response from SOAP server
```
---------------------------
ID: 2
Response-Code: 200
Encoding: UTF-8
Content-Type: text/xml
Headers: {}
Payload: <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:executeMatchUpdateResponse xmlns:ns2="http://webservice.index.mdm.sun.com/"><return><EUID>Xlate</EUID><matchFieldChanged>false</matchFieldChanged><overlayDetected>false</overlayDetected><resultCode>1</resultCode></return></ns2:executeMatchUpdateResponse></soap:Body></soap:Envelope>
--------------------------------------
```

