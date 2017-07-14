/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.operation.export.graph;

import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.export.Exporter;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.user.User;

public class OtherGraphExporter implements Exporter {
    private final String jobId;
    private final Graph graph;
    private final User user;


    public OtherGraphExporter(final User user, final String jobId, final Graph graph) {
        this.user = user;
        this.jobId = jobId;
        this.graph = graph;
    }

    @Override
    public void add(final String key, final Iterable<?> elements) throws OperationException {
        if (null == elements) {
            return;
        }

        graph.execute(new AddElements.Builder()
                .input((Iterable<Element>) elements)
                .build(), user);
    }

    @Override
    public CloseableIterable<?> get(final String key) throws OperationException {
        throw new UnsupportedOperationException("Getting export from another Graph is not supported");
    }

    protected String getJobId() {
        return jobId;
    }

    protected User getUser() {
        return user;
    }

    protected Graph getGraph() {
        return graph;
    }
}
