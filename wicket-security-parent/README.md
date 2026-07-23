# Wicket Security

A comprehensive security framework for Apache Wicket applications, providing authentication and authorization capabilities through a modular, extensible architecture.

## Overview

Wicket Security is a multi-module security framework designed for Apache Wicket web applications. It provides role-based access control (RBAC), permission-based security, and fine-grained authorization at the component, page, and model levels.

**Version:** 10.8.0-SNAPSHOT  
**License:** Apache License 2.0  
**Inception Year:** 2006

## Architecture

The framework consists of four main modules organized into two parent projects:

### Module Hierarchy

```mermaid
graph TD
    A[wicket-security-parent] --> B[wasp-parent]
    A --> C[swarm-parent]
    B --> D[wicomsec]
    B --> E[wasp]
    C --> F[hive]
    C --> G[swarm]
    E -.depends on.-> D
    G -.depends on.-> E
    G -.depends on.-> F
    F -.depends on.-> D
    
    style A fill:#e1f5ff
    style B fill:#fff4e1
    style C fill:#fff4e1
    style D fill:#e8f5e9
    style E fill:#e8f5e9
    style F fill:#e8f5e9
    style G fill:#e8f5e9
```

## Modules

### 1. **Wicomsec** (Wicket Common Security)
**Artifact:** `wicketstuff-security-wicomsec`

The foundation module containing common security-related classes and interfaces.

**Key Components:**
- `Actions` - Central registry for action factories
- `ActionFactory` - Factory pattern for creating security actions
- `WaspAction` - Represents security actions (read, write, enable, render, etc.)
- Common security abstractions and utilities

**Purpose:** Provides shared security primitives used by other modules.

### 2. **WASP** (Wicket Abstract Security Platform)
**Artifact:** `wicketstuff-security-wasp`

The core security framework providing abstract security mechanisms for Wicket applications.

**Key Components:**
- `ISecurityCheck` - Interface for security checks
- `WaspAuthorizationStrategy` - Integration with Wicket's authorization system
- `ISecureComponent` - Interface for components with security features
- `ISecureModel` - Interface for models with security features
- Component-level and class-level security checks

**Purpose:** Provides the abstract foundation for implementing security in Wicket applications, independent of specific authentication/authorization implementations.

**Dependencies:**
- wicomsec
- Apache Wicket Extensions
- Jakarta Servlet API

### 3. **Hive**
**Artifact:** `wicketstuff-security-hive`

A basic ACL (Access Control List) security implementation.

**Key Components:**
- `Hive` - Security policy container (similar to JAAS Policy)
- `HiveMind` - Registry for multiple hive instances
- `PolicyFileHiveFactory` - Factory for creating hives from policy files
- `Principal` - Represents security principals (users, roles)
- `Permission` - Represents permissions
- `Subject` - Represents authenticated subjects

**Purpose:** Provides a concrete ACL-based security policy implementation, allowing multiple security policies per JVM.

**Dependencies:**
- wicomsec

### 4. **SWARM** (Standard Wicket Authentication and Rights Management)
**Artifact:** `wicketstuff-security-swarm`

A complete, ready-to-use security implementation combining WASP and Hive.

**Key Components:**
- `SwarmWebApplication` - Base class for Swarm-enabled applications
- `SwarmStrategy` - Authorization strategy implementation
- `SwarmActionFactory` - Factory for Swarm-specific actions
- `DataSecurityCheck` - Security checks for data access
- Integration layer combining WASP and Hive

**Purpose:** Provides a production-ready security solution with minimal configuration required.

**Dependencies:**
- wasp
- hive

## Component Interaction Flow

```mermaid
sequenceDiagram
    participant User
    participant WicketApp as Wicket Application
    participant SWARM as SWARM Layer
    participant WASP as WASP Layer
    participant Hive as Hive Policy
    
    User->>WicketApp: Access Component/Page
    WicketApp->>SWARM: Check Authorization
    SWARM->>WASP: Evaluate Security Check
    WASP->>Hive: Query Permissions
    Hive-->>WASP: Return Permission Set
    WASP-->>SWARM: Authorization Result
    SWARM-->>WicketApp: Grant/Deny Access
    WicketApp-->>User: Render/Redirect
```

