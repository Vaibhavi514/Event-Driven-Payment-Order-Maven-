# 💳 Cloud-Native Payment & Order Processing Microservice

A resilient, scalable backend microservice built with **Java (Spring Boot 3)** and **Apache Maven**, containerized using **Docker**, provisioned declaratively via **Terraform (IaC)**, and automatically deployed to **AWS ECS Fargate** behind an **Application Load Balancer (ALB)** with **GitHub Actions CI/CD**.

---

## 🏛️ Architecture Overview

Client / HTTP Requests (Browser / Postman / cURL)
                     │ (Port 80)
                     ▼
       [ Application Load Balancer (ALB) ]
                     │ (Forward to Target Group :8080)
                     ▼
          [ AWS ECS Fargate Cluster ]
                     │
          [ Spring Boot Container ]
          ├── Port: 8080
          ├── Multi-stage Docker Image (Eclipse Temurin JRE 17)
          ├── Java 17 / Spring Boot 3.2.x REST API
          └── CloudWatch Log Group (/ecs/payment-service)


Key ComponentsNetworking & Isolation: Custom AWS VPC spanning two Availability Zones with public subnets, an Internet Gateway, and custom route tables.Security & Access Control: Least-privilege AWS Security Groups (ALB only accepts port 80; ECS tasks only accept traffic originating from ALB) and IAM Task Execution Roles.Continuous Integration & Delivery: Automated multi-stage CI/CD pipeline in GitHub Actions that runs tests with Maven, builds multi-arch Docker images, pushes to Amazon ECR, and executes zero-downtime rolling updates to ECS Fargate.🛠️ Tech StackDomainTechnology / ToolBackend FrameworkJava 17, Spring Boot 3.2.x (Spring Web, Spring Actuator, Jakarta Validation)Build AutomationApache MavenContainerizationDocker (Multi-stage build)Infrastructure as CodeTerraform (HashiCorp AWS Provider ~> 5.0)Cloud PlatformAWS (VPC, ALB, ECS Fargate, ECR, CloudWatch, IAM)CI/CD AutomationGitHub Actions


📁 Repository Structure

.
├── .github/
│   └── workflows/
│       └── deploy.yml              # GitHub Actions CI/CD pipeline
├── src/
│   ├── main/
│   │   ├── java/com/example/payment/
│   │   │   ├── controller/
│   │   │   │   ├── HealthController.java     # /health endpoint
│   │   │   │   └── OrderController.java      # /api/v1/orders/process endpoint
│   │   │   ├── model/
│   │   │   │   └── OrderRequest.java         # Request payload schema & validation
│   │   │   └── PaymentServiceApplication.java
│   │   └── resources/
│   │       └── application.yml     # Application properties & Actuator exposure
├── terraform/
│   ├── main.tf                     # Infrastructure declarations (VPC, ALB, ECS, ECR)
│   ├── variables.tf                # Configuration variables
│   ├── terraform.tfvars            # Environment variable overrides
│   └── outputs.tf                  # Infrastructure endpoints & resource names
├── Dockerfile                      # Multi-stage container build definition
├── pom.xml                         # Maven dependencies & build plugins
└── README.md



🚀 API Specification
1. Health Check
Endpoint: GET /health

Description: Health check used by AWS ALB Target Group to ensure container liveness.

Response (200 OK):
{
  "service": "payment-service",
  "status": "UP"
}


2. Process Order / Payment
Endpoint: POST /api/v1/orders/process

Headers: Content-Type: application/json

Request Body:

{
  "orderId": "ORD-9901",
  "customerId": "CUST-4412",
  "amount": 149.99,
  "currency": "USD"
}
Response (201 Created):

JSON
{
  "orderId": "ORD-9901",
  "customerId": "CUST-4412",
  "amount": 149.99,
  "currency": "USD",
  "status": "PAYMENT_SUCCESSFUL",
  "transactionId": "TXN-7253CFA4"
}
💻 Local Setup & Execution
Prerequisites
JDK 17+

Apache Maven 3.8+

Docker Desktop

AWS CLI v2

Terraform 1.5+

Run Locally with Maven
Bash
# Clean, compile, and run the Spring Boot application
mvn clean spring-boot:run
Build & Run Container Locally
Bash
# Build multi-stage Docker image
docker build -t payment-service:latest .

# Run container on port 8080
docker run -p 8080:8080 payment-service:latest
☁️ Infrastructure Deployment (Terraform)
Authenticate AWS CLI:

Bash
aws configure
Initialize and Provision Cloud Resources:

Bash
cd terraform
terraform init
terraform plan
terraform apply -auto-approve
Capture Outputs:

alb_dns_name: Public load balancer URL.

ecr_repository_url: ECR Docker registry repository.

🔄 CI/CD Pipeline (GitHub Actions)
The workflow automatically executes on every push to the main branch:

Verify & Test: Compiles source code and runs unit tests via mvn clean verify.

Authenticate with AWS: Uses repository secrets to establish secure session with AWS.

Container Build & Tag: Multi-stage build tags the image with both :latest and the unique commit SHA.

Push to ECR: Securely uploads container to Amazon Elastic Container Registry.

ECS Rolling Deployment: Renders task definition and triggers a zero-downtime rolling update on ECS Fargate.

Required GitHub Repository Secrets
Go to Settings > Secrets and variables > Actions and configure:

AWS_ACCESS_KEY_ID

AWS_SECRET_ACCESS_KEY

AWS_REGION (e.g., us-east-1)

🧪 Testing the Live Deployment
Replace <ALB_DNS_NAME> with your Terraform ALB DNS output:

Bash
# Check Health
curl -X GET http://<ALB_DNS_NAME>/health

# Process Payment
curl -X POST http://<ALB_DNS_NAME>/api/v1/orders/process \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "ORD-9901",
    "customerId": "CUST-4412",
    "amount": 149.99,
    "currency": "USD"
  }'
🧹 Teardown & Resource Cleanup
To avoid ongoing AWS charges after evaluation:

Bash
cd terraform
terraform destroy -auto-approve



