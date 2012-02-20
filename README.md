Facebook Demo Recommender based on Java EE 6 and Mahout
=============

This project is a demo of how you can use mahout to build a cool
musician recommender for your friends.
It includse everything what you need including a modern REST
and a conservative SOAP interface. In the end a web application
archive will be generated.

Here is how you get this working
--------------------------------

`The embedded glassfish jar will be about 67mb. So be a little bit patient until it is loaded.`

    git clone git@github.com:ManuelB/facebook-recommender-demo.git
    cd facebook-recommender-demo
    mvn install
    mvn embedded-glassfish:run 

Open the following URL in your browser:
http://localhost:8080/myapp/FacebookRecommender/person/Manuel%20Blechschmidt

You will need the following requirements to get the application working:

* git (http://git-scm.com/download)
* Java 6 JDK (http://www.java.com/en/download/index.jsp)
* Maven 3 (http://maven.apache.org/)
* Java EE 6 compatible Container e.g. Glassfish 3 (http://glassfish.java.net/, Glassfish is bundled)
