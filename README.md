# Hello Micrometer

- <https://micrometer.io/>

## start

```sh
mvn spring-boot:run
```

## test

```sh
http -b localhost:8080/increment && http -b localhost:8080/actuator/metrics/hello.counter


{
    "availableTags": [],
    "description": "Counts hello",
    "measurements": [
        {
            "statistic": "COUNT",
            "value": 8.0
        }
    ],
    "name": "hello.counter"
}
```

```sh
http -b localhost:8080/time && http -b localhost:8080/actuator/metrics/hello.timer
http -b "localhost:8080/gauge?value=123" && http -b localhost:8080/actuator/metrics/hello.gauge
http -b "localhost:8080/record?amount=1.23" && http -b localhost:8080/actuator/metrics/custom.distribution.summary
```