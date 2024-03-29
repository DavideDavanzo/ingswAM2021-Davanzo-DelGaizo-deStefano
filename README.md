# Prova Finale di Ingegneria del Software - a.a. 2020-21

![](src/main/resources/images/loading_screen.jpg)

<img src="https://craniointernational.com/2021/wp-content/uploads/2021/05/Masters-of-Renaissance_box3D.png" width=140px height=140 px align="right" />

Java porting of [Maestri del Rinascimento](http://www.craniocreations.it/prodotto/masters-of-renaissance) 
(board game by [Cranio Creations](http://www.craniocreations.it)), 
final test of "Software Engineering" course at Politecnico di Milano.

Course held by: Alessandro Margara.

Developers: Davide Davanzo, [Dario Del Gaizo](https://github.com/Dario58), [Alessandra de Stefano](https://github.com/AlessandradeStefano).

## About the project

The target of this project was to develop the software version of a chosen board game, 
using Java language to create a distributed system. This is composed of a single server connected to multiple clients.

More precisely, we were asked to implement an MVC design pattern and a TCP network using sockets.



## Tools, plugins and external libraries

- Intellij IDEA
- Maven
- Git
- Json + Jackson
- JavaFX + SceneBuilder
- JUnit

## UML

- [initial UML](deliveries/uml/Initial_uml.png)
- [final UMLs](deliveries/uml) (divided by packages)

## Functionalities

__Main ones__

- [x] Single-player match
- [x] Multi-player match (2 to 4) 
- [x] Socket connection
- [x] Cli
- [x] Gui

__Advanced (2)__

- [x] Multiple parallel matches
- [ ] Persistence
- [ ] Parameters editor
- [ ] Local match
- [x] Resilience to disconnections

## Run

First of all download the [jar](deliveries/MaestriDelRinascimento.jar).

Server and clients use the same one, launching it differently.
- __Server__: download and double click on the [ServerApp.bat](deliveries/ServerApp.bat) file (default port number: 1234) or type from cmd line

        java -jar MaestriDelRinascimento.jar <port_num>
  
    Replace <port_num> with the port's number you want to use (e.g. 1234).\
    WARNING! CLIENTS MUST KNOW THIS NUMBER TO PLAY!
  

- __Client__: each client can choose independently to play using Cli or Gui.
    - __Gui__ --> download and double click on the [ClientGuiApp.bat](deliveries/ClientGuiApp.bat) file (default port number: 1234) or type from cmd line
      
            java -jar MaestriDelRinascimento.jar <server_IP> <port_num>
    
        Replace <port_num> with the same number used by the server.\
        Replace <server_IP> with the IP number of the server host.
    - __Cli__ --> to be able to see every color and symbol used in this version download and configure WSL. To do so follow this [link](https://github.com/ingconti/W10JavaCLI). \
        Then type on Ubuntu Shell
        
            java -jar MaestriDelRinascimento.jar -cli <server_IP> <port_num>
      
        Replace <port_num> with the same number used by the server.\
        Replace <server_IP> with the IP number of the server host.
      

## Play

You can check the game's rules [here](src/main/resources/maestri-rules.pdf). Now you are ready to have fun!
