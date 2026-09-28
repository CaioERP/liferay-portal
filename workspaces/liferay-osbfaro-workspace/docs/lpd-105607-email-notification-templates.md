# Configuring Segment & Lifecycle Notification Email Templates

LPD-105607 adds two admin-editable email templates, one for **Segment** "new
member added" notifications, one for **Lifecycle** trigger notifications (new
accounts, stalled accounts, stage changes, etc.). Both are configured through
DXP's native Notification Templates store — not through code, and not
per-Site (see **Scope** below).

## Where to find it

1. Log in as a user with **Control Panel** access at the company level
   (this is no longer a per-Site setting — see **Scope** below).
2. Open **Control Panel → Notifications → Notification Templates**.
3. Two entries are pre-provisioned for your company automatically, with no
   notification needing to send first:
   - **Lifecycle Notification**
   - **Segment Notification**
4. Click into the one you want to configure.

## Scope

These templates are **company-wide**, not per-Site/workspace. A single
Lifecycle Notification template and a single Segment Notification template
apply to every channel/workspace in the company — there is no per-Site
override.

## Fields

The native Notification Templates screen shows more fields than Faro uses.
**Only these two are read by Faro when sending:**

| Field | Purpose |
|---|---|
| **Email Subject** | The notification email's subject line |
| **Email Body** | The notification email's HTML body |

Every other field on the screen (From, From Name, To, Send To, etc.) is set
once at provisioning time and is **not** read by Faro at send time — editing
them has no effect on Segment/Lifecycle emails. Faro determines the actual
sender and recipient itself (see **Placeholder tokens** below for
`[%SENDER_NAME%]`, and note recipients are always the user(s) with active
notification preferences for that Segment/Lifecycle, not anyone configured
here).

Both Email Subject and Email Body are **localizable** — use the
language/globe selector on the field to provide separate content per
company-available locale. Liferay falls back to the company's default
locale if a translation is missing for the recipient's locale.

## Placeholder tokens

Each template can reference placeholder tokens that get substituted with real
values at send time. Using a token not listed for that template type will not
be substituted — it will appear literally in the sent email.

### Lifecycle Notification tokens

| Token | Resolves to |
|---|---|
| `[%LIFECYCLE_NAME%]` | The Lifecycle's configured name |
| `[%LIFECYCLE_RESULT_TEXT%]` | Either the single matching account's name (when exactly one account triggered the notification) or a count summary like "5 accounts" |
| `[%LIFECYCLE_TRIGGER_LABEL%]` | The localized trigger type label (e.g. "New Accounts", "New Stalled Accounts", "Net New Pipeline Accounts", "New At Risk Accounts", "Account Stage Changes") |
| `[%LIFECYCLE_URL%]` | Link to the channel's Lifecycle page (there is no per-lifecycle deep link today — see **Known gaps** in the design spec) |
| `[%SENDER_NAME%]` | The workspace's configured sender name |

### Segment Notification tokens

| Token | Resolves to |
|---|---|
| `[%SEGMENT_NEW_MEMBERS_COUNT%]` | The net-new-member count for the configured frequency window |
| `[%SEGMENT_URL%]` | Link to the segment's detail page |
| `[%SENDER_NAME%]` | The workspace's configured sender name |

## Example

**Segment Notification — Email Subject:**
```
[%SEGMENT_NEW_MEMBERS_COUNT%] new members joined your segment
```

**Segment Notification — Email Body:**
```html
<p>Hi,</p>
<p><strong>[%SEGMENT_NEW_MEMBERS_COUNT%]</strong> new members were added to your
segment.</p>
<p><a href="[%SEGMENT_URL%]">View the segment</a></p>
<p>— [%SENDER_NAME%]</p>
```

## Important: templates are seeded with placeholder content, not blank

Unlike the previous Group-scoped settings screens, the native Notification
Templates store requires non-blank subject/body content to save a row at
all. Faro provisions both templates with short placeholder copy when your
company is registered (or, for existing companies, the next time this
module starts) — not lazily on first send — so they are already visible
under Control Panel → Notifications → Notification Templates before any
notification has gone out. Edit the placeholder content there to
customize it; your edits are never overwritten by a later send. If
provisioning fails for a company (for example, no user holds the
Administrator role yet), Faro logs the error and falls back to sending
with the same placeholder copy in-memory, without persisting a row —
retrying provisioning on the next module restart.

## Known limitation (as of this writing)

There is currently no end-user-facing UI in Faro for toggling *which*
segments/lifecycles a user wants notifications for — the preference-storage
REST API (`PreferencesFaroController`) and the scheduled email job exist and
are fully wired, but nothing in the Faro frontend calls them yet. Until that
UI ships, preferences can only be set via direct REST calls.
