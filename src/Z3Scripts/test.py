from z3 import *
x = Real('x')
y = Real('y')
s = Solver()
s.add(x > 1, y > 1, Or(x + y > 3, x - y < 2))
print(s.check())
if s.check() == sat:
	m = s.model()
	for d in m.decls():
		print("%s = %s" % (d.name(), m[d]))
else:
	print(unsat)
