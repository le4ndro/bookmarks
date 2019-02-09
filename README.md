# Bookmarks
Sample API project for bookmarking web sites using Spring Boot and PostgreSQL

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software

* Java JDK 8
* Apache Maven
* PostgreSQL Database

### Installing

1. Clone this repository

```
$ git clone https://github.com/le4ndro/bookmarks.git
$ cd bookmarks
```

2. Create database on postgresql

```
$ createdb bookmarksdb
```

3. Make a local copy of set_env.sample.sh

```
$ cp set_env.sample.sh set_env.sh (or rename set_env.sample.bat to set_env.bat on Windows)
```

4. Edit your set_env.sh (or set_env.bat on Windows) file with your parameters and set variables for your environment

```
$ chmod 755 set_env.sh
$ source ./set_env.sh
(or run set_env.bat on Windows)
```

5. Run the application

```
$ mvn spring-boot:run
```

## Deployment

1. Create an executable jar

```
$ mvn package
```

2. Run the application

```
$ java -jar target/bookmarks-0.0.1-SNAPSHOT.jar
```

## Built With

* [Spring Boot](https://docs.spring.io/spring-boot/docs/1.3.1.RELEASE/reference/htmlsingle/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags).

## Authors

* **Leandro Souza** - *Initial work*

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* This project is in development