USING cz.mg.types.Int AS Integer
USING cz.mg.stamps.public
USING cz.mg.stamps.private

@public
CLASS Bar
    @private Integer$ bar

    @public
    FUNCTION sayRiddle INPUT Bar& self
        print: "The riddle is: "
        print: self.bar