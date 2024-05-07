package au.com.vanguard.demo.weatherapi.client.key;

/** Strategy interface for obtaining client API Keys */
public interface ClientAPIKeyStrategy {

    String getNext();
}
