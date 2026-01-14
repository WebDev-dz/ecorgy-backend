````markdown
## 1. Product Overview
A secure, server-rendered Admin Dashboard for managing core business data (users, products, orders) via Thymeleaf templates.
It provides role-based access control and a consistent sidebar/topbar layout for fast admin operations.

## 2. Core Features

### 2.1 User Roles
| Role | Registration Method | Core Permissions |
|------|---------------------|------------------|
| Super Admin | Pre-provisioned by system/operator | Full access to all pages and actions, can manage roles and deactivate accounts |
| Admin | Created by Super Admin | Manage users, products, and orders; view analytics; limited settings |
| Support/Viewer | Created by Super Admin/Admin | Read-only access to users/products/orders; can view order/user details but cannot edit/delete |

### 2.2 Feature Module
Our Admin Dashboard requirements consist of the following main pages:
1. **Admin Login**: authentication form, error handling, logout entry.
2. **Admin Overview**: KPI cards, recent activity tables, quick links.
3. **User Management**: user list/search, user detail, role/status updates.
4. **Product Management**: product list/search, create/edit product.
5. **Order Management**: order list/filter, order detail, status updates.
## 1. Product Overview
A secure, server-rendered Admin Dashboard for managing core business data (users including admins/clients/sellers/workers, products, product items, tasks, reviews, locations) via Thymeleaf templates.
It provides role-based access control and a consistent sidebar/topbar layout for fast admin operations in a marketplace/service system where clients request tasks related to products, handled by workers, sold by sellers, with reviews.

## 2. Core Features

### 2.1 User Roles
| Role | Registration Method | Core Permissions |
|------|---------------------|------------------|
| Super Admin | Pre-provisioned by system/operator or created via /api/admins endpoint with full permissions array | Full access to all pages and actions (CRUD on all entities: admins, clients, sellers, workers, products, product items, tasks, reviews, locations); can manage permissions and deactivate accounts |
| Admin | Created by Super Admin via /api/admins endpoint with limited permissions | Manage users (clients, sellers, workers), products, product items, tasks, reviews; view/update locations; view analytics; limited settings (no permission management or admin deletions) |
| Support/Viewer | Created by Super Admin/Admin via /api/admins endpoint with read-only permissions | Read-only access to users (all types), products, product items, tasks, reviews, locations; can view details but cannot create/edit/delete |

### 2.2 Feature Module
Our Admin Dashboard requirements consist of the following main pages:
1. **Admin Login**: authentication form using /auth/login, error handling, logout entry.
2. **Admin Overview**: KPI cards, recent activity tables (e.g., recent tasks, reviews), quick links.
3. **User Management**: Manage all user types (admins, clients, sellers, workers) with list/search, detail views, updates (e.g., via /api/users/{id}, /api/admins/{id}, etc.).
4. **Product Management**: Product and product item list/search, create/edit (e.g., via /api/products, /api/product-items).
5. **Task Management**: Task list/filter, detail, status updates (e.g., via /api/tasks).
6. **Review Management**: Review list/search, detail, deletion (e.g., via /api/reviews).

### 2.3 Page Details
| Page Name | Module Name | Feature description |
|-----------|-------------|---------------------|
| Admin Login | Login form | Authenticate with email + password via /auth/login; show validation errors; redirect to Overview on success; support signup via /auth/signup for initial setup |
| Admin Login | Session actions | Provide logout action; prevent access to admin pages when not authenticated; use JWT bearer token for API calls post-login |
| Admin Overview | Layout shell | Render shared sidebar + topbar; show current user + role (from permissions); highlight active nav item |
| Admin Overview | KPIs | Display high-level metrics (e.g., total users by type, total products, tasks today, pending tasks, average review rating) fetched via relevant GET endpoints like /api/users, /api/tasks/status/{status} |
| Admin Overview | Recent activity | List latest tasks (/api/tasks), reviews (/api/reviews), and products (/api/products) with links to details |
| User Management | User list | List all users by type (admins via /api/admins, clients via /api/clients, sellers via /api/sellers, workers via /api/workers) with pagination; search by name/email (e.g., /api/users/email/{email}); filter by role/status |
| User Management | User detail | View profile fields (e.g., name, email, birthDate, phone, location for clients, products for sellers, tasks for workers/clients); show audit info (created/updated); link to related entities (e.g., tasks, reviews) |
| User Management | Admin actions | Create users via POST endpoints (e.g., /api/admins, /api/clients); update role/status/permissions (for admins) via PUT (e.g., /api/admins/{id}); reset/update password via /api/users/{id}/password; delete via DELETE endpoints (restricted to higher roles) |
| Product Management | Product list | List products (/api/products) and product items (/api/product-items) with pagination; search by name/serial (/api/products/serial/{serialNumber}); filter by seller (/api/products/seller/{sellerId}) |
| Product Management | Product editor | Create/edit product (name, description, serialNumber, image via multipart POST /api/products; sellerId) or product item (price, linked product/seller via /api/product-items); validate required fields |
| Product Management | Admin actions | Activate/deactivate (via status updates in tasks if linked); delete product (/api/products/{id}) or item (/api/product-items/{id}) (restricted to higher roles); view linked items (/api/product-items/product/{productId}) |
| Task Management | Task list | List tasks (/api/tasks) with pagination; filter by status (/api/tasks/status/{status}), date, client (/api/tasks/client/{clientId}), worker (/api/tasks/worker/{workerId}), product (/api/tasks/product/{productId}) |
| Task Management | Task detail | View task fields (description, status, dates, linked product, worker, client, location); show timeline/status history |
| Task Management | Admin actions | Create task via /api/tasks POST; update status/dates via /api/tasks/{id} PUT (e.g., pending → confirmed → achieved); add notes (in description); delete via /api/tasks/{id} DELETE |
| Review Management | Review list | List reviews (/api/reviews) with pagination; filter by product (/api/reviews/product/{productId}), client (/api/reviews/client/{clientId}) |
| Review Management | Review detail | View review fields (rating, comment, createdAt, linked product/client) |
| Review Management | Admin actions | Create review via /api/reviews POST (with productId/clientId); delete via /api/reviews/{id} DELETE (no edit, as reviews are client-submitted) |

