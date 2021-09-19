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
## User logging

## Train Searching

Filters:

- Departure & Arrival
- outbound datetime [& return datetime]
- number tickets
- Class : First, Business, Eco

## Train Booking

Require :

- Train ID
- Travel class
- Ticket type (flexible, not flexible)

## SOAP

- Authentification
- Searching
- Reservation

## Rest

This WS provide response to SOAP requests

## Self-evaluation
| Requierments                                                                   | Jérémie | Mikaël |
|--------------------------------------------------------------------------------|---------|--------|
| Create REST Train Filtering service B                                          | 4       | 4      |
| Create SOAP Train Booking service A                                            | 4       | 4      |
| Interaction between two services                                               | 4       | 4      |
| Test with Web service Client (instead of using Eclipse's Web service Explorer) | 1.25    | 1      |
| Work with complex data type (class, table, etc.)                               | 2       | 2      |
| Work with database (in text file, xml, in mysql, etc.)                         | 2       | 2      |