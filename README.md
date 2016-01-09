# Inconsistency Measurement Toolkit

This repository contains source code for the KR 2010 paper [1].

This is an eclipse workspace.

To test the program, please run `id.py`

The data used in the paper is in the directory "data".

```
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
```


[1] G. Xiao, Z. Lin, Y. Ma, and G. Qi. **Computing inconsistency measurements under multi-valued semantics by partial max-SAT solvers.** In *Proc. of KR’10*, pages 340–349, 2010. [pdf](http://www.ghxiao.org/publications/xlmq2010-kr-id2pmaxsat.pdf)

## LICENSE

```
   Copyright 2016 Guohui Xiao

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
