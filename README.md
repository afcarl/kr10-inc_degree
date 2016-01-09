Computing Inconsistency Measurements under Multi-Valued
Semantics by Partial Max-SAT Solvers

This is an eclipse workspace

To test the program, please run id.py

The data used in the paper is in the directory "data".

Usage: id.py [options] instance.cnf

Options:
  -h, --help            show this help message and exit
  -s SEMANTICS, --semantics=SEMANTICS
                        the semantics used for inconsistency measure: 4 or
  -p PARTIALMAXSATSOLVER, --partial-max-sat-solver=PARTIALMAXSATSOLVER
                        partialMaxSATSolver: sat4j or clone or msuncore or
                        minimaxsat

Example:

$./id.py -s 4 -p sat4j ./data/KSEM09/001.cnf

If you have any question, please contact with xiao(a)kr.tuwien.ac.at.