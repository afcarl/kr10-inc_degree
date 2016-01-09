
#!/usr/bin/env python

import time
import os

#print "hello world"

#if __name__ == '__main__':
#    cnffile = sys.argv[0]
if True:
    t1 = time.time()
    
    cnffile = "./data/UUF50.218.1000/uuf50-010.cnf"
    #cnffile = "./data/UUF75.325.100/uuf75-010.cnf"
    #cnffile = "./tmp/easy.cnf"
    wcnffile = cnffile[: - 4] + ".wcnf"    
    print "cnfFile = %s\n" % cnffile
    print "wcnfFile = %s\n" % wcnffile

    print "trsanlating\n"



    classpath = "./bin;./lib/commons-beanutils.jar;./lib/commons-cli.jar;./lib/commons-logging.jar;./lib/cspparserxml.jar;./lib/org.sat4j.core.jar;./lib/org.sat4j.pb.jar;./lib/org.sat4j.maxsat.jar"
#    classpath = "./bin:./lib/commons-beanutils.jar:./lib/commons-cli.jar:./lib/commons-logging.jar:./lib/cspparserxml.jar:./lib/org.sat4j.core.jar:./lib/org.sat4j.pb.jar:./lib/org.sat4j.maxsat.jar"
    translaterMode = "4"
    translaterClass = "edu.pku.id.MultiValuedTranslaterCli"
    cmd = "java -cp %s %s -m %s %s %s" % (classpath, translaterClass, translaterMode, cnffile, wcnffile)
    #print cmd
    os.system(cmd)

    maxSat = "sat4j"
    
    maxModelFile = cnffile[: - 4] + ".model"
    if(maxSat == "sat4j"):
        maxSatClass = "org.sat4j.maxsat.GenericOptLauncher"
        os.system("java -cp %s %s %s > %s" % (classpath, maxSatClass, wcnffile, maxModelFile));
    elif maxSat == "clone":
        os.system("./clone.sh %s > %s" % (wcnffile, maxModelFile))
    
    idClass = "edu.pku.id.ResultModelReader"
    os.system("java -cp %s %s %s" % (classpath, idClass, maxModelFile));

    t2 = time.time()
    dt = t2 - t1
    
    print "time consuming %d(ms)" % dt

#    os.system("./covers " + cnffile + " > " + "mcs.mcs")
#    os.system("./construct " + "mcs.mcs" + " > " + "mus.mus")
#    os.system("./stratifiedIncMeasurer " + "mus.mus" + " > " + "miv.miv")