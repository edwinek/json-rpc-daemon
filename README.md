# JSON-RPC Daemon
I intended this to be an extended version of the [`example-daemon`](https://github.com/edwinek/example-daemon) project using [`jsonrcp4j`](https://github.com/briandilley/jsonrpc4j) for JSON-RPC, however I ended up going down a completely new route (for me), in the form of Spring Boot.

The daemon runs on an Ubuntu endpoint and exposes a `greeter` JSON-RPC API, running in an embedded Tomcat instance. 

### Prerequisites
* Docker
* Git

### Setup
1. Clone the repos:
   * `git clone https://www.github.com/edwinek/json-rpc-daemon`
2. Change into repos dir and make the build script executable:
   * `cd json-rpc-daemon`
   * `chmod +x build.sh`
3. Run the script:
    * `./build.sh`
    * This will create the Docker image, start a container with an interactive bash terminal.
    
### Test
1. From the bash terminal on the Docker container, run `service json-rpc-daemon start`
    * The service will start in the background.
2. From a second terminal window on your host machine test the endpoint as follows:
```bash
curl -H "Content-Type:application/json" -d '{"id":"1","jsonrpc":"2.0","method":"greet","params":{"name":"Edwinek"}}' http://localhost:8080/greeter
``` 
3. From the Docker terminal, stop the service with `service json-rpc-daemon stop`
4. Observe that the service has stopped by inspecting the log on the Docker terminal in `/var/log/json-rpc-daemon`
5. You can also check that the curl command in the second terminal no longer works while the service has stopped
    
###  Tidyup
1. Close the connection to each of the Docker bash sessions by using `Control-D`
2. The Docker container can then be stopped with:
   * `docker stop json_rpc_container`
   * The container will delete itself upon being stopped
3. The image snapshots can be deleted with:
   * `docker rmi json_rpc_image:latest`
 
### Project breakdown
#### Docker container
The container runs the minimal image of Ubuntu 18.04, with the OpenJDK Java 8 JDK / runtime. I opted for Ubuntu over something like Alpine, for the`SysVinit` support. Unlike the `example-daemon` project, an additional binary was not required to hook the OS calls into the daemon - Spring Boot gives us this 'for free'.
#### Gradle
* The Spring Boot plugin provides the `bootJar`, task and so no modification was required to create a jar-with-dependencies (unlike with the `example-daemon` project). 
* The `bootJar` task extends the original `jar` task, and so respects the same config; we specify `/tmp` on on the container to ensure that no Docker-owned files are created on the host file system, and as per convention we ensure that the jar is located in the `/opt` directory upon creation. 
* Additionally we specify the class that contains the `main` method.
* Finally, the `launchScript()` command, builds an additional header into the executable Jar, so that the OS can treat it like a daemon.
#### Logging
Logging is provided out of the box, by Spring Boot and so no additional config was required. A log file is created at `/var/log/json-rpc-daemon.log`.
#### Scripts
* build.sh
    * Creates the image based on the Dockerfile
    * Spins up a detached instance of the container running bash
    * Builds the jar using the gradlew wrapper
    * Creates a symlink to the Jar in `/etc/init.d` and sets its permissions
    * Attaches a bash session to the container
