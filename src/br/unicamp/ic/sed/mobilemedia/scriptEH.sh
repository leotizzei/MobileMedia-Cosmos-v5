 #!/bin/bash
        for i in $( ls ); do
	    find $i -name "*.java" -or -name "*.aj" | xargs more | grep -E try\|catch\|Exception\|EH > $i.txt
        done

