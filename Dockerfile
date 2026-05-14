# syntax=docker/dockerfile:1

#Start your image with a node
FROM node:18-alpine

RUN mkdir -p /app