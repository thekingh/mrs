# mrs - Modular Robotics Simulation Install Instructions

There are a few different ways to install this simulator.
* Traditional installation of dependencies on Unix based OS
* Docker based install

We recommend the docker installation to avoid dependency issues. 


## Docker install
Overview:

1. Install Docker
2. Install VNC client
3. Download/run atong01/mrs from docker hub
4. Run the program
5. View the program

### 1. Install Docker

Docker is a containerization software for unix based systems follow the install instructions for your system here: https://docs.docker.com/engine/installation/

### 2. Install VNC client

A VNC client is needed to view the screen of the container created by docker. We recommend VNC viewer for OSX systems (only tested path) but in theory any VNC viewer will do.

### 3. Get image

This is the cool part. Docker hub enables upload of builds much like github enables upload and sharing of code. Once docker is installed simply run the command:

        docker pull atong01/mrs:latest

### 4. Run the program

This is a little bit trickier run the command located in the docker.sh script. Either:

        sh docker.sh

If you have downloaded the MRS repo from github, or the full command:

        docker run -d -p 5900:5900 atong01/mrs

This command can be explained in a few parts. Docker run tells docker to run a specified image with options. Here we use the options -d and -p. -d tells docker to run the process as a daeman, -p tells docker which ports to link to the host machine. 

To find if you are building on an OSX or Windows build then docker is actually installed in a virtualization software, so the docker host is a virtual machine. Thus if you are running on one of these systems you need the host IP address. To find the host IP address run this command from docker.

        docker-machine env default

This assumes that your docker virtual machine is called "default", if it is not then you should already know how to find its IP address, but substuting the machine name should work.

### 5. View the Program

Now if you run the command:

        docker ps

You should see one docker image running with the name atong01/mrs. Docker ps essentially lists all of the currently active containers.

We now need to use VNC client to view the container. The docker container is linked to port 5900, therefore we need to tell the VNC client where to look for the docker desktop. When your VNC viewer asks for the VNC server, tell it your IP address (found in step 4), with port 5900. In VNC viewer this is done by specifying $(IP):5900 in the VNC server line. 

The default password for this container is abcd1234, then you should be up and running!

The docker desktop boots into /source/viz/visualizer. You can run the command

        sh run.sh

From this directory to start the visualization software.
