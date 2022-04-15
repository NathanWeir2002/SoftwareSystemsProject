*Contributors: Nathan Weir, Tin Trung Bien, and Ashar Izhar.*

![Default Chat Between Foo and Bar.](https://i.imgur.com/kUgcKAp.png)

# Software Systems Group Project
This file contains information about:
- our project and it's functionality,
- how to run the application,
- references to other materials/libraries used on our solution,
- and a URL directing you to a video demo of the application.

## What's going on in the project?
This project demonstrates a chatting application with one or more clients chatting in a server. 

![Chat Test!](https://media2.giphy.com/media/PK6K4HuWi7HNHXF3Ht/giphy.gif?cid=790b76114ff2618cc7e9eac4521ef89bd918c6f4095b1289&rid=giphy.gif&ct=g)

After the server is shut down, a graph displaying the chatting activity of all of the clients over the course of the running time of the server is presented. 
- A CSV file is used as a means of storing the values that will then be displayed on the graph. 

*INSERT GRAPH SCREENSHOT HERE*

## How to Run the Application:

**For a video presentation of how to run the program, see youtube link [here!](https://www.youtube.com/watch?v=dQw4w9WgXcQ)**

[<img src="https://www.freeiconspng.com/thumbs/youtube-logo-png/hd-youtube-logo-png-transparent-background-20.png" alt="Youtube Logo" width="200"/>](https://www.youtube.com/watch?v=dQw4w9WgXcQ)

**To begin, through Windows, the program may be run in IntelliJ:**
- Right click `ClientMainApplication` and navigate to /Modify Run Configuration.../Modify Options/, and select "Allow Multiple Instances".

<img src="https://i.imgur.com/GH7DwIw.png" alt="Highlighted files"/>

- Then, run the `ServerMainApplication` method. This will initialize the server.
- Next, run the `ClientMainApplication` method. This will initialize one single client.

<img src="https://i.imgur.com/xqI77aq.png" alt="Highlighted files"/>

- To disconnect a client from the server, close the client of choice's window. This will prompt a message in the server mentioning that the client has been disconnected.
- To close the server, close the server's window. 
- Once the server is closed, a graph displaying the user activity in intervals of one hour each will be displayed.
- Close the graph to complete the program. If the graph is closed before the clients are disconnected, the clients will be notified that their connection has been lost.

**Alternatively, use an executable Jar file found [here.](https://drive.google.com/drive/folders/1qcNNLoXyQnWAwGEZZL4OOepyXH4zNhBS?usp=sharing)**

- First, initialize the server by either running the ServerExecute.bat, or run `java -jar ServerExecute.jar` in the command line.
- Then, initialize a client by either running the ClientExecute.bat, or run `java -jar ClientExecute.jar` in the command line.
- Both .bat files simply include the previous two commands, respectively.

<img src="https://i.imgur.com/a2hRTz8.png" alt="Google Drive"/>

- To disconnect a client from the server, close the client of choice's window. This will prompt a message in the server mentioning that the client has been disconnected.
- To close the server, close the server's window. 
- Once the server is closed, a graph displaying the user activity in intervals of one hour each will be displayed.
- Close the graph to complete the program. If the graph is closed before the clients are disconnected, the clients will be notified that their connection has been lost.
