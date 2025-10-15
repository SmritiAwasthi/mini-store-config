#Task		Location
#Folder		Anywhere you like (e.g., C:\Users\You\Projects\kafka-docker)
#File		Inside that folder, named docker-compose.yml
#Run command	From the same folder: docker-compose up -d
	docker-compose up -d


#Check if both services are running:
	docker ps

#For KAfka UI (post running below cmd access localhost:8080 to preview Kafka in UI) :
	docker run -d -p 8080:8080 -v C:/minishop-microserviceApp/kafka-docker/kafka-ui/application.yml:/app/application.yml tchiotludo/akhq


#To stop the services:
	docker-compose down

#Verify Topic Exists (Optional)
	docker exec kafka kafka-topics.sh --list --bootstrap-server localhost:9092

#To Create Topic in Kafka using Docker Bash command:
#For Confluent Kafka (confluentinc/cp-kafka):
	docker exec kafka kafka-topics \
 	 --create \
 	 --topic my-topic \
  	--bootstrap-server localhost:9092 \
 	 --partitions 1 \
 	 --replication-factor 1