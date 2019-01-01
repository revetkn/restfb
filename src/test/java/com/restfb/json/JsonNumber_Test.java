/*******************************************************************************
 * Copyright (c) 2013, 2015 EclipseSource.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.restfb.json;

import static com.restfb.json.TestUtil.serializeAndDeserialize;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonNumber_Test {

  private StringWriter output;
  private JsonWriter writer;

  @BeforeEach
  public void setUp() {
    output = new StringWriter();
    writer = new JsonWriter(output);
  }

  @Test
  public void constructor_failsWithNull() {
    assertThrows(NullPointerException.class, () -> new JsonNumber(null), "string is null");
  }

  @Test
  public void write() throws IOException {
    new JsonNumber("23").write(writer);

    assertEquals("23", output.toString());
  }

  @Test
  public void toString_returnsInputString() {
    assertEquals("foo", new JsonNumber("foo").toString());
  }

  @Test
  public void isNumber() {
    assertTrue(new JsonNumber("23").isNumber());
  }

  @Test
  public void asInt() {
    assertEquals(23, new JsonNumber("23").asInt());
  }

  @Test
  public void asInt_failsWithExceedingValues() {
    assertThrows(NumberFormatException.class, () -> new JsonNumber("10000000000").asInt());
  }

  @Test
  public void asInt_failsWithExponent() {
    assertThrows(NumberFormatException.class, () -> new JsonNumber("1e5").asInt());
  }

  @Test
  public void asInt_failsWithFractional() {
    assertThrows(NumberFormatException.class, () -> new JsonNumber("23.5").asInt());
  }

  @Test
  public void asLong() {
    assertEquals(23l, new JsonNumber("23").asLong());
  }

  @Test
  public void asLong_failsWithExceedingValues() {
    assertThrows(NumberFormatException.class, () -> new JsonNumber("10000000000000000000").asLong());
  }

  @Test
  public void asLong_failsWithExponent() {
    assertThrows(NumberFormatException.class, () -> new JsonNumber("1e5").asLong());
  }

  @Test
  public void asLong_failsWithFractional() {
    assertThrows(NumberFormatException.class, () -> new JsonNumber("23.5").asLong());
  }

  @Test
  public void asFloat() {
    assertEquals(23.05f, new JsonNumber("23.05").asFloat());
  }

  @Test
  public void asFloat_returnsInfinityForExceedingValues() {
    assertEquals(Float.POSITIVE_INFINITY, new JsonNumber("1e50").asFloat());
    assertEquals(Float.NEGATIVE_INFINITY, new JsonNumber("-1e50").asFloat());
  }

  @Test
  public void asDouble() {
    double result = new JsonNumber("23.05").asDouble();

    assertEquals(23.05, result);
  }

  @Test
  public void asDouble_returnsInfinityForExceedingValues() {
    assertEquals(Double.POSITIVE_INFINITY, new JsonNumber("1e500").asDouble());
    assertEquals(Double.NEGATIVE_INFINITY, new JsonNumber("-1e500").asDouble());
  }

  @Test
  public void equals_trueForSameInstance() {
    JsonNumber number = new JsonNumber("23");

    assertTrue(number.equals(number));
  }

  @Test
  public void equals_trueForEqualNumberStrings() {
    assertTrue(new JsonNumber("23").equals(new JsonNumber("23")));
  }

  @Test
  public void equals_falseForDifferentNumberStrings() {
    assertFalse(new JsonNumber("23").equals(new JsonNumber("42")));
    assertFalse(new JsonNumber("1e+5").equals(new JsonNumber("1e5")));
  }

  @Test
  public void equals_falseForNull() {
    assertFalse(new JsonNumber("23").equals(null));
  }

  @Test
  public void equals_falseForSubclass() {
    assertFalse(new JsonNumber("23").equals(new JsonNumber("23") {}));
  }

  @Test
  public void hashCode_equalsForEqualStrings() {
    assertTrue(new JsonNumber("23").hashCode() == new JsonNumber("23").hashCode());
  }

  @Test
  public void hashCode_differsForDifferentStrings() {
    assertFalse(new JsonNumber("23").hashCode() == new JsonNumber("42").hashCode());
  }

  @Test
  public void canBeSerializedAndDeserialized() throws Exception {
    JsonNumber number = new JsonNumber("3.14");

    assertEquals(number, serializeAndDeserialize(number));
  }

}
