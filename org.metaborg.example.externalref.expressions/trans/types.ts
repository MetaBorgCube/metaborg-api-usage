module analysis/types

imports

  signatures/-
    
type rules

  IntLiteral(_)   : IntTy()
  StringLiteral(_): StringTy()

type rules

  Add(e1, e2) +
  Mul(e1, e2) : IntTy()
  where e1: IntTy()
   else error "expected numeric argument" on e1
    and e2: IntTy()
   else error "expected numeric argument" on e2
   
  Conc(e1, e2) : StringTy()
  where e1: StringTy()
   else error "expected character or string argument" on e1
    and e2: StringTy()
   else error "expected character or string argument" on e2
  