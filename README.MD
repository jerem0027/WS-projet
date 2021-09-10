# Webservice - Train journey booking service

## Requirements

sqlitejdbc v3.7.2 : http://www.java2s.com/Code/Jar/s/Downloadsqlitejdbc372jar.html

### data genrator
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

