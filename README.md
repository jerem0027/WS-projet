# Webservice - Train journey booking service

## Students
* HENRION Jérémie
* FERREIRA DE ALMEIDA Mikaël

## Requirements

sqlitejdbc v3.7.2 : http://www.java2s.com/Code/Jar/s/Downloadsqlitejdbc372jar.htm
org.json v20210307: https://jar-download.com/artifacts/org.json

### data generator
```sh
pip3 install SQLAlchemy==1.4.23
```

## Self-evaluation
| Requierments                                                                   | Jérémie | Mikaël |
|--------------------------------------------------------------------------------|---------|--------|
| Create REST Train Filtering service B                                          | 4       | 4      |
| Create SOAP Train Booking service A                                            | 4       | 4      |
| Interaction between two services                                               | 4       | 4      |
| Test with Web service Client (instead of using Eclipse's Web service Explorer) | 1.25    | 1      |
| Work with complex data type (class, table, etc.)                               | 2       | 2      |
| Work with database (in text file, xml, in mysql, etc.)                         | 2       | 2      |

## Installation

### Generate Data Bases
```sh
python3 data_generator/database.py
```

### Service SOAP
#### Step 1 - Download Requirements

Download and extract :
- Tomcat version 9.0.52 : https://tomcat.apache.org/download-90.cgi
- sqlitejdbc v3.7.2 : http://www.java2s.com/Code/Jar/s/Downloadsqlitejdbc372jar.htm
- org.json v20210307: https://jar-download.com/artifacts/org.json

#### Step 2 - Settings
- Open the server view on Eclipse by the menu Windows→Show View→Other→Servers.
- Right click on the Server view select New → Server. Let the Server's host name is localhost, select the Server type is Apache → Tomcat v9.0 Server. Click Next.
- Specify the installation directory, such as C:\Program Files\Apache Software Foundation\Tomcat 9.0. Click Finish.

#### Step 3 - Add libraries
- Copy all .jar files into java web Project "WS-SOAP"
- Right click on java web Project "WS-SOAP" → Properties → Java Build Path. Select Libraries and add  JARs :
    * sqlitejdbc
    * org.json

#### Step 4 - Add Environment variable
- Right click on java web Project "WS-SOAP" → Run as → Run Configurations... → Apache Tomcat → Tomcat v9.0 Server at localhost
- Go to environment → New and add Name: DB_PATH / Value: "path_to_your_db/soap.db"
- Apply

#### Step 5 - Setting up Axis2
- Apache Axis2 is Web services/SOAP/WSDL engine and is the successor of Apache Axis. It supports both SOAP 1.1 and 1.2.
- Download and extract Axis2 1.6.2 binary distribution and extract it on your machine (http://archive.apache.org/dist/axis/axis2/java/core/1.6.2/).
- Select Window → Preferences on eclipse. Go to Web services → Axis2 Preferences. In Axis2 runtime location browse to your axis2 home folder, for example ~/axis2-1.6.2-bin/axis2-1.6.2. Click Ok.

#### Step 6 - Create Web Service

- Right click on the Booking class, select Web Sevices → Create Web service.
- In the Configuration part, select Web service runtime: Apache Axis and select Apache Axis2 in the Web service runtime tab, then click Ok. Click Finish.
- Do the same manipulation with the Account Class.

#### Step 7 - Access to services
- Right click on WS-SOAP web project, then Run as → Run on a server. Choose Tomcat v9.0. Click Next.
- Make sure that WS-SOAP web project is in the Configured list. If not add it from Available list to Configured list. Click Finish.


### Service REST

#### Step 1 - Download Requirements

Download and extract :
- sqlitejdbc v3.7.2 : http://www.java2s.com/Code/Jar/s/Downloadsqlitejdbc372jar.htm
- org.json v20210307: https://jar-download.com/artifacts/org.json
- org.restlet : https://restlet.talend.com/downloads/current/ (Other edition - Java EE)

#### Step 2 - Add libraries
- Copy org.restlet.jar from restlet-jee-2.4.3/lib into java web Project "WS-REST"
- Copy all other .jar files into java web Project "WS-REST"
- Right click on java web Project "WS-REST" → Properties → Java Build Path. Select Libraries and add  JARs :
    * sqlitejdbc
    * org.json
    * org.restlet

#### Step 3 - Add Environment variable
- Right click on RouterApplication → Run as → Run Configurations...
- Go to environment → New and add Name: DB_PATH / Value: "path_to_your_db/rest.db"
- Apply

#### Step 4 - Run
- Now you can run the service by clicking Right click on RouterApplication → Run as → Java Application.