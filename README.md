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

clean, install, and run spring boot. This module expose jolokia on port 11000
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
