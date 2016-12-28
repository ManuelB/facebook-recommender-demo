Facebook Demo Recommender based on Java EE 7 and Mahout
=============

Copyright: Incentergy GmbH http://www.incentergy.de

This project is a demo of how you can use mahout to build a cool
musician recommender for your friends.
It includse everything what you need including a modern REST
and a conservative SOAP interface. In the end a web application
archive will be generated.

The presentation can be found here:

http://de.slideshare.net/ManuelB86/how-to-build-a-recommender-system-based-on-mahout-and-java-ee

Here is how you get this working
--------------------------------

`The embedded glassfish jar will be about 81mb. So be a little bit patient until it is loaded.`

    git clone git://github.com/ManuelB/facebook-recommender-demo.git
    cd facebook-recommender-demo
    mvn install
    mvn embedded-glassfish:run 

Open the following URL in your browser:

[http://localhost:8010/facebook-recommender-demo/FacebookRecommender/person/Manuel%20Blechschmidt](http://localhost:8010/facebook-recommender-demo/FacebookRecommender/person/Manuel%20Blechschmidt)

You will need the following requirements to get the application working:

* git (http://git-scm.com/download)
* Java 8 JDK (http://www.java.com/en/download/index.jsp)
* Maven 3 (http://maven.apache.org/)
* Java EE 7 compatible Container e.g. Glassfish 4.1 (http://glassfish.java.net/, Glassfish is bundled)


If you need support
--------------------------------

Feel free to contact us: http://www.incentergy.de/impressum/kontakt/
