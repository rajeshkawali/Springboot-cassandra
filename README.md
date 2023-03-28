# Springboot-cassandra
Spring Boot with Cassandra DB

### Open a command prompt(cmd) window and navigate to the bin directory of your Cassandra
PS C:\Users\Rajesh_Kawali> cd C:\setup\apache-cassandra-4.1.1\bin

### Start the Cassandra DB
PS C:\setup\apache-cassandra-4.1.1\bin> cassandra

### Open a new command prompt(cmd) and type cqlsh
PS C:\setup\apache-cassandra-4.1.1\bin> cqlsh

### How to create Keyspace in Cassandra DB
cqlsh> CREATE KEYSPACE mykeyspace WITH REPLICATION = {'class' : 'SimpleStrategy','replication_factor' : 1};

### Keyspace is created. Now use created keyspace
cqlsh> use mykeyspace;

### How to create table in Cassandra DB
cqlsh:mykeyspace> CREATE table user(id UUID PRIMARY KEY, firstName text, lastName text, age int, gender text, role text);

### How to insert values to table
INSERT INTO user(id, firstName, lastName, age, gender, role) VALUES(12345, "Rajesh", "Kawali", 25, "male", "Software Engineer");

### How to retrive data from db
cqlsh:mykeyspace> select * from user;

### To drop a keyspace 
cqlsh:mykeyspace> drop keyspace mykeyspace;

### To drop a table
cqlsh:mykeyspace> drop table mykeyspace.user;

