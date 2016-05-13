#!/bin/bash
ssh -L 8000:127.0.0.1:8000 195.220.53.35
java -jar test2-jar-with-dependencies.jar