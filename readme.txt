--------------------------------- Instruction ---------------------------------
source code map:
---- RPCKeyValueStore
	---- src
		---- Model
			OperationType.java
			Request.java
		---- util
			ClientHelper.java
			Logger.java
		Client.java
		KeyValueStoreServer.java
		KeyValueStoreHandler.java
		KeyValueStoreService.java
		libthrift-0.13.0.jar
		slf4j-simple-1.7.25.jar
		slf4j-api-1.7.25.jar
	---- bin
	readme.txt
	keyvaluestore.thrift


1. the external jar files you need (included in the RPCKeyValueStore/src folder): 
	slf4j-simple-1.7.25.jar
	libthrift-0.13.0.jar
	slf4j-api-1.7.25.jar

2. go to RPCKeyValueStore folder
	
	cd RPCKeyValueStore

3. compile the server side java files

	javac -d bin -cp "src/slf4j-simple-1.7.25.jar:src/libthrift-0.13.0.jar:src/slf4j-api-1.7.25.jar:src/" src/KeyValueStoreServer.java


4. compile the client java files

	javac -d bin -cp "src/slf4j-simple-1.7.25.jar:src/libthrift-0.13.0.jar:src/slf4j-api-1.7.25.jar:src/" src/Client.java



5. run the server side class files

	cd bin

	java -cp "../src/slf4j-simple-1.7.25.jar:../src/libthrift-0.13.0.jar:../src/slf4j-api-1.7.25.jar:../src/:." KeyValueStoreServer 11866


6. run the client side class files

	go to the RPCKeyValueStore/bin folder if not (cd bin)

	java -cp "../src/slf4j-simple-1.7.25.jar:../src/libthrift-0.13.0.jar:../src/slf4j-api-1.7.25.jar:../src/:." Client localhost 11866



