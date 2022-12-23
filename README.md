# SESP-kafka-spring-microservices

It's a project used to explain how to implement an asynchronous file manager.

The actors of this project are two spring services which comunicate trought a Kafka server.
The features allow the user to create, load and promote pdf files in a central storage.
In the centra storage is implemented a notification system which notify to all users the changes on the file.
