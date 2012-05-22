/*
 *  The OpenDiamond Platform for Interactive Search
 *
 *  Copyright (c) 2009 Carnegie Mellon University
 *  All rights reserved.
 *
 *  This software is distributed under the terms of the Eclipse Public
 *  License, Version 1.0 which can be found in the file named LICENSE.
 *  ANY USE, REPRODUCTION OR DISTRIBUTION OF THIS SOFTWARE CONSTITUTES
 *  RECIPIENT'S ACCEPTANCE OF THIS AGREEMENT
 */

package edu.cmu.cs.diamond.opendiamond;

import java.util.Set;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class XDR_reexecute implements XDREncodeable {
    private final String objectID;

    private final XDR_attr_name_list attributes;

    public XDR_reexecute(String objectID, Set<String> attributes) {
        this.objectID = objectID;

        if (attributes != null && !attributes.isEmpty()) {
            this.attributes = new XDR_attr_name_list(attributes);
        } else {
            this.attributes = null;
        }
    }

    public byte[] encode() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        try {
            out.write(XDREncoders.encodeString(objectID));

            // Optional attributes list
            if (attributes != null) {
                out.writeInt(1);
                out.write(attributes.encode());
            } else {
                out.writeInt(0);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }
}
