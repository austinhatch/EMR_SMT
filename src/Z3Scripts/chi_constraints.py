from z3 import *
general_chi = Int( 'general_chi' )
general_dob = Real( 'general_dob' )
general_gender = Int( 'general_gender' )
general_name = String( 'general_name' )
s = Solver()
gender_assign = (general_gender == 2)
s.add(gender_assign)
dob_assign = (general_dob == 418873019497)
s.add(dob_assign)
name_assign = (general_name == "LILLIE DOW")
s.add(name_assign)
chi_assign = (general_chi == 1104833329)
s.add(chi_assign)
general_chi_AllDiff = ()
s.add(general_chi_AllDiff)
if s.check() == sat:
	m = s.model()
	for d in m.decls():
		print("%s = %s" % (d.name(), m[d]))
else:
	print(unsat)
	print(s.proof())