## Security Check Flow

```mermaid
flowchart LR
    A[Component Access] --> B{Security Check}
    B -->|Component Level| C[ISecureComponent]
    B -->|Class Level| D[Class Authorization]
    B -->|Model Level| E[ISecureModel]
    
    C --> F[WaspAuthorizationStrategy]
    D --> F
    E --> F
    
    F --> G[Hive Policy]
    G --> H{Has Permission?}
    H -->|Yes| I[Grant Access]
    H -->|No| J[Deny Access]
    
    style A fill:#e3f2fd
    style I fill:#c8e6c9
    style J fill:#ffcdd2
```

## Permission Model

```mermaid
graph LR
    A[Subject/User] -->|has| B[Principal]
    B -->|granted| C[Permission]
    C -->|for| D[Action]
    D -->|on| E[Component/Page/Model]
    
    F[Hive] -->|stores| B
    F -->|manages| C
    
    style A fill:#fff9c4
    style F fill:#e1bee7
```

## Usage Overview

### Basic Integration

1. **Extend SwarmWebApplication:**
```java
public class MyApplication extends SwarmWebApplication {
    @Override
    protected void setupActionFactory() {
        super.setupActionFactory();
    }
    
    @Override
    protected Object getHiveKey() {
        return "myapp";
    }
}
```

2. **Define Security Policies:**
   - Create policy files defining principals and permissions
   - Configure Hive to load these policies

3. **Secure Components:**
   - Implement `ISecureComponent` on custom components
   - Use security checks to control rendering and enabling
   - Apply security at page, component, or model level

## Key Features

- ✅ **Component-Level Security** - Secure individual Wicket components
- ✅ **Page-Level Security** - Control access to entire pages
- ✅ **Model-Level Security** - Secure data access through models
- ✅ **Action-Based Permissions** - Fine-grained control (render, enable, etc.)
- ✅ **Multiple Security Policies** - Support for multiple Hives per JVM
- ✅ **Extensible Architecture** - Abstract base allows custom implementations
- ✅ **ACL Support** - Built-in access control list implementation
- ✅ **Integration Ready** - Works seamlessly with Wicket's authorization system

## Build Information

**Build Tool:** Maven  
**Java Version:** Compatible with Jakarta EE 10  
**Wicket Version:** 10.x

### Building the Project

```bash
mvn clean install
```

### Running Tests

```bash
mvn test
```

Note: Performance tests (SpeedTest.java) are excluded in release builds.

## Developers

- **Maurice Marrink** (Deceased) - Original Developer - Topicus
- **Emond Papegaaij** - Developer - Topicus  
- **Olger Warnier** - Developer - Joining Tracks / Zorginiatieven

## Module Dependencies Graph

```mermaid
graph TD
    subgraph "WASP Family"
        WICOMSEC[wicomsec<br/>Common Security]
        WASP[wasp<br/>Abstract Platform]
    end
    
    subgraph "SWARM Family"
        HIVE[hive<br/>ACL Implementation]
        SWARM[swarm<br/>Complete Solution]
    end
    
    subgraph "External Dependencies"
        WICKET[Apache Wicket]
        SERVLET[Jakarta Servlet API]
        SLF4J[SLF4J Logging]
    end
    
    WASP --> WICOMSEC
    WASP --> WICKET
    WASP --> SERVLET
    HIVE --> WICOMSEC
    SWARM --> WASP
    SWARM --> HIVE
    SWARM --> WICKET
    
    WICOMSEC --> SLF4J
    
    style WICOMSEC fill:#4caf50
    style WASP fill:#2196f3
    style HIVE fill:#ff9800
    style SWARM fill:#9c27b0
```

## License

Licensed under the Apache License, Version 2.0. See LICENSE file for details.

## Contributing

This project is part of the WicketStuff project (http://wicketstuff.org/).

## Support

For issues and questions, please refer to the WicketStuff community resources.
