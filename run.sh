cd ../CreateAsm
echo $(pwd)
java -Xmx1g -jar jars/assembler.jar 2.asm ../CA_Lab4_SRIHARI/srihari.out
cd ../CA_Lab4_SRIHARI
ant make-jar
java -jar jars/simulator.jar src/configuration/config.xml delete/output.txt srihari.out
echo $(pwd)
