

**To run the program**

1. Launch sbt:

        sbt 
This downloads all the dependencies for the project.

2. To run program in SBT:

First argument is the test file and the second argument is the number of requests that are processed in parallel.

   > run src/main/resources/urls.txt 3



**Notes:**
 
In order to demonstrate the parallel hashing and user can control the degree of parallelism, 
I have made one second of delay for calculating the MD5

   


