package edu.cmu.cs.diamond.opendiamond;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Result {
    final protected Map<String, byte[]> attributes = new HashMap<String, byte[]>();

    public byte[] getData() {
        return getValue("");
    }

    public byte[] getValue(String key) {
        return attributes.get(key);
    }

    public Set<String> getKeys() {
        return Collections.unmodifiableSet(attributes.keySet());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Result [");

        for (String name : getKeys()) {
            byte value[] = getValue(name);
            sb.append(" '" + name + "'");
            if (value.length == 0) {
                sb.append(":" + "''");
            } else if (name.endsWith(".int")) {
                sb.append(":" + Util.extractInt(value));
            } else if (name.endsWith("-Name")) {
                sb.append(":'" + Util.extractString(value) + "'");
            } else if (name.endsWith(".time")) {
                sb.append(":" + Util.extractLong(value));
            }
            sb.append(" (" + value.length + ")");
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String getServerName() {
        return Util.extractString(getValue("Device-Name"));
    }

    public String getObjectName() {
        return Util.extractString(getValue("Display-Name"));
    }

    String getObjectID() {
        return Util.extractString(getValue("_ObjectID"));
    }
}