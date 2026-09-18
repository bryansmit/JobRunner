#!/bin/bash

echo "Long-running job started."

for i in {1..5}
do
    echo "Working... step $i"
    sleep 2
done

echo "Long-running job finished."

exit 0