
## [SpringBoot Blog API](https://github.com/boolfalse/spring-boot-blog-api)



<img src="https://img-c.udemycdn.com/course/750x422/4261034_59d9_7.jpg" alt="Building Real-Time REST APIs with Spring Boot - Blog App">

#### Prerequisites:

- Java JDK [v17](https://www.oracle.com/java/technologies/downloads/#java17)



#### Setup:

- Setup [`Maven`](https://maven.apache.org/download.cgi).
```shell
# maven: download, extract and move to the executable folder
wget https://dlcdn.apache.org/maven/maven-3/3.9.3/binaries/apache-maven-3.9.3-bin.tar.gz
tar -xvf apache-maven-3.9.3-bin.tar.gz
sudo mv apache-maven-3.6.3 /opt/

# publish maven path
export PATH="$PATH:/opt/apache-maven-3.9.3/bin"

# set maven path to the default usable programs
nano ~/.bashrc
# add the line
PATH="$PATH:/opt/apache-maven-3.9.3/bin"
# reload the terminal or apply the changes
source ~/.bashrc
```

- Run the app via CLI:
```shell
mvn spring-boot:run
```



#### Resources:

- Udemy [Course](https://www.udemy.com/course/building-real-time-rest-apis-with-spring-boot/)
- Course's [source code](https://github.com/RameshMF/springboot-blog-rest-api)
- Download the course free from [www.downloadly.ir](https://downloadlynet.ir/2022/10/73153/04/building-real-time-rest-apis-with-spring-boot-blog-app/21/)

- Spring Initializr [configuration](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.2&packaging=jar&jvmVersion=17&groupId=am.github&artifactId=spring-boot-blog-api&name=spring-boot-blog-api&description=SpringBoot%20Blog%20REST%20API&packageName=am.github.spring-boot-blog-api&dependencies=web,data-jpa,mysql,lombok,devtools)



#### Author:

- [BoolFalse](https://boolfalse.com/)
