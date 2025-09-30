# DevSecOps Hello World (Java + Docker + Terraform)

A minimal, security-focused demo app that serves a **Hello World** HTML page from a Java 21 program.
The app is containerized and provisioned via Terraform (Docker provider) so it is runnable locally without cloud accounts.

## TL;DR: Run locally

```bash
# 1) Build & test
mvn -B -e verify

# 2) Containerize
docker build -t devsecops-hello:local .
docker run --rm -p 8080:8080 devsecops-hello:local

# 3) Or use Terraform to provision and run the container
cd terraform
terraform init
terraform apply -auto-approve
# open http://localhost:8080
```

## CI/CD & Security Gates

- **Unit tests + SpotBugs** (Maven `verify`).
- **CodeQL** (SAST for Java) on pushes/PRs and weekly.
- **Dependency Review** on PRs.
- **Trivy** container image scan.
- **tfsec** Terraform static analysis.
- **OpenSSF Scorecard** scheduled.

## Repository Hardening

- Dependabot updates for Maven, Docker, Terraform, and Actions.
- `SECURITY.md`, `CODEOWNERS`, PR/Issue templates.
- Recommend enabling branch protection on `main` (see below).

### Suggested Branch Protections (enable in GitHub UI)
- Require pull request reviews (min 1-2; include code owners).
- Require status checks to pass: CI, CodeQL, Trivy, tfsec, Dependency Review.
- Require conversation resolution; dismiss stale approvals.
- Require signed commits; enforce linear history; restrict who can push.
- Enable secret scanning and push protection; enable vulnerability alerts.
- Restrict Actions to approved/reusable workflows; disable self-hosted runners unless needed.

## Design Notes

- Distroless Java image for minimal attack surface; non-root user.
- Strict Content Security Policy (CSP) header set by the app.
- Terraform uses the Docker provider to keep the demo runnable anywhere Docker runs.

## Next Steps (optional)
- Switch to a managed registry (GHCR/ECR) + OIDC for auth.
- Add DAST (ZAP Baseline) against the running container in CI.
- Add SLSA provenance via GitHub's `generate-provenance`.
