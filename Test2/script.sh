#!/bin/bash
ssh -L 8001:127.0.0.1:8001 195.220.53.35
java -jar test2-jar-with-dependencies.jar