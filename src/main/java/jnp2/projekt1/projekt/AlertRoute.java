package jnp2.projekt1.projekt;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AlertRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //restConfiguration().host("{{host}}");

        from("timer:scheduler?period=5000")
                .setHeader(Exchange.HTTP_METHOD).constant("GET")
                .to("https://api.bittrex.com/api/v1.1/public/getcurrencies")
                //.to("https://v2.jokeapi.dev/joke/Any?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&type=single")
                .to("direct://sendAlert");

        from("direct://sendAlert")
                .sample(1, TimeUnit.SECONDS)
                .process(new AlertProcessor())
                .setHeader("to",simple("micha.soczewicy@gmail.com"))
                .to("smtps://smtp.gmail.com?username={{mail.user}}&password={{mail.password}}")

                .log("Send alert: ${body}");
    }
}