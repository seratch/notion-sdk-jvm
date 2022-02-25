package tests.java_compatibility;

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
                new ParagraphBlock.Element(
                        Arrays.asList(new PageProperty.RichText()),
                        Collections.emptyList()
                ),
                "id",
                false,
                "2021-05-01",
                null,
                "2021-05-02",
                null
        );
        assertNotNull(block);
    }

    // TODO: add tests for others
}
