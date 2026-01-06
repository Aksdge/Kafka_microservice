# Kafka Microservice Project

This project demonstrates a Kafka-based microservice architecture with two Spring Boot services:
- **deliveryBoy**: Producer service that sends location updates to Kafka
- **endUser**: Consumer service that receives and processes location updates from Kafka

## 📋 Prerequisites

Before running this project, ensure you have the following installed:

1. **Java 17** or higher
2. **Apache Maven** 3.6+ (or use Maven Wrapper included in the project)
3. **Apache Kafka** installed and running on `localhost:9092`
4. **Zookeeper** (required for Kafka, usually bundled with Kafka)

## 📁 Project Structure

```
Kafka_microservice/
├── deliveryBoy/          # Producer Microservice
│   ├── src/main/java/com/deliveryBoy/
│   │   ├── Controller/
│   │   │   ├── LocationController.java    # REST endpoint for location updates
│   │   │   └── LocationRequest.java       # Request DTO
│   │   ├── kafkaConfig/
│   │   │   ├── Config.java                # Kafka topic configuration
│   │   │   └── AppConstants.java          # Topic name constants
│   │   └── kafkaService/
│   │       └── kafkaService.java          # Kafka producer service
│   └── src/main/resources/
│       └── application.properties         # Application configuration (Port: 8081)
│
└── endUser/              # Consumer Microservice
    ├── src/main/java/com/endUser/endUser/
    │   ├── kafka/
    │   │   ├── LocationConsumer.java      # Kafka consumer service
    │   │   └── AppConstants.java          # Topic name constants
    │   └── EndUserApplication.java        # Main application class
    └── src/main/resources/
        └── application.properties         # Application configuration (Port: 8082)
```

## 🚀 Step-by-Step Setup and Running Guide

### Step 1: Start Zookeeper

Open a terminal/command prompt and navigate to your Kafka installation directory:

**Windows:**
```bash
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```

**Linux/Mac:**
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

Keep this terminal open. Zookeeper must be running for Kafka to work.

### Step 2: Start Kafka Server

Open a **new** terminal/command prompt and navigate to your Kafka installation directory:

**Windows:**
```bash
bin\windows\kafka-server-start.bat config\server.properties
```

**Linux/Mac:**
```bash
bin/kafka-server-start.sh config/server.properties
```

Keep this terminal open. Kafka server must be running.

### Step 3: Create Kafka Topic (Optional)

The topic will be created automatically by the application, but you can also create it manually:

**Windows:**
```bash
bin\windows\kafka-topics.bat --create --topic location-update-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

**Linux/Mac:**
```bash
bin/kafka-topics.sh --create --topic location-update-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

Verify the topic was created:
```bash
bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
```

### Step 4: Build the Projects

Open a **new** terminal and navigate to the project root:

```bash
cd C:\javaPracties\Kafka_microservice
```

Build the **deliveryBoy** service:
```bash
cd deliveryBoy
mvn clean install
```

Build the **endUser** service:
```bash
cd ..\endUser
mvn clean install
```

### Step 5: Run the Consumer Service (endUser)

In a **new** terminal, navigate to the endUser directory:

```bash
cd C:\javaPracties\Kafka_microservice\endUser
mvn spring-boot:run
```

**Expected Output:**
```
Started EndUserApplication in X.XXX seconds
```

The consumer service will start on **port 8082** and begin listening for location updates from Kafka.

### Step 6: Run the Producer Service (deliveryBoy)

In a **new** terminal, navigate to the deliveryBoy directory:

```bash
cd C:\javaPracties\Kafka_microservice\deliveryBoy
mvn spring-boot:run
```

**Expected Output:**
```
Started DeliveryBoyApplication in X.XXX seconds
```

The producer service will start on **port 8081** and is ready to accept location update requests.

## 🧪 Testing the Services

### Test 1: Send Location Update via REST API

**Using cURL:**
```bash
curl -X POST http://localhost:8081/location/update ^
  -H "Content-Type: application/json" ^
  -d "{\"location\": \"12.3456,78.9012\"}"
```

**Using PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8081/location/update" `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"location": "12.3456,78.9012"}'
```

**Using Postman/Thunder Client:**
- **Method:** POST
- **URL:** `http://localhost:8081/location/update`
- **Headers:** `Content-Type: application/json`
- **Body (JSON):**
  ```json
  {
    "location": "12.3456,78.9012"
  }
  ```

**Expected Response:**
```json
"Location updated successfully"
```

### Test 2: Verify Consumer Received the Message

Check the **endUser** service terminal/console. You should see:
```
Received location: 12.3456,78.9012
```

### Test 3: Test with Multiple Locations

Send multiple location updates:
```bash
curl -X POST http://localhost:8081/location/update -H "Content-Type: application/json" -d "{\"location\": \"10.1234,20.5678\"}"
curl -X POST http://localhost:8081/location/update -H "Content-Type: application/json" -d "{\"location\": \"30.9876,40.5432\"}"
curl -X POST http://localhost:8081/location/update -H "Content-Type: application/json" -d "{\"location\": \"50.1111,60.2222\"}"
```

Each location update should appear in the endUser service logs.

## 📡 API Endpoints

### deliveryBoy Service (Port 8081)

#### POST `/location/update`
Sends a location update to Kafka.

**Request Body:**
```json
{
  "location": "latitude,longitude"
}
```

**Success Response (200 OK):**
```
Location updated successfully
```

**Error Response (400 Bad Request):**
```
location is required
```

## ⚙️ Configuration

### deliveryBoy Service Configuration
- **Port:** 8081
- **Kafka Bootstrap Server:** localhost:9092
- **Topic:** location-update-topic
- **Partitions:** 3
- **Replication Factor:** 1

### endUser Service Configuration
- **Port:** 8082
- **Kafka Bootstrap Server:** localhost:9092
- **Topic:** location-update-topic
- **Consumer Group:** group-id
- **Auto Offset Reset:** earliest

## 🔍 Troubleshooting

### Issue: "Connection refused" or "Cannot connect to Kafka"
**Solution:** 
- Ensure Zookeeper is running (Step 1)
- Ensure Kafka server is running (Step 2)
- Verify Kafka is running on `localhost:9092`

### Issue: "Topic not found"
**Solution:**
- The topic should be created automatically by the application
- Or manually create it using Step 3

### Issue: "Port already in use"
**Solution:**
- Check if another application is using port 8081 or 8082
- Change the port in `application.properties` if needed

### Issue: "Maven build fails"
**Solution:**
- Ensure Java 17+ is installed: `java -version`
- Ensure Maven is installed: `mvn -version`
- Try cleaning and rebuilding: `mvn clean install`

## 📝 Notes

- The **deliveryBoy** service acts as a **producer** - it sends messages to Kafka
- The **endUser** service acts as a **consumer** - it receives messages from Kafka
- Both services must be running for the complete flow to work
- Kafka and Zookeeper must be running before starting the services
- The consumer service should be started before the producer to ensure no messages are missed

## 🎯 Summary of Running Order

1. ✅ Start Zookeeper
2. ✅ Start Kafka Server
3. ✅ Build both projects (`mvn clean install`)
4. ✅ Start endUser service (Consumer) - Port 8082
5. ✅ Start deliveryBoy service (Producer) - Port 8081
6. ✅ Test by sending POST requests to `/location/update`

---

**Happy Coding! 🚀**

