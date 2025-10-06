# Challenge Write-Up
## Summary

I built a small but production-minded demo that serves a Hello World page from a Java app, containerised it, provisioned it locally with Terraform, and hardened the repository and CI so every change passes through security gates before merge. The public repository is at:
https://github.com/Benefit00/devsecops-hello-world

## Goals and scope

- Deliver a minimal Java web app that returns an HTML page.
- Package and run it as a container.
- Provision via Infrastructure as Code (IaC) for local reproducibility.
- Enforce secure SDLC practices in the repository and pipelines.
- Document the approach, decisions, and problems encountered.

## Environment

macOS on Apple Silicon, Homebrew, Docker Desktop, Java 21 via Temurin, Maven. I upgraded from Java 19 to 21 for compatibility with the build and CodeQL.

## Approach and methodology

I treated this as a small DevSecOps engagement:

- Plan the controls that must exist in code and in the repo.
- Implement the smallest possible Java service and keep dependencies low.
- Build and package with Maven, then containerise with a multi-stage Dockerfile.
- Provision locally via Terraform using the Docker provider to avoid cloud friction.
- Gate changes with CI and security checks, and enable repository protections.
- Iterate in short feature branches, merge via pull requests only, and record outcomes.

## Implementation

- Application: Java 21, a tiny HTTP handler that serves a static HTML page.
- Containerisation: multi-stage build with a Maven builder and a minimal runtime. On Apple Silicon, I used the multi-arch distroless Java 21 variant that runs as a non-root user.
- Infrastructure as Code: Terraform (Docker provider) builds and runs the container locally so anyone can reproduce the environment without cloud credentials.

## Secure SDLC controls

- Repository protections: restrict deletions, block force pushes, require signed commits, require approvals including first-time contributor approval, and require branches to meet status checks before merge.
- Code security and analysis: dependency graph, automatic dependency submission, Dependabot security updates, grouped security updates, secret and push protection, Copilot Autofix where available.
- Security gates in CI: CodeQL analysis, CI job for build and tests with static checks, Dependency Review on pull requests.
- Developer hygiene: CODEOWNERS, SECURITY policy, PR template, Dependabot configuration.

## Delivery steps

- Initialised git, created a public GitHub repository, and pushed the code.
- Enabled branch protection and code security features listed above.
- Opened pull requests from feature branches rather than pushing to main.
- Resolved a CodeQL workflow merge conflict during a rebase, ensured the workflow uses Java 21 and autobuild, and continued the rebase.
- Merged the branches through pull requests after checks passed.
- Cleaned up remote and local branches and confirmed that only main remains.

## Problems encountered and how I solved them

- Java toolchain mismatch: I started on Java 19, which conflicted with the project’s Java 21 target and SpotBugs. I installed Temurin 21 and verified the build locally and in CI.
- Runtime base image on Apple Silicon: a digest-pinned distroless image was not available for arm64. I switched to the multi-arch distroless Java 21 nonroot tag so Docker could select the correct architecture.
- CodeQL workflow conflict: during a rebase I hit a conflict in the CodeQL workflow. I resolved it by normalizing the filename, keeping autobuild, and setting Java 21 explicitly.
- Commit signing: initial commits failed to sign. I configured commit signing, verified the Verified badge on PR commits, and kept the repository rule that requires signed commits.

## Results and verification

- The app builds, runs in Docker locally, and is reachable in the browser.
- Terraform applies cleanly and provisions the container with the Docker provider.
- Pull requests show required checks for CI, CodeQL, and Dependency Review before merge.
- Branch protection prevents direct pushes and enforces approvals and signed commits.

## Next steps

If I were to extend this, I would add a lightweight DAST step against the running container, generate an SBOM with attestation, and wire OIDC-based deploys to a managed registry to demonstrate a complete build-to-release path.
