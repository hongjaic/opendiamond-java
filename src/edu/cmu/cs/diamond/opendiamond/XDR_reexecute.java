package edu.cmu.cs.diamond.opendiamond;

import java.util.Arrays;
import java.util.Set;

public class XDR_reexecute implements XDREncodeable {
    private final String objectID;

    private final XDR_attr_name_list attributes;

    public XDR_reexecute(String objectID, Set<String> attributes) {
        this.objectID = objectID;
        this.attributes = new XDR_attr_name_list(attributes);
    }

    @Override
    public byte[] encode() {
        byte b1[] = XDREncoders.encodeString(objectID);
        byte b2[] = attributes.encode();

        byte result[] = Arrays.copyOf(b1, b1.length + b2.length);
        System.arraycopy(b2, 0, result, b1.length, b2.length);

        return result;
    }
}