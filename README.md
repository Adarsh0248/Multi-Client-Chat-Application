# Multi-Client Chat Application

A Java-based chat system where multiple clients can connect to a server and exchange real-time messages.

## Features
- Multi-client support using TCP sockets
- Concurrent server using multithreading
- Broadcast messages to all clients
- Console-based interface

## Tech Stack
- Java
- TCP Sockets
- Multithreading
- OOP

---

## ⚙️ How to Run

### 1. Clone the Repository
```bash
git clone https://github.com/Adarsh0248/ChatApp.git
cd ChatApp
1. Compile the code:
   ```bash
   javac src/*.java
```
2.Start the server
```bash
  java -cp src ChatServer
```
3.Run Clients
```bash
  java -cp src ChatClient
```
4. Start Chatting 🎉

Type a message in any client → it gets broadcast to all connected clients.

Server console shows logs of connected/disconnected clients.



