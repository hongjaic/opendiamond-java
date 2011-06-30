/*
 *  The OpenDiamond Platform for Interactive Search
 *  Version 5
 *
 *  Copyright (c) 2007, 2009-2010 Carnegie Mellon University
 *  All rights reserved.
 *
 *  This software is distributed under the terms of the Eclipse Public
 *  License, Version 1.0 which can be found in the file named LICENSE.
 *  ANY USE, REPRODUCTION OR DISTRIBUTION OF THIS SOFTWARE CONSTITUTES
 *  RECIPIENT'S ACCEPTANCE OF THIS AGREEMENT
 */

package edu.cmu.cs.diamond.opendiamond;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Diamond filter. Use with {@link SearchFactory} to perform Diamond searches.
 * 
 */
public class Filter {
    final private FilterCode code;

    final private List<String> dependencies;

    final private List<String> arguments;

    final private String name;

    final private double minScore;

    final private double maxScore;

    final private byte blob[];

    final private byte encodedBlob[];

    final private byte encodedBlobSig[];

    /**
     * Constructs a new filter with the given parameters (including blob
     * and maxScore).
     * 
     * @param name
     *            the name of this filter
     * @param code
     *            the binary code that implements the Filter
     * @param minScore
     *            the filter score below which an object will be dropped
     * @param maxScore
     *            the filter score above which an object will be dropped
     * @param dependencies
     *            a list of other filter names that this filter depends on
     * @param arguments
     *            a list of arguments to the filter
     * @param blob
     *            a binary argument to this filter
     */
    public Filter(String name, FilterCode code, double minScore,
            double maxScore, Collection<String> dependencies,
            List<String> arguments, byte blob[]) {

        // TODO check for valid characters as in filter_spec.l
        this.name = name.trim();
        this.code = code;
        this.minScore = minScore;
        this.maxScore = maxScore;

        this.dependencies = new ArrayList<String>(dependencies);
        this.arguments = new ArrayList<String>(arguments);

        this.blob = blob;

        XDR_sig_val sig = XDR_sig_val.createSignature(blob);
        encodedBlobSig = new XDR_blob_sig(name, sig).encode();
        encodedBlob = new XDR_blob(name, blob).encode();
    }

    /**
     * Constructs a new Filter with the given parameters (including
     * maxScore).
     * 
     * @param name
     *            the name of the new filter
     * @param code
     *            the binary code that implements the filter
     * @param minScore
     *            the filter score below which an object will be dropped
     * @param maxScore
     *            the filter score above which an object will be dropped
     * @param dependencies
     *            a list of other filter names that this filter depends on
     * @param arguments
     *            a list of arguments to the filter
     */
    public Filter(String name, FilterCode code, double minScore,
            double maxScore, Collection<String> dependencies,
            List<String> arguments) {
        this(name, code, minScore, maxScore, dependencies, arguments,
                new byte[0]);
    }

    /**
     * Constructs a new filter with the given parameters (including blob).
     * 
     * @param name
     *            the name of this filter
     * @param code
     *            the binary code that implements the Filter
     * @param minScore
     *            the filter score below which an object will be dropped
     * @param dependencies
     *            a list of other filter names that this filter depends on
     * @param arguments
     *            a list of arguments to the filter
     * @param blob
     *            a binary argument to this filter
     */
    public Filter(String name, FilterCode code, double minScore,
            Collection<String> dependencies, List<String> arguments,
            byte blob[]) {
        this(name, code, minScore, Double.POSITIVE_INFINITY, dependencies,
                arguments, blob);
    }

    /**
     * Constructs a new Filter with the given parameters.
     * 
     * @param name
     *            the name of the new filter
     * @param code
     *            the binary code that implements the filter
     * @param minScore
     *            the filter score below which an object will be dropped
     * @param dependencies
     *            a list of other filter names that this filter depends on
     * @param arguments
     *            a list of arguments to the filter
     */
    public Filter(String name, FilterCode code, double minScore,
            Collection<String> dependencies, List<String> arguments) {
        this(name, code, minScore, Double.POSITIVE_INFINITY, dependencies,
                arguments, new byte[0]);
    }

    @Override
    public String toString() {
        return getName() + ", encoded bloblen: " + getEncodedBlob().length;
    }

    String getFspec() {
        StringBuilder sb = new StringBuilder();

        sb.append("FILTER " + name + "\n");
        sb.append("THRESHOLD " + minScore + "\n");
        sb.append("UPPERTHRESHOLD " + maxScore + "\n");
        sb.append("SIGNATURE " + code.getSignature().asString() + "\n");

        for (String arg : arguments) {
            sb.append("ARG " + arg + "\n");
        }
        for (String req : dependencies) {
            sb.append("REQUIRES " + req + "\n");
        }

        return sb.toString();
    }

    /**
     * Gets the name of this filter.
     * 
     * @return the name of this filter
     */
    public String getName() {
        return name;
    }

    FilterCode getFilterCode() {
        return code;
    }

    byte[] getEncodedBlobSig() {
        return encodedBlobSig;
    }

    byte[] getEncodedBlob() {
        return encodedBlob;
    }

    List<String> getDependencies() {
        return dependencies;
    }

    List<String> getArguments() {
        return arguments;
    }

    double getMinScore() {
        return minScore;
    }

    double getMaxScore() {
        return maxScore;
    }

    byte[] getBlob() {
        return blob;
    }
}
