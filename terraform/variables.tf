variable "aws_region" {
  type        = string
  default     = "us-east-1"
  description = "Target AWS region"
}

variable "project_name" {
  type        = string
  default     = "payment-service"
  description = "Base name for AWS resources"
}

variable "container_port" {
  type        = number
  default     = 8080
  description = "Port exposed by Spring Boot container"
}