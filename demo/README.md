# 🚀 System Design & Implementation Project: Message Queue App with Spring Boot + ActiveMQ Artemis

## 📚 Course: Systems Analysis and Design  
**Instructor:** Dr. Fatemeh Ghasemi  

---

## 🎯 Project Overview

The goal of this assignment is to familiarize students with message queues and asynchronous communication via **Apache ActiveMQ Artemis** and **Spring Boot**. Students implement a stock trading simulator backend that reads and writes messages through INQ and OUTQ queues.

---

## 📦 Application Features

Your Spring Boot service will handle the following:

### 📨 Input Message Formats (from `INQ` queue):

- `<ADD security>` → Add a stock (e.g., `ADD BTC`)
- `<BUY security amount>` → Buy a stock (e.g., `BUY BTC 10`)
- `<SELL security amount>` → Sell a stock (e.g., `SELL BTC 5`)
- `PORTFOLIO` → Show current stock holdings

### 📤 Output Responses (to `OUTQ` queue):

- `0 Success` → Operation completed successfully
- `1 Unknown security` → Tried to use a stock that hasn’t been added
- `2 Not enough positions` → Tried to sell more than owned
- `0 <security1> <amount1> | <security2> <amount2>` → Portfolio snapshot

> Note: All outputs should be plain text, not JSON.

---

## 🛠 Step-by-Step Implementation Guide

### 🔧 Step 1: Install Artemis

1. Download Artemis from [Apache Artemis](https://activemq.apache.org/components/artemis/download/)
2. Follow setup instructions to create a broker instance:
   - Enable **anonymous access**
   - Define two queues: `INQ` and `OUTQ` in `broker.xml`
3. Start the broker:
   - Use `bin/artemis run` or follow Docker instructions
4. Test your broker at [http://localhost:8161/console](http://localhost:8161/console)

---

### 💻 Step 2: Build the Spring Boot Application

- Use [Spring Initializr](https://start.spring.io/)
- Select:
  - Java 21
  - Maven
  - Add dependency: **Spring for Apache ActiveMQ Artemis**
- Implement your logic in a `@Service` class:
  - Use queues for messaging
  - Handle command parsing, validation, and portfolio updates
  - Ensure responses match the expected format

---

### 🧪 Step 3: Manual Testing

- Use Artemis console to:
  - Send messages to `INQ`
  - Monitor responses in `OUTQ`
- Test all commands including edge cases (e.g. selling unknown stock)

---

## 📁 Submission Instructions

- Zip your `target/` build directory:
  - File name format: `A1-{StudentNo}.zip`  
  - Upload on eLearn portal before the deadline
- Ensure your app:
  - Connects correctly to Artemis
  - Sends/receives messages in correct formats
  - Passes all manual tests

---

## 🧠 Tips for Success

- Keep message formatting **plain text**
- Parse inputs defensively — validate security names and amounts
- Structure your logic cleanly with clear method responsibilities

---

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.3/maven-plugin/build-image.html)
* [Spring for Apache ActiveMQ Artemis](https://docs.spring.io/spring-boot/3.4.3/reference/messaging/jms.html#messaging.jms.artemis)

### Guides
The following guides illustrate how to use some features concretely:

* [Messaging with JMS](https://spring.io/guides/gs/messaging-jms/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.