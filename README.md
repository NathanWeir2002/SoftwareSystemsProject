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

![Youtube Logo](https://www.freeiconspng.com/thumbs/youtube-logo-png/hd-youtube-logo-png-transparent-background-20.png = 100x)


Using IntelliJ, right click ClientMainApplication and navigate to /Modify Run Configuration.../Modify Options/, and select "Allow Multiple Instances.
Simply run the main methods present in the `ServerMainApplication` and `ClientMainApplication` classes (the order you run them in doesn't matter but the server must be running in order for the client to begin chatting). If you want to add another client to the server, run the main method in the `ClientMainApplication` and keep doing so until satisfied. Right when the server and all of the `ClientMainApplication` class instances have been shut down, the client activity graph will be produced. Once the window displaying the graph has been shut down, the application is considered complete and you can then repeat the process of running the application.
