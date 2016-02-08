# Watson Concept Expansion Proof of Concept

##Overview

This application relies on IBM Watson Concept Expansion service. The purpose of this service
is to determine the conceptual context of a given set of seed words or phrases and provides 
additional words or phrases that expand on the concept.

This application is deployed at http://watson-bluemix-poc.mybluemix.net/

##Bluemix services

The following Bluemix services were used in this application:

1. Concept Expansion
2. Cloudant NoSQL DB

##Technologies

The application was developed using the technologies below:

1. Java 8
2. Spring 4
3. AngularJS 1.5.0
4. Maven 3.3.9

##Application Structure

The application is composed by the modules below:

1. watson-bluemix-poc : main module (POM parent)
   1.1. watson-bluemix-domain : manages the access to the database
   1.2. watson-bluemix-service : encapsulates the access to the Concept Expansion service provided by the Bluemix platform
   1.3. watson-bluemix-web : definitions of the pages and rest services accessed by the view
