# Chat Box – Real-Time Messaging

### Overview
Chat Box is a real-time messaging application based on a client-server architecture. The application allows users to register, login, join channels, and freely exchange messages within them.

### Status
The project is continuously under development, with ongoing improvements and new features being added. Below are some planned functionalities to be implemented:
- Direct Messaging: Allow users to send private messages between accounts.
- Message History: Load and view past messages within a channel or direct conversation.
- Mailbox: Implement a mailbox system to manage and view received messages.
- Notifications: Alert users about new messages or activities in their channels.
- File Sharing: Enable users to send files within channels or private messages.

## Communication Flow
1. The client connects to the server via a socket.
2. The client sends data to associate with an account.
3. The client chooses a channel to join or create.
4. Messages are exchanged in real-time within a selected channel.
5. The client disconnects by sending the `exit` command.

## Server Commands
- Block – Shuts down a channel or blocks an account.
- Exit – Terminates the server.

## Features
- Account Management
- Channel System
- Real-Time Messaging
- Server Management

## Technologies Used
- Java 23
- Sockets
- Multithreading
- Maven

## Running the Server
1. Clone the repository:
```
git clone https://github.com/varev-dev/chat-box.git
```
2. Build the project:
```
mvn clean install
```
3. Start the server:
```
java -jar chat-server.jar
```
