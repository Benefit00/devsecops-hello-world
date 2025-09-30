terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {}

resource "docker_image" "hello" {
  name = "devsecops-hello:local"
  build {
    context    = "${path.module}/.."
    dockerfile = "${path.module}/../Dockerfile"
  }
}

resource "docker_container" "hello" {
  name  = "devsecops-hello"
  image = docker_image.hello.image_id
  ports {
    internal = 8080
    external = 8080
  }
  restart = "unless-stopped"
}
