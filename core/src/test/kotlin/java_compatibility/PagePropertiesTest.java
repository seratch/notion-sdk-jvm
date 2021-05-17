package java_compatibility;

import notion.api.v1.model.pages.PageProperty;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PagePropertiesTest {

    @Test
    public void noArgConstructorShouldWork() {
        PageProperty property = new PageProperty();
        assertNotNull(property);
    }

    @Test
    public void richText() {
        PageProperty.RichText richText = new PageProperty.RichText();
        assertNotNull(richText);

        PageProperty.RichText.Text text = new PageProperty.RichText.Text();
        text.setContent("Something");
        PageProperty.RichText.Link link = new PageProperty.RichText.Link();
        link.setUrl("https://www.example.com/");
        text.setLink(link);
        richText.setText(text);
    }

    @Test
    public void formula() {
        PageProperty.Formula formula = new PageProperty.Formula("boolean");
        formula.setBoolean(false);
        assertNotNull(formula);
    }

    @Test
    public void date() {
        PageProperty.Date date = new PageProperty.Date();
        date.setStart("2021-05-13");
        assertNotNull(date);
    }
}
