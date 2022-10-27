# marketplace
## Content
- [Installation](#installation-ubuntu)
- [Deployment](#deployment)
## Installation (Ubuntu)
### JDK 17
    sudo apt install openjdk-17-jdk-headless
    sudo update-alternatives --config java

## Installation (MacOs)
### JDK 17
Download jdk 17 for Apple Silicon chip [here](https://download.oracle.com/java/17/archive/jdk-17.0.4.1_macos-aarch64_bin.tar.gz)

Download jdk 17 for Intel chip [here](https://download.oracle.com/java/17/archive/jdk-17.0.4.1_macos-x64_bin.tar.gz)

The directory for the jdk is: `/Users/yourUser/Library/Java/JavaVirtualMachines`

To change the jdk version use following command: `export JAVA_HOME=$(/usr/libexec/java_home -v17)`


### IntelliJ Community [(Download)](https://www.jetbrains.com/idea/download/)

* _Get from VCS_ > _Enter GitHub [URL](https://github.com/jakobeberhardt/marketplace)_ 
* _Toggle  src/main/kotlin/de/neocargo/marketplace/MarketplaceApplication.kt_  >  _Run 'MarketplaceApplication.kt'_

## Deployment
Start deployment (MongoDB)

    cd docker
    docker-compose up -d

Start application on port 8080

    ./gradlew bootRun

Stops deployment
    
    cd docker
    docker-compose stop

Remove deployment

    cd docker 
    docker-compose down