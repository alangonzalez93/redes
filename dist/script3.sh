#!/bin/bash
#Script Redes

(echo "available"; sleep 5; echo "reserve 3"; sleep 5; echo "available") | telnet localhost 6789 