## 3. Core Process
**Authentication + access control flow**
1. You open an admin URL.
2. If not logged in, you are redirected to Admin Login.
3. After login via /auth/login, you land on Admin Overview with JWT for subsequent API calls.
4. Navigation uses the sidebar to open Users, Products, Tasks, or Reviews.
5. Actions (create/edit/delete/status updates) are permitted or blocked based on your role's permissions array (server-side enforcement via security).

**Super Admin flow**
1. Log in → Overview.
2. Go to Users → create/list/update admins/clients/sellers/workers.
3. Go to Products → create/edit products and items.
4. Go to Tasks → manage tasks (assign workers, update statuses).
5. Go to Reviews → view/delete reviews.

**Support/Viewer flow**
1. Log in → Overview.
2. Browse Users/Products/Tasks/Reviews lists and details.
3. Edit/create/delete controls are hidden/disabled, and server-side enforcement denies write requests.

```mermaid
graph TD
  A["Admin Login"] --> B["Admin Overview"]
  B --> C["User Management"]
  B --> D["Product Management"]
  B --> E["Task Management"]
  B --> F["Review Management"]
  C --> C1["User Detail"]
  D --> D1["Product Editor"]
  E --> E1["Task Detail"]
  F --> F1["Review Detail"]
  C1 --> C
  D1 --> D
  E1 --> E
  F1 --> F
````

### 2.3 Page Details

| Page Name          | Module Name     | Feature description                                                                                  |
| ------------------ | --------------- | ---------------------------------------------------------------------------------------------------- |
| Admin Login        | Login form      | Authenticate with username/email + password; show validation errors; redirect to Overview on success |
| Admin Login        | Session actions | Provide logout action; prevent access to admin pages when not authenticated                          |
| Admin Overview     | Layout shell    | Render shared sidebar + topbar; show current user + role; highlight active nav item                  |
| Admin Overview     | KPIs            | Display high-level metrics (e.g., total users, total products, orders today, pending orders)         |
| Admin Overview     | Recent activity | List latest users and latest orders with links to details                                            |
| User Management    | User list       | List users with pagination; search by name/email; filter by role/status                              |
| User Management    | User detail     | View profile fields and recent orders/activity; show audit info (created/updated)                    |
| User Management    | Admin actions   | Update role and status (active/disabled); reset password (optional: trigger flow)                    |
| Product Management | Product list    | List products with pagination; search by name/SKU; filter by status/category                         |
| Product Management | Product editor  | Create/edit product (name, SKU, price, status, description); validate required fields                |
| Product Management | Admin actions   | Activate/deactivate product; delete product (restricted to higher roles)                             |
| Order Management   | Order list      | List orders with pagination; filter by status/date; search by order id/customer                      |
| Order Management   | Order detail    | View order items, totals, customer info, timeline/status history                                     |
| Order Management   | Admin actions   | Update order status (e.g., pending → paid → shipped → completed); add internal notes                 |

## 3. Core Process

**Authentication + access control flow**

1. You open an admin URL.
2. If not logged in, you are redirected to Admin Login.
3. After login, you land on Admin Overview.
4. Navigation uses the sidebar to open Users, Products, or Orders.
5. Actions (edit/delete/status updates) are permitted or blocked based on your role.

**Super Admin flow**

1. Log in → Overview.
2. Go to Users → open a user detail → change role/status.
3. Go to Products → create/edit products.
4. Go to Orders → update order statuses.

**Support/Viewer flow**

1. Log in → Overview.
2. Browse Users/Products/Orders lists and details.
3. Edit controls are hidden/disabled and server-side enforcement denies write requests.

```mermaid
graph TD
  A["Admin Login"] --> B["Admin Overview"]
  B --> C["User Management"]
  B --> D["Product Management"]
  B --> E["Order Management"]
  C --> C1["User Detail"]
  D --> D1["Product Editor"]
  E --> E1["Order Detail"]
  C1 --> C
  D1 --> D
  E1 --> E
```

```

this is prd admin dashboard with themlyf it's not compatible with open api docs now update the prd to be compatible and manage tasks, users, products, reviews like api dos says
```

