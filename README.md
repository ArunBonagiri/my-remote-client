
## Quick overview:

This is a Client-Server application built on java. 
It is used to control local computer remotly by using android device 

Features:
* Mouse
* Keyboard
* power-options
* volume-control

This application developement process devided into two parts

* server (my-remote-server)
* client (my-remote-client)

this current repo contains all the source of client,you can find server repo [here](https://github.com/ArunBonagiri/my-remote-server)

## 	Client (my-remote-client):

The main application of this Android application is to get actions from user-interface 
and send those actions into server in form of textual form like commands.
you can find more information/quick_documentation of this application on [WikiTab](https://github.com/arunbonagiri190/my-remote-client/wiki)

### How to run?

* download apk file into your android device from release directory 
* click on install, you will may get warning messege (because it was from unknown source)
* click ok & install
* before opening an app start server on your computer.
```
   * make sure that both computer and android device are connected to the same wifi network
   * Sorry for any interface-rendering problems, because I tested on less devices.
``` 
	
## Built With

* Java 1.7
* [AndroidStudio](https://developer.android.com/studio/)

### Installing
this [tutorial](https://maxrohde.com/2014/08/18/import-github-project-to-android-studio/) explained best way to installing a git repo into android-studio
I developed this application on android sdk version(compileSdk 23,buildTools 24.0.1 and targetSdk 23)

### Contributions
[Arun Bonagiri](https://github.com/arunbonagiri190)