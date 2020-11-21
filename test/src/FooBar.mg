USING cz.mg.types.Int AS Int32
USING cz.mg.stamps.public
USING cz.mg.stamps.private

@public
CLASS FooBar IS Foo, Bar
    GLOBAL Int32$ globe

    @public
    FUNCTION sayRiddle INPUT FooBar& self
        print: "The riddle is " + self.foo + " and " + self.bar
        print: "The riddle is " + self.foo + " and " + self.bar
        print: "The riddle is " + self.foo + " and " + self.bar
        print: "The riddle is " + self.foo + " and " + self.bar

    @public
    BINARY OPERATOR + INPUT Int32$ left, Int32$ right OUTPUT Int32$ result
        RETURN result $= left + right

    @public
    BINARY OPERATOR pp INPUT Int32$ left, Int32$ right OUTPUT Int32$ result PRIORITY "1"
        RETURN result $= left + right

    @public
    FUNCTION negative INPUT Int32$ value OUTPUT Int32$ result LEFT OPERATOR -
        RETURN result $= -value

    @public
    FUNCTION decrement INPUT Int32$ value OUTPUT Int32$ result RIGHT OPERATOR --
        RETURN result $= value - "1"