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

package uk.gov.gchq.gaffer.operation.impl.add;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Sets;
import uk.gov.gchq.gaffer.generator.TestGeneratorImpl;
import uk.gov.gchq.gaffer.commonutil.JsonAssert;
import uk.gov.gchq.gaffer.exception.SerialisationException;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.operation.OperationTest;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class AddElementsFromFileTest extends OperationTest {
    @Override
    protected Class<? extends Operation> getOperationClass() {
        return AddElementsFromFile.class;
    }

    @Override
    public void shouldSerialiseAndDeserialiseOperation() throws SerialisationException, JsonProcessingException {
        // Given
        final boolean validate = true;
        final boolean skipInvalid = false;
        final String filename = "filename";
        final Integer parallelism = 2;
        final Class<TestGeneratorImpl> generator = TestGeneratorImpl.class;
        final AddElementsFromFile op = new AddElementsFromFile.Builder()
                .filename(filename)
                .generator(generator)
                .parallelism(parallelism)
                .validate(validate)
                .skipInvalidElements(skipInvalid)
                .build();

        // When
        final byte[] json = JSON_SERIALISER.serialise(op, true);
        final AddElementsFromFile deserialisedOp = JSON_SERIALISER.deserialise(json, AddElementsFromFile.class);

        // Then
        JsonAssert.assertEquals(String.format("{%n" +
                        "  \"class\" : \"uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile\",%n" +
                        "  \"filename\" : \"filename\",%n" +
                        "  \"parallelism\" : 2,%n" +
                        "  \"elementGenerator\" : \"uk.gov.gchq.gaffer.generator.TestGeneratorImpl\",%n" +
                        "  \"validate\" : true,%n" +
                        "  \"skipInvalidElements\" : false%n" +
                        "}").getBytes(),
                json);
        assertEquals(filename, deserialisedOp.getFilename());
        assertEquals(generator, deserialisedOp.getElementGenerator());
        assertEquals(parallelism, deserialisedOp.getParallelism());
        assertEquals(validate, deserialisedOp.isValidate());
        assertEquals(skipInvalid, deserialisedOp.isSkipInvalidElements());
    }

    @Override
    public void builderShouldCreatePopulatedOperation() {
        // Given
        final boolean validate = true;
        final boolean skipInvalid = false;
        final String filename = "filename";
        final Integer parallelism = 2;
        final Class<TestGeneratorImpl> generator = TestGeneratorImpl.class;

        // When
        final AddElementsFromFile op = new AddElementsFromFile.Builder()
                .filename(filename)
                .generator(generator)
                .parallelism(parallelism)
                .validate(validate)
                .skipInvalidElements(skipInvalid)
                .build();

        // Then
        assertEquals(filename, op.getFilename());
        assertEquals(generator, op.getElementGenerator());
        assertEquals(parallelism, op.getParallelism());
        assertEquals(validate, op.isValidate());
        assertEquals(skipInvalid, op.isSkipInvalidElements());
    }

    @Override
    protected Set<String> getRequiredFields() {
        return Sets.newHashSet("filename", "elementGenerator");
    }
}
