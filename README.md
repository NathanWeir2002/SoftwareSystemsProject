`Contributors: Nathan Weir, Tin Trung Bien, and Ashar Izhar.`

![Default Chat Between Foo and Bar.](https://i.imgur.com/kUgcKAp.png)

# Software Systems Group Project
> This file contains information about the project and it's functionality, how to run the application, references to other materials/libraries used on our solution, and a URL directing you to a video demo of the application.

## Project Information
This project demonstrates a chatting application with one or more clients chatting in a server. After the server is shut down, a graph displaying the chatting activity of all of the clients over the course of the running time of the server is presented. A CSV file is used as a means of storing the values that will then be displayed on the graph. 

## How to Run the Application: 
Simply run the main methods present in the `ServerMainApplication` and `ClientMainApplication` classes (the order you run them in doesn't matter but the server must be running in order for the client to begin chatting). If you want to add another client to the server, run the main method in the `ClientMainApplication` and keep doing so until satisfied. Right when the server and all of the `ClientMainApplication` class instances have been shut down, the client activity graph will be produced. Once the window displaying the graph has been shut down, the application is considered complete and you can then repeat the process of running the application.
