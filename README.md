# 🍽️ F.I.L. Food — *Food Is Life*

**Meal subscription, ordering & delivery platform** · FoodTech with light HealthTech (dietary & health-adjacent data)

[![GitHub Repository](https://img.shields.io/badge/GitHub-colossalshub%2FFIL--Foods--App-181717?logo=github)](https://github.com/colossalshub/FIL-Foods-App)
[![Course](https://img.shields.io/badge/Course-IAS%20%2F%20SOFTENG-0A66C2)](https://www.umak.edu.ph/)
[![Section](https://img.shields.io/badge/Section-III--BCSAD-2E7D32)](https://github.com/colossalshub/FIL-Foods-App)
[![Group](https://img.shields.io/badge/Group-4-6A1B9A)](https://github.com/colossalshub/FIL-Foods-App)
[![Academic Year](https://img.shields.io/badge/AY-2026--2027-455A64)](https://github.com/colossalshub/FIL-Foods-App)
[![Compliance](https://img.shields.io/badge/Privacy-RA%2010173-1565C0)](https://www.privacy.gov.ph/)
[![Security](https://img.shields.io/badge/Posture-DevSecOps-C62828)](https://owasp.org/www-project-top-ten/)

| | |
|---|---|
| **Institution** | University of Makati — College of Computing and Information Sciences (CCIS) |
| **Course** | Information Assurance and Security (IAS) / Software Engineering (SOFTENG) |
| **Section** | III – BCSAD |
| **Group** | Group 4 |
| **Repository** | [github.com/colossalshub/FIL-Foods-App](https://github.com/colossalshub/FIL-Foods-App) |
| **Submission** | September 26, 2026 |
| **Status** | Capstone portfolio — Application Security Architecture & Software Engineering Integration |

---

## 📋 Project overview

**F.I.L. Food** (“Food Is Life”) is a mobile-first meal subscription, ordering, and delivery platform at the intersection of **FoodTech** and **light HealthTech**. It serves individuals pursuing healthy lifestyles, weight management, prescribed diets, allergies, and nutritional needs—alongside delivery riders and internal staff who manage the meal catalog.

Core capabilities include account and profile management, **dietary-profile capture**, meal catalog browsing, ordering and payment, order/delivery tracking, and push/SMS/email notifications.

**Security philosophy:** *Protect the person before protecting the uptime metric.* Crown-jewel assets are **A-01** (identity & credentials) and **A-02** (dietary & health profiles). Defensive posture emphasizes object-level authorization (BOLA/IDOR prevention), encryption in transit and at rest, and compliance with the **Philippine Data Privacy Act of 2012 (RA 10173)**.

| Asset ID | Data asset | Classification |
|----------|------------|----------------|
| **A-01** | User identity & authentication credentials | Restricted |
| **A-02** | Client dietary & health profiles | Restricted |
| **A-03** | Application source code & CI/CD configs | Internal |
| **A-04a** | Order & delivery information | Restricted |
| **A-04b** | Public vendor food catalog | Public |

---

## 🏗️ System architecture

Production deployment on **AWS** within a **VPC** (public + private subnets), fronted by **API Gateway / ALB + WAF**.

```text
┌─────────────────────┐     TLS 1.3      ┌──────────────────┐     ┌─────────────────────────┐
│  Android Client     │ ───────────────► │  API Gateway     │ ──► │  Node.js / Express /    │
│  Kotlin · Compose   │                  │  ALB · WAF       │     │  TypeScript API         │
│  Coroutines · Retrofit                  └──────────────────┘     │  (AWS ECS Fargate)      │
└─────────────────────┘                                            └───────────┬─────────────┘
                                                                                 │
                    ┌────────────────────────────────────────────────────────────┼────────────────────┐
                    ▼                            ▼                               ▼                    ▼
           PostgreSQL 15                  Redis 7.0                         AWS S3              Third-party APIs
           (Amazon RDS, Multi-AZ)         (ElastiCache sessions)            (encrypted media)   PayMongo / Stripe
                                                                                                Firebase Cloud Messaging
                                                                                                Google Maps Platform
                                                                                                SendGrid
```

### Tier breakdown (Task 1 & Task 2)

| Tier | Technologies |
|------|----------------|
| **Mobile / client** | Native **Android** — Kotlin, **Jetpack Compose**, Coroutines, **Retrofit** |
| **API / backend** | **Node.js**, **Express.js**, **TypeScript**, JWT authentication (asymmetric signing, **RS256**), Helmet.js — **AWS ECS Fargate** |
| **Data persistence** | **PostgreSQL 15** (Amazon RDS, Multi-AZ), **Redis 7.0** (ElastiCache), **AWS S3** (encrypted meal images) |
| **Cloud** | AWS VPC, ALB + WAF, ECS Fargate, RDS, ElastiCache, S3, CloudWatch |
| **Integrations** | **PayMongo / Stripe** (payments), **Firebase Cloud Messaging** (push), **Google Maps Platform** (routing), **SendGrid** (email/SMS) |

### Trust boundaries (Threat Dragon / Task 6)

| Boundary | From → To | Enforcement |
|----------|-----------|-------------|
| **TB1** | Untrusted client → public edge | TLS termination, WAF, rate limiting |
| **TB2** | Edge → internal microservices | JWT verification, mTLS, private subnet ACLs |
| **TB3** | Compute → persistence | PostgreSQL **RLS**, least-privilege IAM, encryption at rest |

---

## 📁 Monorepo directory structure

Target layout for **colossalshub/FIL-Foods-App** (Policy-as-Code and security artifacts live beside application code):

```text
FIL-Foods-App/
├── app/                          # Native Android application (Kotlin / Jetpack Compose)
│   ├── src/main/java/...         # UI, navigation, API clients (Retrofit)
│   └── build.gradle.kts
├── backend/                      # Node.js / Express / TypeScript API service
│   ├── src/                      # Routes, auth middleware, services
│   ├── tests/                    # SEC-TASK-201/202/203 authorization & SQLi suites
│   ├── package.json
│   └── tsconfig.json
├── backend-poc/                  # Task 2 BOLA remediation PoC (vulnerable vs hardened routes)
├── security/
│   ├── threat-model.json         # OWASP Threat Dragon model (Task 6)
│   ├── policies/
│   │   ├── EISP.md               # Enterprise Information Security Policy charter (Task 4)
│   │   └── SysSP.md              # Systems-Specific Security Policy & ACL matrix (Task 4)
│   └── rls/                      # PostgreSQL RLS policy scripts (default-deny)
├── .husky/
│   └── pre-commit                # Gitleaks + lint + test:authz gate
├── .gitleaks.toml                # Secret-scan rules (AWS, JWT, PayMongo)
├── .github/workflows/            # CI: gitleaks, authz tests, SAST
├── README.md
└── .gitignore
```

---

## 👔 C-suite team roles & governance matrix

Roster per **Task 1** — *C-Suite Governance* (milestone brief). The **CISO reports directly to the CEO** (not under the CIO) so security findings on A-01/A-02 are not filtered by release-velocity pressure.

| # | Role | Name | Student ID |
|---|------|------|------------|
| 1 | **Chief Executive Officer (CEO)** / Executive Sponsor | Argueza, Hanna Joy L. | 2026-0001 |
| 2 | **Chief Information Officer (CIO)** / Infrastructure Lead | Gamet, Michael Angelo C. | 2026-0002 |
| 3 | **Chief Information Security Officer (CISO)** / Risk Lead | Roxas, Jetrick M. | 2026-0003 |
| 4 | **Lead Developer / DevSecOps Engineer** | Villanueva, Lourdes Jed R. | 2026-0004 |
| 5 | **Data Privacy Officer (DPO)** / Compliance Specialist *(dual role with CEO)* | Argueza, Hanna Joy L. | 2026-0001 |

### CIANA-PS operational ownership

| Pillar | Primary owner | Operational focus |
|--------|---------------|-------------------|
| **Confidentiality** | CISO (Roxas) | Field-level encryption for A-02, TLS 1.3, object-level authorization |
| **Integrity** | Lead Dev / DevSecOps (Villanueva) | Parameterized queries, input validation, payment webhook checksums, CI/CD gates |
| **Availability** | CIO (Gamet) | Multi-AZ RDS, ECS auto-scaling, Redis failover, SLA monitoring |
| **Non-repudiation** | CISO + Lead Dev | Structured audit logs (JWT `sub`, request IDs) per RA 10173 accountability |
| **Authenticity** | Lead Dev | JWT signature verification, refresh-token rotation, mTLS service-to-service |
| **Privacy** | DPO (Argueza) | Consent API, data minimization, Right-to-Erasure (RA 10173 §16) |
| **Safety** | CEO + DPO | Dietary/allergy accuracy pipelines—data errors can cause physical harm |

**Data owner vs. custodian:** CEO = **Data Owner** (A-01, A-02, A-04); CIO = **Data Custodian** (RDS/S3/Redis operations without unilateral reclassification).

---

## 🛡️ Mandatory secure coding & AI prompting rules

All contributors—and any **AI coding assistants** (Green Zone AI Policy)—must treat these as **non-negotiable** merge criteria.

### Rule 1 — Secret leak prevention (CWE-798 · SEC-TASK-203)

- **Never** hardcode API keys, database passwords, JWT signing keys, or PayMongo/Stripe secrets in source.
- Use environment variables (`.env` locally; **AWS Secrets Manager** / CI secrets in production). **`.env` must not be committed.**
- Every commit is scanned via **Gitleaks** (`.gitleaks.toml`) in **Husky pre-commit** and CI.

### Rule 2 — BOLA / IDOR prevention (CWE-862 · SEC-TASK-201)

- **Never** accept `clientId` (or equivalent) in path or query for user-owned resources (e.g. `GET /api/v1/client/dietary-profile/:clientId`).
- Derive identity **only** from verified JWT claims (`req.user.id` after `authenticate` middleware).
- Enforce **PostgreSQL Row-Level Security (RLS)** so rows match `current_setting('app.current_user_id')`.

```typescript
// ✅ HARDENED — identity from JWT only
app.get('/api/v1/client/dietary-profile', authenticate, async (req, res) => {
  const userId = req.user.id;
  const profile = await db.query(
    'SELECT * FROM dietary_profiles WHERE client_id = $1',
    [userId]
  );
  // ...
});
```

### Rule 3 — SQL injection prevention (CWE-89 · SEC-TASK-202)

- **Always** use parameterized queries (`$1`, `$2`, …). **Template-literal SQL string concatenation is forbidden.**
- Login and all user-controlled filters must use bound parameters; CI must assert SQLi payloads return **401**, not server errors or auth bypass.

### Rule 4 — Privacy-by-design (Task 3 · RA 10173 §16)

- **Data minimization:** collect only what meal personalization requires; store hashed IP in consent records, not raw IP or device fingerprints.
- **Right to erasure:** use **cryptographic shredding**—delete per-user **DEK** in `user_deks` to render A-02 ciphertext unrecoverable; pseudonymize retained order rows for lawful tax/audit retention.
- DPO owns breach notification within **72 hours** (NPC Circular 16-03).

---

## ⚙️ DevSecOps & pre-commit setup

### Prerequisites

- [Gitleaks](https://github.com/gitleaks/gitleaks#installing)
- Node.js 20+ (backend)
- Git

### 1. Clone and install hooks

```bash
git clone https://github.com/colossalshub/FIL-Foods-App.git
cd FIL-Foods-App
cd backend && npm ci
npm install --save-dev husky
npx husky init
```

### 2. Configure Husky pre-commit (Task 4 — Git Policy-as-Code)

Create or verify `.husky/pre-commit`:

```bash
#!/usr/bin/env bash
set -e

echo "🔍 Running gitleaks secret scan..."
gitleaks detect --source . --config .gitleaks.toml --no-git -v
if [ $? -ne 0 ]; then
  echo "❌ Commit blocked: potential secret detected."
  exit 1
fi

echo "🧪 Running lint + authorization test suite..."
cd backend && npm run lint && npm run test:authz
```

```bash
chmod +x .husky/pre-commit
```

### 3. Run Gitleaks manually (local or CI)

```bash
# Full repository scan (fail build on leak — SEC-TASK-203 CI assertion)
gitleaks detect --source . --config .gitleaks.toml --exit-code 1

# Pre-commit style (working tree, verbose)
gitleaks detect --source . --config .gitleaks.toml --no-git -v
```

### 4. Authorization & SQLi test suites (SEC-TASK-201 / 202 / 203)

From `backend/` (once `test:authz` is wired in `package.json`):

```bash
npm run lint
npm run test:authz
```

**Expected CI assertions (Jest / supertest):**

| Ticket | Assertion |
|--------|-----------|
| **SEC-TASK-201** | Cross-tenant read `GET /api/v1/client/dietary-profile/1042` with token for client `55` → **404** |
| **SEC-TASK-202** | SQLi login payload `admin' OR '1'='1` → **401** |
| **SEC-TASK-203** | `gitleaks detect ... --exit-code 1` fails pipeline on any match |

### 5. Task 2 PoC server (local BOLA demo)

```bash
cd backend-poc
npm install
node server.js
# Vulnerable:  GET http://localhost:3000/vulnerable/dietary-profile/1042
# Hardened:    GET http://localhost:3000/hardened/dietary-profile
```

---

## 🔗 Traceability snapshot

| Asset | Control | Backlog ticket |
|-------|---------|----------------|
| A-02 | JWT-only identity + RLS | SEC-TASK-201 |
| A-01 | Parameterized login queries | SEC-TASK-202 |
| A-01 / A-03 | Gitleaks pre-commit + CI | SEC-TASK-203 |

Threat model artifact: [`security/threat-model.json`](security/threat-model.json) (OWASP Threat Dragon, Group 4 — III-BCSAD).

---

## 📚 References

- RA 10173 (Data Privacy Act of 2012) · RA 10175 (Cybercrime Prevention Act of 2012)
- NPC Circular 16-03 (breach notification)
- OWASP Top 10:2021 · CWE-862, CWE-89, CWE-798
- NIST CSF 2.0 · ISO/IEC 27001:2022 (roadmap)
- MITRE ATT&CK · OWASP Threat Dragon

---

## 📄 License & academic use

This repository is developed for the **University of Makati** IAS/SOFTENG capstone (**Group 4, III-BCSAD, AY 2026–2027**). Contact the DPO role holder for privacy inquiries: **dpo@filfood.ph** (per capstone breach-notification template).

---

<p align="center">
  <strong>F.I.L. Food — Group 4 · III-BCSAD · University of Makati CCIS</strong><br/>
  <em>Consolidated Technical Specification — Application Security Architecture & Software Engineering Integration</em>
</p>
