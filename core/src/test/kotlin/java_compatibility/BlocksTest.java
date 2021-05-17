package java_compatibility;

import notion.api.v1.model.blocks.ParagraphBlock;
import notion.api.v1.model.pages.PageProperty;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;

public class BlocksTest {

    @Test
    public void paragraph() {
        ParagraphBlock block = new ParagraphBlock(
                "id",
                new ParagraphBlock.Element(
                        Arrays.asList(new PageProperty.RichText()),
                        Collections.emptyList()
                ),
                false,
                "2021-05-01",
                "2021-05-02"
        );
        assertNotNull(block);
    }

    // TODO: add tests for others
}
