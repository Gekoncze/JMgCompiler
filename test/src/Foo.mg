USING cz.mg.types.Int AS Integer
USING cz.mg.stamps.public
USING cz.mg.stamps.private

@public
CLASS Foo
    @private Integer$ foo

    @public
    FUNCTION sayRiddle INPUT Foo& self
        print: "The riddle is:"
        print: self.foo