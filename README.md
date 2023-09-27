# MOVIE API 
## Using Java Spring boot framework

```
openapi: "3.0.3"
info:
title: "MovieAPI_Springboot API"
description: "MovieAPI_Springboot API"
version: "1.0.0"
servers:
- url: "https://MovieAPI_Springboot"
  paths:
  /api/v1/movies:
  get:
  summary: "GET api/v1/movies"
  operationId: "getAllMovies"
  responses:
  "200":
  description: "OK"
  content:
  '*/*':
  schema:
  type: "array"
  items:
  $ref: "#/components/schemas/Movie"
  /api/v1/movies/{id}:
  get:
  summary: "GET api/v1/movies/{id}"
  operationId: "getSingleMovie"
  parameters:
  - name: "id"
  in: "path"
  required: true
  schema:
  $ref: "#/components/schemas/ObjectId"
  responses:
  "200":
  description: "OK"
  content:
  '*/*':
  schema:
  $ref: "#/components/schemas/Movie"
  components:
  schemas:
  ObjectId:
  type: "object"
  properties:
  timestamp:
  type: "integer"
  format: "int32"
  Review:
  type: "object"
  properties:
  id:
  $ref: "#/components/schemas/ObjectId"
  body:
  type: "string"
  Movie:
  type: "object"
  properties:
  id:
  $ref: "#/components/schemas/ObjectId"
  imdbId:
  type: "string"
  title:
  type: "string"
  releaseDate:
  type: "string"
  trailerLink:
  type: "string"
  poster:
  type: "string"
  genres:
  type: "array"
  items:
  type: "string"
  backdrop:
  type: "array"
  items:
  type: "string"
  reviewIds:
  type: "array"
  items:
  $ref: "#/components/schemas/Review"
```