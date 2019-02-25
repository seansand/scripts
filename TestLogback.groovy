
package chapters.introduction;


@Grab(group='org.slf4j', module='slf4j-api', version='1.7.25')
@Grab(group='ch.qos.logback', module='logback-core', version='1.2.3')
@Grab(group='ch.qos.logback', module='logback-classic', version='1.2.3', scope='test')

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import groovy.util.logging.Slf4j

@Slf4j
public class HelloWorld1 {

  public static void main(String[] args) {

    log.debug("Hello world.");

    try 
    {
        println("Try.")
        println(0 / 0)
    }
    catch (Exception e)
    {
       log.error("Exception happened", e)
       throw e
    }
  }
}