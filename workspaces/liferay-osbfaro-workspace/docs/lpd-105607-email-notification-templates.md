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
3. Two entries are pre-provisioned the first time either notification type
   is ever sent for your company:
   - **Lifecycle Notification**
   - **Segment Notification**

   If neither has sent yet, the entries won't exist until the first send
   attempt creates them with placeholder content — trigger one Segment or
   Lifecycle notification (or wait for the scheduled job) before expecting
   to find them here.
4. Click into the one you want to configure.

## Scope

These templates are **company-wide**, not per-Site/workspace. A single
Lifecycle Notification template and a single Segment Notification template
apply to every channel/workspace in the company — there is no per-Site
override.

## Fields

Each of the two configuration screens has exactly two fields:

| Field | Purpose |
|---|---|
| **Email Subject** | The notification email's subject line |
| **Email Body** | The notification email's HTML body |

Both fields are **localizable** — use the language/globe selector on the field
to provide separate content per site locale. Liferay falls back to the site's
default locale if a translation is missing for the recipient's locale.

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
all. The first time a Segment or Lifecycle notification is ever sent for a
company, Faro auto-creates that company's template with short placeholder
copy so the send doesn't silently go out empty. Edit the placeholder
content in Control Panel → Notifications → Notification Templates to
customize it — your edits are never overwritten by a later send.

## Known limitation (as of this writing)

There is currently no end-user-facing UI in Faro for toggling *which*
segments/lifecycles a user wants notifications for — the preference-storage
REST API (`PreferencesFaroController`) and the scheduled email job exist and
are fully wired, but nothing in the Faro frontend calls them yet. Until that
UI ships, preferences can only be set via direct REST calls.
