# Home Inventory

Spring Boot 4.0 Maven application using Java 25, Spring Data JPA, PostgreSQL, Thymeleaf, and Bean Validation.

## Run

Start PostgreSQL with Docker, then run the app:

```bash
docker compose up -d postgres
mvn spring-boot:run
```

Then open `http://localhost:8080/rooms`.

The database container creates the `home_inventory` database and user on first start.
The application defaults already point at `jdbc:postgresql://localhost:5432/home_inventory`
with username and password `home_inventory`.

## Kubernetes

Build the app image:

```bash
docker build -t ghcr.io/YOUR_GITHUB_USERNAME/home-inventory:0.0.1 .
echo "$CR_PAT" | docker login ghcr.io -u YOUR_GITHUB_USERNAME --password-stdin
docker push ghcr.io/YOUR_GITHUB_USERNAME/home-inventory:0.0.1
```

Update `k8s/app-deployment.yaml` with your GitHub username, then apply the manifests:

```bash
kubectl apply -k k8s
```

The app is deployed in the `home-inventory` namespace and the PostgreSQL service is
available at `postgres.home-inventory.svc.cluster.local:5432`.

GitHub Container Registry stores container images under your personal account or
organization namespace, and GitHub Actions can publish to it with `GITHUB_TOKEN`
when the workflow has `packages: write` permission. The repo label in the Dockerfile
helps GitHub connect the package back to the source repository.

If the GHCR image is private, create a Kubernetes `imagePullSecret` from a GitHub
personal access token with `read:packages` scope and replace the example secret in
`k8s/ghcr-pull-secret.example.yaml`.

You can also create it directly:

```bash
kubectl -n home-inventory create secret docker-registry ghcr-pull-secret \
  --docker-server=ghcr.io \
  --docker-username=YOUR_GITHUB_USERNAME \
  --docker-password=YOUR_READ_PACKAGES_PAT \
  --docker-email=YOUR_EMAIL
```

To reach the app locally:

```bash
kubectl -n home-inventory port-forward svc/home-inventory 8080:8080
```

Then open `http://localhost:8080/rooms`.

## Test

```bash
mvn test
```

Tests use an in-memory H2 database in PostgreSQL compatibility mode.
