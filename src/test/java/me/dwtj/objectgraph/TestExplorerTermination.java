package me.dwtj.objectgraph;

import java.text.MessageFormat;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests the `Explorer` class (with a default `Visitor` and `Navigator`) to make sure that
 * exploration terminates when expected.
 */
public class TestExplorerTermination
{
    public static final int NUM_PRIMITIVE_TYPES = 8;

    Explorer default_explorer;

    @Before
    public void setUp() {
        default_explorer = new Explorer();
    }

    @Test
    public void nullTest()
    {
        final int EXPECTED_COUNT = 0;
        Object nullObj = null;
        default_explorer.explore(nullObj);
        TestVisitCount.assertVisitCount(default_explorer, nullObj, EXPECTED_COUNT);
    }

    @Test
    public void nullFieldTest()
    {
        List<String> listWithNullHeadField = new List<String>(null);
        default_explorer.explore(listWithNullHeadField);
        TestVisitCount.assertVisitCount(default_explorer, listWithNullHeadField, 1);
    }

    @Test
    public void primitivesTest()
    {
        final int EXPECTED_COUNT = 1;
        PrimitiveHolder holder = new PrimitiveHolder();
        default_explorer.explore(holder);
        TestVisitCount.assertVisitCount(default_explorer, holder, EXPECTED_COUNT);
    }

    @Test
    public void boxedPrimitivesTest()
    {
        final int EXPECTED_COUNT = 1 + NUM_PRIMITIVE_TYPES;
        BoxedPrimitiveHolder holder = new BoxedPrimitiveHolder();
        default_explorer.explore(holder);
        TestVisitCount.assertVisitCount(default_explorer, holder, EXPECTED_COUNT);
    }

    @Test
    public void stringTest()
    {
        final int EXPECTED_COUNT = 1;
        String str = "foo";
        default_explorer.explore(str);
        TestVisitCount.assertVisitCount(default_explorer, str, EXPECTED_COUNT);
    }

    /**
     * A class used in tests to make sure that the explorer does not attempt to navigate towards
     * primitive fields.
     */
    private static class PrimitiveHolder
    {
        short _short;
        byte _byte;
        int _int;
        long _long;
        float _float;
        double _double;
        char _char;
        boolean _boolean;
    }

    /**
     * A class used in tests to make sure that the default Explorer does not attempt to navigate
     * beyond boxed primitives.
     */
    private static class BoxedPrimitiveHolder
    {
        Short _short = 42;
        Byte _byte = 42;
        Integer _integer = 42;
        Long _long = 42L;
        Float _float = 42.0f;
        Double _double = 42.0;
        Character _character = 'c';
        Boolean _boolean = true;
    }
}