```markdown
# Page Design Spec — Admin Dashboard (Thymeleaf)

## Global Styles (Desktop-first)

* **Grid & spacing**: 8px spacing system (8/16/24/32). Content max width 1200–1400px in main area.

* **Typography**: Base 14–16px; headings: 24/20/18; table text: 13–14.

* **Colors**:

  * Background: #F6F8FB

  * Surface: #FFFFFF

  * Border: #E5E7EB

  * Primary: #2563EB (hover: #1D4ED8)

  * Danger: #DC2626 (hover: #B91C1C)

  * Success: #16A34A

  * Text: #0F172A (muted: #64748B)

* **Buttons**:

  * Primary: solid primary, white text

  * Secondary: white surface with border

  * Destructive: danger

  * Disabled: 40% opacity, no hover

* **Links**: primary color, underline on hover.

* **Components baseline**: cards, tables with sticky header optional, badges for statuses, pagination.

## Shared Layout Shell (applies to all /admin pages)

### Layout

* **Primary layout system**: CSS Grid.

* Grid: `grid-template-columns: 260px 1fr; grid-template-rows: 56px 1fr;`

  * Sidebar spans rows (topbar + content)

  * Topbar sits above main content

* Responsive:

  * ≥1200px: full sidebar

  * 768–1199px: sidebar collapsible (icon + tooltip)

  * <768px: sidebar becomes off-canvas drawer; topbar shows hamburger

### Sidebar (left)

* Fixed/Sticky container with vertical nav.

* Elements:

  * Brand/logo at top

  * Nav items: Overview, Users, Products, Tasks, Reviews

  * Optional footer: “Logged in as …” + role badge

* Interaction:

  * Active route highlighted

  * Items hidden if role does not allow access (defense-in-depth: server still enforces)

### Topbar (top)

* Left: page title + optional breadcrumbs.

* Right: search (optional), notifications placeholder (optional), user menu.

* User menu: name, role badge, logout.

### Main content area

* Standard page header region:

  * Title + subtitle

  * Primary CTA button (only when role permits)

* Content region:

  * Cards / tables

  * Inline alerts (success/error)

### Thymeleaf structure suggestion

* `templates/admin/layout.html` defines the shell.

* `templates/admin/fragments/sidebar.html`, `topbar.html`, `flash-messages.html`.

* Pages extend layout via fragments (e.g., `th:replace`).

***

## Page: Admin Login

### Meta Information

* Title: “Admin Login”

* Description: “Sign in to manage users, products, tasks, and reviews.”

* Open Graph: title/description same; `og:type=website`

### Page Structure

* Centered card layout (Flexbox): full-height container, card width 380–420px.

### Sections & Components

1. **Login Card**

   * Logo/brand

   * Form fields: email, password

   * “Sign in” primary button

   * Error banner for invalid credentials
2. **Footer**

   * Small text: system name + support link (optional)

***

## Page: Admin Overview (/admin)

### Meta Information

* Title: “Dashboard Overview”

* Description: “Key metrics and recent activity.”

### Page Structure

* Stacked sections with card grid.

### Sections & Components

1. **KPI Card Row** (CSS Grid)

   * 5 cards: Total Users (by type), Total Products, Tasks Today, Pending Tasks, Average Review Rating

   * Each card: label, big number, delta badge (optional)
2. **Recent Activity** (two-column grid on desktop)

   * Recent Tasks table (ID, Description, Status, Created)

   * Recent Reviews table (ID, Rating, Product, Client, Created)
3. **Quick Links**

   * Buttons: “Manage Users”, “Manage Products”, “Manage Tasks”, “Manage Reviews”

Role behavior:

* Viewer sees KPIs + read-only tables.

* Admin/Super Admin sees same plus quick-action buttons.

***

## Page: User Management (/admin/users)

### Meta Information

* Title: “Users”

* Description: “Search and manage users (admins, clients, sellers, workers).”

### Page Structure

* Header + filter bar + table + pagination.

### Sections & Components

1. **Header**

   * Title “Users”

   * Primary CTA: “Create User” (Admin/Super Admin; dropdown for user type if supported)
2. **Filter Bar**

   * Search input (name/email)

   * Dropdown: user type (Admin, Client, Seller, Worker)

   * Dropdown: status (active/disabled)

   * Apply/Reset buttons
3. **Users Table**

   * Columns: Name, Email, Type (badge), Status badge, Created, Actions

   * Row click opens detail (or “View” action)

   * Actions:

     * Viewer: only “View”

     * Admin: “View”, “Edit” (role/status/password if permitted)

     * Super Admin: all + “Deactivate/Delete”
4. **Pagination**

   * Page size select (10/25/50) (optional)

### Page: User Detail (/admin/users/{id})

* Two-column layout on desktop:

  * Left: profile card (name, email, type, status, birthDate, phone, location if applicable)

  * Right: tabs or stacked cards (Linked Tasks if client/worker, Linked Products if seller, Permissions if admin, Recent Activity)

* Action panel:

  * Edit form for fields (role-gated)

  * Password update section (old/new password fields)

  * Status toggle (role-gated)

  * Server returns flash messages for success/failure

***

## Page: Product Management (/admin/products)

### Meta Information

* Title: “Products”

* Description: “Manage products and product items.”

### Page Structure

* Header + filter bar + table + pagination; tabs for Products vs. Product Items.

### Sections & Components

1. **Header**

   * Title “Products”

   * Primary CTA: “New Product” (Admin/Super Admin)
2. **Filter Bar**

   * Search (name/serial number)

   * Filter by seller

   * Apply/Reset buttons
3. **Products Table** (default tab)

   * Columns: Serial Number, Name, Description, Seller, Updated, Actions

   * Actions:

     * Viewer: View

     * Admin: View/Edit

     * Super Admin: View/Edit/Delete
4. **Product Items Table** (secondary tab)

   * Columns: ID, Price, Product Name, Seller, Actions (similar role-based)
5. **Pagination**

   * Page size select (10/25/50) (optional)

### Page: Product Editor (/admin/products/new, /admin/products/{id}/edit)

* Form layout with two-column grid on desktop:

  * Left: core fields (Name, Description, Serial Number, Seller ID)

  * Right: Image upload (multipart file input)

* Buttons: Save (primary), Cancel (secondary), Delete (destructive; Super Admin only)

* Validation:

  * Inline field errors + top form error summary

### Page: Product Item Editor (/admin/product-items/new, /admin/product-items/{id}/edit)

* Similar form: Price, Linked Product ID, Linked Seller ID

* Buttons and validation as above

***

## Page: Task Management (/admin/tasks)

### Meta Information

* Title: “Tasks”

* Description: “Track and update tasks.”

### Page Structure

* Header + filter bar + table + pagination.

### Sections & Components

1. **Header**

   * Title “Tasks”

   * Primary CTA: “New Task” (Admin/Super Admin)
2. **Filter Bar**

   * Search by description/ID

   * Status filter

   * Filters: by client, worker, product

   * Date range (created/confirmed/achieved)
3. **Tasks Table**

   * Columns: ID, Description, Status badge, Client, Worker, Product, Created, Actions

   * Actions:

     * Viewer: View

     * Admin/Super Admin: View + Edit/Update Status
4. **Pagination**

   * Page size select (10/25/50) (optional)

### Page: Task Detail (/admin/tasks/{id})

* Top summary card: Task ID, status, description, dates (created/confirmed/achieved)

* Two-column layout on desktop:

  * Left: linked entities (Product card, Client card, Worker card, Location details)

  * Right: status history timeline + notes (editable description)

* Status update component:

  * Dropdown with allowed transitions; submit button

  * Viewer cannot change status (control hidden + server enforcement)

***

## Page: Review Management (/admin/reviews)

### Meta Information

* Title: “Reviews”

* Description: “Manage product reviews.”

### Page Structure

* Header + filter bar + table + pagination.

### Sections & Components

1. **Header**

   * Title “Reviews”
2. **Filter Bar**

   * Search by comment/ID

   * Filter by product, client

   * Rating range (optional)
3. **Reviews Table**

   * Columns: ID, Rating (badge), Comment (truncated), Product, Client, Created, Actions

   * Actions:

     * Viewer: View

     * Admin/Super Admin: View + Delete (no edit)
4. **Pagination**

   * Page size select (10/25/50) (optional)

### Page: Review Detail (/admin/reviews/{id})

* Summary card: Review ID, rating, comment, createdAt

* Linked entities: Product card, Client card

* Action: Delete button (destructive; role-gated)

***

## Interaction & State Guidelines

* Use server-side flash messages for create/update/delete actions (success/error).

* Confirm dialogs for destructive actions (deactivate/delete).

* For locations: Managed inline within client/task editors (e.g., fields for latitude/longitude/street/city/zip/country); no dedicated page unless expanded.
```