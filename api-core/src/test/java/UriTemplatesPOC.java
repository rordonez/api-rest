import org.junit.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by rafa on 08/06/14.
 */

public class UriTemplatesPOC {

    @Test
    public void uriTest() {
        UriComponents uri = UriComponentsBuilder.fromHttpUrl("http://www.google.es").queryParam("name", 2).queryParam("name2").pathSegment("{hola}").pathSegment("{seg}")
               .build();
        System.out.println("uri = " + uri.toString());
        System.out.println("uri = " + uri.toUriString());
        uri.expand("name",4);
        System.out.println("uri = " + uri.toUriString());

    }

}
