# Agent instructions

Wicket Stuff is a multi-module Maven project of add-on modules for Apache Wicket. Follow the developer documentation instead of guessing conventions:

* [docs/index.md](docs/index.md): overview of the developer docs
* [docs/building.md](docs/building.md): building and testing
* [docs/code-style.md](docs/code-style.md): code formatting and test naming
* [docs/git-workflow.md](docs/git-workflow.md): branches and pull requests
* [docs/release-process.md](docs/release-process.md): making a release

## Security

Read [SECURITY.md](SECURITY.md) before acting on a suspected vulnerability.

* Never file or describe a suspected vulnerability in a public issue, pull request, commit message or pull request description. It goes to [GitHub private vulnerability reporting](https://github.com/wicketstuff/core/security), as SECURITY.md describes.
* A report must refer to code that exists on the tip of a supported branch (`wicket-11.x` or `wicket-10.x`). Check out that branch, quote the code from it and name the commit. Do not report classes, methods, settings or files you have not found there, and do not report from a released artifact alone.
* Vulnerabilities in Apache Wicket itself go to the Apache Wicket project. Example modules (`*-examples`) are out of scope and can be handled publicly.
