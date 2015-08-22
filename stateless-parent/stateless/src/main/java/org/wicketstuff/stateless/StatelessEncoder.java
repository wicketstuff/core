/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.stateless;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.INamedParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.encoding.UrlEncoder;

/**
 * Centralize algorithms that are shared.
 * 
 * @author jfk
 */
public class StatelessEncoder
{
    /**
     * Merges the query parameters of the url with the named parameters
     * from the {@link PageParameters}. The page parameters override the query
     * parameters.
     *
     * @param url the url with the original parameters
     * @param params the page parameters to merge
     * @return an Url with merged parameters
     */
    public static Url mergeParameters(final Url url, final PageParameters params)
    {
        if (params == null)
        {
            return url;
        }

        Charset charset = url.getCharset();

        Url mergedUrl = Url.parse(url.toString(), charset);

        UrlEncoder urlEncoder = UrlEncoder.QUERY_INSTANCE;

        Set<String> setParameters = new HashSet<String>();

        int indexedCount = params.getIndexedCount();
        if (indexedCount > 0) 
        {
            String jsessionidString = null;
            List<String> segments = mergedUrl.getSegments();
            if (segments.size() > 0) 
            {
                String lastSegment = segments.get(segments.size() - 1);
                int jsessionidIndex = lastSegment.indexOf(";jsessionid=");
                if (jsessionidIndex != -1) 
                {
                   segments.set(segments.size() - 1, lastSegment.substring(0, jsessionidIndex));
                   jsessionidString = lastSegment.substring(jsessionidIndex);
                }
            }
            for (int i = 0; i < indexedCount; i++)
            {
                segments.add(params.get(i).toString());
            }
            if (jsessionidString != null)
            {
                segments.set(segments.size() - 1, segments.get(segments.size() - 1).concat(jsessionidString));
            }
        }

        for (INamedParameters.NamedPair pair : params.getAllNamed())
        {
            String key = urlEncoder.encode(pair.getKey(), charset);
            String value = urlEncoder.encode(pair.getValue(), charset);

            if (setParameters.contains(key)) {
                mergedUrl.addQueryParameter(key, value);
            } else {
                mergedUrl.setQueryParameter(key, value);
                setParameters.add(key);
            }
        }

        return mergedUrl;
    }

    private StatelessEncoder()
    {// forbid instantiation
    }
}
