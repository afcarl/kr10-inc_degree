
#!/usr/bin/env python

import time
import os

dataPathRoot = './data/DC_unsat/unsat'
dataPathRoot = './data/UUF250.1065.100'
dataPathRoot = './data/UUF50.218.1000'
dataPathRoot = './data/UUF75.325.100'
def main():
    for filename in os.listdir(dataPathRoot):
        if filename[-4:]==".cnf":
            #print  filename
            cnffile = os.path.join(dataPathRoot, filename)
            testOneFile(cnffile)    

def testOneFile(cnffile):    
    t1 = time.time()
    print ("t1 = %f") % (t1);
    wcnffile = cnffile[: - 4] + ".wcnf"    
    print "cnfFile = %s" % cnffile
    print "wcnfFile = %s" % wcnffile

    print "translation begin"

    classpath = "./bin;./lib/commons-beanutils.jar;./lib/commons-cli.jar;./lib/commons-logging.jar;./lib/cspparserxml.jar;./lib/org.sat4j.core.jar;./lib/org.sat4j.pb.jar;./lib/org.sat4j.maxsat.jar"
#    classpath = "./bin:./lib/commons-beanutils.jar:./lib/commons-cli.jar:./lib/commons-logging.jar:./lib/cspparserxml.jar:./lib/org.sat4j.core.jar:./lib/org.sat4j.pb.jar:./lib/org.sat4j.maxsat.jar"
    translaterMode = "4"
    translaterClass = "edu.pku.id.MultiValuedTranslaterCli"
    cmd = "java -cp %s %s -m %s %s %s" % (classpath, translaterClass, translaterMode, cnffile, wcnffile)
    #print cmd
    os.system(cmd)

    print "translation done"
    t2 = time.time()
    print ("t2 = %f") % (t2);

    maxSat = "sat4j"
    
    maxModelFile = cnffile[: - 4] + ".model"
    if(maxSat == "sat4j"):
        maxSatClass = "org.sat4j.maxsat.GenericOptLauncher"
        os.system("java -cp %s %s %s > %s" % (classpath, maxSatClass, wcnffile, maxModelFile));
    elif maxSat == "clone":
        os.system("./clone.sh %s > %s" % (wcnffile, maxModelFile))
    
    idClass = "edu.pku.id.ResultModelReader"
    os.system("java -cp %s %s %s" % (classpath, idClass, maxModelFile));

    t3 = time.time()
    dt = t3 - t1
    
    print "time consuming %f (s)\n" % dt

if __name__ == '__main__':
    main()