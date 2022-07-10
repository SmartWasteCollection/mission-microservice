FROM mcr.microsoft.com/azure-cli:latest
COPY ./ /mission-microservice/
WORKDIR /mission-microservice/

ARG service
ARG secret
ARG tenant

ENV AZURE_SERVICE_PRINCIPAL_NAME=$service
ENV AZURE_SECRET=$secret
ENV AZURE_TENANT=$tenant

EXPOSE 8080
EXPOSE 27017

COPY bootstrap.sh /bootstrap.sh
RUN chmod +x ./bootstrap.sh
ENTRYPOINT ["bash", "./bootstrap.sh"]