#!/usr/bin/python

import time
import os
from optparse import OptionParser


dataPathRoot = './data/DC_unsat/unsat'
dataPathRoot = './data/UUF250.1065.100'
dataPathRoot = './data/UUF75.325.100'
dataPathRoot = './data/UUF50.218.1000'
dataPathRoot = './data/paper'
#dataPathRoot = './data/my'
dataPathRoot = './data/KSEM09'



def main():
    usage = "usage: id.py [options] instance.cnf"
    parser = OptionParser(usage)
    parser.add_option("-s", "--semantics", dest="semantics",
                  help="the semantics used for inconsistency measure: 4 or Q", default='4')

    parser.add_option("-p", "--partial-max-sat-solver", dest="partialMaxSATSolver",
                  help="partialMaxSATSolver: sat4j or clone or msuncore or minimaxsat", default='sat4j')


    (options, args) = parser.parse_args()
    
    semantics = options.semantics
    maxSat = options.partialMaxSATSolver

    #print parser.usage()

    cnffile = args[0]
    encoding = "p"

    testOneFile(cnffile,semantics,maxSat,encoding)
    

def testOneFile(cnffile,semantics,maxSat,encoding):   
    timeout = 120 

    t1 = time.time()
    wcnffile = cnffile[:-4] + "." + encoding + ".wcnf"    
    name = cnffile[cnffile.rfind(os.sep) + 1:]

    timeout = 120
    classpath = "./bin:./lib/commons-beanutils.jar:./lib/commons-cli.jar:./lib/commons-logging.jar:./lib/cspparserxml.jar:./lib/org.sat4j.core.jar:./lib/org.sat4j.pb.jar:./lib/org.sat4j.maxsat.jar".replace(":", os.pathsep)
    
    encoderClass = "edu.pku.id.cli.IncDegreeEncoderCli"
    cmd = "java -cp %s %s -s %s -e %s %s %s" % (classpath, encoderClass, semantics, encoding, cnffile, wcnffile)

    os.system(cmd)
    
    maxModelFile = cnffile[:-4] + ".model"
    
    if(maxSat == "sat4j"):
        maxSatClass = "org.sat4j.maxsat.GenericOptLauncher"
        os.system("java -Xmx512m -Xms256m -cp %s %s -t %d %s > %s" % (classpath, maxSatClass, timeout, wcnffile, maxModelFile));
    elif maxSat == "clone":
        os.system("./clone.sh %s > %s" % (wcnffile, maxModelFile))
    elif maxSat == "msuncore":
        os.system("./msuncore -T %d %s > %s" % (timeout, wcnffile, maxModelFile))
    elif maxSat == "minimaxsat":
        os.system("./minimaxsat1.0 -F=2 %s > %s" % (wcnffile, maxModelFile))
    
    t2 = time.time()
    
    idClass = "edu.pku.id.cli.ResultModelReader"
    
    os.system("java -cp %s %s %s" % (classpath, idClass, maxModelFile));

    
    #print "time consuming %f (s)\n" % dt
    print "time:%fs" % (t2-t1)
    
if __name__ == '__main__':
    main()
