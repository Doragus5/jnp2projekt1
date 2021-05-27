package jnp2.projekt1.projekt;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.camel.Exchange;

import org.apache.camel.Processor;

import org.springframework.stereotype.Component;
import java.lang.*;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class AlertProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        System.out.println("To moje body:\n\n\n\n"+body+"\n\n\n\n");
        String reg = "(.\"Currency\":\"BTC\".*),.\"Currency\":\"LTC\"";
        Pattern r =  Pattern.compile(reg, Pattern.MULTILINE);
        Matcher matcher = r.matcher(body);
        String result="";
        if(matcher.find())
            result = matcher.group(0);
        result = result.substring(0,result.length()-18);
        exchange.getIn().setBody(result);

    }
}
