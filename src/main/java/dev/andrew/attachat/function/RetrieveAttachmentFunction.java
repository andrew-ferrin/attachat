package dev.andrew.attachat.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.github.sashirestela.openai.common.function.Functional;

public class RetrieveAttachmentFunction implements Functional {

    @JsonProperty(required = true)
    @JsonPropertyDescription("The identifier of the parent document to which the attachment belongs")
    public String attachmentParentDocument;

    @JsonProperty(required = true)
    @JsonPropertyDescription("The attachment filename (metadata only; not used to retrieve the attachment)")
    public String attachmentFilename;

    @JsonProperty(required = true)
    @JsonPropertyDescription("The attachment sequence number, used to retrieve the attachment")
    public String attachmentSequenceNumber;

    @Override
    public Object execute() {
        throw new UnsupportedOperationException("This function is never meant to be executed.");
    }

}
