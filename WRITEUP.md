# Approach, Tools, Gates, and Best Practices

## Methodology
1. **Plan**: Define requirements, risks, and security controls.
2. **Develop**: Implement minimal Java web service returning an HTML page.
3. **Build**: Maven build with unit tests and SpotBugs.
4. **Containerize**: Multi-stage Docker build; distroless runtime; non-root.
5. **Provision**: Terraform (Docker provider) builds image and runs container with port binding.
6. **Validate**: SAST (CodeQL), IaC scanning (tfsec), image scanning (Trivy), dependency checks.
7. **Deliver**: CI/CD via GitHub Actions; repo hardening documented.

## Security Controls & Rationale
- **Least privilege runtime**: Non-root in distroless base.
- **Supply chain**: Dependabot, pinned actions by major, Scorecard.
- **SAST**: CodeQL for Java; SpotBugs in `verify`.
- **IaC**: tfsec to enforce Terraform best practices.
- **Container**: Trivy scans OS + app libs; fail on HIGH/CRITICAL by policy.
- **Branch protections**: required reviews, signed commits, mandatory checks.
- **Secrets**: None committed; recommend OIDC for cloud, GitHub Environments for deploy gates.
- **Headers**: Strong CSP, no inline scripts.

## Problems & Resolutions
- **Cloud portability**: Avoided cloud-specific Terraform to keep this fully runnable. Used Docker provider instead.
- **Image size vs. security**: Chose distroless for minimized surface and fewer CVEs.
- **Action hardening**: Avoided insecure `untrusted` checkouts; used minimal permissions.

## How to Extend
- Replace Docker provider with modules for ECS/EKS/AKS once cloud creds are available.
- Add OPA/Conftest policies for Terraform plan gating.
- Add DAST (ZAP Baseline) and SBOM (Syft) with signed attestations.
