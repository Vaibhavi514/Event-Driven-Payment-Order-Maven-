output "ecr_repository_url" {
  value       = aws_ecr_repository.app_repo.repository_url
  description = "URL of ECR repository"
}

output "alb_dns_name" {
  value       = aws_lb.main.dns_name
  description = "Public URL of Application Load Balancer"
}

output "ecs_cluster_name" {
  value       = aws_ecs_cluster.main.name
}

output "ecs_service_name" {
  value       = aws_ecs_service.main.name
}