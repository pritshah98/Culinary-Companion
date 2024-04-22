FROM postgres:latest

# Set environment variables
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=pwd
ENV POSTGRES_DB=recipedata

# Copy SQL initialization script
# COPY create_tables.sql /docker-entrypoint-initdb.d/

# To start postgresql db in docker
# docker build -t my-postgres .
# docker run -d -p 5432:5432 my-postgres

# Viewing db in terminal
# psql -h localhost -p 5432 -U admin recipedata
# enter in password