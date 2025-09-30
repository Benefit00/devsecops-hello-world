# Security Policy

- **Reporting:** Please report vulnerabilities privately via security@your-org.example. Do not open public issues.
- **Supported branches:** Only `main` receives security updates.
- **Dependencies:** Automated via Dependabot with security updates enabled.
- **Build pipeline:** CodeQL, Dependency Review, Trivy (containers), and tfsec (Terraform).
- **Secrets:** Never commit secrets. Use GitHub Environments and OpenID Connect for cloud auth.
