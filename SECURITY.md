# Security Policy

Wicketstuff is a community project of add-on modules for Apache Wicket. It is
not an Apache Software Foundation project, and it is maintained by volunteers.

## Reporting a Vulnerability

**Please do not report security vulnerabilities through GitHub issues, GitHub
discussions or pull requests.** Doing so discloses the issue publicly before a
fix is available.

Report suspected vulnerabilities privately through GitHub's private
vulnerability reporting: open the
[Security tab](https://github.com/wicketstuff/core/security) of this repository
and choose **Report a vulnerability**.

A useful report includes:

- the affected Wicketstuff version(s) and the module (e.g. `wicketstuff-select2`),
- the affected class and method, ideally with a source reference,
- a concrete description of how an attacker reaches the code, including what
  the attacker is assumed to control,
- the impact you believe follows from that, and
- a reproducer where possible — a failing test is ideal.

We ask reporters to keep the issue confidential until a fixed release is
published. We will keep you informed of our assessment and of the release
timeline, and credit you in the advisory unless you ask us not to. Because the
project is run by volunteers, we cannot commit to fixed response times.

### Vulnerabilities in Apache Wicket itself

If the problem lies in Apache Wicket rather than in a Wicketstuff module, report
it to the Apache Wicket project instead, as described in its
[security policy](https://github.com/apache/wicket/blob/master/SECURITY.md).
If you are unsure which project the problem belongs to, report it here and we
will forward it.

### Vulnerabilities in third-party libraries

Several modules wrap or bundle third-party libraries, such as JavaScript
libraries delivered as WebJars. A vulnerability in such a library belongs with
its upstream project. If a Wicketstuff release ships a version with a published
vulnerability, a public issue or pull request that upgrades the dependency is
fine, since the vulnerability is already public. Use the private channel when
the way a Wicketstuff module uses the library makes it exploitable in a way that
is not already public.

## Supported Versions

Wicketstuff versions follow Apache Wicket's major versions: Wicketstuff 11.x
targets Wicket 11.x, and so on. Security fixes are applied to the release lines
below, each maintained on its `wicket-<major>.x` branch.

| Version | Supported                 |
| ------- | ------------------------- |
| 11.x    | :white_check_mark:        |
| 10.x    | :white_check_mark: (LTS)  |
| 9.x     | :x:                       |
| < 9.0   | :x:                       |

If you are running an unsupported version, the fix is to upgrade.

## Scope

### A finding must be verified against a supported branch tip

Verify the issue against the current tip of a supported line: the `wicket-11.x`
or `wicket-10.x` branch. Say which one, and name the commit you tested.
Reporting only the release you happen to be running is not sufficient: a
released artifact is always behind its branch, and what you found may already be
fixed there. A branch existing in this repository is not a statement that the
line is supported; the table above is.

### A report must refer to code that exists

We do not assess a report whose subject cannot be found in the codebase: a
class, method, setting or file that does not exist on the branch the report
names. Quote the code you are describing, from the branch you are targeting, and
give paths as they appear in the repository. How the report was produced does
not matter to us, by hand, with tooling or with a model. Accuracy does.

### Example modules are sample code

The `*-examples` modules exist to demonstrate a module's features in as few
lines as possible. They are not written to production standards and are not a
security reference. Problems in them are welcome, because example code gets
copied, but they are fixed on the development branch only, receive no CVE and
can be reported publicly.

## Reporting Something That Is Not a Vulnerability

Findings that are real but not vulnerabilities are still welcome. Please raise
them publicly as a [GitHub issue](https://github.com/wicketstuff/core/issues) or
pull request rather than through the private channel. Hardening suggestions,
defence-in-depth improvements and clarifications to this document all fall into
that category.

If you are unsure which channel applies, use the private one. We would rather
receive a non-issue privately than a real issue publicly.
