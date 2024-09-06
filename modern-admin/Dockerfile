# 基于java镜像创建新镜像
#FROM 10.110.1.241:5000/openjdk:8
FROM 10.110.1.241/moder_tsp_01/openjdk:8v
# 作者
MAINTAINER tangzhihui

WORKDIR /modern
# 将jar包添加到容器中并更名为app.jar
COPY /home/mode/jar/modern-admin.jar /modern/app.jar
# 运行jar包
ENTRYPOINT ["nohup","java","-jar","app.jar","&"]