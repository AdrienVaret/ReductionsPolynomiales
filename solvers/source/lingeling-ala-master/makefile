MAKEFLAGS=-j $(if $(CORES),$(CORES),1)
CC=gcc
CFLAGS=-Wall -O3 -DNLGLOG -DNDEBUG -DNCHKSOL -DNLGLPICOSAT $(PROF)
LIBS=-lm
LDEPS=
HDEPS=
AIGER=
all: targets
targets: liblgl.a lingeling plingeling ilingeling 
analyze:
	clang --analyze $(CFLAGS) $(shell ls *.c *.h)
liblgl.a: lglib.o lglbnr.o makefile
	ar rc $@ lglib.o lglbnr.o
	ranlib $@
lingeling: lglmain.o liblgl.a makefile $(LDEPS)
	$(CC) $(CFLAGS) -o $@ lglmain.o -L. -llgl $(LIBS)
plingeling: plingeling.o liblgl.a makefile $(LDEPS)
	$(CC) $(CFLAGS) -pthread -o $@ plingeling.o -L. -llgl $(LIBS)
blimc: blimc.o liblgl.a makefile $(AIGER)/aiger.o $(LDEPS)
	$(CC) $(CFLAGS) -o $@ blimc.o -L. $(AIGER)/aiger.o -llgl $(LIBS)
ilingeling: ilingeling.o liblgl.a makefile $(LDEPS)
	$(CC) $(CFLAGS) -pthread -o $@ ilingeling.o -L. -llgl $(LIBS)
lglmain.o: lglmain.c lglib.h makefile
	$(CC) $(CFLAGS) -c lglmain.c
plingeling.o: plingeling.c lglib.h makefile
	$(CC) $(CFLAGS) -c plingeling.c
ilingeling.o: ilingeling.c lglib.h makefile
	$(CC) $(CFLAGS) -c ilingeling.c
blimc.o: blimc.c lglib.h $(AIGER)/aiger.h makefile
	$(CC) $(CFLAGS) -I$(AIGER) -c -o $@ blimc.c
lglib.o: lglib.c lglib.h makefile $(HDEPS)
	$(CC) $(CFLAGS) -c lglib.c
lglbnr.o: lglbnr.c lglcfg.h lglcflags.h makefile
	$(CC) $(CFLAGS) -c lglbnr.c
lglcfg.h: VERSION mkconfig lglbnr.c lglib.c lglmain.c lglcflags.h makefile
	rm -f $@
	./mkconfig > $@
lglcflags.h: makefile
	rm -f $@
	echo '#define LGL_CC "$(shell $(CC) --version|head -1)"' >> $@
	echo '#define LGL_CFLAGS "$(CFLAGS)"' >> $@
clean: clean-other clean-config
clean-config:
	rm -f makefile lglcfg.h lglcflags.h
clean-other:
	rm -f lingeling plingeling ilingeling blimc
	rm -f *.gcno *.gcda cscope.out gmon.out *.gcov *.gch *.plist
	rm -f *.E *.o *.s *.a log/*.log 
.PHONY: all targets clean clean-config clean-other
