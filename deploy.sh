pkill -9 java
nohup java  -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005  -jar /home/admin/wxservice/target/wxservice-0.0.1-SNAPSHOT.jar   >> ./log/wxservice` date +%Y-%m-%d`.log 2>&1 &