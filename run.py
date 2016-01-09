#!/usr/bin/python

import time
import os

dataPathRoot = './data/DC_unsat/unsat'
dataPathRoot = './data/UUF250.1065.100'
dataPathRoot = './data/UUF75.325.100'
dataPathRoot = './data/UUF50.218.1000'
dataPathRoot = './data/paper'
#dataPathRoot = './data/my'
#dataPathRoot = './data/KSEM09'

semantics = "q"
#semantics = "q"

encoding = "p"

def main():
    print "instance name \tid_%s\tsat4j\tmsuncore\tclone\tminimaxsat\t%s" % (semantics,encoding)

#    for filename in os.listdir(dataPathRoot):
#        if filename[:4]=="C170" and filename[-4:] == ".cnf":
#            #print  filename
#            cnffile = os.path.join(dataPathRoot, filename)
#            testOneFile(cnffile)


    testOneFile('./data/paper/C170_FR_SZ_96.cnf')
#    testOneFile('./data/my/t.cnf')



def testOneFile(cnffile):

    t1 = time.time()
#    print ("t1 = %f") % (t1);
    wcnffile = cnffile[:-4] + "." + encoding + ".wcnf"
    name = cnffile[cnffile.rfind(os.sep) + 1:]
#   print name

#    print "cnfFile = %s" % cnffile
#    print "wcnfFile = %s" % wcnffile

    #print "translation begin"

    #timeout = 120
    timeout = 240

    classpath = "./bin:./lib/commons-beanutils.jar:./lib/commons-cli-1.2.jar:./lib/commons-logging.jar:./lib/cspparserxml.jar:./lib/org.sat4j.core.jar:./lib/org.sat4j.pb.jar:./lib/org.sat4j.maxsat.jar:./lib/log4j-1.2.14.jar:./lib/slf4j-api-1.5.10.jar:./lib/sli4j-core-2.0.jar:./lib/sli4j-slf4j-log4j-2.0.jar:./lib/slf4j-log4j12-1.5.10.jar".replace(":", os.pathsep)

    encoderClass = "edu.pku.id.cli.IncDegreeEncoderCli"
    cmd = "java -cp %s %s -s %s -e %s %s %s" % (classpath, encoderClass, semantics, encoding, cnffile, wcnffile)
    #print cmd
    os.system(cmd)

    print "translation done"
    t2 = time.time()
#    print ("t2 = %f") % (t2);

    maxSat = "sat4j"

    maxModelFile = cnffile[:-4] + ".model"

    #if(maxSat == "sat4j"):
    maxSatClass = "org.sat4j.maxsat.GenericOptLauncher"
    os.system("java -Xmx512m -Xms256m -cp %s %s -t %d %s > %s" % (classpath, maxSatClass, timeout, wcnffile, maxModelFile));

    t3 = time.time()

    print "sat4j done"

    #elif maxSat == "clone":

    #os.system("./msuncore -T %d %s > %s" % (timeout, wcnffile, maxModelFile))

    t4 = time.time()

    #os.system("./clone.sh %s > %s" % (wcnffile, maxModelFile))

    t5 = time.time()

    #./minimaxsat1.0 -F=2 file.wcnf
    #os.system("./minimaxsat1.0 -F=2 %s > %s" % (wcnffile, maxModelFile))

    t6 = time.time()

    idClass = "edu.pku.id.cli.ResultModelReader"

    #if semantics == "4":
    os.system("java -cp %s %s %s" % (classpath, idClass, maxModelFile));


    print "transformation consuming %f (s)\n" % (t2 - t1)
    print "%s\t \t%f\t%f\t%f\t%f" % (name, t3 - t2, t4 - t3, t5 - t4, t6 - t5)


if __name__ == '__main__':
    main()
