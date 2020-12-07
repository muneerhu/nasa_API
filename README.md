# nasa_API_Test
This is the project that contains the Test cases for NASA Open API for APOD
# APOD
One of the most popular websites at NASA is the Astronomy Picture of the Day. In fact, this website is one of the most popular websites across all federal agencies. It has the popular appeal of a Justin Bieber video. This endpoint structures the APOD imagery and associated metadata so that it can be repurposed for other applications. In addition, if the concept_tags parameter is set to True, then keywords derived from the image explanation are returned. These keywords could be used as auto-generated hashtags for twitter or instagram feeds; but generally help with discoverability of relevant imagery.

All the API are documents here https://api.nasa.gov/

# HTTP Request
end_point: GET https://api.nasa.gov/planetary/apod
# Query Param:
API_KEY -> required
concept_tags,
hd,
date


### Framework for testing

RestAssured

All the test cases are available in the package : NasaAPODTest.java

## Technologies

* Java 8
* Maven
* TestNg
* Rest Assured
* json

